package com.example.SensorRestAPI.controllers;

import com.example.SensorRestAPI.dto.SensorDTO;
import com.example.SensorRestAPI.models.Sensor;
import com.example.SensorRestAPI.services.SensorsService;
import com.example.SensorRestAPI.util.ErrorMessage;
import com.example.SensorRestAPI.util.NotValidMeasurementException;
import com.example.SensorRestAPI.util.NotValidSensorNameException;
import com.example.SensorRestAPI.util.SensorDTOValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/sensors")
public class SensorsController {

    private final SensorsService sensorsService;
    private final ModelMapper modelMapper;
    private final SensorDTOValidator sensorDTOValidator;

    @Autowired
    public SensorsController(SensorsService sensorsService, ModelMapper modelMapper, SensorDTOValidator sensorDTOValidator) {
        this.sensorsService = sensorsService;
        this.modelMapper = modelMapper;
        this.sensorDTOValidator = sensorDTOValidator;
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> registerSensor(@RequestBody @Valid SensorDTO sensorDTO,
                                                  BindingResult bindingResult) {
        sensorDTOValidator.validate(sensorDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            StringBuilder message = new StringBuilder();

            for (FieldError fieldError: fieldErrors) {
                message.append(fieldError.getDefaultMessage()).append("&");
            }
            message.deleteCharAt(message.length() - 1);
            throw new NotValidSensorNameException(message.toString());
        }

        Sensor sensor = convertToSensor(sensorDTO);

        sensorsService.save(sensor);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> exceptionHandler(NotValidSensorNameException e) {
        ErrorMessage errorMessage = new ErrorMessage(e.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    private Sensor convertToSensor(SensorDTO sensorDTO) {
        return modelMapper.map(sensorDTO, Sensor.class);
    }
}
