package com.example.bookingroomservice.controller;


import com.example.bookingroomservice.dto.CabinetRequest;
import com.example.bookingroomservice.dto.CabinetResponse;
import com.example.bookingroomservice.service.CabinetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cabinet")
@RequiredArgsConstructor
public class CabinetController {

    private final CabinetService cabinetService;

    @GetMapping
    public ResponseEntity<List<CabinetResponse>> getAllCabinets() {
        return ResponseEntity.ok(cabinetService.getAllCabinets());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CabinetResponse> getCabinetById(@PathVariable Long id) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().build();
        }

        return cabinetService.getCabinetById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/number/{number}")
    public ResponseEntity<CabinetResponse> getCabinetByNumber(@PathVariable long number) {
        if (number <= 0) {
            return ResponseEntity.badRequest().build();
        }

        return cabinetService.getCabinetByNumber(number)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CabinetResponse> createCabinet(@Valid @RequestBody CabinetRequest request) {
        CabinetResponse created = cabinetService.addCabinet(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CabinetResponse> updateCabinet(
            @PathVariable Long id,
            @Valid @RequestBody CabinetRequest request) {
        try {
            CabinetResponse updated = cabinetService.updateCabinet(id, request);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
