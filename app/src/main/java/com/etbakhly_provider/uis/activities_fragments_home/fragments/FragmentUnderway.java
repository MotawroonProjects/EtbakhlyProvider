package com.etbakhly_provider.uis.activities_fragments_home.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.etbakhly_provider.R;
import com.etbakhly_provider.adapter.AwaitingApprovalAdapter;
import com.etbakhly_provider.adapter.UnderwayAdapter;
import com.etbakhly_provider.databinding.FragmentOrderBinding;
import com.etbakhly_provider.model.OrderModel;
import com.etbakhly_provider.model.UserModel;
import com.etbakhly_provider.mvvm.FragmentUnderwayMvvm;
import com.etbakhly_provider.uis.activities_fragments_home.HomeActivity;

import java.util.List;


public class FragmentUnderway extends Fragment {
    private FragmentOrderBinding binding;
    private UnderwayAdapter adapter;
    private HomeActivity activity;
    private FragmentUnderwayMvvm mvvm;
    private UserModel userModel;

    public static FragmentUnderway newInstance() {
        FragmentUnderway fragment = new FragmentUnderway();
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (HomeActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_order, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        mvvm= ViewModelProviders.of(this).get(FragmentUnderwayMvvm.class);
        adapter=new UnderwayAdapter(activity,this) ;

        mvvm.getIsDataLoading().observe(activity, isLoading -> {
            binding.swipeRefresh.setRefreshing(isLoading);
        });

        mvvm.getOnDataSuccess().observe(activity, orderList -> {
            if (orderList.size() >0){
                if (adapter !=null){
                    adapter.updateList(orderList);
                    binding.tvNoData.setVisibility(View.GONE);
                }
            }else {
                binding.tvNoData.setVisibility(View.VISIBLE);
            }
        });

        binding.swipeRefresh.setOnRefreshListener(() -> {
            mvvm.getUnderwayOrder("27");
        });
        binding.recyclerOrder.setAdapter(adapter);
        binding.recyclerOrder.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.VERTICAL,false));
        mvvm.getUnderwayOrder("27");

    }
}