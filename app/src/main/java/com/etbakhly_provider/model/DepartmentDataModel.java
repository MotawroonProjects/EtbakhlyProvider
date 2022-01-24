package com.etbakhly_provider.model;

import java.io.Serializable;
import java.util.List;

public class DepartmentDataModel extends StatusResponse implements Serializable {
    private List<DepartmentModel> data;

    public List<DepartmentModel> getData() {
        return data;
    }

    public void setData(List<DepartmentModel> data) {
        this.data = data;
    }
}
