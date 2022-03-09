package com.etbakhly_provider.uis.activity_add_dishes;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.etbakhly_provider.R;
import com.etbakhly_provider.adapter.SpinnerDishCategoryAdapter;
import com.etbakhly_provider.databinding.ActivityAddDishesBinding;
import com.etbakhly_provider.model.AddDishModel;
import com.etbakhly_provider.model.BuffetModel;
import com.etbakhly_provider.model.DishModel;
import com.etbakhly_provider.model.DishNoteDetailsModel;
import com.etbakhly_provider.mvvm.ActivityAddDishMvvm;
import com.etbakhly_provider.share.Common;
import com.etbakhly_provider.tags.Tags;
import com.etbakhly_provider.uis.activity_base.BaseActivity;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AddDishesActivity extends BaseActivity {
    private ActivityAddDishesBinding binding;
    private ActivityAddDishMvvm mvvm;
    private AddDishModel addDishModel;
    private DishModel dishModel;
    private ActivityResultLauncher<Intent> launcher;
    private final String READ_PERM = Manifest.permission.READ_EXTERNAL_STORAGE;
    private final String write_permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private final String camera_permission = Manifest.permission.CAMERA;
    private final int READ_REQ = 1, CAMERA_REQ = 2;
    private int selectedReq = 0;
    private Uri uri = null;
    private SpinnerDishCategoryAdapter spinnerDishCategoryAdapter;
    private List<BuffetModel.Category> categoryList = new ArrayList<>();
    private List<DishNoteDetailsModel> dishNoteDetailsModelList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_dishes);
        getDataFromIntent();
        initView();
    }

    private void getDataFromIntent() {

        Intent intent = getIntent();
        categoryList = (List<BuffetModel.Category>) intent.getSerializableExtra("data");
        if (intent.hasExtra("data2")) {
            dishModel = (DishModel) intent.getSerializableExtra("data2");

        }


    }

    private void initView() {
        mvvm = ViewModelProviders.of(this).get(ActivityAddDishMvvm.class);
        addDishModel = new AddDishModel();
        if (categoryList != null && categoryList.size() > 0) {
            addDishModel.setCategory_dishes_id(categoryList.get(0).getId());
            binding.tvNote.setVisibility(View.GONE);
        } else {
            binding.tvNote.setVisibility(View.VISIBLE);
        }
        if (dishModel != null) {
            addDishModel.setTitel(dishModel.getTitel());
            addDishModel.setPhoto(dishModel.getPhoto());
            addDishModel.setQty(dishModel.getQty());
            addDishModel.setDetails(dishModel.getDetails());
            addDishModel.setPrice(dishModel.getPrice());
            addDishModel.setId(dishModel.getId());
            addDishModel.setCaterer_id(dishModel.getCaterer_id());


            if (dishModel.getPhoto() != null && !dishModel.getPhoto().isEmpty()) {
                Picasso.get().load(Tags.base_url + dishModel.getPhoto()).fit().into(binding.image);
                binding.icon.setVisibility(View.GONE);
            }


        }
        binding.setModel(addDishModel);


        spinnerDishCategoryAdapter = new SpinnerDishCategoryAdapter(categoryList, this);

        binding.spinner.setAdapter(spinnerDishCategoryAdapter);
        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                addDishModel.setCategory_dishes_id(categoryList.get(i).getId());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mvvm.getOnAddedSuccess().observe(this, aBoolean -> {
            if (aBoolean) {
                Toast.makeText(AddDishesActivity.this, getResources().getString(R.string.succ), Toast.LENGTH_LONG).show();
                setResult(RESULT_OK);
                finish();
            }
        });

        mvvm.getOnUpdateSuccess().observe(this, aBoolean -> {
            if (aBoolean) {
                Toast.makeText(AddDishesActivity.this, R.string.updated, Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            }
        });
        binding.btnDone.setOnClickListener(view -> {
            if (addDishModel.isDataValid(this)) {
                addDishModel.setCaterer_id(getUserModel().getData().getCaterer().getId());
                if (dishModel == null) {
                    mvvm.storeDish(this, addDishModel);

                } else {

                    mvvm.updateDish(this, addDishModel);
                }
            }
        });
        binding.setLang(getLang());

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                if (selectedReq == READ_REQ) {

                    uri = result.getData().getData();
                    addDishModel.setPhoto(uri.toString());
                    File file = new File(Common.getImagePath(this, uri));
                    Picasso.get().load(file).fit().into(binding.image);
                    binding.icon.setVisibility(View.GONE);

                } else if (selectedReq == CAMERA_REQ) {
                    Bitmap bitmap = (Bitmap) result.getData().getExtras().get("data");
                    uri = getUriFromBitmap(bitmap);
                    if (uri != null) {
                        addDishModel.setPhoto(uri.toString());

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

        binding.llBack.setOnClickListener(view -> finish());


        binding.checkbox1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                dishNoteDetailsModelList.add(new DishNoteDetailsModel("1", binding.checkbox1.getText().toString()));
            } else {
                int pos = getNotePos("1");
                if (pos != -1) {
                    dishNoteDetailsModelList.remove(pos);
                }
            }

            addDishModel.setDishNoteDetailsModelList(dishNoteDetailsModelList);
        });
        binding.checkbox2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                dishNoteDetailsModelList.add(new DishNoteDetailsModel("2", binding.checkbox2.getText().toString()));
            } else {
                int pos = getNotePos("2");
                if (pos != -1) {
                    dishNoteDetailsModelList.remove(pos);
                }
            }
            addDishModel.setDishNoteDetailsModelList(dishNoteDetailsModelList);

        });
        binding.checkbox3.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                dishNoteDetailsModelList.add(new DishNoteDetailsModel("3", binding.checkbox3.getText().toString()));
            } else {
                int pos = getNotePos("3");
                if (pos != -1) {
                    dishNoteDetailsModelList.remove(pos);
                }
            }
            addDishModel.setDishNoteDetailsModelList(dishNoteDetailsModelList);

        });
        binding.checkbox4.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                dishNoteDetailsModelList.add(new DishNoteDetailsModel("4", binding.checkbox4.getText().toString()));
            } else {
                int pos = getNotePos("4");
                if (pos != -1) {
                    dishNoteDetailsModelList.remove(pos);
                }
            }
            addDishModel.setDishNoteDetailsModelList(dishNoteDetailsModelList);

        });

    }

    private int getNotePos(String id) {
        int pos = -1;
        for (int index = 0; index < dishNoteDetailsModelList.size(); index++) {
            DishNoteDetailsModel model = dishNoteDetailsModelList.get(index);
            if (model.getId().equals(id)) {
                pos = index;
                return pos;
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


}