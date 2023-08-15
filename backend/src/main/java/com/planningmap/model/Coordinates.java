package com.planningmap.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Coordinates {
    private double lat;
    private double lng;
}
