package com.planningmap.repository;


import com.planningmap.model.Role;
import com.planningmap.model.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(RoleEnum roleEnum);
    Boolean existsByName(RoleEnum roleEnum);
}
