package com.etbakhly_provider.model;

import java.io.Serializable;

public class SingleKitchenDataModel  extends StatusResponse implements Serializable {
    private KitchenModel data;

    public KitchenModel getData() {
        return data;
    }
}
