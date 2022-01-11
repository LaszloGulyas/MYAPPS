package com.laca.scraperapp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix="instagram-cookie")
public class InstagramCookieConfig {

    private String csrftoken;
    private String dsuserid;
    private String sessionid;

    public String getCsrftoken() {
        return csrftoken;
    }

    public void setCsrftoken(String csrftoken) {
        this.csrftoken = csrftoken;
    }

    public String getDsuserid() {
        return dsuserid;
    }

    public void setDsuserid(String dsuserid) {
        this.dsuserid = dsuserid;
    }

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }
}
