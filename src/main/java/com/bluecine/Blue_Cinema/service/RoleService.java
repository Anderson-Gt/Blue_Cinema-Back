package com.bluecine.Blue_Cinema.service;

import java.util.Optional;

import com.bluecine.Blue_Cinema.entity.Role;
import com.bluecine.Blue_Cinema.enums.RoleName;

public interface RoleService {

    public Optional<Role> getByRoleName(RoleName roleName);

    public void Save(Role role);
        
}
