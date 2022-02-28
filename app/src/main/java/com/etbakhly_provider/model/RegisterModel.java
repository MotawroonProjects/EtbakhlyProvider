package com.etbakhly_provider.model;

import android.content.Context;
import android.util.Patterns;
import android.widget.Toast;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;

import com.etbakhly_provider.BR;
import com.etbakhly_provider.R;


public class RegisterModel extends BaseObservable {
    private String photoUrl;
    private String phone_code;
    private String phone;
    private String name;
    private String email;
    private String service;
    private String address;
    private double lat;
    private double lng;
    private boolean isValid;


    public void isDataValid() {
        if (!photoUrl.isEmpty() &&
                !name.isEmpty() &&
                !email.isEmpty() &&
                !address.isEmpty() &&
                !service.isEmpty() &&
                Patterns.EMAIL_ADDRESS.matcher(email).matches()

        ) {

            setValid(true);

        } else {


            setValid(false);

        }
    }

    public RegisterModel(String phone_code, String phone) {
        setPhone_code(phone_code);
        setPhone(phone);
        setPhotoUrl("");
        setName("");
        setEmail("");
        setService("");
        setAddress("");


    }

    @Bindable
    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
        isDataValid();
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public String getPhone_code() {
        return phone_code;
    }

    public void setPhone_code(String phone_code) {
        this.phone_code = phone_code;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
        isDataValid();
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);
        isDataValid();


    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
        isDataValid();

    }

    @Bindable
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        notifyPropertyChanged(BR.address);
        isDataValid();

    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    @Bindable
    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
        notifyPropertyChanged(BR.valid);
    }
}