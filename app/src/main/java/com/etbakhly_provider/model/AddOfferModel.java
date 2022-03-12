package com.etbakhly_provider.model;

import android.content.Context;
import android.widget.Toast;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;

import com.etbakhly_provider.BR;
import com.etbakhly_provider.R;

import java.util.ArrayList;
import java.util.List;

public class AddOfferModel extends BaseObservable {
    private String title;
    private String sub_titel;
    private String price;
    private String end_date;
    private String option_id;
    private String caterer_id;
    private String photo;
    private String id;

    public ObservableField<String> error_title = new ObservableField<>();
    public ObservableField<String> error_sub_title = new ObservableField<>();
    public ObservableField<String> error_price = new ObservableField<>();
    public ObservableField<String> error_end_date = new ObservableField<>();

    public Boolean isDataValid(Context context) {
        if (!title.isEmpty() &&
                !sub_titel.isEmpty() &&
                !price.isEmpty() &&
                !end_date.isEmpty() &&
                !photo.isEmpty()) {

            error_title.set(null);
            error_price.set(null);
            error_sub_title.set(null);
            error_end_date.set(null);
            return true;
        } else {
            if (photo.isEmpty()) {
                Toast.makeText(context, R.string.choose_image, Toast.LENGTH_SHORT).show();
            }
            if (title.isEmpty()) {
                error_title.set(context.getString(R.string.field_required));
            } else {
                error_title.set(null);
            }
            if (price.isEmpty()) {
                error_price.set(context.getString(R.string.field_required));
            } else {
                error_price.set(null);
            }
            if (sub_titel.isEmpty()) {
                error_sub_title.set(context.getString(R.string.field_required));
            } else {
                error_sub_title.set(null);
            }
            if (end_date.isEmpty()) {
                error_end_date.set(context.getString(R.string.field_required));
            } else {
                error_end_date.set(null);
            }


        }
        return false;
    }

    public AddOfferModel() {
        title = "";
        sub_titel = "";
        price = "";
        end_date = "";
        photo = "";
        id = "";
        caterer_id = "";
        option_id = "";
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.titel);
    }

    @Bindable
    public String getSub_titel() {
        return sub_titel;
    }

    public void setSub_titel(String sub_titel) {
        this.sub_titel = sub_titel;
        notifyPropertyChanged(BR.sub_titel);
    }

    @Bindable
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
        notifyPropertyChanged(BR.price);
    }

    @Bindable
    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
        notifyPropertyChanged(BR.end_date);
    }

    public String getOption_id() {
        return option_id;
    }

    public void setOption_id(String option_id) {
        this.option_id = option_id;
    }

    public String getCaterer_id() {
        return caterer_id;
    }

    public void setCaterer_id(String caterer_id) {
        this.caterer_id = caterer_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
