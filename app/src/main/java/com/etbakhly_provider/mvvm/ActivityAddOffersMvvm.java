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
import com.etbakhly_provider.model.AddOfferModel;
import com.etbakhly_provider.model.BuffetModel;
import com.etbakhly_provider.model.DishesDataModel;
import com.etbakhly_provider.model.OfferModel;
import com.etbakhly_provider.model.SingleOfferDataModel;
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

public class ActivityAddOffersMvvm extends AndroidViewModel {

    private MutableLiveData<OfferModel> addOfferLiveData;
    private MutableLiveData<OfferModel> updateOfferLiveData;

    private CompositeDisposable disposable = new CompositeDisposable();

    public ActivityAddOffersMvvm(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<OfferModel> getAddOfferMutableLiveData() {
        if (addOfferLiveData == null) {
            addOfferLiveData = new MutableLiveData<>();
        }
        return addOfferLiveData;
    }

    public MutableLiveData<OfferModel> getUpdateOfferLiveData() {
        if (updateOfferLiveData == null) {
            updateOfferLiveData = new MutableLiveData<>();
        }
        return updateOfferLiveData;
    }

    public void storeOffer(Context context, AddOfferModel addOfferModel) {
        ProgressDialog dialog = Common.createProgressDialog(context, context.getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();

        RequestBody name = Common.getRequestBodyText(addOfferModel.getTitle());
        RequestBody title = Common.getRequestBodyText(addOfferModel.getTitle());
        RequestBody subTitle = Common.getRequestBodyText(addOfferModel.getSub_titel());
        RequestBody endDate = Common.getRequestBodyText(addOfferModel.getEnd_date());
        RequestBody option_id = Common.getRequestBodyText(addOfferModel.getOption_id());
        RequestBody caterer_id = Common.getRequestBodyText(addOfferModel.getCaterer_id());
        RequestBody price = Common.getRequestBodyText(addOfferModel.getPrice());

        MultipartBody.Part image = null;
        if (addOfferModel.getPhoto() != null && !addOfferModel.getPhoto().isEmpty()) {
            image = Common.getMultiPart(context, Uri.parse(addOfferModel.getPhoto()), "photo");
        }
        Api.getService(Tags.base_url).storeOffer(name, option_id, title, subTitle, image, price, endDate, caterer_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<SingleOfferDataModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<SingleOfferDataModel> response) {
                        dialog.dismiss();
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatus() == 200) {
                                addOfferLiveData.postValue(response.body().getData());
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

    public void updateOffer(Context context, AddOfferModel addOfferModel) {
        ProgressDialog dialog = Common.createProgressDialog(context, context.getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();

        RequestBody name = Common.getRequestBodyText(addOfferModel.getTitle());
        RequestBody title = Common.getRequestBodyText(addOfferModel.getTitle());
        RequestBody subTitle = Common.getRequestBodyText(addOfferModel.getSub_titel());
        RequestBody endDate = Common.getRequestBodyText(addOfferModel.getEnd_date());
        RequestBody option_id = Common.getRequestBodyText(addOfferModel.getOption_id());
        RequestBody caterer_id = Common.getRequestBodyText(addOfferModel.getCaterer_id());
        RequestBody price = Common.getRequestBodyText(addOfferModel.getPrice());
        RequestBody offer_id = Common.getRequestBodyText(addOfferModel.getId());

        MultipartBody.Part image = null;
        if (!addOfferModel.getPhoto().contains("storage")) {
            image = Common.getMultiPart(context, Uri.parse(addOfferModel.getPhoto()), "photo");
        }

        Api.getService(Tags.base_url).updateOffer(name, option_id, title, subTitle, image, price, endDate, caterer_id,offer_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<SingleOfferDataModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<SingleOfferDataModel> response) {
                        dialog.dismiss();
                        if (response.isSuccessful()) {

                            if (response.body() != null && response.body().getStatus() == 200) {
                                getUpdateOfferLiveData().postValue(response.body().getData());
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


}
