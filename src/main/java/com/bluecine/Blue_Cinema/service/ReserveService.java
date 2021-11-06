package com.bluecine.Blue_Cinema.service;

import java.util.Optional;

import com.bluecine.Blue_Cinema.entity.Reserve;

public interface ReserveService {

    public Iterable<Reserve>findAll();

    public Optional<Reserve>findById(Long Id);

    //public Optional<Movie>findByBillboard(Boolean billboard);

    public Reserve save(Reserve reserve);

    public void deleteById(Long Id);
    
}
