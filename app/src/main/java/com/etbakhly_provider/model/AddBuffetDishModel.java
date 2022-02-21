package com.etbakhly_provider.model;

import android.content.Context;
import android.widget.Toast;

import androidx.databinding.BaseObservable;
import androidx.databinding.ObservableField;

import com.etbakhly_provider.R;

public class AddBuffetDishModel extends BaseObservable {
    private String titel;
    private String category_dishes_id;
    private String price;
    private String details;
    private String qty;
    private String buffets_id;
    private String photo;
    private String updated_at;
    private String created_at;
    private String id;

    public ObservableField<String> error_title = new ObservableField<>();
    public ObservableField<String> error_price = new ObservableField<>();
    public ObservableField<String> error_details = new ObservableField<>();
    public ObservableField<String> error_qty = new ObservableField<>();

    public Boolean isDataValid(Context context){
        if (!titel.isEmpty() &&
                !price.isEmpty() &&
                !details.isEmpty() &&
                !qty.isEmpty() &&
                !photo.isEmpty()){
            error_title.set(null);
            error_price.set(null);
            error_details.set(null);
            error_qty.set(null);
            return true;
        }else {
            if (photo.isEmpty()){
                Toast.makeText(context, R.string.choose_image, Toast.LENGTH_SHORT).show();
            }
            if (titel.isEmpty()){
                error_title.set(context.getString(R.string.field_required));
            }
            if (price.isEmpty()){
                error_price.set(context.getString(R.string.field_required));
            }
            if (details.isEmpty()){
                error_details.set(context.getString(R.string.field_required));
            }
            if (qty.isEmpty()){
                error_qty.set(context.getString(R.string.field_required));
            }
        }
        return false;
    }

    public AddBuffetDishModel() {
        titel="";
        price="";
        details="";
        category_dishes_id="";
        qty="";
        id="";
        buffets_id="";
        updated_at="";
        created_at="";
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getCategory_dishes_id() {
        return category_dishes_id;
    }

    public void setCategory_dishes_id(String category_dishes_id) {
        this.category_dishes_id = category_dishes_id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getBuffets_id() {
        return buffets_id;
    }

    public void setBuffets_id(String buffets_id) {
        this.buffets_id = buffets_id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
