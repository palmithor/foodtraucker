package com.foodtraucker.serverless.trucks;

import javax.validation.constraints.NotNull;

/**
 * @author palmithor
 * @since 26.10.2017.
 */
public class CheckinRequest {

    @NotNull
    private Float lat; // TODO set max min
    @NotNull
    private Float lon; // TODO set max min
    @NotNull
    private Long checkin; // TODO create validator which validates that checkin is before checkout
    @NotNull
    private Long checkout;

    public Float getLat() {
        return lat;
    }

    public void setLat(final Float lat) {
        this.lat = lat;
    }

    public Float getLon() {
        return lon;
    }

    public void setLon(final Float lon) {
        this.lon = lon;
    }

    public Long getCheckin() {
        return checkin;
    }

    public void setCheckin(final Long checkin) {
        this.checkin = checkin;
    }

    public Long getCheckout() {
        return checkout;
    }

    public void setCheckout(final Long checkout) {
        this.checkout = checkout;
    }

    public static Builder createBuilder() {
        return new Builder();
    }


    public static final class Builder {
        private Float lat;
        private Float lon;
        private Long checkin;
        private Long checkout;

        private Builder() {
        }

        public Builder lat(final Float lat) {
            this.lat = lat;
            return this;
        }

        public Builder lon(final Float lon) {
            this.lon = lon;
            return this;
        }

        public Builder checkin(final Long checkin) {
            this.checkin = checkin;
            return this;
        }

        public Builder checkout(final Long checkout) {
            this.checkout = checkout;
            return this;
        }

        public CheckinRequest build() {
            CheckinRequest checkinRequest = new CheckinRequest();
            checkinRequest.setLat(lat);
            checkinRequest.setLon(lon);
            checkinRequest.setCheckin(checkin);
            checkinRequest.setCheckout(checkout);
            return checkinRequest;
        }
    }
}
