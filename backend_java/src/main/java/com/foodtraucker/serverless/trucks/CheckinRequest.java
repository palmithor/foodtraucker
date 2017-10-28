package com.foodtraucker.serverless.trucks;

import java.math.BigDecimal;

/**
 * @author palmithor
 * @since 26.10.2017.
 */
public class CheckinRequest {

    private BigDecimal lat;
    private BigDecimal lon;

    private Long checkin;
    private Long checkout;

    public BigDecimal getLat() {
        return lat;
    }

    public void setLat(final BigDecimal lat) {
        this.lat = lat;
    }

    public BigDecimal getLon() {
        return lon;
    }

    public void setLon(final BigDecimal lon) {
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
}
