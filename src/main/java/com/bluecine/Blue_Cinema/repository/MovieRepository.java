package com.bluecine.Blue_Cinema.repository;

import com.bluecine.Blue_Cinema.entity.Movie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie,Long>{
    
}
