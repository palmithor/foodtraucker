package com.foodtraucker.serverless.menus;

/**
 * @author armannds
 * @since 2017-11-12
 */
public class AddMenuRequest {

    private String name;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static final class Builder {
        private String name;
        private String description;

        private Builder() { }

        public Builder name(final String name) {
            this.name = name;
            return this;
        }

        public Builder description(final String description) {
            this.description = description;
            return this;
        }

        public AddMenuRequest build() {
            AddMenuRequest addMenuRequest = new AddMenuRequest();
            addMenuRequest.setName(name);
            addMenuRequest.setDescription(description);
            return addMenuRequest;
        }
    }
}
