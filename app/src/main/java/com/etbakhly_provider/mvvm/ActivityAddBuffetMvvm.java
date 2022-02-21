package com.etbakhly_provider.mvvm;

import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.etbakhly_provider.model.AddBuffetModel;
import com.etbakhly_provider.model.AddBuffetDataModel;
import com.etbakhly_provider.remote.Api;
import com.etbakhly_provider.share.Common;
import com.etbakhly_provider.tags.Tags;

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
    private CompositeDisposable disposable = new CompositeDisposable();

    public ActivityAddBuffetMvvm(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Boolean> getAddBuffetMutableLiveData() {
        if (addBuffetLiveData==null){
            addBuffetLiveData=new MutableLiveData<>();
        }
        return addBuffetLiveData;
    }

    public void storeBuffet(Context context, AddBuffetModel addBuffetModel, Uri uri){
        RequestBody titel= Common.getRequestBodyText(addBuffetModel.getTitel());
        RequestBody number_people= Common.getRequestBodyText(addBuffetModel.getNumber_people());
        RequestBody service_provider_type= Common.getRequestBodyText(addBuffetModel.getService_provider_type());
        RequestBody order_time= Common.getRequestBodyText(addBuffetModel.getOrder_time());
        RequestBody price= Common.getRequestBodyText(addBuffetModel.getTitel());
        RequestBody category_dishes_id= Common.getRequestBodyText(addBuffetModel.getCategory_dishes_id()+"");
        RequestBody caterer_id= Common.getRequestBodyText("27");

        MultipartBody.Part image=Common.getMultiPart(context,uri,"photo");

        Api.getService(Tags.base_url).storeBuffet(titel,number_people,service_provider_type,order_time,image,price,category_dishes_id,caterer_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<AddBuffetDataModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<AddBuffetDataModel> response) {
                        if (response.isSuccessful()){
                            if (response.body().getStatus()==200){
                                addBuffetLiveData.postValue(true);
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
