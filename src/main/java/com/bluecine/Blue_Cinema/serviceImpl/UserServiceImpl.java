package com.bluecine.Blue_Cinema.serviceImpl;

import java.util.Optional;

import com.bluecine.Blue_Cinema.entity.User;
import com.bluecine.Blue_Cinema.repository.UserRepository;
import com.bluecine.Blue_Cinema.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public Iterable<User> findAll() {        
        return userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<User>findAll(Pageable pageable){
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        
        return userRepository.findById(id);
    }

    @Override
    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        userRepository.deleteById(id);       
    }

    @Override
    @Transactional
    public Optional<User> findByEmail(String email) {
       return userRepository.findByEmail(email);
        
    }

    @Override
    @Transactional
    public boolean existsByEmail(String email){
        return userRepository.existsByEmail(email);

    }

    @Override
    @Transactional
    public boolean existsByDocumentNumber(long documentNumber){
        return userRepository.existsByDocumentNumber(documentNumber);
    }
    
}
