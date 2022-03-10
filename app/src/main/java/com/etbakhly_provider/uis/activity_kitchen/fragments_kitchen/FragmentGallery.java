package com.etbakhly_provider.uis.activity_kitchen.fragments_kitchen;


import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.etbakhly_provider.R;
import com.etbakhly_provider.adapter.GalleryAdapter;
import com.etbakhly_provider.adapter.LocalGalleryAdapter;
import com.etbakhly_provider.databinding.FragmentGalleryBinding;
import com.etbakhly_provider.databinding.LocalImageDialogBinding;
import com.etbakhly_provider.databinding.SelectImageDialogBinding;
import com.etbakhly_provider.model.KitchenModel;
import com.etbakhly_provider.mvvm.ActivityBuffetsMvvm;
import com.etbakhly_provider.mvvm.ActivityHomeGeneralMvvm;
import com.etbakhly_provider.mvvm.FragmentGalleryMvvm;
import com.etbakhly_provider.service_uploading_images.ServiceUploadImages;
import com.etbakhly_provider.share.Common;
import com.etbakhly_provider.uis.activity_base.BaseFragment;
import com.etbakhly_provider.uis.activity_kitchen.KitchenDetailsActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class FragmentGallery extends BaseFragment {
    private FragmentGalleryBinding binding;
    private FragmentGalleryMvvm mvvm;
    private ActivityHomeGeneralMvvm activityHomeGeneralMvvm;

    private GalleryAdapter adapter;
    private KitchenModel model;
    private KitchenDetailsActivity activity;
    private ActivityResultLauncher<Intent> launcher;
    private final String READ_PERM = Manifest.permission.READ_EXTERNAL_STORAGE;
    private final String write_permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private final String camera_permission = Manifest.permission.CAMERA;
    private final int READ_REQ = 1, CAMERA_REQ = 2;
    private int selectedReq = 0;
    private Uri uri = null;
    private List<String> uris;
    private LocalGalleryAdapter localGalleryAdapter;
    private BottomSheetDialog localImageDialog;


    public static FragmentGallery newInstance(KitchenModel model) {
        FragmentGallery fragment = new FragmentGallery();
        Bundle args = new Bundle();
        args.putSerializable("data", model);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (KitchenDetailsActivity) context;
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                if (selectedReq == READ_REQ && result.getData() != null) {

                    ClipData clipData = result.getData().getClipData();
                    if (clipData != null) {
                        for (int index = 0; index < clipData.getItemCount(); index++) {
                            ClipData.Item item = clipData.getItemAt(index);
                            uri = item.getUri();
                            uris.add(uri.toString());
                        }
                    } else {
                        uri = result.getData().getData();
                        uris.add(uri.toString());

                    }

                    showLocalImageDialog();

                } else if (selectedReq == CAMERA_REQ && result.getData() != null) {
                    Bitmap bitmap = (Bitmap) result.getData().getExtras().get("data");
                    uri = getUriFromBitmap(bitmap);
                    if (uri != null) {

                    }
                }
            }
        });

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            model = (KitchenModel) getArguments().getSerializable("data");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_gallery, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        activityHomeGeneralMvvm = ViewModelProviders.of(activity).get(ActivityHomeGeneralMvvm.class);

        mvvm = ViewModelProviders.of(this).get(FragmentGalleryMvvm.class);

        uris = new ArrayList<>();
        adapter = new GalleryAdapter(activity, this);
        binding.recViewGallery.setLayoutManager(new GridLayoutManager(activity, 4));
        binding.recViewGallery.setAdapter(adapter);

        activityHomeGeneralMvvm.onGallerySuccess().observe(activity, refresh -> {
            mvvm.getGallery(getUserModel().getData().getCaterer().getId());

        });
        mvvm.getIsLoading().observe(activity, isLoading -> {
            if (isLoading) {
                binding.progBar.setVisibility(View.VISIBLE);
            } else {
                binding.progBar.setVisibility(View.GONE);

            }
        });
        mvvm.getOnDeleteSuccess().observe(activity, pos -> {
            if (mvvm.getGalleryLiveData().getValue() != null) {
                if (mvvm.getGalleryLiveData().getValue().size() == 0) {
                    binding.tvNoData.setVisibility(View.VISIBLE);
                }
            }
            if (adapter != null) {
                adapter.notifyItemRemoved(pos);
            }
        });
        mvvm.getGalleryLiveData().observe(activity, list -> {
            if (list.size() > 0) {
                if (adapter != null) {
                    adapter.updateList(list);
                }
                binding.tvNoData.setVisibility(View.GONE);
            } else {
                binding.tvNoData.setVisibility(View.VISIBLE);

            }
        });
        binding.addImage.setOnClickListener(v -> checkReadPermission());
        mvvm.getGallery(getUserModel().getData().getCaterer().getId());

    }

    private void showLocalImageDialog() {
        localImageDialog = new BottomSheetDialog(activity);
        localImageDialog.setCanceledOnTouchOutside(false);
        LocalImageDialogBinding localImageDialogBinding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.local_image_dialog, null, false);

        localImageDialogBinding.recView.setLayoutManager(new GridLayoutManager(activity, 3));
        localGalleryAdapter = new LocalGalleryAdapter(activity, this, uris);
        localImageDialogBinding.recView.setAdapter(localGalleryAdapter);

        localImageDialogBinding.btnUpload.setOnClickListener(v -> {
            Toast.makeText(activity, getString(R.string.uploading), Toast.LENGTH_LONG).show();
            Intent intent = new Intent(activity, ServiceUploadImages.class);
            intent.putExtra("data", (Serializable) uris);
            activity.startService(intent);
            localImageDialog.dismiss();
            uris.clear();
        });
        localImageDialogBinding.btnCancel.setOnClickListener(v -> {
            localImageDialog.dismiss();
        });


        localImageDialog.setContentView(localImageDialogBinding.getRoot());
        localImageDialog.show();
    }

/*
    private void showChooseImageDialog() {
        BottomSheetDialog dialog = new BottomSheetDialog(activity);
        dialog.setCanceledOnTouchOutside(false);
        SelectImageDialogBinding selectImageDialogBinding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.select_image_dialog, null, false);
        selectImageDialogBinding.btnCancel.setOnClickListener(v -> {
            dialog.dismiss();
        });
        selectImageDialogBinding.llGallery.setOnClickListener(v -> {
            dialog.dismiss();
            checkReadPermission();
        });

        selectImageDialogBinding.llCamera.setOnClickListener(v -> {
            dialog.dismiss();
            checkCameraPermission();
        });
        dialog.setContentView(selectImageDialogBinding.getRoot());
        dialog.show();
    }
*/


    public void checkCameraPermission() {


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
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);

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


    public void checkReadPermission() {
        if (ActivityCompat.checkSelfPermission(activity, READ_PERM) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{READ_PERM}, READ_REQ);
        } else {
            SelectImage(READ_REQ);
        }
    }

    private Uri getUriFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        return Uri.parse(MediaStore.Images.Media.insertImage(activity.getContentResolver(), bitmap, "", ""));
    }

    public void deleteLocalImage(int adapterPosition) {
        uris.remove(adapterPosition);
        if (uris.size() == 0 && localImageDialog != null) {
            localImageDialog.dismiss();
        }
        if (localGalleryAdapter != null) {
            localGalleryAdapter.notifyItemRemoved(adapterPosition);
        }
    }

    public void deleteImage(KitchenModel.Photo photo, int adapterPosition) {
        mvvm.deleteImage(photo.getId(), adapterPosition, activity);
    }
}