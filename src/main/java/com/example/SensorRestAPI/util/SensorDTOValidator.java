package com.example.SensorRestAPI.util;

import com.example.SensorRestAPI.dto.SensorDTO;
import com.example.SensorRestAPI.models.Sensor;
import com.example.SensorRestAPI.services.SensorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;


@Component
public class SensorDTOValidator implements Validator {

    private SensorsService sensorsService;

    @Autowired
    public SensorDTOValidator(SensorsService sensorsService) {
        this.sensorsService = sensorsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(SensorDTO.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SensorDTO sensor = (SensorDTO) target;
        Optional<Sensor> sensorFromDb = sensorsService.findByName(sensor.getName());

        if (sensorFromDb.isPresent()) {
            errors.rejectValue("name", "", "Sensor with the same name already exists");
        }
    }
}
