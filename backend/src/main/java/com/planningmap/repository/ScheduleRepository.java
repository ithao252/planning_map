package com.planningmap.repository;


import com.planningmap.model.Schedule;
import com.planningmap.repository.custom.ScheduleRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer>, ScheduleRepositoryCustom {

}
