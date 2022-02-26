package com.etbakhly_provider.model;

import android.content.Context;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;


import com.etbakhly_provider.BR;

import java.io.Serializable;

public class AddZoneModel extends BaseObservable implements Serializable {
    private String zone_id;
    private String title;
    private String zone_cost;


    public AddZoneModel(String zone_id, String title, String zone_cost) {
        this.zone_id = zone_id;
        this.title = title;
        this.zone_cost = zone_cost;
    }

    public String getZone_id() {
        return zone_id;
    }

    public String getTitle() {
        return title;
    }

    public String getZone_cost() {
        return zone_cost;
    }
}
