package com.planningmap.service.impl;


import com.planningmap.exception.CoreApiException;
import com.planningmap.model.Role;
import com.planningmap.model.RoleEnum;
import com.planningmap.model.User;
import com.planningmap.repository.RoleRepository;
import com.planningmap.repository.UserRepository;
import com.planningmap.security.JwtUtils;
import com.planningmap.security.UserDetailsImpl;
import com.planningmap.service.AuthService;
import com.planningmap.support.UserDetailsWithJwt;
import com.planningmap.support.error.ErrorType;
import com.planningmap.support.error.ExitCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public UserDetailsWithJwt login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                user.getUsername(), user.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return new UserDetailsWithJwt(jwt, userDetails);
    }

    @Override
    public void signup(User user) {
        String username = user.getUsername();
        String password = user.getPassword();

        if (userRepository.existsByUsername(username)){
            ErrorType existedUser = new ErrorType(
                    HttpStatus.CONFLICT,
                    ExitCode.E2000,
                    "Existed user with username = " + username);
            throw new CoreApiException(existedUser);
        }

        User encodedUser = new User();
        encodedUser.setUsername(username);
        encodedUser.setPassword(passwordEncoder.encode(password));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(RoleEnum.ROLE_USER);
        roles.add(userRole);
        encodedUser.setRoles(roles);

        userRepository.save(encodedUser);
    }
}
