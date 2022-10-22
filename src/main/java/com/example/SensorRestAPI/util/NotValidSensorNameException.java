package com.example.SensorRestAPI.util;

public class NotValidSensorNameException extends RuntimeException {

    public NotValidSensorNameException(String message) {
        super(message);
    }
}
