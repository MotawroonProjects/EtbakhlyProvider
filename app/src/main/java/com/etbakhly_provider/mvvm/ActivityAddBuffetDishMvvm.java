package com.etbakhly_provider.mvvm;

import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.etbakhly_provider.model.AddBuffetDishDataModel;
import com.etbakhly_provider.model.AddBuffetDishModel;
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

public class ActivityAddBuffetDishMvvm extends AndroidViewModel {

    private MutableLiveData<Boolean> addBuffetDishLiveData;
    private CompositeDisposable disposable = new CompositeDisposable();
    public ActivityAddBuffetDishMvvm(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Boolean> getAddBuffetDishLiveData() {
        if (addBuffetDishLiveData==null){
            addBuffetDishLiveData=new MutableLiveData<>();
        }
        return addBuffetDishLiveData;
    }

    public void storeBuffetsDishes(Context context, AddBuffetDishModel addBuffetDishModel, Uri uri){
        RequestBody titel= Common.getRequestBodyText(addBuffetDishModel.getTitel());
        RequestBody qty= Common.getRequestBodyText(addBuffetDishModel.getQty());
        RequestBody price= Common.getRequestBodyText(addBuffetDishModel.getTitel());
        RequestBody category_dishes_id= Common.getRequestBodyText(addBuffetDishModel.getCategory_dishes_id()+"");
        RequestBody buffets_id= Common.getRequestBodyText("27");
        RequestBody details=Common.getRequestBodyText(addBuffetDishModel.getDetails());

        MultipartBody.Part image=Common.getMultiPart(context,uri,"photo");

        Api.getService(Tags.base_url).storeBuffetsDishes(titel,category_dishes_id,price,details,image,qty,buffets_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<AddBuffetDishDataModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<AddBuffetDishDataModel> response) {
                        if (response.isSuccessful()){
                            if (response.body().getStatus()==200){
                                addBuffetDishLiveData.postValue(true);
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
