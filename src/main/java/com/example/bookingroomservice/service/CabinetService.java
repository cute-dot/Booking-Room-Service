package com.example.bookingroomservice.service;


import com.example.bookingroomservice.dto.CabinetRequest;
import com.example.bookingroomservice.dto.CabinetResponse;
import com.example.bookingroomservice.model.Cabinet;

import java.util.List;
import java.util.Optional;

public interface CabinetService {

    List<CabinetResponse> getAllCabinets();

    Optional<CabinetResponse> getCabinetById(Long id);

    Optional<CabinetResponse> getCabinetByNumber(long number);

    CabinetResponse addCabinet(CabinetRequest cabinet);

    CabinetResponse updateCabinet(Long id, CabinetRequest cabinet);

}
