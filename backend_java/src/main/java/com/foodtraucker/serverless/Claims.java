package com.foodtraucker.serverless;

/**
 * @author palmithor
 * @since 2017-11-01
 */
public class Claims {

    private String sub;
    private String aud;
    private String email_verified;
    private String token_use;
    private String auth_time;
    private String iss;
    private String exp;
    private String iat;
    private String email;

    public String getSub() {
        return sub;
    }

    public void setSub(final String sub) {
        this.sub = sub;
    }

    public String getAud() {
        return aud;
    }

    public void setAud(final String aud) {
        this.aud = aud;
    }

    public String getEmail_verified() {
        return email_verified;
    }

    public void setEmail_verified(final String email_verified) {
        this.email_verified = email_verified;
    }

    public String getToken_use() {
        return token_use;
    }

    public void setToken_use(final String token_use) {
        this.token_use = token_use;
    }

    public String getAuth_time() {
        return auth_time;
    }

    public void setAuth_time(final String auth_time) {
        this.auth_time = auth_time;
    }

    public String getIss() {
        return iss;
    }

    public void setIss(final String iss) {
        this.iss = iss;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(final String exp) {
        this.exp = exp;
    }

    public String getIat() {
        return iat;
    }

    public void setIat(final String iat) {
        this.iat = iat;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }
}
