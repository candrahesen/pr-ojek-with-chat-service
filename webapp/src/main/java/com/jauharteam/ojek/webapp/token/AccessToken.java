package com.jauharteam.ojek.webapp.token;

public class AccessToken {

    private String accessToken;
    private String userAgent;
    private String ipAddress;

    public AccessToken() {
    }

    public AccessToken(String accessToken, String userAgent, String ipAddress) {
        this.accessToken = accessToken;
        this.userAgent = userAgent;
        this.ipAddress = ipAddress;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    @Override
    public String toString() {
        return "AccessToken{" +
                "accessToken='" + accessToken + '\'' +
                ", userAgent='" + userAgent + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                '}';
    }
}
