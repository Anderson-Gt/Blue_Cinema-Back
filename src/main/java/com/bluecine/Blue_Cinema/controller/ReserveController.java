package com.bluecine.Blue_Cinema.controller;

import java.util.List;

import com.bluecine.Blue_Cinema.dto.EditReserveDto;
import com.bluecine.Blue_Cinema.dto.Message;
import com.bluecine.Blue_Cinema.dto.ReserveDto;
import com.bluecine.Blue_Cinema.entity.Reserve;
import com.bluecine.Blue_Cinema.service.MovieService;
import com.bluecine.Blue_Cinema.service.ReserveService;
import com.bluecine.Blue_Cinema.service.ScheduleService;
import com.bluecine.Blue_Cinema.service.UserService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin
@RequestMapping("/api/reserves")
public class ReserveController {

    @Autowired
    private ReserveService reserveService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private UserService userService;

    @Autowired
    private ScheduleService scheduleService;
        
    //create a new reserve
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody ReserveDto reserveDto){
        if(StringUtils.isBlank(reserveDto.getEmail()))
        return new ResponseEntity(new Message("El email del usuario es obligatorio"), HttpStatus.BAD_REQUEST);
        if(reserveDto.getChairs().size()==0)
        return new ResponseEntity(new Message("La reserva debe tener sillas asignadas"), HttpStatus.BAD_REQUEST);

        String response = reserveService.createReserve(reserveDto);

        return new ResponseEntity(new Message(response), HttpStatus.OK);
    }

    //update a reserve
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") Long reserveId, @RequestBody EditReserveDto editReserveDto){
        if(!reserveService.existsById(reserveId))
        return new ResponseEntity(new Message("No se encuentra reserva por id"), HttpStatus.NOT_FOUND);
        if(editReserveDto.getChairs().size()==0)
        return new ResponseEntity(new Message("La reserva debe contener sillas"), HttpStatus.BAD_REQUEST);
        
        String response = reserveService.updateReserve(reserveId, editReserveDto);

        return new ResponseEntity(new Message(response), HttpStatus.OK);

    }

    //Get name of an user
    @GetMapping("/name")
    public ResponseEntity<String> userName(@RequestParam("email") String email){
        String userName = userService.findByEmail(email).get().getNames();
        return new ResponseEntity(userName,HttpStatus.OK);
    }

    //Get time of schedule by id
    @GetMapping("/schedule")
    public ResponseEntity<String>schedule(@RequestParam("idSchedule") int id){
        String schedule = scheduleService.findById(id).get().getTime();
        return new ResponseEntity(schedule, HttpStatus.OK);
    }

    //Read All user reserves by email
    @GetMapping("/usermail")
    public ResponseEntity<?> readByUser(@RequestParam("email") String email){
        if(!userService.existsByEmail(email))
        return new ResponseEntity(new Message("No se encuentra usuario por email"), HttpStatus.NOT_FOUND);
    
        List<Reserve> response = reserveService.readReservesByUser(email);

        return new ResponseEntity(response, HttpStatus.OK);
    }

    //Read reserves By IdSchedule and IdMovie to get the chairs;
    @GetMapping("/chairs")
    public ResponseEntity<?>getChairs(@RequestParam("idSchedule") Integer idSchedule, @RequestParam Long idMovie){
        if(!movieService.existById(idMovie))
        return new ResponseEntity(new Message("No se encuentra pelicula por ID"), HttpStatus.NOT_FOUND);

        List<Integer> response = reserveService.getReservedChairs(idSchedule, idMovie);
        return new ResponseEntity(response, HttpStatus.OK);
        
    }

    //read a reserve
    @GetMapping("/{id}")
    public ResponseEntity<?> read(@PathVariable(value="id") Long reserveId){

        if(!reserveService.existsById(reserveId))
        return new ResponseEntity(new Message("no existe reserva por ID"), HttpStatus.NOT_FOUND);
        Reserve reserve = reserveService.findById(reserveId).get();

        return new ResponseEntity(reserve,HttpStatus.OK);
    }

    //delete a reserve
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?>delete(@PathVariable(value = "id") Long reserveId){
       if(!reserveService.existsById(reserveId))
       return new ResponseEntity(new Message("no existe reserva por ID"), HttpStatus.NOT_FOUND);
       reserveService.deleteById(reserveId);
       return new ResponseEntity(new Message("Reserva eliminada"), HttpStatus.OK);
    }

    


}
