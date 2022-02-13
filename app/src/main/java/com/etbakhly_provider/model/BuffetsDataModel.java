package com.etbakhly_provider.model;

import java.io.Serializable;
import java.util.List;

public class BuffetsDataModel extends StatusResponse implements Serializable {
    private List<BuffetModel> data;

    public List<BuffetModel> getData() {
        return data;
    }
}
