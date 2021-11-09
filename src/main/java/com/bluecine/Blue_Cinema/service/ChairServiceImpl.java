package com.bluecine.Blue_Cinema.service;

import java.util.Optional;

import com.bluecine.Blue_Cinema.entity.Chair;
import com.bluecine.Blue_Cinema.repository.ChairRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChairServiceImpl implements ChairService{
    
    @Autowired
    private ChairRepository chairRepository;
    

    @Override
    @Transactional(readOnly = true)
    public Iterable<Chair> findAll() {        
        return chairRepository.findAll();
    }

    
    @Override
    @Transactional(readOnly = true)
    public Optional<Chair> findById(Long id) {
        return chairRepository.findById(id);
    }

    @Override
    @Transactional
    public Chair save(Chair chair) {
        return chairRepository.save(chair);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        chairRepository.deleteById(id);        
    }
    
}
