package com.etbakhly_provider.model;

import java.io.Serializable;

public class AddressModel implements Serializable {
    public String id;
    public String user_id;
    public String address;
    public String note;
    public String created_at;
    public String updated_at;

    public String getId() {
        return id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getAddress() {
        return address;
    }

    public String getNote() {
        return note;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }
}
