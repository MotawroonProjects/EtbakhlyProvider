package com.etbakhly_provider.model;

import android.content.Context;
import android.util.Patterns;
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
    private String note;
    private String address;
    private String from;
    private String to;
    private String tax;
    private String customers_service;
    private String discount;
    private String licenseNumber;
    private int cat_id;
    private String sex_type;
    private String deliverytime;
    private String processingtime;
    private String booking_before;
    private String is_delivery;
    private boolean is_valid1;
    private boolean is_valid2;
    private double lat;
    private double lng;
    private List<AddZoneModel> addZoneModels;
    private Context context;
    public ObservableField<String> error_note = new ObservableField<>();
    public ObservableField<String> error_address = new ObservableField<>();
    public ObservableField<String> error_from = new ObservableField<>();
    public ObservableField<String> error_to = new ObservableField<>();
    public ObservableField<String> error_licenseNumber = new ObservableField<>();
    public ObservableField<String> error_deliverytime = new ObservableField<>();
    public ObservableField<String> error_processingtime = new ObservableField<>();

    public ObservableField<String> error_tax = new ObservableField<>();
    public ObservableField<String> error_customerservice = new ObservableField<>();
    public ObservableField<String> error_discount = new ObservableField<>();

    public SignUpModel(Context context) {
        this.to = "";
        this.licenseNumber = "";
        this.note = "";
        this.from = "";
        this.address = "";
        this.booking_before = "";
        this.is_delivery = "";
        this.deliverytime = "";
        this.processingtime = "";
        this.cat_id = 0;
        this.tax = "";
        this.discount = "";
        this.customers_service = "";
        addZoneModels = new ArrayList<>();
        is_valid1 = false;
        this.context = context;

    }

    public boolean isStep1Valid(Context context) {
        if (!note.isEmpty() &&
                !from.isEmpty()

                && !to.isEmpty() && !deliverytime.isEmpty() &&
                !processingtime.isEmpty() &&
                cat_id != 0 && !is_delivery.isEmpty() && ((is_delivery.equals("delivry") && addZoneModels.size() > 0) || is_delivery.equals("not_delivry"))
        ) {
            error_note.set(null);
            error_from.set(null);
            error_to.set(null);
            error_deliverytime.set(null);
            error_processingtime.set(null);
            setIs_valid1(true);
            return true;
        } else {

            if (note.isEmpty()) {
                error_note.set(context.getString(R.string.field_required));
            } else {
                error_note.set(null);
            }


            if (from.isEmpty()) {
                error_from.set(context.getString(R.string.field_required));
            } else {
                error_from.set(null);
            }
            if (to.isEmpty()) {
                error_to.set(context.getString(R.string.field_required));
            } else {
                error_to.set(null);
            }
            if (deliverytime.isEmpty()) {
                error_deliverytime.set(context.getString(R.string.field_required));
            } else {
                error_deliverytime.set(null);
            }
            if (processingtime.isEmpty()) {
                error_processingtime.set(context.getString(R.string.field_required));
            } else {
                error_processingtime.set(null);
            }


            if (cat_id == 0) {
                Toast.makeText(context, context.getResources().getString(R.string.ch_cat), Toast.LENGTH_LONG).show();

            }
            if (is_delivery.isEmpty()) {
                Toast.makeText(context, context.getResources().getString(R.string.ch_delivert), Toast.LENGTH_LONG).show();
            } else if ((is_delivery.equals("delivry") && addZoneModels.size() == 0)) {
                Toast.makeText(context, context.getResources().getString(R.string.ch_zone), Toast.LENGTH_LONG).show();
            }

            setIs_valid1(false);
            return false;
        }
    }

    public boolean isStep2Valid(Context context) {
        if (
                !licenseNumber.isEmpty()
                        && !address.isEmpty()
        ) {
            error_licenseNumber.set(null);
            error_address.set(null);

            setIs_valid2(true);
            return true;
        } else {
            if (licenseNumber.isEmpty()) {
                error_licenseNumber.set(context.getString(R.string.field_required));
            } else {
                error_licenseNumber.set(null);
            }
            if (address.isEmpty()) {
                error_address.set(context.getString(R.string.field_required));
            } else {
                error_address.set(null);
            }

            return false;
        }
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
    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
        notifyPropertyChanged(BR.from);
        isStep1Valid(context);
    }

    @Bindable
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        notifyPropertyChanged(BR.address);
        isStep2Valid(context);
    }

    public String getBooking_before() {
        return booking_before;
    }

    public void setBooking_before(String booking_before) {
        this.booking_before = booking_before;
    }


    @Bindable
    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
        notifyPropertyChanged(BR.to);
        isStep1Valid(context);

    }

    @Bindable
    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
        notifyPropertyChanged(BR.licenseNumber);
        isStep2Valid(context);


    }


    @Bindable
    public String getDeliverytime() {
        return deliverytime;
    }

    public void setDeliverytime(String deliverytime) {
        this.deliverytime = deliverytime;
        notifyPropertyChanged(BR.deliverytime);
        isStep1Valid(context);

    }

    @Bindable
    public String getProcessingtime() {
        return processingtime;
    }

    public void setProcessingtime(String processingtime) {
        this.processingtime = processingtime;

        notifyPropertyChanged(BR.processingtime);
        isStep1Valid(context);

    }

    public int getCat_id() {
        return cat_id;
    }

    public void setCat_id(int cat_id) {
        this.cat_id = cat_id;
        isStep1Valid(context);

    }

    public String getIs_delivery() {
        return is_delivery;

    }

    public void setIs_delivery(String is_delivery) {
        this.is_delivery = is_delivery;
        isStep1Valid(context);

    }

    public List<AddZoneModel> getAddZoneModels() {
        return addZoneModels;
    }

    public void setAddZoneModels(List<AddZoneModel> addZoneModels) {
        this.addZoneModels = addZoneModels;
        isStep1Valid(context);

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
    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    @Bindable
    public String getCustomers_service() {
        return customers_service;
    }

    public void setCustomers_service(String customers_service) {
        this.customers_service = customers_service;
    }

    @Bindable
    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public boolean isIs_valid2() {
        return is_valid2;
    }

    public void setIs_valid2(boolean is_valid2) {
        this.is_valid2 = is_valid2;
    }

    public String getSex_type() {
        return sex_type;
    }

    public void setSex_type(String sex_type) {
        this.sex_type = sex_type;
    }
}
