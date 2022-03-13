package com.etbakhly_provider.mvvm;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.etbakhly_provider.R;
import com.etbakhly_provider.model.OrderDataModel;
import com.etbakhly_provider.model.OrderModel;
import com.etbakhly_provider.model.StatusResponse;
import com.etbakhly_provider.model.UserModel;
import com.etbakhly_provider.remote.Api;
import com.etbakhly_provider.share.Common;
import com.etbakhly_provider.tags.Tags;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.IOException;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ActivityHomeGeneralMvvm extends AndroidViewModel {


    private MutableLiveData<String> onStatusSuccess;
    private MutableLiveData<Integer> onPositionScreenChanged;
    private MutableLiveData<UserModel> onTokenSuccess;
    private MutableLiveData<Boolean> onLoggedOutSuccess;
    private MutableLiveData<Boolean> onGallerySuccess;
    private MutableLiveData<Boolean> onFragmentNewOrderRefreshed;
    private MutableLiveData<Boolean> onFragmentPendingOrderRefreshed;
    private MutableLiveData<Boolean> onFragmentCompleteOrderRefreshed;

    private CompositeDisposable disposable = new CompositeDisposable();

    public ActivityHomeGeneralMvvm(@NonNull Application application) {
        super(application);
    }


    public MutableLiveData<String> getOnStatusSuccess() {
        if (onStatusSuccess == null) {
            onStatusSuccess = new MutableLiveData<>();
        }
        return onStatusSuccess;
    }

    public MutableLiveData<Integer> getPosChangedSuccess() {
        if (onPositionScreenChanged == null) {
            onPositionScreenChanged = new MutableLiveData<>();
        }
        return onPositionScreenChanged;
    }

    public MutableLiveData<UserModel> onTokenSuccess() {
        if (onTokenSuccess == null) {
            onTokenSuccess = new MutableLiveData<>();
        }

        return onTokenSuccess;
    }


    public MutableLiveData<Boolean> onLoggedOutSuccess() {
        if (onLoggedOutSuccess == null) {
            onLoggedOutSuccess = new MutableLiveData<>();
        }

        return onLoggedOutSuccess;
    }

    public MutableLiveData<Boolean> getOnFragmentPendingOrderRefreshed() {
        if (onFragmentPendingOrderRefreshed == null) {
            onFragmentPendingOrderRefreshed = new MutableLiveData<>();
        }

        return onFragmentPendingOrderRefreshed;
    }

    public MutableLiveData<Boolean> getOnFragmentCompleteOrderRefreshed() {
        if (onFragmentCompleteOrderRefreshed == null) {
            onFragmentCompleteOrderRefreshed = new MutableLiveData<>();
        }

        return onFragmentCompleteOrderRefreshed;
    }

    public MutableLiveData<Boolean> getOnFragmentNewOrderRefreshed() {
        if (onFragmentNewOrderRefreshed == null) {
            onFragmentNewOrderRefreshed = new MutableLiveData<>();
        }

        return onFragmentNewOrderRefreshed;
    }


    public MutableLiveData<Boolean> onGallerySuccess() {
        if (onGallerySuccess == null) {
            onGallerySuccess = new MutableLiveData<>();
        }

        return onGallerySuccess;
    }

    public void updateToken(UserModel userModel) {
        if (userModel == null) {
            return;
        }
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                String token = task.getResult();
                Api.getService(Tags.base_url)
                        .updateFireBaseToken(token, userModel.getData().getId(), "android")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new SingleObserver<Response<StatusResponse>>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                disposable.add(d);
                            }

                            @Override
                            public void onSuccess(@NonNull Response<StatusResponse> response) {

                                if (response.isSuccessful()) {
                                    Log.e("status", response.body().getStatus() + "");
                                    if (response.body() != null) {
                                        if (response.body().getStatus() == 200) {
                                            userModel.setFireBaseToken(token);
                                            onTokenSuccess().setValue(userModel);
                                            Log.e("token", "updated");

                                        }
                                    }

                                } else {
                                    try {
                                        Log.e("error", response.errorBody().string() + "__" + response.code());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }

                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                Log.e("token", e.toString());

                            }
                        });
            }
        });


    }

    public void logout(UserModel userModel, Context context) {
        if (userModel == null) {
            return;
        }
        Log.e("token", userModel.getFireBaseToken());
        ProgressDialog dialog = Common.createProgressDialog(context, context.getString(R.string.wait));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();

        Api.getService(Tags.base_url)
                .logout(userModel.getData().getId(), userModel.getFireBaseToken())
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
                            if (response.body() != null) {
                                if (response.body().getStatus() == 200) {
                                    onLoggedOutSuccess().setValue(true);

                                }
                            }

                        } else {
                            try {
                                Log.e("error", response.errorBody().string() + "__" + response.code());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        dialog.dismiss();

                        Log.e("token", e.toString());

                    }
                });


    }


}
