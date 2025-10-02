package com.example.bookingroomservice.service.impl;


import com.example.bookingroomservice.dto.ReservationRequest;
import com.example.bookingroomservice.dto.ReservationResponse;
import com.example.bookingroomservice.model.Cabinet;
import com.example.bookingroomservice.model.Reservation;
import com.example.bookingroomservice.repository.CabinetRepository;
import com.example.bookingroomservice.repository.ReservationRepository;
import com.example.bookingroomservice.service.ReservationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private static final Logger log = LoggerFactory.getLogger(ReservationServiceImpl.class);

    private final ReservationRepository reservationRepository;
    private final CabinetRepository cabinetRepository;


    @Override
    public List<ReservationResponse> getAllReservations() {
        return reservationRepository.findAll()
                .stream()
                .map(ReservationResponse::fromEntity)
                .toList();
    }

    @Override
    public List<ReservationResponse> getReservationsByCabinetNumber(long cabinetNumber) {
        Optional<Cabinet> cabinet = cabinetRepository.findByCabinetNumber(cabinetNumber);
        if (cabinet.isEmpty()) {
            throw new IllegalArgumentException("Cabinet with number " + cabinetNumber + " not found");
        }
        return cabinet.get()
                .getReservations()
                .stream()
                .map(ReservationResponse::fromEntity)
                .toList();
    }

    @Override
    public Optional<ReservationResponse> getReservationById(Long id) {
        log.debug("Searching for reservation by id: {}", id);
        return reservationRepository.findById(id)
                .map(ReservationResponse::fromEntity);
    }

    @Override
    @Transactional
    public ReservationResponse bookCabinet(ReservationRequest reservationRequest) {
        long cabinetNumber = reservationRequest.getCabinetNumber();
        OffsetDateTime startTime = reservationRequest.getStartTime();
        OffsetDateTime endTime = reservationRequest.getEndTime();

        log.debug("Starting cabinet booking: {} from {} to {}",
                cabinetNumber, startTime, endTime);

        if (reservationRequest.getStartTime().isAfter(reservationRequest.getEndTime())) {
            throw new IllegalArgumentException("Reservation start time must be before end time");
        }

        if (reservationRequest.getStartTime().isBefore(OffsetDateTime.now())) {
            throw new IllegalArgumentException("Cannot book cabinet in the past");
        }

        Cabinet cabinet = cabinetRepository.findByCabinetNumber(reservationRequest.getCabinetNumber())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Cabinet with number " + cabinetNumber + " not found"));

        List<Reservation> conflictingReservations =
                reservationRepository.findConflictingReservations(
                    cabinet.getId(),
                    reservationRequest.getStartTime(),
                    reservationRequest.getEndTime());

        if (!conflictingReservations.isEmpty()) {
            log.warn("Booking conflict detected for cabinet {}: found {} overlapping reservations",
                cabinet.getId(), conflictingReservations.size());
            throw new IllegalArgumentException("Cabinet is already booked for the specified time");
        }

        Reservation reservation = ReservationRequest
                .toEntity(reservationRequest, cabinet);

        Reservation savedReservation =
                reservationRepository.save(reservation);

        log.info("Cabinet {} successfully booked, reservation ID: {}",
            cabinet.getCabinetNumber(), savedReservation.getId());

        return ReservationResponse.fromEntity(savedReservation);
    }

    @Override
    @Transactional
    public ReservationResponse updateReservation(ReservationRequest reservationRequest) {
        long cabinetNumber = reservationRequest.getCabinetNumber();
        OffsetDateTime startTime = reservationRequest.getStartTime();
        OffsetDateTime endTime = reservationRequest.getEndTime();
        log.debug("Starting reservation update for cabinet number: {}", cabinetNumber);

        Reservation existingReservation = reservationRepository.findReservationByCabinet_CabinetNumberAndStartTimeAndEndTime(
                cabinetNumber, startTime, endTime)
                .orElseThrow(() -> new IllegalArgumentException(
                String.format("Reservation for cabinet number %d with start time %s and end time %s not found",
                        cabinetNumber, startTime, endTime))
        );

        if (reservationRequest.getStartTime().isAfter(reservationRequest.getEndTime())) {
            throw new IllegalArgumentException("Reservation start time must be before end time");
        }

        if (reservationRequest.getStartTime().isBefore(OffsetDateTime.now())) {
            throw new IllegalArgumentException("Cannot book cabinet in the past");
        }

        Cabinet cabinet = cabinetRepository.findByCabinetNumber(reservationRequest.getCabinetNumber())
                .orElseThrow(() -> new IllegalArgumentException(
                    "Cabinet with number " + reservationRequest.getCabinetNumber() + " not found")
                );

        List<Reservation> conflictingReservations = reservationRepository.findConflictingReservationsExcludingId(
                cabinet.getId(),
                existingReservation.getId(),
                reservationRequest.getStartTime(),
                reservationRequest.getEndTime());

        if (!conflictingReservations.isEmpty()) {
            log.warn("Booking update conflict detected for cabinet {}: found {} overlapping reservations",
                cabinet.getId(), conflictingReservations.size());
            throw new IllegalArgumentException("Cabinet is already booked for the specified time by another user");
        }

        existingReservation.setStartTime(reservationRequest.getStartTime());
        existingReservation.setEndTime(reservationRequest.getEndTime());
        existingReservation.setEventName(reservationRequest.getEventName());
        existingReservation.setReservedBy(reservationRequest.getReservedBy());
        existingReservation.setCabinet(cabinet);

        Reservation updatedReservation = reservationRepository.save(existingReservation);

        log.info("Reservation with ID {} successfully updated", updatedReservation.getId());

        return ReservationResponse.fromEntity(updatedReservation);
    }
}
