package com.etbakhly_provider.model;

import java.io.Serializable;

public class AddFeastDishDataModel extends StatusResponse implements Serializable {
    private AddFeastDishModel data;

    public AddFeastDishModel getData() {
        return data;
    }
}
