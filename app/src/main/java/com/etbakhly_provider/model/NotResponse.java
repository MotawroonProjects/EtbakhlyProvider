package com.etbakhly_provider.model;

import java.io.Serializable;

public class NotResponse implements Serializable {
    private boolean status;

    public NotResponse(boolean status) {
        this.status = status;
    }

    public boolean isStatus() {
        return status;
    }
}
