package com.etbakhly_provider.uis.activity_add_offer;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.etbakhly_provider.R;
import com.etbakhly_provider.adapter.SpinnerDishCategoryAdapter;
import com.etbakhly_provider.databinding.ActivityAddOfferBinding;
import com.etbakhly_provider.model.AddBuffetModel;
import com.etbakhly_provider.model.AddOfferModel;
import com.etbakhly_provider.model.BuffetModel;
import com.etbakhly_provider.model.DishModel;
import com.etbakhly_provider.model.OfferModel;
import com.etbakhly_provider.mvvm.ActivityAddBuffetMvvm;
import com.etbakhly_provider.mvvm.ActivityAddOffersMvvm;
import com.etbakhly_provider.share.Common;
import com.etbakhly_provider.tags.Tags;
import com.etbakhly_provider.uis.activity_add_buffet.AddBuffetActivity;
import com.etbakhly_provider.uis.activity_add_buffet.add_buffet_dish_activity.AddBuffetDishActivity;
import com.etbakhly_provider.uis.activity_base.BaseActivity;
import com.squareup.picasso.Picasso;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddOfferActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener {
    private ActivityAddOfferBinding binding;
    private ActivityAddOffersMvvm mvvm;
    private OfferModel offerModel;
    private AddOfferModel addOfferModel;
    private ActivityResultLauncher<Intent> launcher;
    private final String READ_PERM = Manifest.permission.READ_EXTERNAL_STORAGE;
    private final String write_permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private final String camera_permission = Manifest.permission.CAMERA;
    private final int READ_REQ = 1, CAMERA_REQ = 2;
    private int selectedReq = 0;
    private Uri uri = null;
    private DatePickerDialog datePickerDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_offer);
        getDataFromIntent();
        initView();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent.hasExtra("data")) {
            offerModel = (OfferModel) intent.getSerializableExtra("data");

        }
    }


    private void initView() {
        mvvm = ViewModelProviders.of(this).get(ActivityAddOffersMvvm.class);

        String option_id = "";
        String type = getUserModel().getData().getType();
        if (type.equals("service")) {
            option_id = "1";
        } else if (type.equals("chef")) {
            option_id = "2";

        } else {
            option_id = "3";

        }
        addOfferModel = new AddOfferModel();
        addOfferModel.setCaterer_id(getUserModel().getData().getCaterer().getId());
        addOfferModel.setOption_id(option_id);

        if (offerModel != null) {

            addOfferModel.setTitle(offerModel.getTitle());
            addOfferModel.setSub_titel(offerModel.getSub_titel());
            addOfferModel.setPhoto(offerModel.getPhoto());
            addOfferModel.setPrice(offerModel.getPrice());
            addOfferModel.setEnd_date(offerModel.getEnd_date());
            addOfferModel.setId(offerModel.getId());

            if (offerModel.getPhoto() != null && !offerModel.getPhoto().isEmpty()) {
                Picasso.get().load(Tags.base_url + offerModel.getPhoto()).fit().into(binding.image);
                binding.icon.setVisibility(View.GONE);
            }

            binding.tvTitle.setText(getString(R.string.update));

        }
        binding.setModel(addOfferModel);


        mvvm.getAddOfferMutableLiveData().observe(this, offerModel -> {
            if (offerModel != null) {
                Toast.makeText(this, getResources().getString(R.string.succ), Toast.LENGTH_LONG).show();
                Intent intent = getIntent();
                intent.putExtra("data", offerModel);
                intent.putExtra("action", "add");
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        mvvm.getUpdateOfferLiveData().observe(this, offerModel -> {
            if (offerModel != null) {
                Toast.makeText(this, R.string.updated, Toast.LENGTH_SHORT).show();
                Intent intent = getIntent();
                intent.putExtra("data", offerModel);
                intent.putExtra("action", "update");

                setResult(RESULT_OK, intent);
                finish();
            }
        });


        binding.btnDone.setOnClickListener(view -> {
            if (addOfferModel.isDataValid(this)) {
                addOfferModel.setCaterer_id(getUserModel().getData().getCaterer().getId());
                if (offerModel == null) {
                    mvvm.storeOffer(this, addOfferModel);
                } else {
                    mvvm.updateOffer(this, addOfferModel);
                }

            }
        });
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                if (selectedReq == READ_REQ) {

                    uri = result.getData().getData();
                    addOfferModel.setPhoto(uri.toString());
                    File file = new File(Common.getImagePath(this, uri));
                    Picasso.get().load(file).fit().into(binding.image);
                    binding.icon.setVisibility(View.GONE);

                } else if (selectedReq == CAMERA_REQ) {
                    Bitmap bitmap = (Bitmap) result.getData().getExtras().get("data");
                    uri = getUriFromBitmap(bitmap);
                    if (uri != null) {
                        addOfferModel.setPhoto(uri.toString());

                        String path = Common.getImagePath(this, uri);
                        if (path != null) {
                            Picasso.get().load(new File(path)).fit().into(binding.image);

                        } else {
                            Picasso.get().load(uri).fit().into(binding.image);

                        }
                        binding.icon.setVisibility(View.GONE);
                    }
                }
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

        binding.image.setOnClickListener(view -> openSheet());

        binding.setLang(getLang());
        binding.llBack.setOnClickListener(view -> finish());
        binding.llDate.setOnClickListener(v -> createDateDialog());
    }

    private void createDateDialog() {

        Calendar calendar = Calendar.getInstance();
        datePickerDialog = DatePickerDialog.newInstance(this);
        datePickerDialog.setMinDate(calendar);
        datePickerDialog.dismissOnPause(true);
        datePickerDialog.setAccentColor(ActivityCompat.getColor(this, R.color.colorPrimary));
        datePickerDialog.setCancelColor(ActivityCompat.getColor(this, R.color.gray4));
        datePickerDialog.setOkColor(ActivityCompat.getColor(this, R.color.colorPrimary));
        datePickerDialog.setVersion(DatePickerDialog.Version.VERSION_2);
        try {
            datePickerDialog.show(getFragmentManager(), "");

        } catch (Exception e) {

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

    private Uri getUriFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        return Uri.parse(MediaStore.Images.Media.insertImage(this.getContentResolver(), bitmap, "", ""));
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

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, monthOfYear);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).format(calendar.getTime());
        addOfferModel.setEnd_date(date);
        binding.setModel(addOfferModel);
    }
}