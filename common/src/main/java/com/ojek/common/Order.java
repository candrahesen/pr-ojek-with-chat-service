package com.ojek.common;

import java.util.Date;

public class Order {

    Integer id;
    Integer driverId;
    Integer customerId;
    Boolean customerShow;
    Boolean driverShow;
    Integer locationId;
    Integer destinationId;
    Date time;
    Integer rate;
    String comment;

    public Order() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDriverId() {
        return driverId;
    }

    public void setDriverId(Integer driverId) {
        this.driverId = driverId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Boolean getCustomerShow() {
        return customerShow;
    }

    public void setCustomerShow(Boolean customerShow) {
        this.customerShow = customerShow;
    }

    public Boolean getDriverShow() {
        return driverShow;
    }

    public void setDriverShow(Boolean driverShow) {
        this.driverShow = driverShow;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public Integer getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(Integer destinationId) {
        this.destinationId = destinationId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", driverId=" + driverId +
                ", customerId=" + customerId +
                ", customerShow=" + customerShow +
                ", driverShow=" + driverShow +
                ", locationId=" + locationId +
                ", destinationId=" + destinationId +
                ", time=" + time +
                ", rate=" + rate +
                ", comment='" + comment + '\'' +
                '}';
    }
}
