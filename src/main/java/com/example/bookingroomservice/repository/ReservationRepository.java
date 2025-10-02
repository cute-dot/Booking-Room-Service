package com.example.bookingroomservice.repository;

import com.example.bookingroomservice.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Optional<Reservation> findReservationByCabinet_CabinetNumberAndStartTimeAndEndTime(
        long cabinetNumber, OffsetDateTime startTime, OffsetDateTime endTime);

    @Query("SELECT r FROM Reservation r WHERE r.cabinet.id = :cabinetId " +
           "AND ((r.startTime <= :endTime AND r.endTime >= :startTime))")
    List<Reservation> findConflictingReservations(
        @Param("cabinetId") Long cabinetId,
        @Param("startTime") OffsetDateTime startTime,
        @Param("endTime") OffsetDateTime endTime);

    @Query("SELECT r FROM Reservation r WHERE r.cabinet.id = :cabinetId " +
           "AND r.id != :reservationId " +
           "AND ((r.startTime <= :endTime AND r.endTime >= :startTime))")
    List<Reservation> findConflictingReservationsExcludingId(
        @Param("cabinetId") Long cabinetId,
        @Param("reservationId") Long reservationId,
        @Param("startTime") OffsetDateTime startTime,
        @Param("endTime") OffsetDateTime endTime);

}
