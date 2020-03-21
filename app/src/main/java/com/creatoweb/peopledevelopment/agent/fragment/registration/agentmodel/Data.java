package com.creatoweb.peopledevelopment.agent.fragment.registration.agentmodel;

import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("employee_id")
    private String employeeId;

    @SerializedName("employee_name")
    private String employeeName;

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

}
