package com.etbakhly_provider.model;

import java.io.Serializable;
import java.util.List;

public class OfferDataModel extends StatusResponse implements Serializable {
    private List<OfferModel> data;

    public List<OfferModel> getData() {
        return data;
    }
}
