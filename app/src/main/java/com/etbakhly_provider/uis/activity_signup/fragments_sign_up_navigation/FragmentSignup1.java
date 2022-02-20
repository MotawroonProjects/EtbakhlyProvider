package com.etbakhly_provider.uis.activity_signup.fragments_sign_up_navigation;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
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
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavAction;
import androidx.navigation.Navigation;

import com.etbakhly_provider.R;

import com.etbakhly_provider.adapter.SpinnerCategoryAdapter;
import com.etbakhly_provider.adapter.SpinnerCityAdapter;
import com.etbakhly_provider.adapter.SpinnerCountryAdapter;
import com.etbakhly_provider.adapter.SpinnerZoneAdapter;
import com.etbakhly_provider.databinding.FragmentSignUp1Binding;
import com.etbakhly_provider.databinding.ZoneRowBinding;
import com.etbakhly_provider.model.AddZoneModel;
import com.etbakhly_provider.model.CategoryModel;
import com.etbakhly_provider.model.CountryModel;
import com.etbakhly_provider.model.SignUpModel;
import com.etbakhly_provider.mvvm.FragmentSignup1Mvvm;
import com.etbakhly_provider.share.Common;
import com.etbakhly_provider.uis.activity_base.BaseFragment;
import com.etbakhly_provider.uis.activity_signup.SignupActivity;
import com.squareup.picasso.Picasso;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class FragmentSignup1 extends BaseFragment implements TimePickerDialog.OnTimeSetListener {
    private SignupActivity activity;

    private FragmentSignUp1Binding binding;
    private FragmentSignup1Mvvm fragmentSignup1Mvvm;
    private ActivityResultLauncher<Intent> launcher;
    private final String READ_PERM = Manifest.permission.READ_EXTERNAL_STORAGE;
    private final String write_permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private final String camera_permission = Manifest.permission.CAMERA;
    private final int READ_REQ = 1, CAMERA_REQ = 2;
    private int selectedReq = 0;
    private Uri uri = null;
    private SignUpModel model;
    private TimePickerDialog timePickerDialog;
    private SpinnerCountryAdapter spinnerCountryAdapter;
    private SpinnerCityAdapter spinnerCityAdapter;
    private SpinnerZoneAdapter spinnerZoneAdapter;
    private SpinnerCategoryAdapter spinnerCategoryAdapter;
    private CompositeDisposable disposable = new CompositeDisposable();
    private String type;
    private List<ZoneRowBinding> zoneRowBindingList;
    private List<AddZoneModel> addZoneModelList;

    private ProgressDialog dialog;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (SignupActivity) context;
//        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
//            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
//                if (selectedReq == READ_REQ) {
//
//                    uri = result.getData().getData();
//                    model.setImageUrl(uri.toString());
//
//                    File file = new File(Common.getImagePath(activity, uri));
//                    Picasso.get().load(file).fit().into(binding.image);
//
//                } else if (selectedReq == CAMERA_REQ) {
//                    Bitmap bitmap = (Bitmap) result.getData().getExtras().get("data");
//                    uri = getUriFromBitmap(bitmap);
//                    if (uri != null) {
//                        String path = Common.getImagePath(activity, uri);
//                        model.setImageUrl(uri.toString());
//                        if (path != null) {
//                            Picasso.get().load(new File(path)).fit().into(binding.image);
//
//                        } else {
//                            Picasso.get().load(uri).fit().into(binding.image);
//
//                        }
//                    }
//                }
//            }
//        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up1, container, false);
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
        zoneRowBindingList = new ArrayList<>();
        addZoneModelList = new ArrayList<>();
        dialog = Common.createProgressDialog(activity, activity.getResources().getString(R.string.wait));
        dialog.setCancelable(false);
        model = new SignUpModel(activity);
        model.setIs_valid1(false);
        model.setIs_delivery("delivry");
        binding.setModel(model);
        spinnerCountryAdapter = new SpinnerCountryAdapter(activity);
        spinnerCityAdapter = new SpinnerCityAdapter(activity);
        spinnerZoneAdapter = new SpinnerZoneAdapter(activity);
        spinnerCategoryAdapter = new SpinnerCategoryAdapter(activity);
        fragmentSignup1Mvvm = ViewModelProviders.of(this).get(FragmentSignup1Mvvm.class);
        fragmentSignup1Mvvm.getIsLoading().observe(this, new androidx.lifecycle.Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {

                if (aBoolean) {
                    dialog.show();
                } else {
                    dialog.dismiss();
                }
            }
        });
        fragmentSignup1Mvvm.onCategoryDataSuccess().observe(this, new androidx.lifecycle.Observer<List<CategoryModel>>() {
            @Override
            public void onChanged(List<CategoryModel> categoryModels) {
                if (categoryModels != null) {
                    categoryModels.add(0, new CategoryModel(getResources().getString(R.string.ch_cat)));
                    spinnerCategoryAdapter.updateData(categoryModels);
                }
            }
        });
        fragmentSignup1Mvvm.getCountryLiveData().observe(this, new androidx.lifecycle.Observer<List<CountryModel>>() {
            @Override
            public void onChanged(List<CountryModel> countryModels) {
                if (countryModels != null) {
                    countryModels.add(0, new CountryModel(getResources().getString(R.string.choose_country)));
                    spinnerCountryAdapter.updateData(countryModels);
                }
            }
        });
        fragmentSignup1Mvvm.getCityLiveData().observe(this, new androidx.lifecycle.Observer<List<CountryModel>>() {
            @Override
            public void onChanged(List<CountryModel> countryModels) {
                if (countryModels != null) {
                    countryModels.add(0, new CountryModel(getResources().getString(R.string.ch_city)));

                    spinnerCityAdapter.updateData(countryModels);
                }
            }
        });
        fragmentSignup1Mvvm.getZoneLiveData().observe(this, new androidx.lifecycle.Observer<List<CountryModel>>() {
            @Override
            public void onChanged(List<CountryModel> countryModels) {
                if (countryModels != null) {
                    countryModels.add(0, new CountryModel(getResources().getString(R.string.ch_zone)));

                    spinnerZoneAdapter.updateData(countryModels);
                }
            }
        });
        binding.spCountry.setAdapter(spinnerCountryAdapter);
        binding.spCity.setAdapter(spinnerCityAdapter);
        binding.spZone.setAdapter(spinnerZoneAdapter);
        binding.spcategory.setAdapter(spinnerCategoryAdapter);

