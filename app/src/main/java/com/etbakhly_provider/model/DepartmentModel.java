package com.etbakhly_provider.model;

import java.io.Serializable;

public class DepartmentModel implements Serializable {
    private String id;
    private String is_sale;
    private String name;
    private String image;
    private Object parent_id;
    private String is_active;
    private String created_at;
    private String updated_at;

    public String getId() {
        return id;
    }

    public String getIs_sale() {
        return is_sale;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public Object getParent_id() {
        return parent_id;
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
}
