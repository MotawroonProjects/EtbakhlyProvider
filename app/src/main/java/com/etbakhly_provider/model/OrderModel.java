package com.etbakhly_provider.model;

import java.io.Serializable;
import java.util.List;

public class OrderModel implements Serializable {
    private String id;
    private String user_id;
    private String caterer_id;
    private String option_id;
    private String total;
    private String coupon_cost ="0.0";
    private String address_id;
    private String address;
    private String notes;
    private String booking_date;
    private String status_order;
    private String is_end;
    private String cancel_by;
    private String is_pay;
    private String created_at;
    private String updated_at;
    private UserModel.Data user;
    private KitchenModel caterer;
    private ZoneCover zone_cover;
    private List<OrderDetail> order_details;

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

    public UserModel.Data getUser() {
        return user;
    }

    public String getAddress() {
        return address;
    }

    public KitchenModel getCaterer() {
        return caterer;
    }

    public ZoneCover getZone_cover() {
        return zone_cover;
    }

    public String getCoupon_cost() {
        return coupon_cost;
    }

    public List<OrderDetail> getOrder_details() {
        return order_details;
    }
    public static class OrderDetail implements Serializable{
        private String id;
        private String order_id;
        private String offer_id;
        private String qty;
        private String dishes_id;
        private String buffets_id;
        private String feast_id;
        private String created_at;
        private String updated_at;
        private OfferModel offer;
        private CategoryDishModel.Dish dishes;
        private BuffetModel buffet;
        private BuffetModel feast;

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

        public OfferModel getOffer() {
            return offer;
        }

        public CategoryDishModel.Dish getDishes() {
            return dishes;
        }

        public BuffetModel getBuffet() {
            return buffet;
        }

        public BuffetModel getFeast() {
            return feast;
        }
    }
}
