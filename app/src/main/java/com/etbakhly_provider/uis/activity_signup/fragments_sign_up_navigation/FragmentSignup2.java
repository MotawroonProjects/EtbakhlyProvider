package com.etbakhly_provider.uis.activity_signup.fragments_sign_up_navigation;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.etbakhly_provider.R;
import com.etbakhly_provider.adapter.SpinnerDayAdapter;
import com.etbakhly_provider.databinding.FragmentSignUp2Binding;
import com.etbakhly_provider.model.SelectedLocation;
import com.etbakhly_provider.model.SignUpModel;
import com.etbakhly_provider.model.UserModel;
import com.etbakhly_provider.mvvm.ActivitySignupMvvm;
import com.etbakhly_provider.mvvm.FragmentSignup1Mvvm;
import com.etbakhly_provider.mvvm.FragmentSignup2Mvvm;
import com.etbakhly_provider.mvvm.GeneralSignUpMvvm;
import com.etbakhly_provider.share.Common;
import com.etbakhly_provider.uis.activity_base.BaseFragment;
import com.etbakhly_provider.uis.activity_map.MapActivity;
import com.etbakhly_provider.uis.activity_signup.SignupActivity;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FragmentSignup2 extends BaseFragment {
    private SignupActivity activity;
    private FragmentSignUp2Binding binding;
    private SignUpModel signUpModel;
    private GeneralSignUpMvvm generalSignUpMvvm;
    private FragmentSignup2Mvvm mvvm;
    private CompositeDisposable disposable = new CompositeDisposable();
    private UserModel userModel;


    public static FragmentSignup2 newInstance(UserModel userModel) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", userModel);
        FragmentSignup2 fragmentSignup2 = new FragmentSignup2();
        fragmentSignup2.setArguments(bundle);
        return fragmentSignup2;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (SignupActivity) context;


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            userModel = (UserModel) bundle.getSerializable("data");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up2, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Observable.timer(130, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);

                    }

                    @Override
                    public void onNext(@NonNull Long aLong) {
                        initView();

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void initView() {

        generalSignUpMvvm = ViewModelProviders.of(activity).get(GeneralSignUpMvvm.class);
        mvvm = ViewModelProviders.of(this).get(FragmentSignup2Mvvm.class);
        mvvm.getUserData().observe(this, userModel -> {
            Intent intent = activity.getIntent();
            intent.putExtra("data", userModel);
            activity.setResult(RESULT_OK, intent);
            activity.finish();

        });
        generalSignUpMvvm.getSignUpModel().observe(activity, model -> {
            if (model != null) {
                this.signUpModel = model;
                binding.setModel(model);
            }

        });
        binding.setLang(getLang());


        binding.rdwomen.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                signUpModel.setSex_type("women");

            }


        });
        binding.rdmen.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                signUpModel.setSex_type("man");

            }


        });
        binding.rdboth.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                signUpModel.setSex_type("man_and_women");

            }


        });
        binding.rdnothing.setOnCheckedChangeListener((compoundButton, b) -> {

            if (b) {
                signUpModel.setSex_type("not_found");

            }

        });
        binding.btnNext.setOnClickListener(view -> {
            String type = userModel.getData().getType();
            String option_id = "";
            if (type.equals("service")) {
                option_id = "1";
            } else if (type.equals("chef")) {
                option_id = "2";

            } else {
                option_id = "3";

            }
            mvvm.completeRegister(signUpModel, userModel.getData().getId(), option_id, activity);
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        disposable.clear();
    }

}