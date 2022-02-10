package com.etbakhly_provider.model;

import java.io.Serializable;
import java.util.List;

public class SingleOrderModel implements Serializable {
    public String id;
    public String user_id;
    public String caterer_id;
    public String option_id;
    public String total;
    public String zone_id;
    public String notes;
    public String address;
    public String booking_date;
    public String status_order;
    public String is_end;
    public String cancel_by;
    public String why_cancel;
    public String is_pay;
    public String paid_type;
    public String created_at;
    public String updated_at;
    public UserModel user;
    public ZoneModel zones;
    public CatererModel caterer;
    public List<OrderModel.OrderDetail> order_details;
    public OptionModel option;

    public String getId() {
        return id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getCaterer_id() {
        return caterer_id;
    }

    public String getOption_id() {
        return option_id;
    }

    public String getTotal() {
        return total;
    }

    public String getZone_id() {
        return zone_id;
    }

    public String getNotes() {
        return notes;
    }

    public String getAddress() {
        return address;
    }

    public String getBooking_date() {
        return booking_date;
    }

    public String getStatus_order() {
        return status_order;
    }

    public String getIs_end() {
        return is_end;
    }

    public String getCancel_by() {
        return cancel_by;
    }

    public String getWhy_cancel() {
        return why_cancel;
    }

    public String getIs_pay() {
        return is_pay;
    }

    public String getPaid_type() {
        return paid_type;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public UserModel getUser() {
        return user;
    }

    public ZoneModel getZones() {
        return zones;
    }

    public CatererModel getCaterer() {
        return caterer;
    }

    public List<OrderModel.OrderDetail> getOrder_details() {
        return order_details;
    }

    public OptionModel getOption() {
        return option;
    }
}
