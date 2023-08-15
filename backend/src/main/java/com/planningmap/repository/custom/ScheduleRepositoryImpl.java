package com.planningmap.repository.custom;


import com.planningmap.model.QSchedule;
import com.planningmap.model.QStop;
import com.planningmap.model.Schedule;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.Optional;

public class ScheduleRepositoryImpl implements ScheduleRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Schedule> findByIdSortStopOrder(Integer id) {
        return Optional.ofNullable(
                    new JPAQuery<Schedule>(em)
                    .from(QSchedule.schedule)
                    .leftJoin(QSchedule.schedule.stops, QStop.stop)
                    .fetchJoin()
                    .where(QSchedule.schedule.id.eq(id))
                    .orderBy(QStop.stop.stopOrder.asc())
                    .fetchFirst());
    }

}
