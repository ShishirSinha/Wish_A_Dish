package com.example.wishadish;

public class WaitListClass {

    private String mCustName, mCustNumber;
    private int mTableFor, mWaitTimeMinutes;

    public WaitListClass(String mCustName, String mCustNum, int mTableFor, int mWaitTimeMinutes) {
        this.mCustName = mCustName;
        this.mCustNumber = mCustNum;
        this.mTableFor = mTableFor;
        this.mWaitTimeMinutes = mWaitTimeMinutes;
    }

    public String getmCustName() {
        return mCustName;
    }

    public void setmCustName(String mCustName) {
        this.mCustName = mCustName;
    }

    public String getmCustNumber() {
        return mCustNumber;
    }

    public void setmCustNumber(String mCustNumber) {
        this.mCustNumber = mCustNumber;
    }

    public int getmTableFor() {
        return mTableFor;
    }

    public void setmTableFor(int mTableFor) {
        this.mTableFor = mTableFor;
    }

    public int getmWaitTimeMinutes() {
        return mWaitTimeMinutes;
    }

    public void setmWaitTimeMinutes(int mWaitTimeMinutes) {
        this.mWaitTimeMinutes = mWaitTimeMinutes;
    }
}
