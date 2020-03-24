package ru.vasiliev.retrofit.entity;

import com.google.gson.annotations.Expose;

public class Wind {

    @Expose
    private Long deg;
    @Expose
    private Long speed;

    public Long getDeg() {
        return deg;
    }

    public void setDeg(Long deg) {
        this.deg = deg;
    }

    public Long getSpeed() {
        return speed;
    }

    public void setSpeed(Long speed) {
        this.speed = speed;
    }

}
