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
    private Context context;


    public AddZoneModel(Context context) {
        title = "";
        zone_cost = "";
        this.context= context;
    }

    public String getZone_id() {
        return zone_id;
    }

    public void setZone_id(String zone_id) {
        this.zone_id = zone_id;
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    @Bindable
    public String getZone_cost() {
        return zone_cost;
    }

    public void setZone_cost(String zone_cost) {
        this.zone_cost = zone_cost;
        notifyPropertyChanged(BR.zone_cost);


    }


}
