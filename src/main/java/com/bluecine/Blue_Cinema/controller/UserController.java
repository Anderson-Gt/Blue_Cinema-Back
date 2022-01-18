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







   /* //Create a new user
    @PostMapping
    public ResponseEntity<?> create(@RequestBody User user){
        Optional<User> oUser = userService.findById(user.getDocumentNumber());
        if(!oUser.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
        }
        return ResponseEntity.badRequest().build();
        
    }

    //Read an user
    //Hay que hacer una exception
    @GetMapping("/{id}")
    public ResponseEntity<?> read(@PathVariable(value = "id") Long userId){
        Optional<User> oUser = userService.findById(userId);
        
        if(!oUser.isPresent()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(oUser);
    }

    //Read an user by email
    @GetMapping("/findBy/{email}")
    public ResponseEntity<?> readByEmail(@PathVariable(value = "email") String email){
        User oUser = userService.findByEmail(email);
        if(oUser==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(oUser);
    }

    //update an user
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody User userDetails,@PathVariable(value="id") Long userId){
        Optional<User> user = userService.findById(userId);
        if(!user.isPresent()){
            return ResponseEntity.notFound().build();
        }

        user.get().setDocumentType(userDetails.getDocumentType());
        user.get().setDocumentNumber(userDetails.getDocumentNumber());
        user.get().setNames(userDetails.getNames());
        user.get().setSurnames(userDetails.getSurnames());
        user.get().setPassword(userDetails.getPassword());

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user.get()));
    }
        
    //delete an user
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long userId){
        if(!userService.findById(userId).isPresent()){
            return ResponseEntity.notFound().build();
        }
        userService.deleteById(userId);
        return ResponseEntity.ok().build();

    }

    //read all users
    @GetMapping
    public List<User>readAll(){
        List<User>users = StreamSupport
        .stream(userService.findAll().spliterator(), false)
        .collect(Collectors.toList());
        return users;
        
    }*/

}
