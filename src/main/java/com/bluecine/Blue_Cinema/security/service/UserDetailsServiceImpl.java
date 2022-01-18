package com.bluecine.Blue_Cinema.security.service;

import com.bluecine.Blue_Cinema.entity.User;
import com.bluecine.Blue_Cinema.security.entity.MainUser;
import com.bluecine.Blue_Cinema.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByEmail(username).get();
        return MainUser.build(user);
    }

    
}
