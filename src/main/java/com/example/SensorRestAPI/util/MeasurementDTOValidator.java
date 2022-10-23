package com.example.SensorRestAPI.util;

import com.example.SensorRestAPI.dto.MeasurementDTO;
import com.example.SensorRestAPI.models.Sensor;
import com.example.SensorRestAPI.services.SensorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;


@Component
public class MeasurementDTOValidator implements Validator {

    private final SensorsService sensorsService;

    @Autowired
    public MeasurementDTOValidator(SensorsService sensorsService) {
        this.sensorsService = sensorsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(MeasurementDTO.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MeasurementDTO measurementDTO = (MeasurementDTO) target;

        if (((MeasurementDTO) target).getSensor() == null) {
            errors.rejectValue("sensor", "" , "Sensor should not be empty");
            return;
        }

        Optional<Sensor> sensorFromDb = sensorsService.findByName(measurementDTO.getSensor().getName());

        if (sensorFromDb.isEmpty()) {
            errors.rejectValue("sensor", "" , "Sensor with this name not found");
        }
    }
}
