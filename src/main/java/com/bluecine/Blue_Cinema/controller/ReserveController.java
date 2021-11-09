package com.bluecine.Blue_Cinema.controller;

import java.util.Optional;

import com.bluecine.Blue_Cinema.entity.Reserve;
import com.bluecine.Blue_Cinema.service.ReserveService;
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
public class ReserveController {

    @Autowired
    private ReserveService reserveService;
        
    //create a new reserve
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Reserve reserve){
        return ResponseEntity.status(HttpStatus.CREATED).body(reserveService.save(reserve));
    }

    //read a reserve
    @GetMapping("/{id}")
    public ResponseEntity<?> read(@PathVariable(value="id") Long reserveId){
        Optional<Reserve>oReserve = reserveService.findById(reserveId);
        if(!oReserve.isPresent()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(oReserve);
    }

    //update a reserve
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Reserve reserveDetails, @PathVariable(value = "id") Long reserveId){
        Optional<Reserve> reserve = reserveService.findById(reserveId);
        if(!reserve.isPresent()){
            return ResponseEntity.notFound().build();
        }

        reserve.get().setAmount(reserveDetails.getAmount());
        reserve.get().setTotalPrice(reserveDetails.getTotalPrice());
        

        return ResponseEntity.status(HttpStatus.CREATED).body(reserveService.save(reserve.get()));
    }

    //delete a reserve
    @DeleteMapping("/{id}")
    public ResponseEntity<?>delete(@PathVariable(value = "id") Long reserveId){
        if(!reserveService.findById(reserveId).isPresent()){
            return ResponseEntity.notFound().build();
        }
        reserveService.deleteById(reserveId);
        return ResponseEntity.ok().build();
    }

    


}
