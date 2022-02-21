package com.etbakhly_provider.model;

import java.io.Serializable;

public class DeliveryModel implements Serializable {
    private int zone_id;
    private double zone_cost;

    public int getZone_id() {
        return zone_id;
    }

    public void setZone_id(int zone_id) {
        this.zone_id = zone_id;
    }

    public double getZone_cost() {
        return zone_cost;
    }

    public void setZone_cost(double zone_cost) {
        this.zone_cost = zone_cost;
    }
}
