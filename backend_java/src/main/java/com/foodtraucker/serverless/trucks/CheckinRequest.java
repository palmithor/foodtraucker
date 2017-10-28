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

}
