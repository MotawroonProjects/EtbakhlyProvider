package com.etbakhly_provider.uis.activity_profile.fragments_profile;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.etbakhly_provider.R;
import com.etbakhly_provider.adapter.GalleryAdapter;
import com.etbakhly_provider.databinding.FragmentGalleryBinding;
import com.etbakhly_provider.uis.activity_base.BaseFragment;
import com.etbakhly_provider.uis.activity_profile.ProfileActivity;


public class FragmentGallery extends BaseFragment {
    private FragmentGalleryBinding binding;
    private GalleryAdapter adapter;
    private ProfileActivity activity;


    public static FragmentGallery newInstance() {
        FragmentGallery fragment = new FragmentGallery();
        Bundle args = new Bundle();
//        args.putSerializable("data", model);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (ProfileActivity) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            model = (KitchenModel) getArguments().getSerializable("data");
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
        adapter = new GalleryAdapter(activity, this);
        binding.recViewGallery.setLayoutManager(new GridLayoutManager(activity, 3));
        binding.recViewGallery.setAdapter(adapter);
        binding.tvNoData.setVisibility(View.GONE);
    }

}