package com.etbakhly_provider.model;

import java.io.Serializable;

public class OfferModel implements Serializable {

    private String id;
    private String name;
    private String title;
    private String sub_titel;
    private String photo;
    private String end_date;
    private String caterer_id;
    private String price;
    private String created_at;
    private String updated_at;
    private boolean isInCart = false;
    private int amountInCart;
    private KitchenModel caterer;

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

    public String getPrice() {
        return price;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public boolean isInCart() {
        return isInCart;
    }

    public void setInCart(boolean inCart) {
        isInCart = inCart;
    }

    public int getAmountInCart() {
        return amountInCart;
    }

    public void setAmountInCart(int amountInCart) {
        this.amountInCart = amountInCart;
    }

    public KitchenModel getCaterer() {
        return caterer;
    }
}
