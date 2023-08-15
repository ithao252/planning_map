package com.planningmap.repository;


import com.planningmap.model.PlaceNote;
import com.planningmap.model.PlaceScheduleKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceNoteRepository extends JpaRepository<PlaceNote, PlaceScheduleKey> {
}
