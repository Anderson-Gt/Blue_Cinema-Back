package com.bluecine.Blue_Cinema.serviceImpl;

import java.util.Optional;

import com.bluecine.Blue_Cinema.entity.Schedule;
import com.bluecine.Blue_Cinema.repository.ScheduleRepository;
import com.bluecine.Blue_Cinema.service.ScheduleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Override
    @Transactional(readOnly = true)
    public Iterable<Schedule> findAll() {
       return scheduleRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Schedule> findById(Integer Id) {
        return scheduleRepository.findById(Id);
    }

    @Override
    @Transactional
    public Schedule save(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    @Override
    @Transactional
    public void deleteById(Integer Id) {
        scheduleRepository.deleteById(Id);               
    }
    
}
