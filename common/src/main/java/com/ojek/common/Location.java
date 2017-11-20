package com.ojek.common;

/**
 * Created by dery on 11/4/17.
 */
public class Location {
    Integer id;
    String location;
    Integer userId;
    Integer prefNum;

    public Location() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPrefNum() {
        return prefNum;
    }

    public void setPrefNum(Integer prefNum) {
        this.prefNum = prefNum;
    }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", location='" + location + '\'' +
                ", userId=" + userId +
                ", prefNum=" + prefNum +
                '}';
    }
}
