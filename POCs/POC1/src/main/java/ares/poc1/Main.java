package ares.poc1;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    private static final int MAX_ENERGY = 5;
    private static final int SHOOT_RANGE = 10;

    public static void main(String[] args) throws IOException {
        Path file = Path.of("poc1-init.json");
        Gson gson = new GsonBuilder().serializeNulls().create();
        GameState state;

        try (Reader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            state = gson.fromJson(reader, GameState.class);
        }

        System.err.println("Etat initial charge : " + state.getRobots().size() + " robots");

        BufferedReader input = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
        String line;
        int sequence = 1;

        while ((line = input.readLine()) != null) {
            Action action;

            try {
                action = gson.fromJson(line, Action.class);
            } catch (JsonParseException e) {
                System.err.println("Ligne invalide : JSON incorrect");
                continue;
            }

            if (action == null || action.getRobotId() == null || action.getAction() == null) {
                System.err.println("Ligne invalide : robot_id ou action manquant");
                continue;
            }

            Robot robot = null;
            for (Robot current : state.getRobots()) {
                if (current.getId().equals(action.getRobotId())) {
                    robot = current;
                    break;
                }
            }

            if (robot == null) {
                System.err.println("Robot inconnu : " + action.getRobotId());
                continue;
            }

            if (action.getAction().equals("WAIT")) {
                robot.setEnergy(MAX_ENERGY);

                JsonObject event = new JsonObject();
                event.addProperty("seq", sequence);
                sequence++;
                event.addProperty("type", "ROBOT_WAITED");
                event.addProperty("robot_id", robot.getId());
                event.addProperty("energy", robot.getEnergy());
                System.out.println(gson.toJson(event));
            } else if (action.getAction().equals("MOVE")) {
                if (action.getAngle() == null || !Double.isFinite(action.getAngle())) {
                    System.err.println("Ligne invalide : angle manquant ou incorrect");
                    continue;
                }

                JsonObject event = new JsonObject();
                event.addProperty("seq", sequence);
                sequence++;
                event.addProperty("robot_id", robot.getId());

                if (robot.getHp() <= 0) {
                    event.addProperty("type", "ACTION_REJECTED");
                    event.addProperty("reason", "ROBOT_DEAD");
                } else if (robot.getEnergy() <= 0) {
                    event.addProperty("type", "ACTION_REJECTED");
                    event.addProperty("reason", "NO_ENERGY");
                } else {
                    double x = robot.getX() + state.getMoveSpeed() * Math.cos(action.getAngle());
                    double y = robot.getY() + state.getMoveSpeed() * Math.sin(action.getAngle());

                    x = Math.max(0, Math.min(x, state.getWorld().getW()));
                    y = Math.max(0, Math.min(y, state.getWorld().getH()));

                    robot.setX(x);
                    robot.setY(y);
                    robot.setHeading(action.getAngle());
                    robot.setEnergy(robot.getEnergy() - 1);

                    event.addProperty("type", "ROBOT_MOVED");
                    event.addProperty("x", robot.getX());
                    event.addProperty("y", robot.getY());
                    event.addProperty("heading", robot.getHeading());
                    event.addProperty("energy", robot.getEnergy());
                }

                System.out.println(gson.toJson(event));
            } else if (action.getAction().equals("MINE")) {
                JsonObject event = new JsonObject();
                event.addProperty("seq", sequence);
                sequence++;
                event.addProperty("type", "ACTION_REJECTED");
                event.addProperty("robot_id", robot.getId());

                if (robot.getHp() <= 0) {
                    event.addProperty("reason", "ROBOT_DEAD");
                } else if (robot.getEnergy() <= 0) {
                    event.addProperty("reason", "NO_ENERGY");
                } else {
                    robot.setEnergy(robot.getEnergy() - 1);
                    event.addProperty("reason", "NO_CRYSTAL");
                    event.addProperty("energy", robot.getEnergy());
                }

                System.out.println(gson.toJson(event));
            } else if (action.getAction().equals("SHOOT")) {
                if (action.getAngle() == null || !Double.isFinite(action.getAngle())) {
                    System.err.println("Ligne invalide : angle manquant ou incorrect");
                    continue;
                }

                JsonObject event = new JsonObject();
                event.addProperty("seq", sequence);
                sequence++;
                event.addProperty("robot_id", robot.getId());
                Robot target = null;

                if (robot.getHp() <= 0) {
                    event.addProperty("type", "ACTION_REJECTED");
                    event.addProperty("reason", "ROBOT_DEAD");
                } else if (robot.getEnergy() <= 0) {
                    event.addProperty("type", "ACTION_REJECTED");
                    event.addProperty("reason", "NO_ENERGY");
                } else {
                    robot.setEnergy(robot.getEnergy() - 1);
                    double shotX = robot.getX() + SHOOT_RANGE * Math.cos(action.getAngle());
                    double shotY = robot.getY() + SHOOT_RANGE * Math.sin(action.getAngle());

                    for (Robot current : state.getRobots()) {
                        if (current != robot && current.getHp() > 0
                                && current.getX() == shotX && current.getY() == shotY) {
                            target = current;
                            break;
                        }
                    }

                    event.addProperty("type", "ROBOT_SHOT");
                    event.addProperty("energy", robot.getEnergy());
                    if (target == null) {
                        event.add("hit", JsonNull.INSTANCE);
                    } else {
                        event.addProperty("hit", target.getId());
                    }
                }

                System.out.println(gson.toJson(event));

                if (target != null) {
                    target.setHp(target.getHp() - 1);

                    JsonObject damageEvent = new JsonObject();
                    damageEvent.addProperty("seq", sequence);
                    sequence++;
                    damageEvent.addProperty("type", "ROBOT_DAMAGED");
                    damageEvent.addProperty("robot_id", target.getId());
                    damageEvent.addProperty("hp", target.getHp());
                    System.out.println(gson.toJson(damageEvent));
                }
            } else {
                System.err.println("Action non prise en charge : " + action.getAction());
            }
        }
    }
}
