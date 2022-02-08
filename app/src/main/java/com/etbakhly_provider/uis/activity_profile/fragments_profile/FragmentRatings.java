package com.etbakhly_provider.uis.activity_profile.fragments_profile;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.etbakhly_provider.R;
import com.etbakhly_provider.adapter.RatingAdapter;
import com.etbakhly_provider.databinding.FragmentGalleryBinding;
import com.etbakhly_provider.databinding.FragmentRatingsBinding;
import com.etbakhly_provider.uis.activity_base.BaseFragment;
import com.etbakhly_provider.uis.activity_profile.ProfileActivity;


public class FragmentRatings extends BaseFragment {
    private ProfileActivity activity;
    private FragmentRatingsBinding binding;
    private RatingAdapter adapter;

    public static FragmentRatings newInstance() {
        FragmentRatings fragment = new FragmentRatings();
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (ProfileActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ratings, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        adapter = new RatingAdapter(activity, this);
        binding.recViewRatings.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.VERTICAL, false));
        binding.recViewRatings.setAdapter(adapter);
        binding.tvNoData.setVisibility(View.GONE);
    }
}