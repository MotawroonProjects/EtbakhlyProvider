package com.etbakhly_provider.model;

import java.io.Serializable;

public class StatusResponse implements Serializable {
    protected int status;
    protected  Object message;


    public int getStatus() {
        return status;
    }

    public Object getMessage() {
        return message;
    }
}
