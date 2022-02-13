package com.etbakhly_provider.mvvm;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;


import com.etbakhly_provider.R;
import com.etbakhly_provider.model.RegisterModel;
import com.etbakhly_provider.model.UserModel;
import com.etbakhly_provider.remote.Api;
import com.etbakhly_provider.share.Common;
import com.etbakhly_provider.tags.Tags;

import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

public class ActivityRegisterMvvm extends AndroidViewModel {
    public MutableLiveData<UserModel> onUserDataSuccess;

    private CompositeDisposable disposable = new CompositeDisposable();

    public ActivityRegisterMvvm(@NonNull Application application) {
        super(application);


    }

    public MutableLiveData<UserModel> getUserData() {
        if (onUserDataSuccess == null) {
            onUserDataSuccess = new MutableLiveData<>();
        }
        return onUserDataSuccess;
    }

    public void signUp(RegisterModel model, Context context) {

        ProgressDialog dialog = Common.createProgressDialog(context, context.getResources().getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();


        Api.getService(Tags.base_url).signUp(model.getName(), model.getPhone_code(), model.getPhone(), model.getEmail(), model.getLng() + "", model.getLat() + "", model.getService(), "android")
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
                        if (userModelResponse.isSuccessful()) {

                            if (userModelResponse.body() != null) {
                                if (userModelResponse.body().getStatus() == 200) {

                                    getUserData().setValue(userModelResponse.body());
                                } else if (userModelResponse.body().getStatus() == 404) {
                                    Toast.makeText(context, R.string.ph_em_found, Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        dialog.dismiss();
                    }
                });
    }

//    public void update(SignUpModel model, String user_id, Context context) {
//        ProgressDialog dialog = Common.createProgressDialog(context, context.getResources().getString(R.string.wait));
//        dialog.setCancelable(false);
//        dialog.show();
//        RequestBody user_id_part = Common.getRequestBodyText(user_id);
//
//        RequestBody name_part = Common.getRequestBodyText(model.getName());
//        RequestBody email_part = Common.getRequestBodyText(model.getEmail());
//
//        MultipartBody.Part image = null;
//        if (model.getImage() != null && !model.getImage().isEmpty()) {
//            if (!model.getImage().startsWith("http")) {
//                image = Common.getMultiPart(context, Uri.parse(model.getImage()), "photo");
//
//            }
//        }
//
//
//        Api.getService(Tags.base_url).updateProfile(user_id_part, name_part, email_part, image)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<Response<UserModel>>() {
//                    @Override
//                    public void onSubscribe(@NonNull Disposable d) {
//                        disposable.add(d);
//                    }
//
//                    @Override
//                    public void onNext(@NonNull Response<UserModel> response) {
//                        dialog.dismiss();
//                        if (response.isSuccessful()) {
//
//                            if (response.body() != null) {
//                                if (response.body().getStatus() == 200) {
//
//                                    getUserData().setValue(response.body());
//                                } else if (response.body().getStatus() == 404) {
//                                    Toast.makeText(context, R.string.ph_em_found, Toast.LENGTH_LONG).show();
//                                }
//                            }
//
//                        }
//                    }
//
//                    @Override
//                    public void onError(@NonNull Throwable throwable) {
//                        dialog.dismiss();
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        dialog.dismiss();
//                    }
//                });
//    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
