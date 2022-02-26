package com.etbakhly_provider.model;

import android.content.Context;
import android.widget.Toast;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;

import com.etbakhly_provider.BR;
import com.etbakhly_provider.R;

public class AddBuffetModel extends BaseObservable {
    private String titel;
    private String number_people;
    private String service_provider_type;
    private String order_time;
    private String price;
    private String category_dishes_id;
    private String caterer_id;
    private String photo;
    private String id;

    public ObservableField<String> error_title = new ObservableField<>();
    public ObservableField<String> error_price = new ObservableField<>();
    public ObservableField<String> error_number_people = new ObservableField<>();
    public ObservableField<String> error_service_provider_type = new ObservableField<>();
    public ObservableField<String> error_order_time = new ObservableField<>();

    public boolean isDataValid(Context context) {
        if (!titel.isEmpty() &&
                !number_people.isEmpty() &&
                !service_provider_type.isEmpty() &&
                !order_time.isEmpty() &&
                !price.isEmpty() &&
                !photo.isEmpty()
        ) {
            error_title.set(null);
            error_price.set(null);
            error_number_people.set(null);
            error_service_provider_type.set(null);
            error_order_time.set(null);
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
            if (number_people.isEmpty()) {
                error_number_people.set(context.getString(R.string.field_required));
            } else {
                error_number_people.set(null);
            }
            if (service_provider_type.isEmpty()) {
                error_service_provider_type.set(context.getString(R.string.field_required));
            } else {
                error_service_provider_type.set(null);
            }
            if (order_time.isEmpty()) {
                error_order_time.set(context.getString(R.string.field_required));
            } else {
                error_order_time.set(null);
            }
        }
        return false;
    }

    public AddBuffetModel() {
        titel = "";
        number_people = "";
        service_provider_type = "";
        order_time = "";
        price = "";
        category_dishes_id = "";
        caterer_id = "";
        photo = "";
        id = "";
    }

    @Bindable
    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
        notifyPropertyChanged(BR.titel);
    }

    @Bindable
    public String getNumber_people() {
        return number_people;
    }

    public void setNumber_people(String number_people) {
        this.number_people = number_people;
        notifyPropertyChanged(BR.number_people);
    }

    @Bindable
    public String getService_provider_type() {
        return service_provider_type;
    }

    public void setService_provider_type(String service_provider_type) {
        this.service_provider_type = service_provider_type;
        notifyPropertyChanged(BR.service_provider_type);
    }

    @Bindable
    public String getOrder_time() {
        return order_time;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
        notifyPropertyChanged(BR.order_time);
    }

    @Bindable
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
        notifyPropertyChanged(BR.price);
    }

    public String getCategory_dishes_id() {
        return category_dishes_id;
    }

    public void setCategory_dishes_id(String category_dishes_id) {
        this.category_dishes_id = category_dishes_id;
    }

    public String getCaterer_id() {
        return caterer_id;
    }

    public void setCaterer_id(String caterer_id) {
        this.caterer_id = caterer_id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
