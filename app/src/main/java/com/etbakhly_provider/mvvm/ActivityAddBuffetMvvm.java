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
import com.etbakhly_provider.model.AddBuffetDataModel;
import com.etbakhly_provider.model.StatusResponse;
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

public class ActivityAddBuffetMvvm extends AndroidViewModel {

    private MutableLiveData<Boolean> addBuffetLiveData;
    private MutableLiveData<Boolean> updateBuffetLiveData;

    private CompositeDisposable disposable = new CompositeDisposable();

    public ActivityAddBuffetMvvm(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Boolean> getAddBuffetMutableLiveData() {
        if (addBuffetLiveData == null) {
            addBuffetLiveData = new MutableLiveData<>();
        }
        return addBuffetLiveData;
    }

    public MutableLiveData<Boolean> getUpdateBuffetLiveData() {
        if (updateBuffetLiveData==null){
            updateBuffetLiveData=new MutableLiveData<>();
        }
        return updateBuffetLiveData;
    }

    public void storeBuffet(Context context, AddBuffetModel addBuffetModel, Uri uri) {
        ProgressDialog dialog = Common.createProgressDialog(context, context.getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();
        Log.e("data", addBuffetModel.getTitel() + "__" + addBuffetModel.getNumber_people() + "_" + addBuffetModel.getService_provider_type() + "_" + addBuffetModel.getOrder_time() + "_" + addBuffetModel.getPhoto() + "_" + addBuffetModel.getPrice());

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
        Log.e("id",addBuffetModel.getCategory_dishes_id()+"__"+addBuffetModel.getCaterer_id());

        Api.getService(Tags.base_url).storeBuffet(titel, number_people, service_provider_type, order_time, image, price, category_dishes_id, caterer_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<AddBuffetDataModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<AddBuffetDataModel> response) {
                        dialog.dismiss();
                        if (response.isSuccessful()) {
                            Log.e("status", response.body().getStatus() + "");
                            if (response.body() != null && response.body().getStatus() == 200) {
                                addBuffetLiveData.postValue(true);
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

    public void editBuffet(Context context, AddBuffetModel addBuffetModel, Uri uri){
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

        MultipartBody.Part image=null;
        if (!addBuffetModel.getPhoto().contains("storage")){
            image= Common.getMultiPart(context, uri, "photo");
        }

        Api.getService(Tags.base_url).updateBuffet(titel,number_people,service_provider_type,order_time,image,price,category_dishes_id,Buffet_id)
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
                        if (response.isSuccessful()){
                            if (response.body()!=null && response.body().getStatus()==200){
                                updateBuffetLiveData.postValue(true);
                            }
                        } else {
                            try {
                                Log.e("error",response.errorBody().string());
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
}
