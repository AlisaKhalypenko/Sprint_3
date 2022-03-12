package com.ya;

import java.util.List;

public class Orders {
    public int id;
    public int courierId;
    public String firstName;
    public String lastName;
    public String address;
    public String metroStation;
    public String phone;
    public int rentTime;
    public String deliveryDate;
    public int track;
    public List<String> color;
    public String comment;
    public String createdAt;
    public String updatedAt;
    public int status;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName (String firstName){
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName (String lastName){
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress (String address){
        this.address = address;
    }

    public String getMetroStation() {
        return metroStation;
    }

    public void setMetroStation (String metroStation){
        this.metroStation = metroStation;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone (String phone){
        this.phone = phone;
    }

    public int getRentTime() {
        return rentTime;
    }

    public void setRentTime (int rentTime){
        this.rentTime = rentTime;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate (String deliveryDate){
        this.deliveryDate = deliveryDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment (String comment){
        this.comment = comment;
    }

    public List<String> getColor() {
        return color;
    }

    public List<String> setColor (List<String> color){
        this.color = color;
        return color;
    }

    public int getId() {
        return id;
    }

    public void setId (int id){
        this.id = id;
    }

    public int getCourierId() {
        return courierId;
    }

    public void setCourierId (int courierId){
        this.courierId = courierId;
    }

    public int getTrack() {
        return track;
    }

    public void setTrack (int track){
        this.track = track;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt (String createdAt){
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt (String updatedAt){
        this.updatedAt = updatedAt;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus (int status){
        this.status = status;
    }
}
