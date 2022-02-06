package com.etbakhly_provider.mvvm;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.etbakhly_provider.model.OrderDataModel;
import com.etbakhly_provider.model.OrderModel;
import com.etbakhly_provider.model.StatusResponse;
import com.etbakhly_provider.remote.Api;
import com.etbakhly_provider.tags.Tags;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ActivityHomeGeneralMvvm extends AndroidViewModel {


    private MutableLiveData<String> onStatusSuccess;

    private CompositeDisposable disposable = new CompositeDisposable();

    public ActivityHomeGeneralMvvm(@NonNull Application application) {
        super(application);
    }


    public MutableLiveData<String> getOnStatusSuccess() {
        if (onStatusSuccess == null) {
            onStatusSuccess = new MutableLiveData<>();
        }
        return onStatusSuccess;
    }


}
