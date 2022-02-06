package com.etbakhly_provider.model;

import java.io.Serializable;
import java.util.List;

public class OrderDataModel extends StatusResponse implements Serializable {
    private List<OrderModel> myOrder;


    public List<OrderModel> getData() {
        return myOrder;
    }
}
