package com.etbakhly_provider.model;

import java.io.Serializable;
import java.util.List;

public class CategoryDishModel implements Serializable {
    public String id;
    public String titel;
    public String caterer_id;
    public String created_at;
    public String updated_at;
    public List<Dish> dishes;

    public String getId() {
        return id;
    }

    public String getTitel() {
        return titel;
    }

    public String getCaterer_id() {
        return caterer_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public List<Dish> getDishes() {
        return dishes;
    }
    public static class Dish implements Serializable{

        public int id;
        public String category_dishes_id;
        public String caterer_id;
        public String buffets_id;
        public String feast_id;
        public String titel;
        public String photo;
        public String price;
        public String details;
        public String qty;
        public String created_at;
        public String updated_at;

        public int getId() {
            return id;
        }

        public String getCategory_dishes_id() {
            return category_dishes_id;
        }

        public String getCaterer_id() {
            return caterer_id;
        }

        public String getBuffets_id() {
            return buffets_id;
        }

        public String getFeast_id() {
            return feast_id;
        }

        public String getTitel() {
            return titel;
        }

        public String getPhoto() {
            return photo;
        }

        public String getPrice() {
            return price;
        }

        public String getDetails() {
            return details;
        }

        public String getQty() {
            return qty;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }
    }
}
