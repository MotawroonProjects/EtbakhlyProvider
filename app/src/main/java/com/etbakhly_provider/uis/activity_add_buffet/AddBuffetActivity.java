package com.etbakhly_provider.uis.activity_add_buffet;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

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

import com.etbakhly_provider.R;
import com.etbakhly_provider.adapter.SpinnerDishCategoryAdapter;
import com.etbakhly_provider.databinding.ActivityAddBuffetBinding;
import com.etbakhly_provider.model.AddBuffetModel;
import com.etbakhly_provider.model.BuffetModel;
import com.etbakhly_provider.model.DishModel;
import com.etbakhly_provider.mvvm.ActivityAddBuffetMvvm;
import com.etbakhly_provider.share.Common;
import com.etbakhly_provider.tags.Tags;
import com.etbakhly_provider.uis.activity_add_buffet.add_buffet_dish_activity.AddBuffetDishActivity;

import com.etbakhly_provider.uis.activity_base.BaseActivity;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AddBuffetActivity extends BaseActivity {
    private ActivityAddBuffetBinding binding;
    private ActivityAddBuffetMvvm mvvm;
    private BuffetModel buffetModel;
    private AddBuffetModel addBuffetModel;
    private SpinnerDishCategoryAdapter spinnerDishCategoryAdapter;
    private ActivityResultLauncher<Intent> launcher;
    private final String READ_PERM = Manifest.permission.READ_EXTERNAL_STORAGE;
    private final String write_permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private final String camera_permission = Manifest.permission.CAMERA;
    private final int READ_REQ = 1, CAMERA_REQ = 2;
    private int selectedReq = 0;
    private Uri uri = null;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_buffet);
        getDataFromIntent();
        initView();
    }
    private void getDataFromIntent() {
        Intent intent=getIntent();
        if (intent.hasExtra("data")){
            buffetModel = (BuffetModel) intent.getSerializableExtra("data");

        }
    }


    private void initView() {
        mvvm = ViewModelProviders.of(this).get(ActivityAddBuffetMvvm.class);
        addBuffetModel = new AddBuffetModel();

        if (buffetModel!=null){

            addBuffetModel.setTitel(buffetModel.getTitel());
            addBuffetModel.setNumber_people(buffetModel.getNumber_people());
            addBuffetModel.setPhoto(buffetModel.getPhoto());
            addBuffetModel.setPrice(buffetModel.getPrice());
            addBuffetModel.setService_provider_type(buffetModel.getService_provider_type());
            addBuffetModel.setOrder_time(buffetModel.getOrder_time());
            addBuffetModel.setId(buffetModel.getId());
            addBuffetModel.setCaterer_id(buffetModel.getCaterer_id());

            if (buffetModel.getService_provider_type().equals("man")){
                binding.rdmen.setChecked(true);
            }else if (buffetModel.getService_provider_type().equals("women")){
                binding.rdwomen.setChecked(true);

            }else if (buffetModel.getService_provider_type().equals("man_and_women")){
                binding.rdboth.setChecked(true);

            }else if (buffetModel.getService_provider_type().equals("not_found")){
                binding.rdnothing.setChecked(true);

            }

            if (buffetModel.getPhoto() != null && !buffetModel.getPhoto().isEmpty()) {
                Picasso.get().load(Tags.base_url + buffetModel.getPhoto()).fit().into(binding.image);
                binding.icon.setVisibility(View.GONE);
            }

        }
        binding.setModel(addBuffetModel);


        spinnerDishCategoryAdapter = new SpinnerDishCategoryAdapter(new ArrayList<>(), this);

        binding.spinner.setAdapter(spinnerDishCategoryAdapter);
        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (mvvm.onCategoryDataSuccess().getValue()!=null){
                    addBuffetModel.setCategory_dishes_id(mvvm.onCategoryDataSuccess().getValue().get(i).getId());

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        mvvm.onCategoryDataSuccess().observe(this, categories -> {
            if (spinnerDishCategoryAdapter!=null){
                spinnerDishCategoryAdapter.updateList(categories);
                if (categories.size()>1){
                    binding.tvNote.setVisibility(View.GONE);
                }else {
                    if (categories.get(0).getId()==null||categories.get(0).getId().isEmpty()){
                        binding.tvNote.setVisibility(View.VISIBLE);

                    }else {
                        binding.tvNote.setVisibility(View.GONE);

                    }
                }


                if (buffetModel!=null){
                    int pos = getCategoryItemPos(categories,buffetModel.getCategory_dishes_id());
                    if (pos!=-1){
                        binding.spinner.setSelection(pos);

                    }
                }
                addBuffetModel.setCategory_dishes_id(categories.get(0).getId());

            }
        });

        mvvm.getAddBuffetMutableLiveData().observe(this, aBoolean -> {
            if (aBoolean) {
                Toast.makeText(AddBuffetActivity.this, getResources().getString(R.string.succ), Toast.LENGTH_LONG).show();
                setResult(RESULT_OK);
                finish();
            }
        });
        mvvm.getUpdateBuffetLiveData().observe(this, aBoolean -> {
            if (aBoolean){
                Toast.makeText(AddBuffetActivity.this, R.string.updated, Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            }
        });

        mvvm.getCategoryDishes(getUserModel().getData().getCaterer().getId(),this);

        binding.btnDone.setOnClickListener(view -> {
            if (addBuffetModel.isDataValid(this)) {
                addBuffetModel.setCaterer_id(getUserModel().getData().getCaterer().getId());
                if (buffetModel==null){
                    mvvm.storeBuffet(this, addBuffetModel, uri);
                }else {
                    mvvm.editBuffet(this,addBuffetModel,uri);
                }

            }
        });
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                if (selectedReq == READ_REQ) {

                    uri = result.getData().getData();
                    addBuffetModel.setPhoto(uri.toString());
                    File file = new File(Common.getImagePath(this, uri));
                    Picasso.get().load(file).fit().into(binding.image);
                    binding.icon.setVisibility(View.GONE);

                } else if (selectedReq == CAMERA_REQ) {
                    Bitmap bitmap = (Bitmap) result.getData().getExtras().get("data");
                    uri = getUriFromBitmap(bitmap);
                    if (uri != null) {
                        addBuffetModel.setPhoto(uri.toString());

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


        binding.rdwomen.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                addBuffetModel.setService_provider_type("women");

            }


        });
        binding.rdmen.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                addBuffetModel.setService_provider_type("man");

            }


        });
        binding.rdboth.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                addBuffetModel.setService_provider_type("man_and_women");

            }


        });
        binding.rdnothing.setOnCheckedChangeListener((compoundButton, b) -> {

            if (b) {
                addBuffetModel.setService_provider_type("not_found");

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

    }

    private int getCategoryItemPos(List<BuffetModel.Category> categories, String category_dishes_id) {
        int pos = -1;
        if (categories!=null){
            for (int index =0;index<categories.size();index++){
                BuffetModel.Category category = categories.get(index);
                if (category.getId()!=null&&category.getId().equals(category_dishes_id)){
                    pos = index;
                    return pos;
                }
            }
        }
        return pos;

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

    public void navigateToAddNewBuffetDish(DishModel model,String category_id) {
        Intent intent = new Intent(AddBuffetActivity.this, AddBuffetDishActivity.class);
        intent.putExtra("data",category_id);
        intent.putExtra("data2",model);
        launcher.launch(intent);

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

}