package com.etbakhly_provider.model;

import java.io.Serializable;

public class SingleProductDataModel extends StatusResponse implements Serializable {
    private ProductModel data;

    public ProductModel getData() {
        return data;
    }
}
