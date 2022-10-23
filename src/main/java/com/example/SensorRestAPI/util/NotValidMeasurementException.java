package com.example.SensorRestAPI.util;

public class NotValidMeasurementException extends RuntimeException{
    public NotValidMeasurementException(String message) {
        super(message);
    }
}
