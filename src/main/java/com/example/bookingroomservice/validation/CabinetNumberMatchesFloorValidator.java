package com.example.bookingroomservice.validation;

import com.example.bookingroomservice.dto.CabinetRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CabinetNumberMatchesFloorValidator implements ConstraintValidator<CabinetNumberMatchesFloorConstraint, CabinetRequest> {

    @Override
    public boolean isValid(CabinetRequest cabinetRequest, ConstraintValidatorContext context) {
        long cabinetNumber = cabinetRequest.getCabinetNumber();
        int firstDigit = getFirstDigit(cabinetNumber);

        return firstDigit == cabinetRequest.getCabinetFloor();
    }

    private int getFirstDigit(long number) {
        while (number >= 10) {
            number /= 10;
        }
        return (int) number;
    }
}
