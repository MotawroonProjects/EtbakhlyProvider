package com.etbakhly_provider.model;

import java.io.Serializable;
import java.util.List;

public class SingleOrderDataModel extends StatusResponse implements Serializable {
    private SingleOrderModel SingelOrder;

    public SingleOrderModel getSingelOrder() {
        return SingelOrder;
    }
}
