package com.planningmap.dto.place;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.planningmap.dto.CoordinatesDto;
import lombok.Data;

@Data
public class PlaceGetDto {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("address")
    private String address;

    @JsonProperty("coordinates")
    private CoordinatesDto coordinates;
}
