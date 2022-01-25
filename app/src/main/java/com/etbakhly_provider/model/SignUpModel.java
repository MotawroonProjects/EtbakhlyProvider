package com.etbakhly_provider.model;

import android.content.Context;
import android.util.Patterns;
import android.widget.Toast;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;

import com.etbakhly_provider.BR;
import com.etbakhly_provider.R;

public class SignUpModel extends BaseObservable  {
    private String imageUrl;
    private String name;
    private int service_id;
    private String address;
    private String from;
    private String to;
    private int area_id;
    private String licenseNumber;
    private int cat_id;
    private String deliverytime;
    private String processingtime;
    private String booking_before;
    private String service_provider;
    private String email;
    private String password;

    public ObservableField<String> error_name = new ObservableField<>();
    public ObservableField<String> error_address = new ObservableField<>();
    public ObservableField<String> error_email = new ObservableField<>();
    public ObservableField<String> error_password = new ObservableField<>();


    public SignUpModel(String to, String licenseNumber) {
        this.to = to;
        this.licenseNumber = licenseNumber;
        this.imageUrl="";
        this.name = "";
        this.from ="";
        this.address = "";
        this.booking_before ="";
        this.service_provider = "";
        this.service_id = 1;
        this.area_id = 1;
        this.deliverytime ="";
        this.processingtime ="";
        this.cat_id =1;
        this.email = "";
        this.password ="";

    }

    public boolean isStep1Valid(Context context)
    {
        if (!name.isEmpty()&&
                !from.isEmpty()
                &&!address.isEmpty()){
            error_name.set(null);
            error_address.set(null);
            return true;
        }else {
            if (name.isEmpty()){
                error_name.set(context.getString(R.string.field_required));
            }else {
                error_name.set(null);
            }

            if (address.isEmpty()){
                error_address.set(context.getString(R.string.field_required));
            }else {
                error_address.set(null);
            }

            return false;
        }
    }
    public boolean isStep2Valid(Context context)
    {
        if (service_id !=0&&
                area_id !=0&&
                !deliverytime.isEmpty()&&
                !processingtime.isEmpty()&&
                cat_id !=0
        )
        {
            return true;
        }else {
            if (service_id ==0){
            }

            if (area_id ==0){
            }



            if (cat_id ==0){
            }

            if (processingtime.isEmpty()){
            }
            return false;
        }
    }
    public boolean isStep3Valid(Context context){
        if (!email.isEmpty()&&
                Patterns.EMAIL_ADDRESS.matcher(email).matches()&&
                !password.isEmpty()&&password.length()>=6
        ){
            error_email.set(null);
            error_password.set(null);
            return true;
        }else {
            if (email.isEmpty()){
                error_email.set(context.getString(R.string.field_required));
            }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                error_email.set(context.getString(R.string.inv_email));

            }else {
                error_email.set(null);
            }

            if (password.isEmpty()){
                error_password.set(context.getString(R.string.field_required));
            }else if (password.length()<6){
                error_password.set(context.getString(R.string.password_short));
            }else {
                error_password.set(null);
            }
            return false;
        }
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

   @Bindable
   public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    @Bindable
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        notifyPropertyChanged(BR.address);
    }

    public String getBooking_before() {
        return booking_before;
    }

    public void setBooking_before(String booking_before) {
        this.booking_before = booking_before;
    }

    public String getService_provider() {
        return service_provider;
    }

    public void setService_provider(String service_provider) {
        this.service_provider = service_provider;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public int getService_id() {
        return service_id;
    }

    public void setService_id(int service_id) {
        this.service_id = service_id;
    }

    public int getArea_id() {
        return area_id;
    }

    public void setArea_id(int area_id) {
        this.area_id = area_id;
    }

    @Bindable
    public String getDeliverytime() {
        return deliverytime;
    }

    public void setDeliverytime(String deliverytime) {
        this.deliverytime = deliverytime;
        notifyPropertyChanged(BR.deliverytime);
    }

    public String getProcessingtime() {
        return processingtime;
    }

    public void setProcessingtime(String processingtime) {
        this.processingtime = processingtime;
    }

    public int getCat_id() {
        return cat_id;
    }

    public void setCat_id(int cat_id) {
        this.cat_id = cat_id;
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
    }
}
