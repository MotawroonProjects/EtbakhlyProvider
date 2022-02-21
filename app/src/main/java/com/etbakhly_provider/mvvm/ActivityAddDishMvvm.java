package com.etbakhly_provider.mvvm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import io.reactivex.disposables.CompositeDisposable;

public class ActivityAddDishMvvm extends AndroidViewModel {

    private MutableLiveData<Boolean> addDishLiveData;
    private CompositeDisposable disposable = new CompositeDisposable();

    public ActivityAddDishMvvm(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Boolean> getAddDishLiveData() {
        if (addDishLiveData==null){
            addDishLiveData=new MutableLiveData<>();
        }
        return addDishLiveData;
    }


}
