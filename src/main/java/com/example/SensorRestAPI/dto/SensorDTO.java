package com.example.SensorRestAPI.dto;


import com.example.SensorRestAPI.models.Measurement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class SensorDTO {
    @NotEmpty(message = "Sensor name should be not empty")
    @Size(min = 3, message = "Name length should be more than 2 characters")
    @Size(max = 30, message = "Name length should be less than 30 characters")
    @Column(name = "name")
    String name;
}
