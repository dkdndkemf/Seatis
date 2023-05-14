package com.example.seatis;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

public class ViewModel extends androidx.lifecycle.ViewModel {
    //private MutableLiveData<ArrayList> data = new MutableLiveData<>();
    private MutableLiveData<Boolean> login;

    public void setIsLogin(boolean newIsLogin) {
        login.setValue(newIsLogin);
    }
    public MutableLiveData<Boolean> getIsLogin() {
        if(login == null) {
            login = new MutableLiveData<Boolean>();
        }
        return login;
    }

}
