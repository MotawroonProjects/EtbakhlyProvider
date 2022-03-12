package com.etbakhly_provider.mvvm;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.etbakhly_provider.model.BuffetModel;
import com.etbakhly_provider.model.BuffetsDataModel;
import com.etbakhly_provider.model.OfferDataModel;
import com.etbakhly_provider.model.OfferModel;
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

public class ActivityOffersMvvm extends AndroidViewModel {
    private MutableLiveData<Boolean> isDataLoading;
    private MutableLiveData<List<OfferModel>> onDataSuccess;
    private MutableLiveData<Integer> onDeleteSuccess;
    private CompositeDisposable disposable = new CompositeDisposable();

    public ActivityOffersMvvm(@NonNull Application application) {
        super(application);
    }



    public MutableLiveData<Boolean> getIsDataLoading() {
        if (isDataLoading == null) {
            isDataLoading = new MutableLiveData<>();
        }
        return isDataLoading;
    }

    public MutableLiveData<Integer> getOnDeleteSuccess() {
        if (onDeleteSuccess == null) {
            onDeleteSuccess = new MutableLiveData<>();
        }
        return onDeleteSuccess;
    }

    public MutableLiveData<List<OfferModel>> onDataSuccess() {
        if (onDataSuccess == null) {
            onDataSuccess = new MutableLiveData<>();
        }
        return onDataSuccess;
    }

    public void getOffers(String kitchen_id) {
        getIsDataLoading().setValue(true);
        Api.getService(Tags.base_url).getOffers(kitchen_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<OfferDataModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<OfferDataModel> response) {
                        getIsDataLoading().setValue(false);
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatus() == 200 && response.body().getData() != null) {
                                onDataSuccess.setValue(response.body().getData());
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("error", e.getMessage());
                    }
                });
    }

    public void deleteOffers(String offer_id,int pos){
        Api.getService(Tags.base_url).deleteOffer(offer_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<StatusResponse>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<StatusResponse> response) {
                        if (response.isSuccessful()){
                            if (response.body()!=null){
                                if (response.body().getStatus()==200){
                                    if (onDataSuccess().getValue()!=null){
                                        onDataSuccess().getValue().remove(pos);

                                    }
                                    getOnDeleteSuccess().setValue(pos);
                                }
                            }
                        }else {

                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("error", e.getMessage());
                    }
                });
    }

}
