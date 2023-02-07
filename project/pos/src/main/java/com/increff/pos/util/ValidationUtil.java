package com.increff.pos.util;

import javax.validation.*;
import java.util.Set;

public class ValidationUtil {

    // Utility function for generic validation of forms
    public static <T> void validateForms(T form){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> violations = validator.validate(form);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}
