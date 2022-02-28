package com.etbakhly_provider.mvvm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.etbakhly_provider.model.UserModel;

import io.reactivex.disposables.CompositeDisposable;

public class ActivitySignupMvvm extends AndroidViewModel {
    public MutableLiveData<UserModel> onUserDataSuccess;

    private CompositeDisposable disposable = new CompositeDisposable();

    public ActivitySignupMvvm(@NonNull Application application) {
        super(application);


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
