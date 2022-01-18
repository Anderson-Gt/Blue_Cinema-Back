package com.bluecine.Blue_Cinema.service;

import java.util.Optional;

import com.bluecine.Blue_Cinema.entity.Schedule;

public interface ScheduleService {
    
    public Iterable<Schedule> findAll();

    public Optional<Schedule>findById(Integer Id);

    public Schedule save(Schedule schedule);

    public void deleteById(Integer Id);
    
}
