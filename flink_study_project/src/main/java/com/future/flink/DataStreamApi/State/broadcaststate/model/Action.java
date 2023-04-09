package com.future.flink.DataStreamApi.State.broadcaststate.model;

public class Action {

    public Long userId;
    public String action;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
