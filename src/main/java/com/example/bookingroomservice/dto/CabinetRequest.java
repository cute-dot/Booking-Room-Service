package com.example.bookingroomservice.dto;

import com.example.bookingroomservice.model.Cabinet;
import com.example.bookingroomservice.validation.CabinetNumberMatchesFloorConstraint;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@CabinetNumberMatchesFloorConstraint
public class CabinetRequest {

    @NotNull(message = "Cabinet number cannot be empty")
    @Min(value = 1, message = "Cabinet number must be a positive number")
    private Long cabinetNumber;

    @NotNull(message = "Cabinet floor cannot be empty")
    @Min(value = 1, message = "Floor must be a positive number")
    private Long cabinetFloor;

    public static Cabinet toEntity(CabinetRequest request) {
        return Cabinet.builder()
                .cabinetNumber(request.getCabinetNumber())
                .cabinetFloor(request.getCabinetFloor())
                .build();
    }
}
