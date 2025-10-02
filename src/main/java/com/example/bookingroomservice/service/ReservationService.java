package com.example.bookingroomservice.service;

import com.example.bookingroomservice.dto.ReservationRequest;
import com.example.bookingroomservice.dto.ReservationResponse;

import java.util.List;
import java.util.Optional;

public interface ReservationService {

    List<ReservationResponse> getAllReservations();

    List<ReservationResponse> getReservationsByCabinetNumber(long cabinetNumber);

    Optional<ReservationResponse> getReservationById(Long id);

    ReservationResponse bookCabinet(ReservationRequest reservation);

    ReservationResponse updateReservation(ReservationRequest reservation);
}
