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
    private String phone_code;
    private String phone;
    private String name;
    private String email;
    private String service;
    private double lat;
    private double lng;
    public ObservableField<String> error_name = new ObservableField<>();
    public ObservableField<String> error_email = new ObservableField<>();


    public boolean isDataValid(Context context) {
        if (!name.isEmpty() &&
                !email.isEmpty() &&
                !service.isEmpty()&&
                Patterns.EMAIL_ADDRESS.matcher(email).matches()

        ) {
            error_name.set(null);
            error_email.set(null);

            return true;
        } else {

            if (name.isEmpty()) {
                error_name.set(context.getString(R.string.field_required));

            } else {
                error_name.set(null);

            }
            if (email.isEmpty()) {
                error_email.set(context.getString(R.string.field_required));

            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                error_email.set(context.getString(R.string.inv_email));

            } else {
                error_email.set(null);

            }

if(service.isEmpty()){
    Toast.makeText(context, context.getResources().getString(R.string.ch_service), Toast.LENGTH_SHORT).show();
}
            return false;
        }
    }

    public RegisterModel(String phone_code, String phone) {
        setPhone_code(phone_code);
        setPhone(phone);
        setName("");
        setEmail("");
        setService("");

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
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);

    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }
}