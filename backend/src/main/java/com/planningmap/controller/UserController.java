package com.planningmap.controller;


import com.planningmap.dto.auth.UserGetDto;
import com.planningmap.model.User;
import com.planningmap.service.UserService;
import com.planningmap.support.response.ApiResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    ModelMapper modelMapper;

    @GetMapping("/user")
    ApiResponse<UserGetDto> getUser() {
        User user = userService.getUser();
        UserGetDto userGetDto = modelMapper.map(user, UserGetDto.class);
        return ApiResponse.success(userGetDto);
    }
}
