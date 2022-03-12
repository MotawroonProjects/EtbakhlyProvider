package com.etbakhly_provider.mvvm;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.etbakhly_provider.model.CountryDataModel;
import com.etbakhly_provider.model.CountryModel;
import com.etbakhly_provider.model.StatusResponse;
import com.etbakhly_provider.remote.Api;
import com.etbakhly_provider.tags.Tags;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ActivitySettingDetailsMvvm extends AndroidViewModel {

    private MutableLiveData<Boolean> isStatusUpdated;

    private CompositeDisposable disposable = new CompositeDisposable();


    public ActivitySettingDetailsMvvm(@NonNull Application application) {
        super(application);
    }


    public MutableLiveData<Boolean> getOnStatusUpdated() {
        if (isStatusUpdated == null) {
            isStatusUpdated = new MutableLiveData<>();
        }
        return isStatusUpdated;
    }

    //_________________________hitting api_________________________________

    public void updateStatus(boolean isBusy, String caterer_id) {

        Api.getService(Tags.base_url)
                .updateStatus(caterer_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<StatusResponse>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<StatusResponse> response) {
                        if (response.isSuccessful()) {
                            Log.e("code",response.body().getStatus()+"");
                            if (response.body() != null && response.body().getStatus() == 200) {

                                getOnStatusUpdated().setValue(isBusy);
                            } else {
                                getOnStatusUpdated().setValue(!isBusy);

                            }
                        } else {
                            getOnStatusUpdated().setValue(!isBusy);

                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        getOnStatusUpdated().setValue(!isBusy);

                        Log.e("Error", "Error", e);
                    }
                });

    }


    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();

    }

}
