package ares.poc1;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class GameState {
    private World world;

    @SerializedName("move_speed")
    private double moveSpeed;

    private List<Robot> robots;

    public World getWorld() {
        return world;
    }

    public double getMoveSpeed() {
        return moveSpeed;
    }

    public List<Robot> getRobots() {
        return robots;
    }
}
