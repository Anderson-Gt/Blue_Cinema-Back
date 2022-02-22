package com.bluecine.Blue_Cinema.repository;

import java.util.Optional;

import com.bluecine.Blue_Cinema.entity.Schedule;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer>{
    Optional<Schedule> findById(Integer Id);
    
}
