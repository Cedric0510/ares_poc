package ares.poc1;

import com.google.gson.annotations.SerializedName;

public class Action {
    @SerializedName("robot_id")
    private String robotId;

    private String action;

    public String getRobotId() {
        return robotId;
    }

    public String getAction() {
        return action;
    }
}
