package com.bluecine.Blue_Cinema.serviceImpl;

import java.util.Optional;

import com.bluecine.Blue_Cinema.entity.Chair;
import com.bluecine.Blue_Cinema.repository.ChairRepository;
import com.bluecine.Blue_Cinema.service.ChairService;

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
    public Optional<Chair> findById(Integer Id) {
        return chairRepository.findById(Id);
    }

    @Override
    @Transactional
    public Chair save(Chair chair) {
        return chairRepository.save(chair);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        chairRepository.deleteById(id);        
    }
    
}
