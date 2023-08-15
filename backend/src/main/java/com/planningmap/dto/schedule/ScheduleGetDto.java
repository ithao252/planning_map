package com.planningmap.dto.schedule;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.planningmap.dto.PlaceNoteDto;
import com.planningmap.dto.StopDto;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class ScheduleGetDto {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("createAt")
    private Instant createAt;

    @JsonProperty("placeNotes")
    private List<PlaceNoteDto> placeNotes;

    @JsonProperty("stops")
    private List<StopDto> stops;

}
