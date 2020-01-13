package com.example.wishadish;

public class TableInfoClass {

    private int mTableNo;
    private int mTableSize;
    private boolean mTableOnline;

    public TableInfoClass(int mTableNo, int mTableSize, boolean mTableOnline) {
        this.mTableNo = mTableNo;
        this.mTableSize = mTableSize;
        this.mTableOnline = mTableOnline;
    }

    public int getmTableNo() {
        return mTableNo;
    }

    public void setmTableNo(int mTableNo) {
        this.mTableNo = mTableNo;
    }

    public int getmTableSize() {
        return mTableSize;
    }

    public void setmTableSize(int mTableSize) {
        this.mTableSize = mTableSize;
    }

    public boolean ismTableOnline() {
        return mTableOnline;
    }

    public void setmTableOnline(boolean mTableOnline) {
        this.mTableOnline = mTableOnline;
    }
}
