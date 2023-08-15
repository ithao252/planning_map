package com.planningmap.controller;


import com.planningmap.dto.auth.JwtDto;
import com.planningmap.dto.auth.UserGetDto;
import com.planningmap.dto.auth.UserPostDto;
import com.planningmap.model.User;
import com.planningmap.security.UserDetailsImpl;
import com.planningmap.service.AuthService;
import com.planningmap.service.UserService;
import com.planningmap.support.UserDetailsWithJwt;
import com.planningmap.support.response.ApiResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    AuthService authService;

    @Autowired
    UserService userService;

    @Autowired
    ModelMapper modelMapper;

    @PostMapping("/login")
    public ApiResponse<JwtDto> login(@RequestBody UserPostDto userPostDto) {

        User loggingUser = modelMapper.map(userPostDto, User.class);
        UserDetailsWithJwt userDetailsWithJwt = authService.login(loggingUser);

        UserDetailsImpl userDetails = userDetailsWithJwt.getUserDetails();
        String token = userDetailsWithJwt.getJwt();

        User loggedUser = userService.getUserFromUsername(userDetails.getUsername());

        UserGetDto userGetDto = modelMapper.map(loggedUser, UserGetDto.class);
        JwtDto jwtDto = new JwtDto(token, userGetDto);
        return ApiResponse.success(jwtDto);
    }

    @PostMapping("/signup")
    public ApiResponse<String> signup(@RequestBody UserPostDto userPostDto) {
        User signingUser = modelMapper.map(userPostDto, User.class);
        authService.signup(signingUser);
        return ApiResponse.success("Signup successfully");
    }
}
