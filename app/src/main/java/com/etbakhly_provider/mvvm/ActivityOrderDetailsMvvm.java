package com.etbakhly_provider.mvvm;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.etbakhly_provider.model.OrderModel;
import com.etbakhly_provider.model.SingleOrderDataModel;
import com.etbakhly_provider.remote.Api;
import com.etbakhly_provider.tags.Tags;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ActivityOrderDetailsMvvm extends AndroidViewModel {

    private MutableLiveData<Boolean> isOrderDataLoading;
    private MutableLiveData<OrderModel> onOrderDetailsSuccess;
    private CompositeDisposable disposable = new CompositeDisposable();

    public ActivityOrderDetailsMvvm(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Boolean> getIsOrderDataLoading() {
        if (isOrderDataLoading == null) {
            isOrderDataLoading = new MutableLiveData<>();
        }
        return isOrderDataLoading;
    }

    public MutableLiveData<OrderModel> getOnOrderDetailsSuccess() {
        if (onOrderDetailsSuccess == null) {
            onOrderDetailsSuccess = new MutableLiveData<>();
        }
        return onOrderDetailsSuccess;
    }

    public void getOrderDetails(String order_id) {
        getIsOrderDataLoading().setValue(true);
        Api.getService(Tags.base_url).getOrderDetails(order_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<SingleOrderDataModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<SingleOrderDataModel> response) {
                        getIsOrderDataLoading().setValue(false);
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatus() == 200 && response.body().getSingelOrder() != null) {
                                onOrderDetailsSuccess.setValue(response.body().getSingelOrder());
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("error", e.getMessage());
                    }
                });
    }
}
