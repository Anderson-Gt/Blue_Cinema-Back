package com.bluecine.Blue_Cinema.service;

import java.util.Optional;

import com.bluecine.Blue_Cinema.entity.Room;

public interface RoomService {

    public Iterable<Room>findAll();

    public Optional<Room>findById(Long Id);

    public Room save(Room reserve);

    public void deleteById(Long Id);
    
}
