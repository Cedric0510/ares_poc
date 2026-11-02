package ares.poc1;

import com.google.gson.Gson;
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

    public static void main(String[] args) throws IOException {
        Path file = Path.of("poc1-init.json");
        Gson gson = new Gson();
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
            } else {
                System.err.println("Action non prise en charge : " + action.getAction());
            }
        }
    }
}
