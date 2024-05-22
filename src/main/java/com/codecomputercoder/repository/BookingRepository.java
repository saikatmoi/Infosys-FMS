package com.codecomputercoder.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codecomputercoder.entity.Booking;

public interface  BookingRepository extends JpaRepository<Booking,Long>  {

    List<Booking> findByUser_userName(String username);

}
