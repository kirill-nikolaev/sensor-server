package com.example.SensorRestAPI.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;


@Getter
@Setter
@NoArgsConstructor
public class MeasurementDTO {

    @Range(min = -100, max = 100, message = "Value must be greater than -100 and less than 100")
    private double value;

    private boolean raining;

    private SensorDTO sensor;
}
