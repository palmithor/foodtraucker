package com.foodtraucker.serverless.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;

/**
 * @author palmithor
 * @since 2017-09-04
 */
@JsonPropertyOrder({"field", "rejectedValue", "message"})
public class FieldConstraintViolation implements Serializable {

    private final String field;
    private final Object rejectedValue;
    private final String message;

    FieldConstraintViolation() {
        this.field = null;
        this.rejectedValue = null;
        this.message = null;
    }

    public FieldConstraintViolation(final String field, final Object rejectedValue, final String message) {
        this.field = field;
        this.rejectedValue = rejectedValue;
        this.message = message;
    }

    @JsonProperty("field")
    public String getField() {
        return field;
    }

    @JsonProperty("rejectedValue")
    public Object getRejectedValue() {
        return rejectedValue;
    }

    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    static final class Builder {
        private String title;
        private Object rejectedValue;
        private String message;

        private Builder() {
        }

        public static Builder create() {
            return new Builder();
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder rejectedValue(Object rejectedValue) {
            this.rejectedValue = rejectedValue;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public FieldConstraintViolation build() {
            return new FieldConstraintViolation(title, rejectedValue, message);
        }
    }
}
