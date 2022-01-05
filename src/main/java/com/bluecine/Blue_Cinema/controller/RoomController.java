
/*//CONTROLADOR POR PREGUNTAR SI ES NECESARIO

package com.bluecine.Blue_Cinema.controller;

import java.util.Optional;

import com.bluecine.Blue_Cinema.entity.Room;
import com.bluecine.Blue_Cinema.service.RoomService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reserves")
public class RoomController {

    @Autowired
    private RoomService roomService;
        
    //create a new room
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Room room){
        return ResponseEntity.status(HttpStatus.CREATED).body(roomService.save(room));
    }

    //read a room
    @GetMapping("/{id}")
    public ResponseEntity<?> read(@PathVariable(value="id") Long roomId){
        Optional<Room>oRoom = roomService.findById(roomId);
        if(!oRoom.isPresent()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(oRoom);
    }

    //update a room
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Room roomDetails, @PathVariable(value = "id") Long roomId){
        Optional<Room> room = roomService.findById(roomId);
        if(!room.isPresent()){
            return ResponseEntity.notFound().build();
        }

        room.get().setAmount(roomDetails.getAmount());
        room.get().setName(roomDetails.getName());        

        return ResponseEntity.status(HttpStatus.CREATED).body(roomService.save(room.get()));
    }

    //delete a room
    @DeleteMapping("/{id}")
    public ResponseEntity<?>delete(@PathVariable(value = "id") Long roomId){
        if(!roomService.findById(roomId).isPresent()){
            return ResponseEntity.notFound().build();
        }
        roomService.deleteById(roomId);
        return ResponseEntity.ok().build();
    }



    
}*/
