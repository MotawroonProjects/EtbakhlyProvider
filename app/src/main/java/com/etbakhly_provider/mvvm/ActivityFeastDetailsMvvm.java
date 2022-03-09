package com.etbakhly_provider.mvvm;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.etbakhly_provider.R;
import com.etbakhly_provider.model.BuffetModel;
import com.etbakhly_provider.model.DishModel;
import com.etbakhly_provider.model.DishesDataModel;
import com.etbakhly_provider.model.SingleCategory;
import com.etbakhly_provider.model.StatusResponse;
import com.etbakhly_provider.remote.Api;
import com.etbakhly_provider.share.Common;
import com.etbakhly_provider.tags.Tags;

import java.io.IOException;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ActivityFeastDetailsMvvm extends AndroidViewModel {

    private MutableLiveData<Integer> onDishUpdatedSuccess;
    private MutableLiveData<Boolean> isLoading;
    private MutableLiveData<Integer> onCategoryDeletedSuccess;
    private MutableLiveData<Integer> onItemSelected;
    private MutableLiveData<Integer> onChildItemSelected;

    private MutableLiveData<BuffetModel.Category> onCategoryAddedSuccess;
    private MutableLiveData<List<BuffetModel.Category>> onCategoryDataSuccess;

    private CompositeDisposable disposable = new CompositeDisposable();

    public ActivityFeastDetailsMvvm(@NonNull Application application) {
        super(application);

    }


    public MutableLiveData<Integer> getOnCategoryDeletedSuccess() {
        if (onCategoryDeletedSuccess == null) {
            onCategoryDeletedSuccess = new MutableLiveData<>();
        }
        return onCategoryDeletedSuccess;
    }

    public MutableLiveData<Integer> getOnItemSelected() {
        if (onItemSelected == null) {
            onItemSelected = new MutableLiveData<>();
        }
        return onItemSelected;
    }

    public MutableLiveData<Integer> getOnChildItemSelected() {
        if (onChildItemSelected == null) {
            onChildItemSelected = new MutableLiveData<>();
        }
        return onChildItemSelected;
    }

    public MutableLiveData<Integer> getOnDishUpdatedSuccess() {
        if (onDishUpdatedSuccess == null) {
            onDishUpdatedSuccess = new MutableLiveData<>();
        }
        return onDishUpdatedSuccess;
    }

    public MutableLiveData<Boolean> getIsDataLoading() {
        if (isLoading == null) {
            isLoading = new MutableLiveData<>();
        }
        return isLoading;
    }

    public MutableLiveData<List<BuffetModel.Category>> onCategoryDataSuccess() {
        if (onCategoryDataSuccess == null) {
            onCategoryDataSuccess = new MutableLiveData<>();
        }
        return onCategoryDataSuccess;
    }

    public MutableLiveData<BuffetModel.Category> getCategoryAddedSuccess() {
        if (onCategoryAddedSuccess == null) {
            onCategoryAddedSuccess = new MutableLiveData<>();
        }

        return onCategoryAddedSuccess;
    }


    public void getCategoryDishes(String kitchen_id,String feast_id, Context context) {
        Log.e("ddd",kitchen_id+"__"+feast_id);

        getIsDataLoading().setValue(true);
        Api.getService(Tags.base_url).getDishes("all", kitchen_id, "dishe","feast",feast_id)
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

                                onCategoryDataSuccess().setValue(response.body().getData());


                            }else {
                                Log.e("error",response.body().getMessage().toString());
                            }
                        }else {
                            try {
                                Log.e("error",response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("error", e.getMessage());
                    }
                });
    }

    public void addCategory(String name, String caterer_id,String feast_id, Context context) {
        ProgressDialog dialog = Common.createProgressDialog(context, context.getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();
        Api.getService(Tags.base_url).addCatererDish(name, caterer_id, "dishe","feast",feast_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<SingleCategory>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<SingleCategory> response) {
                        dialog.dismiss();
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatus() == 200 && response.body().getData() != null) {
                                onCategoryDataSuccess().getValue().add(response.body().getData());
                                getCategoryAddedSuccess().setValue(response.body().getData());
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        dialog.dismiss();

                        Log.e("error", e.getMessage());
                    }
                });
    }

    public void deleteCategory(String Category_Dishes_id, Context context, int pos) {
        ProgressDialog dialog = Common.createProgressDialog(context, context.getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();
        Api.getService(Tags.base_url).deleteCatererDish(Category_Dishes_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<StatusResponse>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<StatusResponse> response) {
                        dialog.dismiss();

                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatus() == 200) {
                                onCategoryDataSuccess().getValue().remove(pos);
                                getOnCategoryDeletedSuccess().postValue(pos);

                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        dialog.dismiss();

                        Log.e("error", e.getMessage());
                    }
                });
    }

    public void deleteDish(String dish_id, Context context, int mainPos, int childPos) {
        ProgressDialog dialog = Common.createProgressDialog(context, context.getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();
        Api.getService(Tags.base_url).deleteDish(dish_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<StatusResponse>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<StatusResponse> response) {
                        dialog.dismiss();

                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatus() == 200) {
                                if (onCategoryDataSuccess().getValue() != null) {
                                    List<DishModel> list = onCategoryDataSuccess().getValue().get(mainPos).getDishes_buffet();
                                    list.remove(childPos);
                                    getOnDishUpdatedSuccess().setValue(mainPos);
                                }


                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        dialog.dismiss();

                        Log.e("error", e.getMessage());
                    }
                });
    }


}
