
//CONTROLADOR POR PREGUNTAR SI ES NECESARIO

/*package com.bluecine.Blue_Cinema.controller;

import java.util.Optional;

import com.bluecine.Blue_Cinema.entity.Chair;
import com.bluecine.Blue_Cinema.service.ChairService;

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
@RequestMapping
public class ChairController {
    
    @Autowired
    private ChairService chairService;
        
    //create a new room
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Chair chair){
        return ResponseEntity.status(HttpStatus.CREATED).body(chairService.save(chair));
    }

    //read a room
    @GetMapping("/{id}")
    public ResponseEntity<?> read(@PathVariable(value="id") Long chairId){
        Optional<Chair>oChair = chairService.findById(chairId);
        if(!oChair.isPresent()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(oChair);
    }

    //update a room
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Chair chairDetails, @PathVariable(value = "id") Long chairId){
        Optional<Chair> chair = chairService.findById(chairId);
        if(!chair.isPresent()){
            return ResponseEntity.notFound().build();
        }
        chair.get().setReserved(chairDetails.isReserved());

        return ResponseEntity.status(HttpStatus.CREATED).body(chairService.save(chair.get()));
    }

    //delete a room
    @DeleteMapping("/{id}")
    public ResponseEntity<?>delete(@PathVariable(value = "id") Long chairId){
        if(!chairService.findById(chairId).isPresent()){
            return ResponseEntity.notFound().build();
        }
        chairService.deleteById(chairId);
        return ResponseEntity.ok().build();
    }




}*/
