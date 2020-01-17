package com.example.wishadish.ui.HumanResources;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddEmployeeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AddEmployeeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is AddEmployee fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}