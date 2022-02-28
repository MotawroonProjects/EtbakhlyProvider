package com.etbakhly_provider.model;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;

import com.etbakhly_provider.BR;
import com.etbakhly_provider.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SignUpModel extends BaseObservable implements Serializable {
    private String working_time_from;
    private String working_time_to;
    private int cat_id;
    private String delivery_time_from;
    private String delivery_time_to;
    private String process_time_from;
    private String process_time_to;
    private String note;
    private String is_delivery;
    private String address;
    private String lat;
    private String lng;
    private List<AddZoneModel> addZoneModels;


    private String tax;
    private String customers_service;
    private String discount;
    private String licenseNumber;
    private String sex_type;
    private String booking_before;
    private boolean is_valid1;
    private boolean is_valid2;


    public SignUpModel() {
        this.working_time_to = "";
        this.working_time_from = "";
        this.cat_id = 0;
        this.delivery_time_from = "";
        this.delivery_time_to = "";
        this.process_time_from = "";
        this.process_time_to = "";
        this.note = "";
        this.is_delivery = "delivry";
        addZoneModels = new ArrayList<>();
        this.address = "";
        this.licenseNumber = "";
        this.booking_before = "";
        this.tax = "0";
        this.discount = "0";
        this.sex_type = "women";
        this.customers_service = "0";
        is_valid1 = false;
        is_valid2 = false;

    }

    public void isStep1Valid() {
        if (!working_time_from.isEmpty()
                && !working_time_to.isEmpty()
                && cat_id != 0
                && !delivery_time_from.isEmpty()
                && !delivery_time_to.isEmpty()
                && !process_time_from.isEmpty()
                && !process_time_to.isEmpty()

        ) {


            if (is_delivery.equals("delivry")) {
                if (addZoneModels.size() > 0) {

                    setIs_valid1(true);

                } else {
                    setIs_valid1(false);

                }
            } else {
                setIs_valid1(true);

            }

        } else {
            setIs_valid1(false);
        }
    }

    public void isStep2Valid() {
        if (!licenseNumber.isEmpty()
                && !booking_before.isEmpty()
        ) {
            setIs_valid2(true);
        } else {
            setIs_valid2(false);
        }
    }

    @Bindable
    public String getWorking_time_from() {
        return working_time_from;
    }

    public void setWorking_time_from(String working_time_from) {
        this.working_time_from = working_time_from;
        notifyPropertyChanged(BR.working_time_from);
        isStep1Valid();
    }


    public int getCat_id() {
        return cat_id;
    }

    public void setCat_id(int cat_id) {
        this.cat_id = cat_id;
        isStep1Valid();

    }

    @Bindable
    public String getDelivery_time_from() {
        return delivery_time_from;
    }

    public void setDelivery_time_from(String delivery_time_from) {
        this.delivery_time_from = delivery_time_from;
        notifyPropertyChanged(BR.delivery_time_from);
    }

    @Bindable
    public String getDelivery_time_to() {
        return delivery_time_to;
    }

    public void setDelivery_time_to(String delivery_time_to) {
        this.delivery_time_to = delivery_time_to;
        notifyPropertyChanged(BR.delivery_time_to);
    }

    @Bindable
    public String getProcess_time_from() {
        return process_time_from;
    }

    public void setProcess_time_from(String process_time_from) {
        this.process_time_from = process_time_from;
        notifyPropertyChanged(BR.process_time_from);
    }

    @Bindable
    public String getProcess_time_to() {
        return process_time_to;
    }

    public void setProcess_time_to(String process_time_to) {
        this.process_time_to = process_time_to;
        notifyPropertyChanged(BR.process_time_to);
    }

    @Bindable
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
        notifyPropertyChanged(BR.name);
    }


    @Bindable
    public String getWorking_time_to() {
        return working_time_to;
    }

    public void setWorking_time_to(String working_time_to) {
        this.working_time_to = working_time_to;
        notifyPropertyChanged(BR.working_time_to);
        isStep1Valid();

    }


    @Bindable
    public String getIs_delivery() {
        return is_delivery;

    }

    public void setIs_delivery(String is_delivery) {
        this.is_delivery = is_delivery;
        isStep1Valid();

    }

    public List<AddZoneModel> getAddZoneModels() {
        return addZoneModels;
    }

    public void setAddZoneModels(List<AddZoneModel> addZoneModels) {
        this.addZoneModels = addZoneModels;
        isStep1Valid();

    }

    @Bindable
    public boolean isIs_valid1() {
        return is_valid1;
    }

    public void setIs_valid1(boolean is_valid1) {
        this.is_valid1 = is_valid1;
        notifyPropertyChanged(BR.is_valid1);

    }

    @Bindable
    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
        notifyPropertyChanged(BR.licenseNumber);
        isStep2Valid();


    }

    @Bindable
    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
        notifyPropertyChanged(BR.tax);
        isStep2Valid();

    }

    @Bindable
    public String getCustomers_service() {
        return customers_service;
    }

    public void setCustomers_service(String customers_service) {
        this.customers_service = customers_service;
        notifyPropertyChanged(BR.customers_service);
        isStep2Valid();
    }

    @Bindable
    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
        notifyPropertyChanged(BR.discount);
        isStep2Valid();
    }


    @Bindable
    public boolean isIs_valid2() {
        return is_valid2;
    }

    public void setIs_valid2(boolean is_valid2) {
        this.is_valid2 = is_valid2;
        notifyPropertyChanged(BR.is_valid2);
    }

    @Bindable
    public String getSex_type() {
        return sex_type;
    }

    public void setSex_type(String sex_type) {
        this.sex_type = sex_type;
        notifyPropertyChanged(BR.sex_type);
        isStep2Valid();
    }

    @Bindable
    public String getBooking_before() {
        return booking_before;
    }

    public void setBooking_before(String booking_before) {
        this.booking_before = booking_before;
        notifyPropertyChanged(BR.booking_before);
        isStep2Valid();
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }



    public void setLng(String lng) {
        this.lng = lng;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void addZone(AddZoneModel model) {
        if (addZoneModels == null) {
            addZoneModels = new ArrayList<>();
        }
        addZoneModels.add(model);
        isStep1Valid();
    }

    public void removeZone(int pos) {
        if (addZoneModels != null && addZoneModels.size() > 0) {
            addZoneModels.remove(pos);
            isStep1Valid();

        }
    }
}
