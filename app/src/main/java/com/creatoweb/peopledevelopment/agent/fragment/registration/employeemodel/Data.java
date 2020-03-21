package com.creatoweb.peopledevelopment.agent.fragment.registration.employeemodel;

import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("employee_id")
    private String employeeId;

    @SerializedName("employee_name")
    private String employeeName;

    @SerializedName("employeeRoleName")
    private String employeeRoleName;

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

    public String getEmployeeRoleName() {
        return employeeRoleName;
    }

    public void setEmployeeRoleName(String employeeRoleName) {
        this.employeeRoleName = employeeRoleName;
    }

}
