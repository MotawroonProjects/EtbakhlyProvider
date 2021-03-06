package com.etbakhly_provider.uis.activity_splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.etbakhly_provider.R;
import com.etbakhly_provider.databinding.ActivitySplashBinding;
import com.etbakhly_provider.language.Language;
import com.etbakhly_provider.model.UserSettingsModel;
import com.etbakhly_provider.uis.activities_home.HomeActivity;
import com.etbakhly_provider.uis.activity_base.BaseActivity;
import com.etbakhly_provider.uis.activity_language_country.LanguageCountryActivity;
import com.etbakhly_provider.uis.activity_login.LoginActivity;
import com.etbakhly_provider.uis.activity_signup.SignupActivity;
import com.google.common.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import io.paperdb.Paper;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SplashActivity extends BaseActivity {
    private ActivitySplashBinding binding;
    private CompositeDisposable disposable = new CompositeDisposable();
    private int req;
    private ActivityResultLauncher<Intent> launcher;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        initView();

    }

    private void initView() {
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (req == 1 && result.getResultCode() == RESULT_OK) {
                if (result.getData() != null) {
                    UserSettingsModel userSettingsModel = getUserSettings();
                    if (userSettingsModel == null) {
                        userSettingsModel = new UserSettingsModel();
                    }
                    userSettingsModel.setFirstTime(false);
                    setUserSettings(userSettingsModel);
                    String lang = result.getData().getStringExtra("lang");
                    refreshActivity(lang);
                } else {
                    setUpData();
                }
            } else if (req == 2 && result.getResultCode() == RESULT_OK) {
                navigateToHomeActivity();
            }
        });
        Observable.timer(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Long aLong) {
                        setUpData();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    private void setUpData() {
        req = 1;
        UserSettingsModel userSettingsModel = getUserSettings();

        if (userSettingsModel == null) {
            navigateToLanguageCountryActivity();
        } else {
            if (userSettingsModel.isFirstTime()) {
                navigateToLanguageCountryActivity();

            } else {

                if (getUserModel() != null) {
                    if (getUserModel().getData().getIs_completed().equals("yes")) {
                        navigateToHomeActivity();

                    } else {
                        navigateToCompleteSignUpActivity();
                    }

                } else {
                    navigateToLoginActivity();
                }

            }
        }
    }

    private void navigateToCompleteSignUpActivity() {
        req = 2;
        Intent intent = new Intent(this, SignupActivity.class);
        launcher.launch(intent);
    }


    private void navigateToLanguageCountryActivity() {
        Intent intent = new Intent(this, LanguageCountryActivity.class);
        launcher.launch(intent);
    }

    public void refreshActivity(String lang) {
        Paper.book().write("lang", lang);
        Language.setNewLocale(this, lang);
        new Handler()
                .postDelayed(() -> {

                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }, 500);


    }


    private void navigateToHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }

    private void navigateToLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }
}

