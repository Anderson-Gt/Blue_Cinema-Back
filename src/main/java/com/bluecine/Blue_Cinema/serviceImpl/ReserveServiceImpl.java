package com.bluecine.Blue_Cinema.serviceImpl;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.bluecine.Blue_Cinema.dto.EditReserveDto;
import com.bluecine.Blue_Cinema.dto.ReserveDto;
import com.bluecine.Blue_Cinema.entity.Chair;
import com.bluecine.Blue_Cinema.entity.Movie;
import com.bluecine.Blue_Cinema.entity.Reserve;
import com.bluecine.Blue_Cinema.entity.User;
import com.bluecine.Blue_Cinema.repository.ReserveRepository;
import com.bluecine.Blue_Cinema.service.ChairService;
import com.bluecine.Blue_Cinema.service.MovieService;
import com.bluecine.Blue_Cinema.service.ReserveService;
import com.bluecine.Blue_Cinema.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReserveServiceImpl implements ReserveService {

    @Autowired
    private ReserveRepository reserveRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private ChairService chairService;
    

    @Override
    @Transactional(readOnly = true)
    public Iterable<Reserve> findAll() {        
        return reserveRepository.findAll();
    }

    
    @Override
    @Transactional(readOnly = true)
    public Optional<Reserve> findById(Long id) {
        return reserveRepository.findById(id);
    }

    @Override
    @Transactional
    public Reserve save(Reserve reserve) {
        return reserveRepository.save(reserve);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        reserveRepository.deleteById(id);        
    }

    @Override
    @Transactional
    public boolean existsById(Long Id){
        return reserveRepository.existsById(Id);
    }

    @Override
    @Transactional
    public String createReserve(ReserveDto reserveDto){
        User user = userService.findByEmail(reserveDto.getEmail()).get();
        Movie movie = movieService.findById(reserveDto.getIdMovie()).get();
        Set<Chair> chairs = new HashSet<>();
        for(int chairReserve:reserveDto.getChairs()){
            chairs.add(chairService.findById(chairReserve).get());
        }
        Reserve reserve = new Reserve(reserveDto.getIdSchedule(), user, movie, chairs);
        save(reserve);

        return "Reserva creada exitosamente";
    }
    
    @Override
    @Transactional
    public String updateReserve(Long reserveId, EditReserveDto editReserveDto){
        Reserve reserve = findById(reserveId).get();

        Set<Chair> chairs = new HashSet<>();
        for(int chairReserve:editReserveDto.getChairs()){
            chairs.add(chairService.findById(chairReserve).get());
        }

        reserve.setChairs(chairs);
        reserve.updateReserve(chairs);

        save(reserve);

        return "Reserva actualizada";
    }

    @Override
    @Transactional
    public List<Reserve> readReservesByUser(String email){
        
        User user = userService.findByEmail(email).get();
        List<Reserve> reserves = findByUsers(user);
        return reserves;
    }

    @Override
    @Transactional
    public List<Integer> getReservedChairs(Integer idSchedule, Long idMovie){
        Movie movie = movieService.findById(idMovie).get();
        List<Reserve> reserves = findByIdScheduleAndMovies(idSchedule, movie);
        Set<Chair> chairs = new HashSet<>();
        
        for(Reserve chair:reserves){
            chairs.addAll(chair.getChairs());
        }

        List<Integer> chairsReserved = new LinkedList<Integer>();

        for(Chair chairReserved:chairs){
            chairsReserved.add(chairReserved.getIdChair());
        }
        return chairsReserved;
    }

    @Override
    @Transactional
    public List<Reserve>findByIdScheduleAndMovies(Integer idSchedule, Movie movie){
        return reserveRepository.findByIdScheduleAndMovies(idSchedule, movie);
    }

    @Override
    @Transactional
    public List<Reserve>findByUsers(User user){
        return reserveRepository.findByUsers(user);
    }
    
}
