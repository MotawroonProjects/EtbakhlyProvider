package com.etbakhly_provider.mvvm;

import android.app.Application;
import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.etbakhly_provider.model.AddFeastDishModel;

import io.reactivex.disposables.CompositeDisposable;

public class ActivityAddFeastDishMvvm extends AndroidViewModel {

    private MutableLiveData<Boolean> addFeastDishLiveData;
    private CompositeDisposable disposable = new CompositeDisposable();

    public ActivityAddFeastDishMvvm(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Boolean> getAddFeastDishLiveData() {
        if (addFeastDishLiveData==null){
            addFeastDishLiveData=new MutableLiveData<>();
        }
        return addFeastDishLiveData;
    }

    public void addFeastDishes(Context context, AddFeastDishModel addFeastDishModel, Uri uri){

    }
}
