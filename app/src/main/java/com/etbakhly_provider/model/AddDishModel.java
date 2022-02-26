package com.etbakhly_provider.model;

import android.content.Context;
import android.widget.Toast;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;

import com.etbakhly_provider.BR;
import com.etbakhly_provider.R;

public class AddDishModel extends BaseObservable {
    private String titel;
    private String category_dishes_id;
    private String price;
    private String details;
    private String qty;
    private String caterer_id;
    private String photo;
    private String id;

    public ObservableField<String> error_title = new ObservableField<>();
    public ObservableField<String> error_price = new ObservableField<>();
    public ObservableField<String> error_details = new ObservableField<>();
    public ObservableField<String> error_qty = new ObservableField<>();

    public Boolean isDataValid(Context context) {
        if (!titel.isEmpty() &&
                !price.isEmpty() &&
                !details.isEmpty() &&
                !qty.isEmpty() &&
                !photo.isEmpty()) {

            error_title.set(null);
            error_price.set(null);
            error_details.set(null);
            error_qty.set(null);
            return true;
        } else {
            if (photo.isEmpty()) {
                Toast.makeText(context, R.string.choose_image, Toast.LENGTH_SHORT).show();
            }
            if (titel.isEmpty()) {
                error_title.set(context.getString(R.string.field_required));
            } else {
                error_title.set(null);
            }
            if (price.isEmpty()) {
                error_price.set(context.getString(R.string.field_required));
            } else {
                error_price.set(null);
            }
            if (details.isEmpty()) {
                error_details.set(context.getString(R.string.field_required));
            } else {
                error_details.set(null);
            }
            if (qty.isEmpty()) {
                error_qty.set(context.getString(R.string.field_required));
            } else {
                error_qty.set(null);
            }
        }
        return false;
    }

    public AddDishModel() {
        titel = "";
        price = "";
        details = "";
        category_dishes_id = "";
        qty = "";
        photo = "";
        id = "";
        caterer_id = "";

    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Bindable
    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
        notifyPropertyChanged(BR.titel);
    }

    public String getCategory_dishes_id() {
        return category_dishes_id;
    }

    public void setCategory_dishes_id(String category_dishes_id) {
        this.category_dishes_id = category_dishes_id;
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
    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
        notifyPropertyChanged(BR.details);
    }

    @Bindable
    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
        notifyPropertyChanged(BR.qty);
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
