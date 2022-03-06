package com.etbakhly_provider.model;

import java.io.Serializable;

public class SingleDishModel extends StatusResponse implements Serializable {
    private DishModel data;

    public DishModel getData() {
        return data;
    }
}
