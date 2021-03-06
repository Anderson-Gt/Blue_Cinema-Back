package com.bluecine.Blue_Cinema.service;

import java.util.List;
import java.util.Optional;

import com.bluecine.Blue_Cinema.dto.MovieDto;
import com.bluecine.Blue_Cinema.entity.Movie;


public interface MovieService {

    public Iterable<Movie>findAllByRegistered(Boolean registered);

    public Optional<Movie>findById(Long Id);

    public List<Movie>findByBillboardAndRegistered(Boolean billboard, Boolean registered);

    public Movie save(Movie movie);

    public boolean existById(Long Id);

    public boolean existsByTitle(String title);

    public Optional<Movie>getByTitle(String name);

    public void deleteById(Long Id);

    public String createMovie(MovieDto movieDto);

    public String updateMovie(Long movieId, MovieDto movie);

}
