package com.planningmap.service.impl;


import com.planningmap.exception.CoreApiException;
import com.planningmap.model.Coordinates;
import com.planningmap.model.Schedule;
import com.planningmap.model.Stop;
import com.planningmap.model.User;
import com.planningmap.repository.PlaceNoteRepository;
import com.planningmap.repository.ScheduleRepository;
import com.planningmap.repository.StopRepository;
import com.planningmap.repository.UserRepository;
import com.planningmap.service.MapService;
import com.planningmap.service.ScheduleService;
import com.planningmap.service.UserService;
import com.planningmap.support.error.ErrorType;
import com.planningmap.support.error.ExitCode;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    PlaceNoteRepository placeNoteRepository;

    @Autowired
    StopRepository stopRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MapService mapService;

    @Autowired
    UserService userService;

    @Override
    @Transactional
    public Schedule addSchedule(Schedule schedule) {
        List<Coordinates> coordinatesList = schedule.getPlaceNotes().stream().map(
                element -> element.getPlace().getCoordinates()
        ).collect(Collectors.toList());
        List<Coordinates> routeCoordinates = mapService.routeDirection(coordinatesList);

        // User
        User user = userService.getUser();

        // Schedule
        Schedule inserted = scheduleRepository.save(schedule);
        inserted.setUser(user);

        // Place Note
        inserted.getPlaceNotes().forEach(
                element -> element.setSchedule(inserted)
        );
        placeNoteRepository.saveAll(inserted.getPlaceNotes());

        // Stops
        List<Stop> stops = new ArrayList<>();
        int order = 1;
        for (Coordinates cor: routeCoordinates) {
            Stop stop = new Stop();
            stop.setCoordinates(cor);
            stop.setStopOrder(order++);
            stop.setSchedule(inserted);
            stops.add(stop);
        }
        stopRepository.saveAll(stops);

        return inserted;
    }

    @Override
    @PostFilter("filterObject.user.id == authentication.principal.id")
    public List<Schedule> getListSchedule() {
        return scheduleRepository.findAll();
    }

    @Override
    @PostAuthorize("returnObject.user.id == authentication.principal.id")
    public Schedule getById(Integer id) {
        ErrorType notFound = new ErrorType(
                HttpStatus.NOT_FOUND,
                ExitCode.E404,
                "Cannot find schedule with id = " + id);
        return scheduleRepository.findByIdSortStopOrder(id)
                .orElseThrow(() -> new CoreApiException(notFound));
    }

    @Override
    public void deleteById(Integer id) {
        Schedule schedule = this.getById(id);
        User user = userService.getUser();
        if (schedule.getUser() != user) {
            throw new AccessDeniedException("Access denied");
        }
        scheduleRepository.deleteById(id);
    }
}
