package com.etbakhly_provider.mvvm;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.etbakhly_provider.R;
import com.etbakhly_provider.model.AddZoneModel;
import com.etbakhly_provider.model.DeliveryModel;
import com.etbakhly_provider.model.SignUpModel;
import com.etbakhly_provider.model.StoreCatererDataModel;
import com.etbakhly_provider.model.UserModel;
import com.etbakhly_provider.remote.Api;
import com.etbakhly_provider.share.Common;
import com.etbakhly_provider.tags.Tags;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class FragmentSignup2Mvvm extends AndroidViewModel {
    private static final String TAG = "FragmentSignup2Mvvm";
    public MutableLiveData<UserModel> onUserDataSuccess;
    private CompositeDisposable disposable = new CompositeDisposable();


    public FragmentSignup2Mvvm(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<UserModel> getUserData() {
        if (onUserDataSuccess == null) {
            onUserDataSuccess = new MutableLiveData<>();
        }
        return onUserDataSuccess;
    }

    public void completeRegister(SignUpModel model, String user_id, String option_id, Context context) {
        Log.e("user_id", user_id);
        Log.e("option_id", option_id);
        Log.e("from_time", model.getWorking_time_from());
        Log.e("to_time", model.getWorking_time_to());
        Log.e("cat", model.getCat_id() + "");
        Log.e("del_time", model.getDelivery_time_from());
        Log.e("del_time_to", model.getDelivery_time_to());
        Log.e("prfrom", model.getProcess_time_from());
        Log.e("proto", model.getProcess_time_to());
        Log.e("not", model.getNote());
        Log.e("numc", model.getLicenseNumber());
        Log.e("tax", model.getTax());
        Log.e("del", model.getIs_delivery());
        Log.e("secus", model.getCustomers_service());
        Log.e("dis", model.getDiscount());
        Log.e("book", model.getBooking_before());
        Log.e("sertyp", model.getSex_type());
        Log.e("lat", model.getLat());
        Log.e("lng", model.getLng());


        List<DeliveryModel> list = new ArrayList<>();
        for (AddZoneModel addZoneModel : model.getAddZoneModels()) {
            DeliveryModel deliveryModel = new DeliveryModel(Integer.parseInt(addZoneModel.getZone_id()), Double.parseDouble(addZoneModel.getZone_cost()));
            list.add(deliveryModel);
            Log.e("zone", deliveryModel.getZone_id() + "___" + deliveryModel.getZone_cost());
        }
        StoreCatererDataModel dataModel = new StoreCatererDataModel(user_id, model.getNote(), option_id, model.getCat_id() + "", model.getSex_type(), model.getIs_delivery(), model.getDelivery_time_from() + "-" + model.getDelivery_time_to(), model.getProcess_time_from() + "-" + model.getProcess_time_to(), "1", model.getLng(), model.getLat(), model.getWorking_time_from(), model.getWorking_time_to(), model.getTax(), model.getCustomers_service(), model.getDiscount(), model.getBooking_before(), model.getLicenseNumber(), model.getAddress(), list);

        ProgressDialog dialog = Common.createProgressDialog(context, context.getResources().getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();


        Api.getService(Tags.base_url).storeCaterer(dataModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<UserModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<UserModel> userModelResponse) {
                        dialog.dismiss();

                        Log.e("lllll", userModelResponse.toString() + "_");


                        if (userModelResponse.isSuccessful()) {

                            if (userModelResponse.body() != null) {
                                if (userModelResponse.body().getStatus() == 200) {
                                    getUserData().setValue(userModelResponse.body());
                                }
                            }

                        } else {


                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        dialog.dismiss();
                        Log.e("lllkkkk", e.toString());
                    }
                });
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();

    }

}
