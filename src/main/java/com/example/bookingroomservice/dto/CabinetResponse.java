package com.example.bookingroomservice.dto;

import com.example.bookingroomservice.model.Cabinet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CabinetResponse {

    private Long id;
    private Long cabinetNumber;
    private Long cabinetFloor;

    public static CabinetResponse fromEntity(Cabinet cabinet) {
        return CabinetResponse.builder()
                .id(cabinet.getId())
                .cabinetNumber(cabinet.getCabinetNumber())
                .cabinetFloor(cabinet.getCabinetFloor())
                .build();
    }
}
