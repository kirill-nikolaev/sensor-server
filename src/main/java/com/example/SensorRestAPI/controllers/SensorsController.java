package com.example.SensorRestAPI.controllers;

import com.example.SensorRestAPI.dto.SensorDTO;
import com.example.SensorRestAPI.models.Sensor;
import com.example.SensorRestAPI.services.SensorsService;
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

@RestController
@RequestMapping("/sensors")
public class SensorsController {

    private SensorsService sensorsService;
    private ModelMapper modelMapper;
    private SensorDTOValidator sensorDTOValidator;

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
            FieldError error = bindingResult.getFieldError();
            String message = error.getDefaultMessage();
            throw new NotValidSensorNameException(message);
        }

        Sensor sensor = convertToSensor(sensorDTO);

        sensorsService.save(sensor);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<HashMap<String, String>> exceptionHandler(NotValidSensorNameException e) {
        HashMap<String, String> body = new HashMap<>();
        body.put("message", e.getMessage());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    private Sensor convertToSensor(SensorDTO sensorDTO) {
        return modelMapper.map(sensorDTO, Sensor.class);
    }
}
