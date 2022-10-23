package com.example.SensorRestAPI.services;

import com.example.SensorRestAPI.models.Measurement;
import com.example.SensorRestAPI.models.Sensor;
import com.example.SensorRestAPI.repositories.MeasurementsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class MeasurementsService {

    private final MeasurementsRepository measurementsRepository;
    private final SensorsService sensorsService;


    @Autowired
    public MeasurementsService(MeasurementsRepository measurementsRepository, SensorsService sensorsService) {
        this.measurementsRepository = measurementsRepository;
        this.sensorsService = sensorsService;
    }

    @Transactional
    public void save(Measurement measurement) {
        Sensor sensor = sensorsService.findByName(measurement.getSensor().getName()).stream()
                .findAny().orElse(null);

        measurement.setSensor(sensor);
        measurement.setRegisteredAt(LocalDateTime.now());
        measurementsRepository.save(measurement);
    }

    public int getRainyDaysCount() {
        List<Measurement> measurementList = measurementsRepository.findByRainingEquals(true);
        return measurementList.stream().map(x -> x.getRegisteredAt().toLocalDate()).collect(Collectors.toSet()).size();
    }

    public List<Measurement> findAll () {
        return measurementsRepository.findAll();
    }
}
