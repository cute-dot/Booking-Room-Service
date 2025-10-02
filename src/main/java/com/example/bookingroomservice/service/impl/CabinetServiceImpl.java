package com.example.bookingroomservice.service.impl;

import com.example.bookingroomservice.dto.CabinetRequest;
import com.example.bookingroomservice.dto.CabinetResponse;
import com.example.bookingroomservice.model.Cabinet;
import com.example.bookingroomservice.repository.CabinetRepository;
import com.example.bookingroomservice.service.CabinetService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CabinetServiceImpl implements CabinetService {

    private static final Logger log = LoggerFactory.getLogger(CabinetServiceImpl.class);

    private final CabinetRepository cabinetRepository;

    @Override
    public List<CabinetResponse> getAllCabinets() {
        return cabinetRepository.findAll().stream()
                .map(CabinetResponse::fromEntity)
                .toList();
    }

    @Override
    public Optional<CabinetResponse> getCabinetById(Long id) {
        log.debug("Searching for cabinet by id: {}", id);
        return cabinetRepository.findById(id)
                .map(CabinetResponse::fromEntity);
    }

    @Override
    public Optional<CabinetResponse> getCabinetByNumber(long number) {
        log.debug("Searching for cabinet by number: {}", number);
        return cabinetRepository.findByCabinetNumber(number)
                .map(CabinetResponse::fromEntity);
    }

    @Override
    @Transactional
    public CabinetResponse addCabinet(CabinetRequest cabinetRequest) {
        Cabinet cabinet = CabinetRequest.toEntity(cabinetRequest);
        Cabinet savedCabinet = cabinetRepository.save(cabinet);
        return CabinetResponse.fromEntity(savedCabinet);
    }

    @Override
    @Transactional
    public CabinetResponse updateCabinet(Long id, CabinetRequest cabinetRequest) {
        Optional<Cabinet> cabinetOpt = cabinetRepository.findById(id);

        if (cabinetOpt.isEmpty()) {
            throw new IllegalArgumentException("Cabinet with ID " + id + " not found");
        }

        Cabinet cabinet = cabinetOpt.get();
        cabinet.setCabinetNumber(cabinetRequest.getCabinetNumber());
        cabinet.setCabinetFloor(cabinetRequest.getCabinetFloor());

        Cabinet updatedCabinet = cabinetRepository.save(cabinet);

        return CabinetResponse.fromEntity(updatedCabinet);
    }
}
