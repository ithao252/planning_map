package com.planningmap.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.planningmap.dto.place.PlaceGetDto;
import lombok.Data;

@Data
public class PlaceNoteDto {

    @JsonProperty("place")
    private PlaceGetDto place;

    @JsonProperty("note")
    private String note;
}
