package com.planningmap.dto.place;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.planningmap.dto.CoordinatesDto;
import lombok.Data;

import java.io.Serializable;

@Data
public class PlacePostDto implements Serializable {

    @JsonProperty("name")
    private String name;

    @JsonProperty("address")
    private String address;

    @JsonProperty("coordinates")
    private CoordinatesDto coordinates;
}
