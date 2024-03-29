package com.planningmap.controller;


import com.planningmap.dto.schedule.ScheduleGetDto;
import com.planningmap.dto.schedule.ScheduleListDto;
import com.planningmap.dto.schedule.SchedulePostDto;
import com.planningmap.model.Schedule;
import com.planningmap.service.ScheduleService;
import com.planningmap.support.response.ApiResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ScheduleController {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ScheduleService scheduleService;

    @PostMapping("/schedules")
    public ApiResponse<ScheduleGetDto> addSchedule(@RequestBody SchedulePostDto scheduleDto) {
        Schedule schedule = modelMapper.map(scheduleDto, Schedule.class);
        Schedule result = scheduleService.addSchedule(schedule);
        ScheduleGetDto scheduleGetDto = modelMapper.map(result, ScheduleGetDto.class);
        return ApiResponse.success(scheduleGetDto);
    }

    @GetMapping("/schedules")
    public ApiResponse<List<ScheduleListDto>> getListSchedule() {
        List<Schedule> schedules = scheduleService.getListSchedule();
        List<ScheduleListDto> scheduleListDto = schedules.stream().map(
            element -> modelMapper.map(element, ScheduleListDto.class)
        ).collect(Collectors.toList());
        return ApiResponse.success(scheduleListDto);
    }


    @GetMapping("/schedules/{id}")
    public ApiResponse<ScheduleGetDto> getById(@PathVariable Integer id) {
        Schedule schedules = scheduleService.getById(id);
        ScheduleGetDto scheduleGetDto = modelMapper.map(schedules, ScheduleGetDto.class);
        return ApiResponse.success(scheduleGetDto);
    }

    @DeleteMapping("/schedules/{id}")
    public ApiResponse<?> deleteById(@PathVariable Integer id) {
        scheduleService.deleteById(id);
        return ApiResponse.success();
    }

}
