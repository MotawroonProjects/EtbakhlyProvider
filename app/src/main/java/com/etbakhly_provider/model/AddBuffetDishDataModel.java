package com.etbakhly_provider.model;

import java.io.Serializable;

public class AddBuffetDishDataModel extends StatusResponse implements Serializable {
    private AddBuffetDishModel data;

    public AddBuffetDishModel getData() {
        return data;
    }
}
