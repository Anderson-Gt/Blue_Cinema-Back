package com.bluecine.Blue_Cinema.repository;

import java.util.List;
import java.util.Optional;

import com.bluecine.Blue_Cinema.entity.Movie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie,Long>{

    List<Movie> findByBillboard(Boolean billboard);
    Optional<Movie> findByTitle(String title);
    boolean existsByTitle(String title);

    
}
