package com.etbakhly_provider.mvvm;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.etbakhly_provider.model.BuffetModel;
import com.etbakhly_provider.model.BuffetsDataModel;
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

public class ActivityFeastsMvvm extends AndroidViewModel {

    private MutableLiveData<Boolean> isDataLoading;
    private MutableLiveData<List<BuffetModel>> onDataSuccess;
    private MutableLiveData<Integer> onStatusSuccess;
    private MutableLiveData<Integer> selectedPos = new MutableLiveData<>(-1);

    private CompositeDisposable disposable = new CompositeDisposable();

    public ActivityFeastsMvvm(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Integer> getOnStatusSuccess() {
        if (onStatusSuccess==null){
            onStatusSuccess=new MutableLiveData<>();
        }
        return onStatusSuccess;
    }

    public MutableLiveData<Boolean> getIsDataLoading() {
        if (isDataLoading == null) {
            isDataLoading = new MutableLiveData<>();
        }
        return isDataLoading;
    }

    public MutableLiveData<List<BuffetModel>> onDataSuccess() {
        if (onDataSuccess == null) {
            onDataSuccess = new MutableLiveData<>();
        }
        return onDataSuccess;
    }

    public MutableLiveData<Integer> getSelectedPos() {
        if (selectedPos == null) {
            selectedPos = new MutableLiveData<>();
        }
        return selectedPos;
    }

    public void getFeasts(String kitchen_id, Context context) {
        getIsDataLoading().setValue(true);
        Api.getService(Tags.base_url).getFeasts(kitchen_id,"provider")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<BuffetsDataModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<BuffetsDataModel> response) {
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

    public void deleteFeasts(String feast_id){
        Api.getService(Tags.base_url).deleteFeasts(feast_id)
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
                                    onStatusSuccess.setValue(1);
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
