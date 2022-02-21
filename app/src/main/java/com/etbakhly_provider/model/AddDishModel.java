package com.etbakhly_provider.model;

import android.content.Context;

import androidx.databinding.BaseObservable;
import androidx.databinding.ObservableField;

import com.etbakhly_provider.R;

public class AddDishModel extends BaseObservable {
    private String titel;
    private String category_dishes_id;
    private String price;
    private String details;
    private String qty;
    private String caterer_id;
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
             !qty.isEmpty()){
            error_title.set(null);
            error_price.set(null);
            error_details.set(null);
            error_qty.set(null);
            return true;
        }else {
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

    public AddDishModel() {
        titel="";
        price="";
        details="";
        category_dishes_id="";
        qty="";
        id="";
        caterer_id="";
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

    public String getCaterer_id() {
        return caterer_id;
    }

    public void setCaterer_id(String caterer_id) {
        this.caterer_id = caterer_id;
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