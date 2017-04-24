package com.luca.flavien.wineyardmanager.DB.Object;

/**
 * Created by Flavien on 24.04.2017.
 */

public class Job {
    private int id;
    private WineLot winelot;
    private Worker worker;
    private String description;
    private String deadline;

    public WineLot getWinelot() {
        return winelot;
    }

    public void setWinelot(WineLot wineLot) {
        this.winelot = wineLot;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }
}
