package com.bluecine.Blue_Cinema.service;

import java.util.List;
import java.util.Optional;

import com.bluecine.Blue_Cinema.entity.Movie;
import com.bluecine.Blue_Cinema.repository.MovieRepository;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieServiceImpl implements MovieService{

    @Autowired
    private MovieRepository movieRepository;

    @Override
    @Transactional(readOnly = true)
    public Iterable<Movie> findAll() {        
        return movieRepository.findAll();
    }

    
    @Override
    @Transactional(readOnly = true)
    public Optional<Movie> findById(Long id) {
        return movieRepository.findById(id);
    }

    @Override
    @Transactional
    public Movie save(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        movieRepository.deleteById(id);        
    }


    @Override
    @Transactional
    public List<Movie> findByBillboard(Boolean billboard) {
       return movieRepository.findByBillboard(billboard);
        
    }

    
}
