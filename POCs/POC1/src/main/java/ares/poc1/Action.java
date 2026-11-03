package ares.poc1;

import com.google.gson.annotations.SerializedName;

public class Action {
    @SerializedName("robot_id")
    private String robotId;

    private String action;
    private Double angle;
    private Double x;
    private Double y;

    public String getRobotId() {
        return robotId;
    }

    public String getAction() {
        return action;
    }

    public Double getAngle() {
        return angle;
    }

    public Double getX() {
        return x;
    }

    public Double getY() {
        return y;
    }
}
