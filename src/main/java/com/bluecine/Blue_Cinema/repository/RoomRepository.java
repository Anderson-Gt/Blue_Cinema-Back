package com.bluecine.Blue_Cinema.repository;

import com.bluecine.Blue_Cinema.entity.Room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room,Long>{
    
}
