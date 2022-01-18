package com.bluecine.Blue_Cinema.service;

import java.util.Optional;

import com.bluecine.Blue_Cinema.entity.User;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;

public interface UserService {
    public Iterable<User>findAll();

    public Page<User>findAll(Pageable pageable);

    public Optional<User> findByEmail(String email);

    public Optional<User>findById(Long id);

    public User save(User user);

    public void deleteById(Long id);

    public boolean existsByEmail(String email);
    
    public boolean existsByDocumentNumber(long documentNumber);
    
    
}
