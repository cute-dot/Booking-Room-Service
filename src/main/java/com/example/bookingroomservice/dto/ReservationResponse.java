package com.example.bookingroomservice.dto;

import com.example.bookingroomservice.model.Reservation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResponse {

    private Long id;
    private OffsetDateTime startTime;
    private OffsetDateTime endTime;
    private String eventName;
    private String reservedBy;
    private Long cabinetId;
    private Long cabinetNumber;
    private Long cabinetFloor;

    public static ReservationResponse fromEntity(Reservation reservation) {
        return ReservationResponse.builder()
                .id(reservation.getId())
                .startTime(reservation.getStartTime())
                .endTime(reservation.getEndTime())
                .eventName(reservation.getEventName())
                .reservedBy(reservation.getReservedBy())
                .cabinetId(reservation.getCabinet().getId())
                .cabinetNumber(reservation.getCabinet().getCabinetNumber())
                .cabinetFloor(reservation.getCabinet().getCabinetFloor())
                .build();
    }
}
