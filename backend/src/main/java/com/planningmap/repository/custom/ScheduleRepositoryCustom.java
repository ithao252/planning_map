package com.planningmap.repository.custom;


import com.planningmap.model.Schedule;

import java.util.Optional;

public interface ScheduleRepositoryCustom {

    Optional<Schedule> findByIdSortStopOrder(Integer id);

}
