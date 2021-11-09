package com.bluecine.Blue_Cinema.service;

import java.util.Optional;

import com.bluecine.Blue_Cinema.entity.Room;
import com.bluecine.Blue_Cinema.repository.RoomRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository;
    

    @Override
    @Transactional(readOnly = true)
    public Iterable<Room> findAll() {        
        return roomRepository.findAll();
    }

    
    @Override
    @Transactional(readOnly = true)
    public Optional<Room> findById(Long id) {
        return roomRepository.findById(id);
    }

    @Override
    @Transactional
    public Room save(Room room) {
        return roomRepository.save(room);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        roomRepository.deleteById(id);        
    }
    
}
