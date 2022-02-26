package com.etbakhly_provider.uis.activity_signup.fragments_sign_up_navigation;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import com.etbakhly_provider.R;

import com.etbakhly_provider.adapter.AddZoneAdapter;
import com.etbakhly_provider.adapter.SpinnerCategoryAdapter;
import com.etbakhly_provider.adapter.SpinnerCityAdapter;
import com.etbakhly_provider.adapter.SpinnerCountryAdapter;
import com.etbakhly_provider.adapter.SpinnerZoneAdapter;
import com.etbakhly_provider.databinding.AddZoneCostSheetBinding;
import com.etbakhly_provider.databinding.FragmentSignUp1Binding;
import com.etbakhly_provider.model.AddZoneModel;
import com.etbakhly_provider.model.CategoryModel;
import com.etbakhly_provider.model.CountryModel;
import com.etbakhly_provider.model.SelectedLocation;
import com.etbakhly_provider.model.SignUpModel;
import com.etbakhly_provider.mvvm.FragmentSignup1Mvvm;
import com.etbakhly_provider.share.Common;
import com.etbakhly_provider.uis.activity_base.BaseFragment;
import com.etbakhly_provider.uis.activity_map.MapActivity;
import com.etbakhly_provider.uis.activity_signup.SignupActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class FragmentSignup1 extends BaseFragment implements TimePickerDialog.OnTimeSetListener {
    private SignupActivity activity;
    private FragmentSignUp1Binding binding;
    private FragmentSignup1Mvvm fragmentSignup1Mvvm;
    private ActivityResultLauncher<Intent> launcher;
    private SignUpModel model;
    private TimePickerDialog timePickerDialog;
    private SpinnerCountryAdapter spinnerCountryAdapter;
    private SpinnerZoneAdapter spinnerZoneAdapter;
    private SpinnerCategoryAdapter spinnerCategoryAdapter;
    private AddZoneAdapter addZoneAdapter;
    private String type;

    public static FragmentSignup1 newInstance() {
        return new FragmentSignup1();
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (SignupActivity) context;
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                SelectedLocation location = (SelectedLocation) result.getData().getSerializableExtra("location");
                model.setAddress(location.getAddress());
                binding.setModel(model);


            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up1, container, false);
            initView();
        }

        return binding.getRoot();
    }


    private void initView() {

        if (model == null) {
            model = new SignUpModel();
        }
        binding.setModel(model);
        binding.setLang(getLang());
        spinnerCountryAdapter = new SpinnerCountryAdapter(activity);
        spinnerZoneAdapter = new SpinnerZoneAdapter(activity);
        spinnerCategoryAdapter = new SpinnerCategoryAdapter(activity);
        fragmentSignup1Mvvm = ViewModelProviders.of(this).get(FragmentSignup1Mvvm.class);
        addZoneAdapter = new AddZoneAdapter(activity, this);
        binding.recView.setLayoutManager(new GridLayoutManager(activity, 2));
        binding.recView.setAdapter(addZoneAdapter);

        fragmentSignup1Mvvm.getOnAddZoneLiveData().observe(activity, addZoneModels -> {
            if (addZoneAdapter!=null){

                addZoneAdapter.updateList(addZoneModels);
            }

        });



        fragmentSignup1Mvvm.onCategoryDataSuccess().observe(activity, categoryModels -> {
            if (categoryModels != null) {
                categoryModels.add(0, new CategoryModel(getResources().getString(R.string.ch_cat)));

                spinnerCategoryAdapter.updateData(categoryModels);
            }
        });
        fragmentSignup1Mvvm.getCountryLiveData().observe(activity, countryModels -> {
            if (countryModels != null) {
                spinnerCountryAdapter.updateData(countryModels);
            }
        });

        fragmentSignup1Mvvm.getZoneLiveData().observe(activity, countryModels -> {

            if (countryModels != null) {
                spinnerZoneAdapter.updateData(countryModels);
            }
        });
        binding.spCountry.setAdapter(spinnerCountryAdapter);
        binding.spZone.setAdapter(spinnerZoneAdapter);
        binding.spcategory.setAdapter(spinnerCategoryAdapter);

        binding.spcategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    model.setCat_id(Integer.parseInt(fragmentSignup1Mvvm.onCategoryDataSuccess().getValue().get(i).getId()));
                } else {
                    model.setCat_id(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.spCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    fragmentSignup1Mvvm.getZone(fragmentSignup1Mvvm.getCountryLiveData().getValue().get(i).getId(), activity);
                } else {

                    binding.spZone.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.spZone.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    createZoneCostSheet(fragmentSignup1Mvvm.getZoneLiveData().getValue().get(i));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.rdDelivery.setOnCheckedChangeListener((compoundButton, b) -> {
            binding.rdSurrender.setChecked(false);
            binding.rdDelivery.setChecked(true);
            model.setIs_delivery("delivry");
            binding.spCountry.setSelection(0);
            binding.llZones.setVisibility(View.VISIBLE);

        });

        binding.rdSurrender.setOnCheckedChangeListener((compoundButton, b) -> {
            binding.rdDelivery.setChecked(false);
            binding.rdSurrender.setChecked(true);
            model.setIs_delivery("not_delivry");
            model.setAddZoneModels(new ArrayList<>());
            fragmentSignup1Mvvm.getOnAddZoneLiveData().setValue(new ArrayList<>());
            binding.llZones.setVisibility(View.GONE);
            binding.spCountry.setSelection(0);


        });

        binding.cardAddress.setOnClickListener(view -> navigateToMapActivity());
        binding.btnNext.setOnClickListener(view -> {
            activity.nextStep(model);
        });
        fragmentSignup1Mvvm.getCategoryData();
        fragmentSignup1Mvvm.getCountries(activity);

        binding.cardWorkingTimeFrom.setOnClickListener(v -> {
            try {
                type = "1";
                createTimeDialog();
            } catch (Exception e) {
            }

        });

        binding.cardWorkingTimeTo.setOnClickListener(v -> {
            try {
                type = "2";
                createTimeDialog();
            } catch (Exception e) {
            }

        });




    }


    private void createTimeDialog() {

        Calendar calendar = Calendar.getInstance();
        timePickerDialog = TimePickerDialog.newInstance(this, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND), false);
        timePickerDialog.dismissOnPause(true);
        timePickerDialog.setAccentColor(ActivityCompat.getColor(activity, R.color.colorPrimary));
        timePickerDialog.setCancelColor(ActivityCompat.getColor(activity, R.color.gray4));
        timePickerDialog.setOkColor(ActivityCompat.getColor(activity, R.color.colorPrimary));
        timePickerDialog.setVersion(TimePickerDialog.Version.VERSION_1);
        timePickerDialog.show(activity.getFragmentManager(), "");

    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);

        String time = new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(calendar.getTime());
        switch (type) {
            case "1":
                model.setWorking_time_from(time);
                break;
            case "2":
                model.setWorking_time_to(time);

                break;



        }

        binding.setModel(model);

    }

    private int getSelectedZonePos(String zone_id) {
        int pos = -1;
        for (int index = 0; index < fragmentSignup1Mvvm.getOnAddZoneLiveData().getValue().size(); index++) {
            if (fragmentSignup1Mvvm.getOnAddZoneLiveData().getValue().get(index).getZone_id().equals(zone_id)) {
                pos = index;
                return pos;
            }
        }
        return pos;
    }


    private void navigateToMapActivity() {
        Intent intent = new Intent(activity, MapActivity.class);
        launcher.launch(intent);
    }

    public void deleteItem(int adapterPosition) {
        model.removeZone(adapterPosition);
        addZoneAdapter.removeItem(adapterPosition);
        binding.spZone.setSelection(0);


    }

    private void createZoneCostSheet(CountryModel zone) {
        BottomSheetDialog dialog = new BottomSheetDialog(activity);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(true);
        AddZoneCostSheetBinding sheetBinding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.add_zone_cost_sheet, null, false);
        dialog.setContentView(sheetBinding.getRoot());
        sheetBinding.btnAdd.setOnClickListener(v -> {
            String cost = sheetBinding.edtCost.getText().toString();
            if (!cost.isEmpty()) {
                sheetBinding.edtCost.setError(null);
                Common.CloseKeyBoard(activity, sheetBinding.edtCost);
                AddZoneModel addZoneModel = new AddZoneModel(zone.getId(), zone.getTitel(), cost);
                int pos = getSelectedZonePos(zone.getId());
                if (pos == -1) {
                    addZoneAdapter.addItem(addZoneModel);
                    model.addZone(addZoneModel);
                    binding.spZone.setSelection(0);
                    dialog.dismiss();
                } else {
                    Toast.makeText(activity, R.string.already_added, Toast.LENGTH_SHORT).show();
                }

            } else {
                sheetBinding.edtCost.setError(getString(R.string.field_required));
            }
        });
        sheetBinding.llClose.setOnClickListener(v -> {
            binding.spZone.setSelection(0);

            dialog.dismiss();

        });
        dialog.show();
    }
}