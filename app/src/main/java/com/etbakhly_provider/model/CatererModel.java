package com.etbakhly_provider.model;

import java.io.Serializable;
import java.util.List;

public class CatererModel implements Serializable {
    public String id;
    public String category_id;
    public String user_id;
    public String option_id;
    public String notes;
    public String sex_type;
    public String is_delivry;
    public String delivry_time;
    public String processing_time;
    public String number_of_reservation_days;
    public String is_Special;
    public String free_delivery;
    public String longitude;
    public String latitude;
    public String address;
    public String start_work;
    public String end_work;
    public String tax;
    public String customers_service;
    public String delivry_cost;
    public String discount;
    public String commercial_register;
    public String is_completed;
    public String status;
    public String created_at;
    public String updated_at;
    public String rates_val;
    public String rates_count;
    public UserModel.Data user;
    public List<Rate> rate;

    public String getId() {
        return id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getOption_id() {
        return option_id;
    }

    public String getNotes() {
        return notes;
    }

    public String getSex_type() {
        return sex_type;
    }

    public String getIs_delivry() {
        return is_delivry;
    }

    public String getDelivry_time() {
        return delivry_time;
    }

    public String getProcessing_time() {
        return processing_time;
    }

    public String getNumber_of_reservation_days() {
        return number_of_reservation_days;
    }

    public String getIs_Special() {
        return is_Special;
    }

    public String getFree_delivery() {
        return free_delivery;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getAddress() {
        return address;
    }

    public String getStart_work() {
        return start_work;
    }

    public String getEnd_work() {
        return end_work;
    }

    public String getTax() {
        return tax;
    }

    public String getCustomers_service() {
        return customers_service;
    }

    public String getDelivry_cost() {
        return delivry_cost;
    }

    public String getDiscount() {
        return discount;
    }

    public String getCommercial_register() {
        return commercial_register;
    }

    public String getIs_completed() {
        return is_completed;
    }

    public String getStatus() {
        return status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public String getRates_val() {
        return rates_val;
    }

    public String getRates_count() {
        return rates_count;
    }

    public UserModel.Data getUser() {
        return user;
    }

    public List<Rate> getRate() {
        return rate;
    }
    public static class Rate implements Serializable{
        public String id;
        public String user_id;
        public String caterer_id;
        public String value;
        public String comment;
        public String created_at;
        public String updated_at;

        public String getId() {
            return id;
        }

        public String getUser_id() {
            return user_id;
        }

        public String getCaterer_id() {
            return caterer_id;
        }

        public String getValue() {
            return value;
        }

        public String getComment() {
            return comment;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }
    }
}
