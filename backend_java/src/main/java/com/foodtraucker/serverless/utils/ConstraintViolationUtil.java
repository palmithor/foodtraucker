package com.foodtraucker.serverless.utils;

import com.foodtraucker.serverless.common.FieldConstraintViolation;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import java.util.Optional;

/**
 * Utility class to work with javax.validation.ConstraintViolations
 *
 * @author palmithor
 * @since 2017-09-04
 */
public class ConstraintViolationUtil {

    private ConstraintViolationUtil() {

    }

    public static FieldConstraintViolation convertFromFieldConstraintViolation(final ConstraintViolation<?> constraintViolation) {
        String field = null;
        if (constraintViolation.getPropertyPath() != null) {
            Optional<Path.Node> fieldValueNodeOptional = StreamUtils.asStream(constraintViolation.getPropertyPath().iterator()).reduce((first, second) -> second);
            if (fieldValueNodeOptional.isPresent()) {
                field = fieldValueNodeOptional.get().toString();
            }
        }
        return new FieldConstraintViolation(field, constraintViolation.getInvalidValue(), constraintViolation.getMessage());
    }

}
