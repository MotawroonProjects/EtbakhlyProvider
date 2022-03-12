package com.etbakhly_provider.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SingleOrderDataModel extends StatusResponse implements Serializable {
    @SerializedName(value = "SingelOrder", alternate = {"data"})
    private OrderModel SingelOrder;

    public OrderModel getSingelOrder() {
        return SingelOrder;
    }
}
