package com.etbakhly_provider.uis.activity_signup;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.etbakhly_provider.R;
import com.etbakhly_provider.databinding.ActivitySignUpBinding;
import com.etbakhly_provider.language.Language;
import com.etbakhly_provider.model.AddZoneModel;
import com.etbakhly_provider.model.DeliveryModel;
import com.etbakhly_provider.model.SignUpModel;
import com.etbakhly_provider.model.StoreCatreerDataModel;
import com.etbakhly_provider.model.UserModel;
import com.etbakhly_provider.mvvm.ActivityRegisterMvvm;
import com.etbakhly_provider.mvvm.ActivitySignupMvvm;
import com.etbakhly_provider.preferences.Preferences;
import com.etbakhly_provider.uis.activity_base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class SignupActivity extends BaseActivity {
    private ActivitySignUpBinding binding;
    private NavController navController;
    private UserModel userModel;
    private StoreCatreerDataModel storeCatreerDataModel;
    private List<DeliveryModel> deliveryModels;
    private ActivitySignupMvvm mvvm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);
        getDataFromIntent();
        initView();


    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        userModel = (UserModel) intent.getSerializableExtra("data");

    }

    private void initView() {
        mvvm = ViewModelProviders.of(this).get(ActivitySignupMvvm.class);

        navController = Navigation.findNavController(this, R.id.navHostFragment);
        mvvm.getUserData().observe(this, new Observer<UserModel>() {
            @Override
            public void onChanged(UserModel userModel) {
                Intent intent = getIntent();
                intent.putExtra("data", userModel);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        //  NavigationUI.setupActionBarWithNavController(this, navController);


    }


    @Override
    public void onBackPressed() {
        int currentFragmentId = navController.getCurrentDestination().getId();
        if (currentFragmentId == R.id.signup1) {
            finish();

        } else {
            navController.popBackStack();
        }

    }


    public void completedata(SignUpModel signUpModel) {
        storeCatreerDataModel = new StoreCatreerDataModel();
        deliveryModels = new ArrayList<>();
        storeCatreerDataModel.setUser_id(userModel.getData().getId());
        storeCatreerDataModel.setCategory_id(signUpModel.getCat_id());
        storeCatreerDataModel.setAddress(signUpModel.getAddress());
        storeCatreerDataModel.setCommercial_register(signUpModel.getLicenseNumber());
        storeCatreerDataModel.setDelivry_time(signUpModel.getDeliverytime());
        storeCatreerDataModel.setProcessing_time(signUpModel.getProcessingtime());
        storeCatreerDataModel.setLatitude(signUpModel.getLat() + "");
        storeCatreerDataModel.setLongitude(signUpModel.getLng() + "");
        storeCatreerDataModel.setNotes(signUpModel.getNote());
        storeCatreerDataModel.setFree_delivery("1");
        storeCatreerDataModel.setStart_work(signUpModel.getFrom());
        storeCatreerDataModel.setEnd_work(signUpModel.getTo());
        storeCatreerDataModel.setIs_delivry(signUpModel.getIs_delivery());
        storeCatreerDataModel.setSex_type(signUpModel.getSex_type());
        storeCatreerDataModel.setNumber_of_reservation_days(signUpModel.getBooking_before());
        if (userModel.getData().getType().equals("service")) {
            storeCatreerDataModel.setOption_id(1);

        } else if (userModel.getData().getType().equals("chef")) {
            storeCatreerDataModel.setOption_id(2);

        } else {
            storeCatreerDataModel.setOption_id(3);

        }
        if (signUpModel.getCustomers_service().isEmpty()) {
            storeCatreerDataModel.setCustomers_service("0");

        } else {
            storeCatreerDataModel.setCustomers_service(signUpModel.getCustomers_service());
        }
        if (signUpModel.getDiscount().isEmpty()) {
            storeCatreerDataModel.setDiscount("0");
        } else {
            storeCatreerDataModel.setDiscount(signUpModel.getDiscount());

        }
        if (signUpModel.getTax().isEmpty()) {
            storeCatreerDataModel.setTax("0");

        } else {
            storeCatreerDataModel.setTax(signUpModel.getTax());

        }
        if (signUpModel.getAddZoneModels().size() > 0) {
            for (int i = 0; i < signUpModel.getAddZoneModels().size(); i++) {
                DeliveryModel deliveryModel = new DeliveryModel();
                AddZoneModel addZoneModel = signUpModel.getAddZoneModels().get(i);
                deliveryModel.setZone_id(Integer.parseInt(addZoneModel.getZone_id()));
                if (addZoneModel.getZone_cost().isEmpty()) {
                    deliveryModel.setZone_cost(0);
                } else {
                    deliveryModel.setZone_cost(Double.parseDouble(addZoneModel.getZone_cost()));
                }
                deliveryModels.add(deliveryModel);
            }
            storeCatreerDataModel.setDelivry(deliveryModels);
            mvvm.signUp(storeCatreerDataModel, this);
        }
    }
}
