package com.example.wishadish;

public class AllOrderClass {

    private String mTableNumber;
    private String mTime;
    private String mOrdernumber;
    private int mOrderAmount;
    private String mDate;

    public AllOrderClass(String mOrdernumber, int mOrderAmount, String mDate, String mTime, String mTableNumber) {
        this.mOrdernumber = mOrdernumber;
        this.mOrderAmount = mOrderAmount;
        this.mDate = mDate;
        this.mTime = mTime;
        this.mTableNumber = mTableNumber;
    }

    public String getmOrdernumber() {
        return mOrdernumber;
    }

    public void setmOrdernumber(String mOrdernumber) {
        this.mOrdernumber = mOrdernumber;
    }

    public int getmOrderAmount() {
        return mOrderAmount;
    }

    public void setmOrderAmount(int mOrderAmount) {
        this.mOrderAmount = mOrderAmount;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public String getmTableNumber() {
        return mTableNumber;
    }

    public void setmTableNumber(String mTableNumber) {
        this.mTableNumber = mTableNumber;
    }

    public String getmTime() {
        return mTime;
    }

    public void setmTime(String mTime) {
        this.mTime = mTime;
    }
}
