package com.etbakhly_provider.model;

import java.io.Serializable;
import java.util.List;

public class CountryDataModel extends StatusResponse implements Serializable {
    private List<CountryModel> data;

    public List<CountryModel> getData() {
        return data;
    }
}
