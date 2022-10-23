package com.example.SensorRestAPI.repositories;

import com.example.SensorRestAPI.models.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeasurementsRepository extends JpaRepository<Measurement, Integer> {

    List<Measurement> findByRainingEquals(Boolean b);
}
