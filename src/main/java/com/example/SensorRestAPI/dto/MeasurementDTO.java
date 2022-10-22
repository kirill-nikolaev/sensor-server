package com.example.SensorRestAPI.dto;

import org.hibernate.validator.constraints.Range;

public class MeasurementDTO {

    @Range(min = -100, max = 100, message = "The value must be greater than -100 and less than 100")
    private double value;

    private boolean raining;

    private SensorDTO sensorDTO;


    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public boolean isRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    public SensorDTO getSensorDTO() {
        return sensorDTO;
    }

    public void setSensorDTO(SensorDTO sensorDTO) {
        this.sensorDTO = sensorDTO;
    }
}
