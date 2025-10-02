package com.example.bookingroomservice.dto;

import com.example.bookingroomservice.model.Cabinet;
import com.example.bookingroomservice.model.Reservation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRequest {

    @NotNull(message = "Start time cannot be empty")
    private OffsetDateTime startTime;

    @NotNull(message = "End time cannot be empty")
    private OffsetDateTime endTime;

    private String eventName;

    @NotBlank(message = "Reserved by field cannot be empty")
    private String reservedBy;

    @NotNull(message = "Cabinet number cannot be empty")
    private long cabinetNumber;

    public static Reservation toEntity(ReservationRequest request, Cabinet cabinet) {
        return Reservation.builder()
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .eventName(request.getEventName())
                .reservedBy(request.getReservedBy())
                .cabinet(cabinet)
                .build();
    }
}
