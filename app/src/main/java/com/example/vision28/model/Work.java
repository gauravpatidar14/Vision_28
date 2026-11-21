package com.example.vision28.model;

public class Work {
    private String name;
    private String time;

    public Work(String name, String time) {
        this.name = name;
        this.time = time;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return this.name;
    }

    public String getTime() {
        return this.time;
    }
}
