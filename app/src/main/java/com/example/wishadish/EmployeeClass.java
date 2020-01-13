package com.example.wishadish;

public class EmployeeClass {

//    private String mEmployeeName;
//    private String mEmployeeEmailId;
//    private String mEmployeeMob;
//    private String mEmployeeMobAlternate;
//    private String mEmployeeAadharNo;
//    private String mEmployeePassword;
//    private String mEmployeeType;
//    private String mEmployeeSalary;
//    private String mSalaryPeriod;
//    private String mEmployeeJoinDate;
//    private String mEmployeeHolidaysAllowed;
//    private String mEmployeeAddress;

    private String mEmployeeName;
    private String mEmployeeType;
    private String mEmployeeEmailId;
    private String mEmployeeMob;
    private String mEmployeeSalary;
    private String mEmployeeAddress;

    public EmployeeClass(String mEmployeeName, String mEmployeeType, String mEmployeeEmailId, String mEmployeeMob, String mEmployeeSalary, String mEmployeeAddress) {
        this.mEmployeeName = mEmployeeName;
        this.mEmployeeType = mEmployeeType;
        this.mEmployeeEmailId = mEmployeeEmailId;
        this.mEmployeeMob = mEmployeeMob;
        this.mEmployeeSalary = mEmployeeSalary;
        this.mEmployeeAddress = mEmployeeAddress;
    }

    public String getmEmployeeName() {
        return mEmployeeName;
    }

    public void setmEmployeeName(String mEmployeeName) {
        this.mEmployeeName = mEmployeeName;
    }

    public String getmEmployeeType() {
        return mEmployeeType;
    }

    public void setmEmployeeType(String mEmployeeType) {
        this.mEmployeeType = mEmployeeType;
    }

    public String getmEmployeeEmailId() {
        return mEmployeeEmailId;
    }

    public void setmEmployeeEmailId(String mEmployeeEmailId) {
        this.mEmployeeEmailId = mEmployeeEmailId;
    }

    public String getmEmployeeMob() {
        return mEmployeeMob;
    }

    public void setmEmployeeMob(String mEmployeeMob) {
        this.mEmployeeMob = mEmployeeMob;
    }

    public String getmEmployeeSalary() {
        return mEmployeeSalary;
    }

    public void setmEmployeeSalary(String mEmployeeSalary) {
        this.mEmployeeSalary = mEmployeeSalary;
    }

    public String getmEmployeeAddress() {
        return mEmployeeAddress;
    }

    public void setmEmployeeAddress(String mEmployeeAddress) {
        this.mEmployeeAddress = mEmployeeAddress;
    }
}
