package com.bluecine.Blue_Cinema.controller;

import java.util.HashSet;
import java.util.Set;

import com.bluecine.Blue_Cinema.dto.Message;
import com.bluecine.Blue_Cinema.dto.NewUserDto;
import com.bluecine.Blue_Cinema.entity.Role;
import com.bluecine.Blue_Cinema.entity.User;
import com.bluecine.Blue_Cinema.enums.RoleName;
import com.bluecine.Blue_Cinema.security.dto.JwtDto;
import com.bluecine.Blue_Cinema.security.dto.LoginUser;
import com.bluecine.Blue_Cinema.security.jwt.JwtProvider;
import com.bluecine.Blue_Cinema.service.RoleService;
import com.bluecine.Blue_Cinema.service.UserService;

import javax.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;
    
    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    JwtProvider jwtProvider;
    

    @PostMapping("/new")
    public ResponseEntity<?> create(@Valid @RequestBody NewUserDto newUserDto, BindingResult bindingResult){
        if(bindingResult.hasErrors())
        return new ResponseEntity(new Message("campos mal puestos o email inválido"), HttpStatus.BAD_REQUEST);

        if(userService.existsByEmail(newUserDto.getEmail()) || userService.existsByDocumentNumber(newUserDto.getDocumentNumber()))
        return new ResponseEntity(new Message("El usuario ya se encuentra registrado"), HttpStatus.BAD_REQUEST);

        User user = new User(newUserDto.getDocumentType(), newUserDto.getDocumentNumber(),newUserDto.getNames(), newUserDto.getSurnames(), newUserDto.getEmail(), passwordEncoder.encode(newUserDto.getPassword()));

        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getByRoleName(RoleName.ROLE_USER).get());
        user.setRoles(roles);
        userService.save(user);

        return new ResponseEntity(new Message("Usuario creado exitosamente"), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtDto> login(@Valid @RequestBody LoginUser loginUser, BindingResult bindingResult){
        if(bindingResult.hasErrors())
        return new ResponseEntity(new Message("campos mal puestos"), HttpStatus.BAD_REQUEST);

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getEmail(), loginUser.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        String jwt = jwtProvider.generateToken(authentication);
        JwtDto jwtDto = new JwtDto(jwt);

        return new ResponseEntity(jwtDto, HttpStatus.OK);
    }

}
