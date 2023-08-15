package com.planningmap.service.impl;

import com.planningmap.exception.CoreApiException;
import com.planningmap.model.Place;
import com.planningmap.model.PlaceNote;
import com.planningmap.model.User;
import com.planningmap.repository.PlaceRepository;
import com.planningmap.repository.UserRepository;
import com.planningmap.service.PlaceService;
import com.planningmap.service.UserService;
import com.planningmap.support.error.ErrorType;
import com.planningmap.support.error.ExitCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaceServiceImpl implements PlaceService {

    @Autowired
    PlaceRepository placeRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Override
    public Place newPlace(Place place) {
        User user = userService.getUser();
        place.setUser(user);
        return placeRepository.save(place);
    }

    @Override
    @PostFilter("filterObject.user.id == authentication.principal.id")
    public List<Place> allPlace() {
        return placeRepository.findAll();
    }

    @Override
    @PostAuthorize("returnObject.user.id == authentication.principal.id")
    public Place onePlace(Integer id) {
        ErrorType notFound = new ErrorType(
                HttpStatus.NOT_FOUND,
                ExitCode.E404,
                "Cannot find place with id = " + id);
        return placeRepository.findById(id)
                .orElseThrow(() -> new CoreApiException(notFound));
    }

    @Override
    public void deleteById(Integer id) {
        Place place = this.onePlace(id);
        User user = userService.getUser();
        if (place.getUser() != user) {
            throw new AccessDeniedException("Access denied");
        }

        List<PlaceNote> placeNotes = place.getPlaceNotes();
        if (placeNotes.size()>0) {
            ErrorType involveException = new ErrorType(
                    HttpStatus.CONFLICT,
                    ExitCode.E1000,
                    "Place are related to some other resource");
            throw new CoreApiException(involveException);
        }
        placeRepository.deleteById(id);
    }
}
