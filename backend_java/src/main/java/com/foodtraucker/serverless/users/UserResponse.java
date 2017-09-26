package com.foodtraucker.serverless.users;

/**
 * @author palmithor
 * @since 2017-09-26
 */
public class UserResponse {

    private final String email;

    public UserResponse(final String email) {
        this.email = email;
    }


    public static final class Builder {
        private String email;

        private Builder() {
        }

        public static Builder create() {
            return new Builder();
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public UserResponse build() {
            return new UserResponse(email);
        }
    }
}
