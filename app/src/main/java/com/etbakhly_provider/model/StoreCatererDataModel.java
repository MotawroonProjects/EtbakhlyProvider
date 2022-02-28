package com.etbakhly_provider.model;

import android.content.Context;
import android.widget.Toast;


import java.io.Serializable;
import java.util.List;

public class StoreCatererDataModel implements Serializable {
    private String user_id;
    private String notes;
    private String  option_id;
    private String category_id;
    private String sex_type;
    private String is_delivry;
    private String  delivry_time;
    private String processing_time;
    private String free_delivery;
    private String longitude;
    private String latitude;
    private String start_work;
    private String end_work;
    private String tax;
    private String customers_service;
    private String discount;
    private String Number_of_reservation_days;
    private String commercial_register;
    private String address;
    private List<DeliveryModel> delivry;

    public StoreCatererDataModel(String user_id, String notes, String option_id, String category_id, String sex_type, String is_delivry, String delivry_time, String processing_time, String free_delivery, String longitude, String latitude, String start_work, String end_work, String tax, String customers_service, String discount, String number_of_reservation_days, String commercial_register, String address, List<DeliveryModel> delivry) {
        this.user_id = user_id;
        this.notes = notes;
        this.option_id = option_id;
        this.category_id = category_id;
        this.sex_type = sex_type;
        this.is_delivry = is_delivry;
        this.delivry_time = delivry_time;
        this.processing_time = processing_time;
        this.free_delivery = free_delivery;
        this.longitude = longitude;
        this.latitude = latitude;
        this.start_work = start_work;
        this.end_work = end_work;
        this.tax = tax;
        this.customers_service = customers_service;
        this.discount = discount;
        Number_of_reservation_days = number_of_reservation_days;
        this.commercial_register = commercial_register;
        this.address = address;
        this.delivry = delivry;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getOption_id() {
        return option_id;
    }

    public void setOption_id(String option_id) {
        this.option_id = option_id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getSex_type() {
        return sex_type;
    }

    public void setSex_type(String sex_type) {
        this.sex_type = sex_type;
    }

    public String getIs_delivry() {
        return is_delivry;
    }

    public void setIs_delivry(String is_delivry) {
        this.is_delivry = is_delivry;
    }

    public String getDelivry_time() {
        return delivry_time;
    }

    public void setDelivry_time(String delivry_time) {
        this.delivry_time = delivry_time;
    }

    public String getProcessing_time() {
        return processing_time;
    }

    public void setProcessing_time(String processing_time) {
        this.processing_time = processing_time;
    }

    public String getFree_delivery() {
        return free_delivery;
    }

    public void setFree_delivery(String free_delivery) {
        this.free_delivery = free_delivery;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getStart_work() {
        return start_work;
    }

    public void setStart_work(String start_work) {
        this.start_work = start_work;
    }

    public String getEnd_work() {
        return end_work;
    }

    public void setEnd_work(String end_work) {
        this.end_work = end_work;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String  getCustomers_service() {
        return customers_service;
    }

    public void setCustomers_service(String customers_service) {
        this.customers_service = customers_service;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getNumber_of_reservation_days() {
        return Number_of_reservation_days;
    }

    public void setNumber_of_reservation_days(String Number_of_reservation_days) {
        this.Number_of_reservation_days = Number_of_reservation_days;
    }

    public String getCommercial_register() {
        return commercial_register;
    }

    public void setCommercial_register(String commercial_register) {
        this.commercial_register = commercial_register;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<DeliveryModel> getDelivry() {
        return delivry;
    }

    public void setDelivry(List<DeliveryModel> delivry) {
        this.delivry = delivry;
    }
}
