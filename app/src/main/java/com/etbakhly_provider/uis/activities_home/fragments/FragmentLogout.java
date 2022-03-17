package com.etbakhly_provider.uis.activities_home.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.etbakhly_provider.R;
import com.etbakhly_provider.databinding.FragmentBottomSheetLanguageDialogBinding;
import com.etbakhly_provider.databinding.FragmentBottomSheetLogoutDialogBinding;
import com.etbakhly_provider.uis.activities_home.HomeActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import io.paperdb.Paper;

public class FragmentLogout extends BottomSheetDialogFragment {
    private FragmentBottomSheetLogoutDialogBinding binding;
    private HomeActivity activity;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (HomeActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bottom_sheet_logout_dialog, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        binding.btnCancel.setOnClickListener(v -> dismiss());
        binding.btnLogout.setOnClickListener(v ->
                {
                    activity.logout();
                    dismiss();
                }
        
        );
    }


}
