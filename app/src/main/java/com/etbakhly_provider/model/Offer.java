package com.etbakhly_provider.model;

import java.io.Serializable;

public class Offer implements Serializable {
    public String id;
    public String name;
    public String title;
    public String sub_titel;
    public String photo;
    public String end_date;
    public String caterer_id;
    public String option_id;
    public String price;
    public String created_at;
    public String updated_at;
    public CatererModel caterer;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public String getSub_titel() {
        return sub_titel;
    }

    public String getPhoto() {
        return photo;
    }

    public String getEnd_date() {
        return end_date;
    }

    public String getCaterer_id() {
        return caterer_id;
    }

    public String getOption_id() {
        return option_id;
    }

    public String getPrice() {
        return price;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public CatererModel getCaterer() {
        return caterer;
    }
}
