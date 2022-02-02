package com.etbakhly_provider.model;

import java.io.Serializable;

public class OptionModel implements Serializable {
    public String id;
    public String titel;
    public String photo;
    public String icon;
    public String created_at;
    public String updated_at;

    public String getId() {
        return id;
    }

    public String getTitel() {
        return titel;
    }

    public String getPhoto() {
        return photo;
    }

    public String getIcon() {
        return icon;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }
}
