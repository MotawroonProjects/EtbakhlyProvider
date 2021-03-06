package com.etbakhly_provider.mvvm;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.etbakhly_provider.model.OrderDataModel;
import com.etbakhly_provider.model.OrderModel;
import com.etbakhly_provider.remote.Api;
import com.etbakhly_provider.tags.Tags;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class FragmentCompletedOrdersMvvm extends AndroidViewModel {


    private MutableLiveData<Boolean> isDataLoading;
    private MutableLiveData<List<OrderModel>> onDataSuccess;

    private CompositeDisposable disposable = new CompositeDisposable();

    public FragmentCompletedOrdersMvvm(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Boolean> getIsDataLoading() {
        if (isDataLoading==null){
            isDataLoading=new MutableLiveData<>();
        }
        return isDataLoading;
    }

    public MutableLiveData<List<OrderModel>> getOnDataSuccess() {
        if (onDataSuccess==null){
            onDataSuccess=new MutableLiveData<>();
        }
        return onDataSuccess;
    }

    public void getCompletedOrders(String caterer_id){
        getIsDataLoading().setValue(true);
        Api.getService(Tags.base_url).getMyOrder(caterer_id,"completed")
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
                        if (response.isSuccessful()){
                            if (response.body()!=null && response.body().getStatus()==200 && response.body().getData()!=null){
                                onDataSuccess.setValue(response.body().getData());
                            }
                        }else {

                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("error", e.getMessage());
                        isDataLoading.setValue(false);
                    }
                });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
