package com.etbakhly_provider.uis.activity_kitchen.fragments_kitchen;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.etbakhly_provider.R;
import com.etbakhly_provider.databinding.FragmentDeliveryAreasBinding;
import com.etbakhly_provider.model.KitchenModel;
import com.etbakhly_provider.model.ZoneCover;
import com.etbakhly_provider.model.ZoneModel;
import com.etbakhly_provider.mvvm.ActivitymapMvvm;
import com.etbakhly_provider.uis.activity_base.BaseFragment;
import com.etbakhly_provider.uis.activity_kitchen.KitchenDetailsActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;


public class FragmentDeliveryAreas extends BaseFragment  {
    private KitchenDetailsActivity activity;
    private FragmentDeliveryAreasBinding binding;
    private GoogleMap mMap;
    private float zoom = 15.0f;



    public static FragmentDeliveryAreas newInstance(KitchenModel model) {
        FragmentDeliveryAreas fragment = new FragmentDeliveryAreas();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_delivery_areas, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
    }





}