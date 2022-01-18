package com.bluecine.Blue_Cinema.service;

import java.util.List;
import java.util.Optional;

import com.bluecine.Blue_Cinema.entity.Movie;

public interface MovieService {

    public Iterable<Movie>findAll();

    public Optional<Movie>findById(Long Id);

    public List<Movie>findByBillboard(Boolean billboard);

    public Movie save(Movie movie);

    public boolean existById(Long Id);

    public boolean existsByTitle(String title);

    public Optional<Movie>getByTitle(String name);

    public void deleteById(Long Id);
}
