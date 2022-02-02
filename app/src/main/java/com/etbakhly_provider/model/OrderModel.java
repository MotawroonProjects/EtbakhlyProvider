package com.etbakhly_provider.model;

import java.io.Serializable;
import java.util.List;

public class OrderModel implements Serializable {
    public String id;
    public String user_id;
    public String caterer_id;
    public String option_id;
    public String total;
    public String address_id;
    public String notes;
    public String booking_date;
    public String status_order;
    public String is_end;
    public String cancel_by;
    public String is_pay;
    public String created_at;
    public String updated_at;
    public UserModel.Data.User user;
    public AddressModel addressModel;
    public CatererModel catererModel;
    public OptionModel optionModel;
    public List<OrderDetail> order_details;

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

    public String getAddress_id() {
        return address_id;
    }

    public String getNotes() {
        return notes;
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

    public String getIs_pay() {
        return is_pay;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public UserModel.Data.User getUser() {
        return user;
    }

    public AddressModel getAddress() {
        return addressModel;
    }

    public CatererModel getCaterer() {
        return catererModel;
    }

    public OptionModel getOption() {
        return optionModel;
    }

    public List<OrderDetail> getOrder_details() {
        return order_details;
    }
    public static class OrderDetail implements Serializable{
        public String id;
        public String order_id;
        public String offer_id;
        public String qty;
        public String dishes_id;
        public String buffets_id;
        public String feast_id;
        public String created_at;
        public String updated_at;
        public String offer;
        public String dishes;
        public BuffetModel buffetModel;
        public FeastModel feastModel;

        public String getId() {
            return id;
        }

        public String getOrder_id() {
            return order_id;
        }

        public String getOffer_id() {
            return offer_id;
        }

        public String getQty() {
            return qty;
        }

        public String getDishes_id() {
            return dishes_id;
        }

        public String getBuffets_id() {
            return buffets_id;
        }

        public String getFeast_id() {
            return feast_id;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public String getOffer() {
            return offer;
        }

        public String getDishes() {
            return dishes;
        }

        public BuffetModel getBuffetModel() {
            return buffetModel;
        }

        public FeastModel getFeastModel() {
            return feastModel;
        }
    }
}
