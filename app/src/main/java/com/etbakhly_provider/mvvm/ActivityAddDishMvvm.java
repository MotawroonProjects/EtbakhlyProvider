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
import com.etbakhly_provider.model.AddDishDataModel;
import com.etbakhly_provider.model.AddDishModel;
import com.etbakhly_provider.model.StatusResponse;
import com.etbakhly_provider.model.UserModel;
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

public class ActivityAddDishMvvm extends AndroidViewModel {

    private MutableLiveData<Boolean> addDishLiveData;
    private MutableLiveData<Boolean> updateDishLiveData;


    private CompositeDisposable disposable = new CompositeDisposable();

    public ActivityAddDishMvvm(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Boolean> getAddDishLiveData() {
        if (addDishLiveData == null) {
            addDishLiveData = new MutableLiveData<>();
        }
        return addDishLiveData;
    }

    public MutableLiveData<Boolean> getUpdateDishLiveData() {
        if (updateDishLiveData == null) {
            updateDishLiveData = new MutableLiveData<>();
        }
        return updateDishLiveData;
    }

    public void storeDish(Context context, AddDishModel addDishModel, Uri uri) {
        ProgressDialog dialog = Common.createProgressDialog(context, context.getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();
        Log.e("data", addDishModel.getTitel() + "__" + addDishModel.getCategory_dishes_id() + "_" + addDishModel.getPrice() + "_" + addDishModel.getDetails() + "_" + addDishModel.getQty() + "_" + addDishModel.getCaterer_id());

        RequestBody titel = Common.getRequestBodyText(addDishModel.getTitel());
        RequestBody qty = Common.getRequestBodyText(addDishModel.getQty());
        RequestBody price = Common.getRequestBodyText(addDishModel.getPrice());
        RequestBody category_dishes_id = Common.getRequestBodyText(addDishModel.getCategory_dishes_id() + "");
        RequestBody caterer_id = Common.getRequestBodyText(addDishModel.getCaterer_id());
        RequestBody details = Common.getRequestBodyText(addDishModel.getDetails());

        MultipartBody.Part image = null;
        if (addDishModel.getPhoto() != null && !addDishModel.getPhoto().isEmpty()) {
            image = Common.getMultiPart(context, uri, "photo");
        }


        Api.getService(Tags.base_url).storeDish(titel, category_dishes_id, price, details, image, qty, caterer_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<AddDishDataModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<AddDishDataModel> response) {
                        dialog.dismiss();
                        if (response.isSuccessful()) {

                            if (response.body() != null && response.body().getStatus() == 200) {
                                addDishLiveData.postValue(true);
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

                        Log.e("error", e.getMessage());
                    }
                });
    }

    public void updateDish(Context context, AddDishModel addDishModel, Uri uri) {
        ProgressDialog dialog = Common.createProgressDialog(context, context.getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();

        RequestBody titel = Common.getRequestBodyText(addDishModel.getTitel());
        RequestBody category_dishes_id = Common.getRequestBodyText(addDishModel.getCategory_dishes_id() + "");
        RequestBody price = Common.getRequestBodyText(addDishModel.getPrice());
        RequestBody details=Common.getRequestBodyText(addDishModel.getDetails());
        RequestBody qty = Common.getRequestBodyText(addDishModel.getQty());
        RequestBody dishes_id = Common.getRequestBodyText(addDishModel.getId());
        MultipartBody.Part image=null;
        if (!addDishModel.getPhoto().contains("storage")){
            image= Common.getMultiPart(context, uri, "photo");
        }


        Log.e("data",addDishModel.getCategory_dishes_id()+"___"+addDishModel.getId());
        Api.getService(Tags.base_url).updateDish(titel, category_dishes_id, price,details, image, qty, dishes_id)
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
                        Log.e("code",response.body().getStatus()+"__");
                        if (response.isSuccessful()){
                            if (response.body()!=null && response.body().getStatus()==200){
                                updateDishLiveData.postValue(true);
                            }
                        }
                        else {
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

}
