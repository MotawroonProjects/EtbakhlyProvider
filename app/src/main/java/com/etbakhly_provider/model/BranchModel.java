package com.etbakhly_provider.model;

import java.io.Serializable;

public class BranchModel implements Serializable {
    private String id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String company_id;
    private String is_active;
    private String created_at;
    private String updated_at;
    private String latitude;
    private String longitude;
    private boolean selected;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getCompany_id() {
        return company_id;
    }

    public String getIs_active() {
        return is_active;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }
}
