package com.bluecine.Blue_Cinema.service;

import java.util.Optional;

import com.bluecine.Blue_Cinema.entity.Movie;

public interface MovieService {

    public Iterable<Movie>findAll();

    public Optional<Movie>findById(Long Id);

    //public Optional<Movie>findByBillboard(Boolean billboard);

    public Movie save(Movie movie);

    public void deleteById(Long Id);
    
}
