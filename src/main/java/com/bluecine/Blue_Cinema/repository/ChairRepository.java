package com.bluecine.Blue_Cinema.repository;

import com.bluecine.Blue_Cinema.entity.Chair;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChairRepository extends JpaRepository<Chair,Long>{
    
}
