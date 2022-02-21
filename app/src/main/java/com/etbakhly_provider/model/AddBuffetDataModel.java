package com.etbakhly_provider.model;

import java.io.Serializable;

public class AddBuffetDataModel extends StatusResponse implements Serializable {
    private AddBuffetModel data;

    public AddBuffetModel getData() {
        return data;
    }
}
