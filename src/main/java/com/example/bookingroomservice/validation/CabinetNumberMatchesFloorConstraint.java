package com.example.bookingroomservice.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CabinetNumberMatchesFloorValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CabinetNumberMatchesFloorConstraint {
    String message() default "First digit of the cabinet number must match the floor number";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
