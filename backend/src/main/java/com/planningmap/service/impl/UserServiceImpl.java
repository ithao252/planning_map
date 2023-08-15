package com.planningmap.service.impl;


import com.planningmap.exception.CoreApiException;
import com.planningmap.model.User;
import com.planningmap.repository.UserRepository;
import com.planningmap.security.UserDetailsImpl;
import com.planningmap.service.UserService;
import com.planningmap.support.error.ErrorType;
import com.planningmap.support.error.ExitCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    public User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        ErrorType notFoundUserError = new ErrorType(
                HttpStatus.NOT_FOUND,
                ExitCode.E2001,
                "Username not found"
        );
        return userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new CoreApiException(notFoundUserError));
    }

    public User getUserFromUsername(String username) {
        ErrorType notFoundUserError = new ErrorType(
                HttpStatus.NOT_FOUND,
                ExitCode.E2001,
                "Username not found"
        );
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new CoreApiException(notFoundUserError));
    }
}
