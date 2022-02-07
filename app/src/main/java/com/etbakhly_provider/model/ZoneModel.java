package com.etbakhly_provider.model;

import java.io.Serializable;

public class ZoneModel implements Serializable {

        private String id;
        private String titel;
        private String citie_id;
        private String created_at;
        private String updated_at;

        public String getId() {
            return id;
        }

        public String getTitel() {
            return titel;
        }

        public String getCitie_id() {
            return citie_id;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

}
