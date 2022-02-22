package com.bluecine.Blue_Cinema.repository;

import java.util.List;

import com.bluecine.Blue_Cinema.entity.Movie;
import com.bluecine.Blue_Cinema.entity.Reserve;
import com.bluecine.Blue_Cinema.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReserveRepository extends JpaRepository<Reserve,Long>{
    boolean existsById(Long Id);
    List<Reserve> findByUsers(User user);
    List<Reserve> findByIdScheduleAndMovies(Integer idSchedule, Movie movie);
    
}
