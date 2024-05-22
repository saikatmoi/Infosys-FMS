package com.codecomputercoder.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codecomputercoder.entity.Passenger;

public interface PassengerRepository extends JpaRepository<Passenger,Integer>{

}