//        binding.flImage.setOnClickListener(view -> {
//            openSheet();
//        });
        binding.spCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    //Log.e("lkkkk",fragmentSignup1Mvvm.getCountryLiveData().getValue().size()+"");
                    fragmentSignup1Mvvm.getCity(fragmentSignup1Mvvm.getCountryLiveData().getValue().get(i).getId());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        binding.spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    fragmentSignup1Mvvm.getZone(fragmentSignup1Mvvm.getCityLiveData().getValue().get(i).getId());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
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
        binding.spZone.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {

                    if (getItemPos(i) == -1) {
                        addMeterView(fragmentSignup1Mvvm.getZoneLiveData().getValue().get(i), i);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        binding.flGallery.setOnClickListener(view -> {
            closeSheet();
            checkReadPermission();
        });

        binding.flCamera.setOnClickListener(view -> {
            closeSheet();
            checkCameraPermission();
        });

        binding.btnCancel.setOnClickListener(view -> closeSheet());
        binding.lltimefrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = "from";
                timePickerDialog.show(activity.getFragmentManager(), "");
            }
        });
        binding.lltimeto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = "to";
                timePickerDialog.show(activity.getFragmentManager(), "");
            }
        });
        binding.rdDelivery.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                binding.rdSurrender.setChecked(false);
                binding.rdDelivery.setChecked(true);
                binding.flarea.setVisibility(View.VISIBLE);
                model.setIs_delivery("delivry");
            }
        });
        binding.rdSurrender.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                binding.rdDelivery.setChecked(false);
                binding.rdSurrender.setChecked(true);
                binding.flarea.setVisibility(View.GONE);
                model.setIs_delivery("not_delivry");
            }
        });
        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", model);
                Navigation.findNavController(binding.getRoot()).navigate(R.id.signup2, bundle);
            }
        });
        createDateDialog();
        fragmentSignup1Mvvm.getCategoryData();
        fragmentSignup1Mvvm.getCountries();

    }


    public void openSheet() {
        binding.expandLayout.setExpanded(true, true);
    }

    public void closeSheet() {
        binding.expandLayout.collapse(true);

    }

    public void checkReadPermission() {
        closeSheet();
        if (ActivityCompat.checkSelfPermission(activity, READ_PERM) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{READ_PERM}, READ_REQ);
        } else {
            SelectImage(READ_REQ);
        }
    }

    public void checkCameraPermission() {

        closeSheet();

        if (ContextCompat.checkSelfPermission(activity, write_permission) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(activity, camera_permission) == PackageManager.PERMISSION_GRANTED
        ) {
            SelectImage(CAMERA_REQ);
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{camera_permission, write_permission}, CAMERA_REQ);
        }
    }

    private void SelectImage(int req) {
        selectedReq = req;
        Intent intent = new Intent();

        if (req == READ_REQ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            } else {
                intent.setAction(Intent.ACTION_GET_CONTENT);

            }

            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setType("image/*");

            launcher.launch(intent);

        } else if (req == CAMERA_REQ) {
            try {
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                launcher.launch(intent);
            } catch (SecurityException e) {
                Toast.makeText(activity, R.string.perm_image_denied, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(activity, R.string.perm_image_denied, Toast.LENGTH_SHORT).show();

            }


        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == READ_REQ) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                SelectImage(requestCode);
            } else {
                Toast.makeText(activity, getString(R.string.perm_image_denied), Toast.LENGTH_SHORT).show();
            }

        } else if (requestCode == CAMERA_REQ) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                SelectImage(requestCode);
            } else {
                Toast.makeText(activity, getString(R.string.perm_image_denied), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private Uri getUriFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        return Uri.parse(MediaStore.Images.Media.insertImage(activity.getContentResolver(), bitmap, "", ""));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        disposable.clear();
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        if (type.equals("from")) {
            model.setFrom(hourOfDay + ":" + minute + ":" + second);
            binding.tvFrom.setText(model.getFrom());
        } else {
            model.setTo(hourOfDay + ":" + minute + ":" + second);
            binding.tvTo.setText(model.getTo());

        }
    }

    private void createDateDialog() {

        Calendar calendar = Calendar.getInstance();
        timePickerDialog = TimePickerDialog.newInstance(this, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND), false);
        timePickerDialog.dismissOnPause(true);
        timePickerDialog.setAccentColor(ActivityCompat.getColor(activity, R.color.colorPrimary));
        timePickerDialog.setCancelColor(ActivityCompat.getColor(activity, R.color.gray4));
        timePickerDialog.setOkColor(ActivityCompat.getColor(activity, R.color.colorPrimary));
        // datePickerDialog.setOkText(getString(R.string.select));
        //datePickerDialog.setCancelText(getString(R.string.cancel));
        timePickerDialog.setVersion(TimePickerDialog.Version.VERSION_2);
        //  timePickerDialog.setMinTime(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));

    }

    private void addMeterView(CountryModel specialModel, int adapterPosition) {
        Log.e("llll", adapterPosition + "");
        ZoneRowBinding rowBinding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.zone_row, null, false);
        AddZoneModel addZoneModel = new AddZoneModel(activity);
        addZoneModel.setTitle(specialModel.getTitel() + "");
        addZoneModel.setZone_id(specialModel.getId());
        rowBinding.getRoot().setTag(adapterPosition);
        rowBinding.setModel(addZoneModel);
        addZoneModelList.add(addZoneModel);
        zoneRowBindingList.add(rowBinding);
        binding.llZone.addView(rowBinding.getRoot());
        model.setAddZoneModels(addZoneModelList);

    }

    private void removeZoneView(int adapterPosition) {
        int childPos = getItemPos(adapterPosition);
        if (childPos != -1) {
            binding.llZone.removeViewAt(childPos);
            zoneRowBindingList.remove(childPos);
            addZoneModelList.remove(childPos);

        }
    }

    private int getItemPos(int id) {
        int pos = -1;
        for (int index = 0; index < zoneRowBindingList.size(); index++) {
            int tag = (int) binding.llZone.getChildAt(index).getTag();
            if (tag == id) {
                pos = index;
                return pos;
            }
        }
        return pos;
    }

}