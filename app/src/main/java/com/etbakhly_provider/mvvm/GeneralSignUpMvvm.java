package com.etbakhly_provider.mvvm;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.etbakhly_provider.model.SignUpModel;

public class GeneralSignUpMvvm extends AndroidViewModel {
    private MutableLiveData<SignUpModel> signUpModelMutableLiveData;

    public GeneralSignUpMvvm(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<SignUpModel> getSignUpModel() {
        if (signUpModelMutableLiveData == null) {
            signUpModelMutableLiveData = new MutableLiveData<>();

        }
        return signUpModelMutableLiveData;
    }
}
