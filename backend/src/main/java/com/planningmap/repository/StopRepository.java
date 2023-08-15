package com.planningmap.repository;


import com.planningmap.model.Stop;
import com.planningmap.model.StopKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StopRepository extends JpaRepository<Stop, StopKey> {
}
