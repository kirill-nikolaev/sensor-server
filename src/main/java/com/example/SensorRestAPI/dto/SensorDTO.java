package com.example.SensorRestAPI.dto;


import com.example.SensorRestAPI.models.Measurement;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


public class SensorDTO {
    @NotEmpty(message = "Sensor name should be not empty")
    @Size(min = 3, message = "Name length should be more than 2 characters")
    @Size(max = 30, message = "Name length should be less than 30 characters")
    @Column(name = "name")
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
