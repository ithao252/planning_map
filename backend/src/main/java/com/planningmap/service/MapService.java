package com.planningmap.service;



import com.planningmap.model.Coordinates;

import java.util.List;

public interface MapService {

    Coordinates searchCoordinate(String searchText);
    List<Coordinates> routeDirection(List<Coordinates> coordinates);
}
