package com.planningmap.service;


import com.planningmap.model.Place;

import java.util.List;

public interface PlaceService {
    Place newPlace(Place place);
    List<Place> allPlace();
    Place onePlace(Integer id);
    void deleteById(Integer id);

}
