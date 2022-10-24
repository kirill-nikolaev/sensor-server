package com.example.SensorRestAPI.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "sensor")
@Getter
@Setter
@NoArgsConstructor
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
}
