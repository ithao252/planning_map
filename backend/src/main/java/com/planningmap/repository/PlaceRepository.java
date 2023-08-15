package com.planningmap.repository;


import com.planningmap.model.Place;
import com.planningmap.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Integer> {
    List<Place> findAllByUser(User user);
}
