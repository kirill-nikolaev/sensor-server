package com.example.SensorRestAPI.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "sensor")
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;

    @NotEmpty(message = "Sensor name should be not empty")
    @Size(min = 3, message = "Name length should be more than 2 characters")
    @Size(max = 30, message = "Name length should be less than 30 characters")
    @Column(name = "name")
    String name;

    @OneToMany(mappedBy = "sensor")
    List<Measurement> measurements;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Measurement> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(List<Measurement> measurements) {
        this.measurements = measurements;
    }
}
