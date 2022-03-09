package com.etbakhly_provider.mvvm;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.etbakhly_provider.R;
import com.etbakhly_provider.model.AddBuffetModel;
import com.etbakhly_provider.model.BuffetModel;
import com.etbakhly_provider.model.DishesDataModel;
import com.etbakhly_provider.model.StatusResponse;
import com.etbakhly_provider.remote.Api;
import com.etbakhly_provider.share.Common;
import com.etbakhly_provider.tags.Tags;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

public class ActivityAddFeastMvvm extends AndroidViewModel {

    private MutableLiveData<Boolean> addFeastLiveData;
    private MutableLiveData<Boolean> updateFeastLiveData;
    private MutableLiveData<List<BuffetModel.Category>> onCategoryDataSuccess;

    private CompositeDisposable disposable = new CompositeDisposable();

    public ActivityAddFeastMvvm(@NonNull Application application) {
        super(application);
        List<BuffetModel.Category> categoryList = new ArrayList<>();
        BuffetModel.Category categoryModel = new BuffetModel.Category();
        categoryModel.setTitel(application.getApplicationContext().getString(R.string.ch_cat));
        categoryList.add(categoryModel);
        onCategoryDataSuccess().setValue(categoryList);
    }

    public MutableLiveData<Boolean> getAddFeastMutableLiveData() {
        if (addFeastLiveData == null) {
            addFeastLiveData = new MutableLiveData<>();
        }
        return addFeastLiveData;
    }

    public MutableLiveData<Boolean> getUpdateFeastLiveData() {
        if (updateFeastLiveData == null) {
            updateFeastLiveData = new MutableLiveData<>();
        }
        return updateFeastLiveData;
    }

    public MutableLiveData<List<BuffetModel.Category>> onCategoryDataSuccess() {
        if (onCategoryDataSuccess == null) {
            onCategoryDataSuccess = new MutableLiveData<>();
        }
        return onCategoryDataSuccess;
    }

    public void storeFeast(Context context, AddBuffetModel addBuffetModel, Uri uri) {
        ProgressDialog dialog = Common.createProgressDialog(context, context.getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();

        RequestBody titel = Common.getRequestBodyText(addBuffetModel.getTitel());
        RequestBody number_people = Common.getRequestBodyText(addBuffetModel.getNumber_people());
        RequestBody service_provider_type = Common.getRequestBodyText(addBuffetModel.getService_provider_type());
        RequestBody order_time = Common.getRequestBodyText(addBuffetModel.getOrder_time());
        RequestBody price = Common.getRequestBodyText(addBuffetModel.getPrice());
        RequestBody category_dishes_id = Common.getRequestBodyText(addBuffetModel.getCategory_dishes_id() + "");
        RequestBody caterer_id = Common.getRequestBodyText(addBuffetModel.getCaterer_id());

        MultipartBody.Part image = null;
        if (addBuffetModel.getPhoto() != null && !addBuffetModel.getPhoto().isEmpty()) {
            image = Common.getMultiPart(context, uri, "photo");
        }
        Log.e("id", addBuffetModel.getCategory_dishes_id() + "__" + addBuffetModel.getCaterer_id());

        Api.getService(Tags.base_url).storeFeast(titel, number_people, service_provider_type, order_time, image, price, category_dishes_id, caterer_id)
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
                            Log.e("status", response.body().getStatus() + "");
                            if (response.body() != null && response.body().getStatus() == 200) {
                                addFeastLiveData.postValue(true);
                            }
                        } else {
                            try {
                                Log.e("error", response.code() + "" + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        dialog.dismiss();
                        Log.d("error", e.getMessage());
                    }
                });

    }

    public void editBuffet(Context context, AddBuffetModel addBuffetModel, Uri uri) {
        ProgressDialog dialog = Common.createProgressDialog(context, context.getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();

        RequestBody titel = Common.getRequestBodyText(addBuffetModel.getTitel());
        RequestBody number_people = Common.getRequestBodyText(addBuffetModel.getNumber_people());
        RequestBody service_provider_type = Common.getRequestBodyText(addBuffetModel.getService_provider_type());
        RequestBody order_time = Common.getRequestBodyText(addBuffetModel.getOrder_time());
        RequestBody price = Common.getRequestBodyText(addBuffetModel.getPrice());
        RequestBody category_dishes_id = Common.getRequestBodyText(addBuffetModel.getCategory_dishes_id() + "");
        RequestBody Buffet_id = Common.getRequestBodyText(addBuffetModel.getId());

        MultipartBody.Part image = null;
        if (!addBuffetModel.getPhoto().contains("storage")) {
            image = Common.getMultiPart(context, uri, "photo");
        }

        Api.getService(Tags.base_url).updateFeast(titel, number_people, service_provider_type, order_time, image, price, category_dishes_id, Buffet_id)
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
                                updateFeastLiveData.postValue(true);
                            }
                        } else {
                            try {
                                Log.e("error", response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("error", e.getMessage());
                    }
                });

    }

    public void getCategoryDishes(String kitchen_id, Context context) {
        Api.getService(Tags.base_url).getDishes("all", kitchen_id, "feast","all",null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<DishesDataModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<DishesDataModel> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatus() == 200 && response.body().getData() != null) {

                                if (response.body().getData().size() > 0) {
                                    onCategoryDataSuccess().setValue(response.body().getData());

                                }


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
