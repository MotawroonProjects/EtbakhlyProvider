package com.etbakhly_provider.uis.activity_add_feast.activity_add_feast_dish;

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
import android.widget.Toast;

import com.etbakhly_provider.R;
import com.etbakhly_provider.databinding.ActivityAddFeastDishBinding;
import com.etbakhly_provider.model.AddBuffetDishModel;
import com.etbakhly_provider.model.DishModel;
import com.etbakhly_provider.mvvm.ActivityAddFeastDishMvvm;
import com.etbakhly_provider.share.Common;
import com.etbakhly_provider.tags.Tags;
import com.etbakhly_provider.uis.activity_base.BaseActivity;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class AddFeastDishActivity extends BaseActivity {

    private ActivityAddFeastDishBinding binding;
    private ActivityAddFeastDishMvvm mvvm;
    private AddBuffetDishModel addFeastDishModel;
    private ActivityResultLauncher<Intent> launcher;
    private final String READ_PERM = Manifest.permission.READ_EXTERNAL_STORAGE;
    private final String write_permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private final String camera_permission = Manifest.permission.CAMERA;
    private final int READ_REQ = 1, CAMERA_REQ = 2;
    private int selectedReq = 0;
    private Uri uri = null;
    private String category_dish_id = "";
    private String feast_id = "";
    private DishModel dishModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_feast_dish);
        getDataFromIntent();
        initView();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        category_dish_id = intent.getStringExtra("data");
        feast_id = intent.getStringExtra("data3");

        if (intent.hasExtra("data2")) {
            dishModel = (DishModel) intent.getSerializableExtra("data2");
        }
    }

    private void initView() {
        mvvm = ViewModelProviders.of(this).get(ActivityAddFeastDishMvvm.class);
        mvvm.getOnDishAddedSuccess().observe(this, dishModel -> {
            Toast.makeText(this, getResources().getString(R.string.succ), Toast.LENGTH_LONG).show();
            Intent intent = getIntent();
            intent.putExtra("action", "add");
            intent.putExtra("data", dishModel);
            setResult(RESULT_OK, intent);
            finish();
        });

        mvvm.getOnDishUpdatedSuccess().observe(this, dishModel -> {
            Toast.makeText(this, getResources().getString(R.string.succ), Toast.LENGTH_LONG).show();
            Intent intent = getIntent();
            intent.putExtra("action", "update");
            intent.putExtra("data", dishModel);
            setResult(RESULT_OK, intent);
            finish();
        });

        addFeastDishModel = new AddBuffetDishModel();
        addFeastDishModel.setBuffets_id(feast_id);
        addFeastDishModel.setCategory_dishes_id(category_dish_id);
        if (dishModel != null) {
            binding.tvTitle.setText(getString(R.string.update));
            addFeastDishModel.setId(dishModel.getId());
            addFeastDishModel.setTitel(dishModel.getTitel());
            addFeastDishModel.setPrice(dishModel.getPrice());
            addFeastDishModel.setQty(dishModel.getQty());
            addFeastDishModel.setDetails(dishModel.getDetails());
            if (dishModel.getPhoto() != null && !dishModel.getPhoto().isEmpty()) {
                if (dishModel.getPhoto() != null && !dishModel.getPhoto().isEmpty()) {
                    Picasso.get().load(Tags.base_url + dishModel.getPhoto()).fit().into(binding.image);
                    binding.icon.setVisibility(View.GONE);
                    addFeastDishModel.setPhoto(dishModel.getPhoto());
                }
            }
        }
        binding.setModel(addFeastDishModel);
        binding.setLang(getLang());

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                if (selectedReq == READ_REQ) {

                    uri = result.getData().getData();
                    addFeastDishModel.setPhoto(uri.toString());

                    File file = new File(Common.getImagePath(this, uri));
                    Picasso.get().load(file).fit().into(binding.image);
                    binding.icon.setVisibility(View.GONE);

                } else if (selectedReq == CAMERA_REQ) {
                    Bitmap bitmap = (Bitmap) result.getData().getExtras().get("data");
                    uri = getUriFromBitmap(bitmap);
                    if (uri != null) {
                        addFeastDishModel.setPhoto(uri.toString());
                        String path = Common.getImagePath(this, uri);
                        if (path != null) {
                            Picasso.get().load(new File(path)).fit().into(binding.image);
                            binding.icon.setVisibility(View.GONE);

                        } else {
                            Picasso.get().load(uri).fit().into(binding.image);
                            binding.icon.setVisibility(View.GONE);

                        }
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


        binding.llBack.setOnClickListener(view -> finish());

        binding.btnDone.setOnClickListener(view -> {
            if (addFeastDishModel.isDataValid(this)) {
                if (dishModel == null) {
                    mvvm.storeFeastDishes(this, addFeastDishModel);

                } else {
                    mvvm.updateFeastDishes(this, addFeastDishModel);

                }
            }
        });
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

}