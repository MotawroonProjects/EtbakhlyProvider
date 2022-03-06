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
import com.etbakhly_provider.model.AddBuffetDishModel;
import com.etbakhly_provider.model.DishModel;
import com.etbakhly_provider.model.SingleDishModel;
import com.etbakhly_provider.remote.Api;
import com.etbakhly_provider.share.Common;
import com.etbakhly_provider.tags.Tags;

import java.io.IOException;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

public class ActivityAddBuffetDishMvvm extends AndroidViewModel {

    private MutableLiveData<DishModel> onDishUpdatedSuccess;
    private CompositeDisposable disposable = new CompositeDisposable();

    public ActivityAddBuffetDishMvvm(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<DishModel> getOnDishUpdatedSuccess() {
        if (onDishUpdatedSuccess == null) {
            onDishUpdatedSuccess = new MutableLiveData<>();
        }
        return onDishUpdatedSuccess;
    }

    public void storeBuffetsDishes(Context context, AddBuffetDishModel addBuffetDishModel) {
        ProgressDialog dialog = Common.createProgressDialog(context, context.getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();

        RequestBody titel = Common.getRequestBodyText(addBuffetDishModel.getTitel());
        RequestBody qty = Common.getRequestBodyText(addBuffetDishModel.getQty());
        RequestBody price = Common.getRequestBodyText(addBuffetDishModel.getPrice());
        RequestBody category_dishes_id = Common.getRequestBodyText(addBuffetDishModel.getCategory_dishes_id() + "");
        RequestBody details = Common.getRequestBodyText(addBuffetDishModel.getDetails());

        MultipartBody.Part image = Common.getMultiPart(context, Uri.parse(addBuffetDishModel.getPhoto()), "photo");

        Api.getService(Tags.base_url).storeBuffetsDishes(titel, category_dishes_id, price, details, image, qty)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<SingleDishModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<SingleDishModel> response) {
                        dialog.dismiss();
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatus() == 200) {
                                onDishUpdatedSuccess.setValue(response.body().getData());
                            }
                        } else {
                            try {
                                Log.e("error", response.code() + "__" + response.errorBody().string());
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

    public void updateBuffetsDishes(Context context, AddBuffetDishModel addBuffetDishModel) {
        ProgressDialog dialog = Common.createProgressDialog(context, context.getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();

        RequestBody dish_id = Common.getRequestBodyText(addBuffetDishModel.getId());

        RequestBody titel = Common.getRequestBodyText(addBuffetDishModel.getTitel());
        RequestBody qty = Common.getRequestBodyText(addBuffetDishModel.getQty());
        RequestBody price = Common.getRequestBodyText(addBuffetDishModel.getPrice());
        RequestBody category_dishes_id = Common.getRequestBodyText(addBuffetDishModel.getCategory_dishes_id() + "");
        RequestBody details = Common.getRequestBodyText(addBuffetDishModel.getDetails());
        MultipartBody.Part image = null;
        if (!addBuffetDishModel.getPhoto().contains("storage")) {
            image = Common.getMultiPart(context, Uri.parse(addBuffetDishModel.getPhoto()), "photo");
        }

        Api.getService(Tags.base_url).updateBuffetsDishes(titel, category_dishes_id, price, details, image, qty, dish_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<SingleDishModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<SingleDishModel> response) {
                        dialog.dismiss();
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatus() == 200) {
                                onDishUpdatedSuccess.setValue(response.body().getData());
                            }
                        } else {
                            try {
                                Log.e("error", response.code() + "__" + response.errorBody().string());
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

}
