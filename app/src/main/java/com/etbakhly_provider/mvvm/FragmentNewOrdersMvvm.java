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
import com.etbakhly_provider.share.App;
import com.etbakhly_provider.tags.Tags;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class FragmentNewOrdersMvvm extends AndroidViewModel {

    private MutableLiveData<Boolean> isDataLoading;
    private MutableLiveData<List<OrderModel>> onDataSuccess;
    private MutableLiveData<Integer> onStatusSuccess;

    private CompositeDisposable disposable = new CompositeDisposable();

    public FragmentNewOrdersMvvm(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Boolean> getIsDataLoading() {
        if (isDataLoading == null) {
            isDataLoading = new MutableLiveData<>();
        }
        return isDataLoading;
    }

    public MutableLiveData<List<OrderModel>> getOnDataSuccess() {
        if (onDataSuccess == null) {
            onDataSuccess = new MutableLiveData<>();
        }
        return onDataSuccess;
    }

    public MutableLiveData<Integer> getOnOrderStatusSuccess() {
        if (onStatusSuccess == null) {
            onStatusSuccess = new MutableLiveData<>();
        }
        return onStatusSuccess;
    }

    public void getNewOrders(String caterer_id) {
        getIsDataLoading().setValue(true);
        Api.getService(Tags.base_url).getMyOrder(caterer_id, "new")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<OrderDataModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<OrderDataModel> response) {
                        isDataLoading.setValue(false);
                        getIsDataLoading().setValue(false);
                        Log.e("llll", response.code() + "" + response.body().getStatus());
                        if (response.isSuccessful()) {

                            if (response.body() != null && response.body().getStatus() == 200 && response.body().getData() != null) {
                                onDataSuccess.setValue(response.body().getData());
                            }
                        } else {

                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("error", e.getMessage());
                        isDataLoading.setValue(false);
                    }
                });
    }

    public void changeStatusOrder(String status_order, String order_id) {
        Api.getService(Tags.base_url).changeStatusOrder(order_id, status_order)
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
                            if (response.body() != null) {
                                if (response.body().getStatus() == 200) {
                                    if (status_order.equals("approval")){
                                        getOnOrderStatusSuccess().setValue(1);

                                    }else if (status_order.equals("refusal")){
                                        getOnOrderStatusSuccess().setValue(2);

                                    }
                                }
                            }
                        } else {

                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("error", e.getMessage());
                    }
                });
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
