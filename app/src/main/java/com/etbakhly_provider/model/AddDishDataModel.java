package com.etbakhly_provider.model;

import java.io.Serializable;

public class AddDishDataModel extends StatusResponse implements Serializable {
    private AddDishModel data;

    public AddDishModel getData() {
        return data;
    }
}
