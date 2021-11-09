package com.bluecine.Blue_Cinema.service;

import java.util.Optional;

import com.bluecine.Blue_Cinema.entity.Chair;

public interface ChairService {

    public Iterable<Chair>findAll();

    public Optional<Chair>findById(Long Id);

    public Chair save(Chair chair);

    public void deleteById(Long Id);
    
}
