package com.planningmap.service;


import com.planningmap.model.User;
import com.planningmap.support.UserDetailsWithJwt;

public interface AuthService {
    UserDetailsWithJwt login(User user);
    void signup(User user);
}
