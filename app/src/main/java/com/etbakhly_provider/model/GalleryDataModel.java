package com.etbakhly_provider.model;

import java.io.Serializable;
import java.util.List;

public class GalleryDataModel extends StatusResponse implements Serializable {
    private List<KitchenModel.Photo> data;

    public List<KitchenModel.Photo> getData() {
        return data;
    }
}
