package com.etbakhly_provider.uis.activity_register;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;


import com.etbakhly_provider.R;
import com.etbakhly_provider.adapter.SpinnerServiceAdapter;
import com.etbakhly_provider.databinding.ActivityRegisterBinding;
import com.etbakhly_provider.model.RegisterModel;
import com.etbakhly_provider.model.SelectedLocation;
import com.etbakhly_provider.model.UserModel;
import com.etbakhly_provider.mvvm.ActivityRegisterMvvm;
import com.etbakhly_provider.share.Common;
import com.etbakhly_provider.tags.Tags;
import com.etbakhly_provider.uis.activity_base.BaseActivity;
import com.etbakhly_provider.uis.activity_map.MapActivity;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends BaseActivity {
    private ActivityRegisterBinding binding;
    private RegisterModel model;
    private ActivityRegisterMvvm mvvm;
    private String phone_code = "", phone = "";
    private UserModel userModel;
    private SpinnerServiceAdapter spinnerServiceAdapter;
    private List<String> optionsList;
    private ActivityResultLauncher<Intent> launcher;
    private final String READ_PERM = Manifest.permission.READ_EXTERNAL_STORAGE;
    private final String write_permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private final String camera_permission = Manifest.permission.CAMERA;
    private final int READ_REQ = 1, CAMERA_REQ = 2;
    private int selectedReq = 0;
    private Uri uri = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        getDataFromIntent();
        initView();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent.getStringExtra("phone") != null) {
            phone_code = intent.getStringExtra("phone_code");
            phone = intent.getStringExtra("phone");
        }
    }

    private void initView() {
        mvvm = ViewModelProviders.of(this).get(ActivityRegisterMvvm.class);
        model = new RegisterModel(phone_code, phone);
        binding.setLang(getLang());
        model.setValid(false);
//        if (getUserModel() != null) {
//            if (getUserModel().getData().getPhoto() != null) {
//                Picasso.get().load(getUserModel().getData().getPhoto()).into(binding.image);}
//                model.setEmail(getUserModel().getData().getEmail());
//                model.setName(getUserModel().getData().getName());
//                model.setService(getUserModel().getData().getType());
//                model.setAddress(getUserModel().getData().getAddress());
//                model.setLat(Double.parseDouble(getUserModel().getData().getLatitude()));
//                model.setLng(Double.parseDouble(getUserModel().getData().getLongitude()));
//                model.setPhone(getUserModel().getData().getPhone());
//                model.setPhone_code(getUserModel().getData().getPhone_code());
//                model.setValid(true);
//
//        }
        optionsList = new ArrayList<>();
        spinnerServiceAdapter = new SpinnerServiceAdapter(this);
        binding.spinnerServices.setAdapter(spinnerServiceAdapter);
        binding.spinnerServices.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    if (i == 1) {
                        model.setService("service");
                    } else if (i == 2) {
                        model.setService("chef");
                    } else if (i == 3) {
                        model.setService("food_track");

                    }
                } else {
                    model.setService("");
                }
                binding.setModel(model);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        setOptionData();
        userModel = getUserModel();
        if (userModel != null) {

            phone_code = userModel.getData().getPhone_code();
            phone = userModel.getData().getPhone();
            model.setName(userModel.getData().getName());
            model.setEmail(userModel.getData().getEmail());

            model.setPhone_code(phone_code);
            model.setPhone(phone);
            binding.setUserModel(userModel);
            model.setAddress(userModel.getData().getAddress());
            model.setService(userModel.getData().getType());


            model.setValid(true);
            //   binding.llPhone.setVisibility(View.GONE);

            if (userModel.getData().getPhoto() != null) {
                String url = Tags.base_url + userModel.getData().getPhoto();
                Picasso.get().load(Uri.parse(url)).into(binding.image);
                model.setPhotoUrl(url);
            }


            binding.btnNext.setText(getString(R.string.update));


        }
        binding.setModel(model);

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (selectedReq == 3 && result.getResultCode() == RESULT_OK && result.getData() != null) {
                SelectedLocation location = (SelectedLocation) result.getData().getSerializableExtra("location");
                model.setAddress(location.getAddress());
                model.setLat(location.getLat());
                model.setLng(location.getLng());
                binding.setModel(model);


            } else {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    if (selectedReq == READ_REQ) {

                        uri = result.getData().getData();
                        model.setPhotoUrl(uri.toString());
                        File file = new File(Common.getImagePath(this, uri));

                        Picasso.get().load(file).fit().into(binding.image);

                    } else if (selectedReq == CAMERA_REQ) {
                        Bitmap bitmap = (Bitmap) result.getData().getExtras().get("data");
                        uri = getUriFromBitmap(bitmap);
                        if (uri != null) {
                            String path = Common.getImagePath(this, uri);
                            model.setPhotoUrl(uri.toString());
                            if (path != null) {
                                Picasso.get().load(new File(path)).fit().into(binding.image);

                            } else {
                                Picasso.get().load(uri).fit().into(binding.image);

                            }
                        }
                    }

                }
            }

        });


        mvvm.getUserData().observe(this, userModel -> {
            Intent intent = getIntent();
            intent.putExtra("data", userModel);
            setResult(RESULT_OK, intent);
            finish();

        });


        binding.cardViewImage.setOnClickListener(view -> {
            Common.CloseKeyBoard(this, binding.edtName);
            openSheet();
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

        binding.cardAddress.setOnClickListener(view -> navigateToMapActivity());


        binding.btnNext.setOnClickListener(view -> {
            if (getUserModel() == null) {
                mvvm.signUp(model, this);

            } else {
                mvvm.update(model, userModel, this);

            }
        });

    }

    private void navigateToMapActivity() {
        selectedReq = 3;
        Intent intent = new Intent(this, MapActivity.class);
        launcher.launch(intent);
    }

    private void setOptionData() {
        optionsList.add(getResources().getString(R.string.ch_service));
        optionsList.add(getResources().getString(R.string.service));
        optionsList.add(getResources().getString(R.string.chef));
        optionsList.add(getResources().getString(R.string.food_truck));
        spinnerServiceAdapter.updateData(optionsList);

    }


    public void openSheet() {
        binding.expandLayout.setExpanded(true, true);
    }

    public void closeSheet() {
        binding.expandLayout.collapse(true);

    }

    public void checkReadPermission() {
        closeSheet();
        if (ActivityCompat.checkSelfPermission(this, READ_PERM) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{READ_PERM}, READ_REQ);
        } else {
            SelectImage(READ_REQ);
        }
    }

    public void checkCameraPermission() {

        closeSheet();

        if (ContextCompat.checkSelfPermission(this, write_permission) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, camera_permission) == PackageManager.PERMISSION_GRANTED
        ) {
            SelectImage(CAMERA_REQ);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{camera_permission, write_permission}, CAMERA_REQ);
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
                Toast.makeText(this, R.string.perm_image_denied, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, R.string.perm_image_denied, Toast.LENGTH_SHORT).show();

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
                Toast.makeText(this, getString(R.string.perm_image_denied), Toast.LENGTH_SHORT).show();
            }

        } else if (requestCode == CAMERA_REQ) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                SelectImage(requestCode);
            } else {
                Toast.makeText(this, getString(R.string.perm_image_denied), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private Uri getUriFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        return Uri.parse(MediaStore.Images.Media.insertImage(this.getContentResolver(), bitmap, "", ""));
    }


}