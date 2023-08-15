package com.planningmap.support;


import com.planningmap.security.UserDetailsImpl;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDetailsWithJwt {

    private String jwt;
    private UserDetailsImpl userDetails;
}
