package com.etbakhly_provider.model;

import java.io.Serializable;
import java.util.List;

public class ZoneDataModel extends StatusResponse implements Serializable {
    private List<ZoneCover> data;

    public List<ZoneCover> getData() {
        return data;
    }
}
