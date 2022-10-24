package com.example.SensorRestAPI.controllers;


import com.example.SensorRestAPI.dto.MeasurementDTO;
import com.example.SensorRestAPI.models.Measurement;
import com.example.SensorRestAPI.services.MeasurementsService;
import com.example.SensorRestAPI.util.ErrorMessage;
import com.example.SensorRestAPI.util.MeasurementDTOValidator;
import com.example.SensorRestAPI.util.NotValidMeasurementException;
import com.example.SensorRestAPI.util.RainyDaysCount;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/measurements")
public class MeasurementsController {

    private final MeasurementDTOValidator measurementDTOValidator;
    private final ModelMapper modelMapper;
    private final MeasurementsService measurementsService;

    @Autowired
    public MeasurementsController(MeasurementDTOValidator measurementDTOValidator,
                                  ModelMapper modelMapper, MeasurementsService measurementsService) {
        this.measurementDTOValidator = measurementDTOValidator;
        this.modelMapper = modelMapper;
        this.measurementsService = measurementsService;
    }


    @PostMapping("/add")
    private ResponseEntity<HttpStatus> addMeasurement(@RequestBody @Valid MeasurementDTO measurementDTO,
                                                  BindingResult bindingResult) {

        measurementDTOValidator.validate(measurementDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            StringBuilder message = new StringBuilder();

            for (FieldError fieldError: fieldErrors) {
                message.append(fieldError.getDefaultMessage()).append("&");
            }
            message.deleteCharAt(message.length() - 1);
            throw new NotValidMeasurementException(message.toString());
        }

        Measurement measurement = convertToMeasurement(measurementDTO);
        measurementsService.save(measurement);
        return ResponseEntity.ok(HttpStatus.OK);
    }


    @GetMapping
    public List<MeasurementDTO> getAllMeasurements() {
        List<Measurement> measurements = measurementsService.findAll();
        return convertToMeasurementDTOList(measurements);
    }

    @GetMapping("/rainyDaysCount")
    public RainyDaysCount getRainyDaysCount() {
        return new RainyDaysCount(measurementsService.getRainyDaysCount());
    }

    @ExceptionHandler
    private ResponseEntity<ErrorMessage> exceptionHandler(NotValidMeasurementException e) {
        ErrorMessage errorMessage = new ErrorMessage(e.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    private Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
        return modelMapper.map(measurementDTO, Measurement.class);
    }

    private MeasurementDTO convertToMeasurementDTO(Measurement measurement) {
        return modelMapper.map(measurement, MeasurementDTO.class);
    }

    private List<MeasurementDTO> convertToMeasurementDTOList(List<Measurement> measurements) {
        return measurements.stream()
                .map(this::convertToMeasurementDTO)
                .collect(Collectors.toList());
    }
}
