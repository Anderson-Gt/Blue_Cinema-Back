package com.bluecine.Blue_Cinema.repository;

import java.util.Optional;

import com.bluecine.Blue_Cinema.entity.Role;
import com.bluecine.Blue_Cinema.enums.RoleName;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
        Optional<Role> findByRoleName(RoleName roleName);  
}
