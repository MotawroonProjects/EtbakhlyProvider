package com.etbakhly_provider.uis.activity_kitchen.fragments_kitchen;



import static android.app.Activity.RESULT_OK;

import android.Manifest;
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
import androidx.recyclerview.widget.GridLayoutManager;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.etbakhly_provider.R;
import com.etbakhly_provider.adapter.GalleryAdapter;
import com.etbakhly_provider.databinding.FragmentGalleryBinding;
import com.etbakhly_provider.model.KitchenModel;
import com.etbakhly_provider.share.Common;
import com.etbakhly_provider.uis.activity_base.BaseFragment;
import com.etbakhly_provider.uis.activity_kitchen.KitchenDetailsActivity;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class FragmentGallery extends BaseFragment {
    private FragmentGalleryBinding binding;
    private GalleryAdapter adapter;
    private KitchenModel model;
    private KitchenDetailsActivity activity;

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
        if (model.getPhotos().size() > 0) {
            adapter=new GalleryAdapter(activity);

            adapter.updateList(model.getPhotos());
            binding.recViewGallery.setLayoutManager(new GridLayoutManager(activity,3));
            binding.recViewGallery.setAdapter(adapter);
            binding.tvNoData.setVisibility(View.GONE);
        } else {
            binding.tvNoData.setVisibility(View.VISIBLE);

        }

    }

}