package com.jauharteam.ojek.webapp;

public class Config {

    private String identityRestPath;
    private String identityServicePath;
    private String userServicePath;
    private String orderServicePath;
    private String locationServicePath;
    private String baseUrl;

    public Config() {
    }

    public String getIdentityServicePath() {
        return identityServicePath;
    }

    public void setIdentityServicePath(String identityServicePath) {
        this.identityServicePath = identityServicePath;
    }

    public String getIdentityRestPath() {
        return identityRestPath;
    }

    public void setIdentityRestPath(String identityRestPath) {
        this.identityRestPath = identityRestPath;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getUserServicePath() {
        return userServicePath;
    }

    public void setUserServicePath(String userServicePath) {
        this.userServicePath = userServicePath;
    }

    public String getOrderServicePath() {
        return orderServicePath;
    }

    public void setOrderServicePath(String orderServicePath) {
        this.orderServicePath = orderServicePath;
    }

    public String getLocationServicePath() {
        return locationServicePath;
    }

    public void setLocationServicePath(String locationServicePath) {
        this.locationServicePath = locationServicePath;
    }

    @Override
    public String toString() {
        return "Config{" +
                "identityRestPath='" + identityRestPath + '\'' +
                ", identityServicePath='" + identityServicePath + '\'' +
                ", userServicePath='" + userServicePath + '\'' +
                ", orderServicePath='" + orderServicePath + '\'' +
                ", locationServicePath='" + locationServicePath + '\'' +
                ", baseUrl='" + baseUrl + '\'' +
                '}';
    }
}
