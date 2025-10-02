package com.example.bookingroomservice.repository;

import com.example.bookingroomservice.model.Cabinet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CabinetRepository extends JpaRepository<Cabinet, Long> {

    Optional<Cabinet> findByCabinetNumber(long cabinetNumber);
}
