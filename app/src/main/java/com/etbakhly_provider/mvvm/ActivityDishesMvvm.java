package com.etbakhly_provider.mvvm;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.etbakhly_provider.model.BuffetModel;
import com.etbakhly_provider.model.CategoryDishModel;
import com.etbakhly_provider.model.DishModel;
import com.etbakhly_provider.model.CategoryDataModel;
import com.etbakhly_provider.model.DishesDataModel;
import com.etbakhly_provider.remote.Api;
import com.etbakhly_provider.tags.Tags;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ActivityDishesMvvm extends AndroidViewModel {

    private MutableLiveData<Boolean> isDataLoading;
    private MutableLiveData<List<BuffetModel.Category>> onDataSuccess;
    private MutableLiveData<List<DishModel>> onDishesSuccess;

    private MutableLiveData<Integer> selectedCategoryPos;

    private CompositeDisposable disposable = new CompositeDisposable();


    public ActivityDishesMvvm(@NonNull Application application) {
        super(application);
    }


    public MutableLiveData<Boolean> getIsDataLoading() {
        if (isDataLoading == null) {
            isDataLoading = new MutableLiveData<>();
        }
        return isDataLoading;
    }

    public MutableLiveData<List<BuffetModel.Category>> onDataSuccess() {
        if (onDataSuccess == null) {
            onDataSuccess = new MutableLiveData<>();
        }
        return onDataSuccess;
    }

    public MutableLiveData<Integer> getSelectedCategoryPos() {
        if (selectedCategoryPos == null) {
            selectedCategoryPos = new MutableLiveData<>(-1);
        }

        return selectedCategoryPos;
    }

    public void setSelectedCategoryPos(int pos) {
        getSelectedCategoryPos().setValue(pos);

    }

    public MutableLiveData<List<DishModel>> onDishSuccess() {
        if (onDishesSuccess == null) {
            onDishesSuccess = new MutableLiveData<>();
        }
        return onDishesSuccess;
    }

    public void getDishes(String kitchen_id) {
        getIsDataLoading().setValue(true);
        Api.getService(Tags.base_url).getDishes("all", kitchen_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<DishesDataModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<DishesDataModel> response) {
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
}
