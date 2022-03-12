package com.etbakhly_provider.model;

import java.io.Serializable;

public class SingleOfferDataModel extends StatusResponse implements Serializable {
    private OfferModel data;

    public OfferModel getData() {
        return data;
    }
}
