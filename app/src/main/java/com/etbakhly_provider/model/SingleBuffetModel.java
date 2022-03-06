package com.etbakhly_provider.model;

import java.io.Serializable;

public class SingleBuffetModel extends StatusResponse implements Serializable {
    private BuffetModel data;

    public BuffetModel getData() {
        return data;
    }
}
