package com.etbakhly_provider.model;

import java.io.Serializable;

public class UserModel extends StatusResponse {
    private Data data;

    public Data getData() {
        return data;
    }

    public class Data implements Serializable {
        private User user;
        private String access_token;
        private String token_type;
        private String expires_in;
        private String firebase_token;

        public User getUser() {
            return user;
        }

        public String getAccess_token() {
            return access_token;
        }

        public String getToken_type() {
            return token_type;
        }

        public String getExpires_in() {
            return expires_in;
        }

        public String getFirebase_token() {
            return firebase_token;
        }

        public class User implements Serializable{
            public String id;
            public String name;
            public String phone;
            public String phone_code;
            public String email;
            public String photo;
            public String yes_i_read_it;
            public String longitude;
            public String latitude;
            public String type;
            public String is_completed;
            public String software_type;
            public String created_at;
            public String updated_at;

            public String getId() {
                return id;
            }

            public String getName() {
                return name;
            }

            public String getPhone() {
                return phone;
            }

            public String getPhone_code() {
                return phone_code;
            }

            public String getEmail() {
                return email;
            }

            public String getPhoto() {
                return photo;
            }

            public String getYes_i_read_it() {
                return yes_i_read_it;
            }

            public String getLongitude() {
                return longitude;
            }

            public String getLatitude() {
                return latitude;
            }

            public String getType() {
                return type;
            }

            public String getIs_completed() {
                return is_completed;
            }

            public String getSoftware_type() {
                return software_type;
            }

            public String getCreated_at() {
                return created_at;
            }

            public String getUpdated_at() {
                return updated_at;
            }
        }
    }

}
