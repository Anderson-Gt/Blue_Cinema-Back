package com.bluecine.Blue_Cinema.serviceImpl;

import java.util.Optional;

import com.bluecine.Blue_Cinema.entity.Role;
import com.bluecine.Blue_Cinema.enums.RoleName;
import com.bluecine.Blue_Cinema.repository.RoleRepository;
import com.bluecine.Blue_Cinema.service.RoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoleServiceImpl implements RoleService{

    @Autowired
    RoleRepository roleRepository;

    @Override
    public Optional<Role> getByRoleName(RoleName roleName) {
        return roleRepository.findByRoleName(roleName);        
    }

    @Override
    public void Save(Role role) {
        roleRepository.save(role);
       
        
    }
    
}
