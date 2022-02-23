package com.etbakhly_provider.model;

import java.io.Serializable;

public class SingleCategory extends StatusResponse implements Serializable {
    private BuffetModel.Category data;

    public BuffetModel.Category getData() {
        return data;
    }
}
