package com.planningmap.service;


import com.planningmap.model.User;

public interface UserService {
    User getUser();
    User getUserFromUsername(String username);
}
