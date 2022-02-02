package com.etbakhly_provider.model;

import java.io.Serializable;
import java.util.List;

public class BuffetModel implements Serializable {
    public String id;
    public String titel;
    public String photo;
    public String order_time;
    public String service_provider_type;
    public String price;
    public String category_dishes_id;
    public String caterer_id;
    public String is_completed;
    public String created_at;
    public String updated_at;
    public List<CategoryDishModel> categor_dishes;

    public String getId() {
        return id;
    }

    public String getTitel() {
        return titel;
    }

    public String getPhoto() {
        return photo;
    }

    public String getOrder_time() {
        return order_time;
    }

    public String getService_provider_type() {
        return service_provider_type;
    }

    public String getPrice() {
        return price;
    }

    public String getCategory_dishes_id() {
        return category_dishes_id;
    }

    public String getCaterer_id() {
        return caterer_id;
    }

    public String getIs_completed() {
        return is_completed;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public List<CategoryDishModel> getCategor_dishes() {
        return categor_dishes;
    }
}
