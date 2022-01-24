package com.etbakhly_provider.model;

import java.io.Serializable;

public class SingleDepartmentDataModel extends StatusResponse implements Serializable {
    private DepartmentModel data;

    public DepartmentModel getData() {
        return data;
    }

    public void setData(DepartmentModel data) {
        this.data = data;
    }
}
