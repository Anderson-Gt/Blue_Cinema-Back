package com.bluecine.Blue_Cinema.repository;

import java.util.Optional;

import com.bluecine.Blue_Cinema.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{
    Optional <User> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByDocumentNumber(long documentNumber);
    
}

