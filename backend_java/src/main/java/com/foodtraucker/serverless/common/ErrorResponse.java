package com.foodtraucker.serverless.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.foodtraucker.serverless.utils.ConstraintViolationUtil;
import com.foodtraucker.serverless.utils.StreamUtils;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * An error response POJO which follows the Google API Design Guide with one exception.
 * <p>
 * The main difference from this error object and the one from Google API Design Guide is
 * that this object contains both code and status_code. The status_code represents the
 * HTTP code of the response and the code is system specific code to allow clients to map
 * errors to a more detailed error message. Further the status field uses the default
 * http status codes instead of the ones defined in the Google API Design Guide.
 * <p>
 * Additionally this error response can contain a list of FieldConstraintViolations
 *
 * @author palmithor
 * @see <a href="https://cloud.google.com/apis/design/">API Design Guide</a>
 * @see <a href="https://tools.ietf.org/html/rfc7231">HTTP Specifications</a>
 * @see FieldConstraintViolation
 * @since 2017-09-04
 */
@JsonPropertyOrder({"statusCode", "status", "code", "message", "details", "fieldViolations"})
public class ErrorResponse implements Serializable {

    private final Integer statusCode;
    private final String status;
    private final Integer code;
    private final String message;
    private final List<String> details;
    private final List<FieldConstraintViolation> fieldViolations;

    ErrorResponse() {
        this.statusCode = null;
        this.status = null;
        this.code = null;
        this.message = null;
        this.details = null;
        this.fieldViolations = null;
    }

    ErrorResponse(final Integer statusCode, final String status,
                  final Integer code, final String message,
                  final List<String> details,
                  final List<FieldConstraintViolation> fieldViolations) {
        this.statusCode = statusCode;
        this.status = status;
        this.code = code;
        this.message = message;
        this.details = details;
        this.fieldViolations = fieldViolations;
    }

    /**
     * The HTTP status code mapping
     */
    @JsonProperty("statusCode")
    public Integer getStatusCode() {
        return statusCode;
    }

    /**
     * This corresponds to HTTP status code message
     */
    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    /**
     * A simple error code that can be easily handled by a client.
     */
    @JsonProperty("code")
    public Integer getCode() {
        return code;
    }

    /**
     * A developer-facing human-readable error message in English. It should
     * both explain the error and offer an actionable resolution to it.
     */
    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    /**
     * Additional error information that the client code can use to handle
     * the error, such as retry delay or a help link.
     */
    @JsonProperty("details")
    public List<String> getDetails() {
        return details;
    }

    /**
     * A list containing all of the constraint violation the request
     */
    @JsonProperty("fieldViolations")
    public List<FieldConstraintViolation> getFieldViolations() {
        return fieldViolations;
    }

    public static Builder create() {
        return new Builder();
    }


    /**
     * Sets the status code to 400 and the status to "Bad Request"
     */
    public static Builder badRequest() {
        Builder builder = new Builder();
        builder.statusCode = 400;
        builder.status = "Bad Request";
        return builder;
    }

    /**
     * Sets the status code to 401 and the status to "Unauthorized"
     */
    public static Builder unauthorized() {
        Builder builder = new Builder();
        builder.statusCode = 401;
        builder.status = "Unauthorized";
        return builder;
    }

    /**
     * Sets the status code to 403 and the status to "Forbidden"
     */
    public static Builder forbidden() {
        Builder builder = new Builder();
        builder.statusCode = 403;
        builder.status = "Forbidden";
        return builder;
    }

    /**
     * Sets the status code to 404 and the status to "Not Found"
     */
    public static Builder notFound() {
        Builder builder = new Builder();
        builder.statusCode = 404;
        builder.status = "Not Found";
        return builder;
    }

    /**
     * Sets the status code to 405 and the status to "Method Not Allowed"
     */
    public static Builder methodNotAllowed() {
        Builder builder = new Builder();
        builder.statusCode = 405;
        builder.status = "Method Not Allowed";
        return builder;
    }

    /**
     * Sets the status code to 409 and the status to "Conflict"
     */
    public static Builder conflict() {
        Builder builder = new Builder();
        builder.statusCode = 409;
        builder.status = "Conflict";
        return builder;
    }

    /**
     * Sets the status code to 500 and the status to "Internal Server Error"
     */
    public static Builder serverError() {
        Builder builder = new Builder();
        builder.statusCode = 500;
        builder.status = "Internal Server Error";
        return builder;
    }

    /**
     * Sets the status code to 504 and the status to "Gateway Timeout"
     */
    public static Builder gatewayTimeout() {
        Builder builder = new Builder();
        builder.statusCode = 504;
        builder.status = "Gateway Timeout";
        return builder;
    }


    public static final class Builder {
        private Integer statusCode;
        private String status;
        private Integer code;
        private String message;
        private List<String> details;

        private List<FieldConstraintViolation> fieldViolations;

        private Builder() {
        }

        /**
         * Set HTTP status code
         */
        public Builder statusCode(final Integer statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        /**
         * Set HTTP status message
         */
        public Builder status(final String status) {
            this.status = status;
            return this;
        }

        /**
         * Set custom code
         */
        public Builder code(final Integer code) {
            this.code = code;
            return this;
        }

        /**
         * Set custom message
         */
        public Builder message(final String message) {
            this.message = message;
            return this;
        }

        /**
         * Set error details
         */
        public Builder details(final List<String> details) {
            this.details = details;
            return this;
        }

        public Builder addDetail(final String detailedMessage) {
            if (this.details == null) {
                this.details = new ArrayList<>();
            }
            this.details.add(detailedMessage);
            return this;
        }

        /**
         * Set field violations
         */
        public Builder addFieldViolation(final Set<? extends ConstraintViolation<?>> constraintViolations) {
            if (this.fieldViolations == null) {
                this.fieldViolations = new ArrayList<>();
            }
            this.fieldViolations.addAll(StreamUtils.asStream(constraintViolations.iterator())
                    .map(ConstraintViolationUtil::convertFromFieldConstraintViolation)
                    .collect(Collectors.toList()));
            return this;
        }

        /**
         * Set field violations
         */
        public Builder addFieldViolation(final String field, final Object rejectedValue, final String message) {
            if (this.fieldViolations == null) {
                this.fieldViolations = new ArrayList<>();
            }
            this.fieldViolations.add(new FieldConstraintViolation(field, rejectedValue, message));
            return this;
        }

        public ErrorResponse build() {
            return new ErrorResponse(statusCode, status, code, message, details, fieldViolations);
        }
    }
}
