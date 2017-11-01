package com.foodtraucker.serverless;

/**
 * @author palmithor
 * @since 2017-11-01
 */
public class Authorizer {

    private Claims claims;


    public Claims getClaims() {
        return claims;
    }

    public void setClaims(final Claims claims) {
        this.claims = claims;
    }

}
