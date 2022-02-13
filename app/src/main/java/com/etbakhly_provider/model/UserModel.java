package com.etbakhly_provider.model;

import java.io.Serializable;

public class UserModel extends StatusResponse {
    private Data data;
    private String fireBaseToken;

    public Data getData() {
        return data;
    }

    public static class Data implements Serializable {
        private String id;
        private String name;
        private String phone;
        private String phone_code;
        private String email;
        private String photo;
        private String yes_i_read_it;
        private String longitude;
        private String latitude;
        private String type;
        private String is_completed;
        private String software_type;
        private String created_at;
        private String updated_at;
        private KitchenModel caterer;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPhone_code() {
            return phone_code;
        }

        public void setPhone_code(String phone_code) {
            this.phone_code = phone_code;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getYes_i_read_it() {
            return yes_i_read_it;
        }

        public void setYes_i_read_it(String yes_i_read_it) {
            this.yes_i_read_it = yes_i_read_it;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getIs_completed() {
            return is_completed;
        }

        public void setIs_completed(String is_completed) {
            this.is_completed = is_completed;
        }

        public String getSoftware_type() {
            return software_type;
        }

        public void setSoftware_type(String software_type) {
            this.software_type = software_type;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public KitchenModel getCaterer() {
            return caterer;
        }
    }

    public String getFireBaseToken() {
        return fireBaseToken;
    }

    public void setFireBaseToken(String fireBaseToken) {
        this.fireBaseToken = fireBaseToken;
    }
}
