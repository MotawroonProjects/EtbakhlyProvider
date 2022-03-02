package com.etbakhly_provider.uis.activity_contact_us;

import android.os.Bundle;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.etbakhly_provider.R;
import com.etbakhly_provider.databinding.ActivityContactUsBinding;
import com.etbakhly_provider.model.ContactUsModel;
import com.etbakhly_provider.model.UserModel;
import com.etbakhly_provider.mvvm.ContactusActivityMvvm;
import com.etbakhly_provider.preferences.Preferences;
import com.etbakhly_provider.uis.activity_base.BaseActivity;


public class ContactUsActivity extends BaseActivity {
    private ActivityContactUsBinding binding;
    private ContactUsModel contactUsModel;
    private ContactusActivityMvvm contactusActivityMvvm;
    private UserModel userModel;
    private Preferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_contact_us);
        initView();

    }

    private void initView() {
        binding.setLang(getLang());

        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(this);
        contactusActivityMvvm = ViewModelProviders.of(this).get(ContactusActivityMvvm.class);
//         setUpToolbar(binding.toolbar, getString(R.string.contact_us), R.color.white, R.color.black);


        contactUsModel = new ContactUsModel();
        contactUsModel.setContext(this);
        if (userModel != null) {
            contactUsModel.setName(userModel.getData().getName());
            contactUsModel.setEmail(userModel.getData().getEmail());

        }
        binding.setContactUsModel(contactUsModel);

        binding.btnSend.setOnClickListener(view -> {
                contactusActivityMvvm.contactus(this, contactUsModel);

        });
        contactusActivityMvvm.send.observe(this, aBoolean -> {
            if (aBoolean) {
                Toast.makeText(ContactUsActivity.this, getResources().getString(R.string.suc), Toast.LENGTH_LONG).show();
                finish();
            }
        });
        binding.llBack.setOnClickListener(view -> finish());

    }


}