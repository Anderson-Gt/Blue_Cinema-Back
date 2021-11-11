package com.bluecine.Blue_Cinema.serviceImpl;

import java.util.Optional;

import com.bluecine.Blue_Cinema.entity.Reserve;
import com.bluecine.Blue_Cinema.repository.ReserveRepository;
import com.bluecine.Blue_Cinema.service.ReserveService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReserveServiceImpl implements ReserveService {

    @Autowired
    private ReserveRepository reserveRepository;
    

    @Override
    @Transactional(readOnly = true)
    public Iterable<Reserve> findAll() {        
        return reserveRepository.findAll();
    }

    
    @Override
    @Transactional(readOnly = true)
    public Optional<Reserve> findById(Long id) {
        return reserveRepository.findById(id);
    }

    @Override
    @Transactional
    public Reserve save(Reserve reserve) {
        return reserveRepository.save(reserve);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        reserveRepository.deleteById(id);        
    }
    
}
