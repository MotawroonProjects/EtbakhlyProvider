package com.etbakhly_provider.uis.activities_home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import com.etbakhly_provider.R;
import com.etbakhly_provider.adapter.SpinnerDishCategoryAdapter;
import com.etbakhly_provider.databinding.AddCategoryDialogFragmentBinding;
import com.etbakhly_provider.model.UserModel;
import com.etbakhly_provider.mvvm.ActivityHomeGeneralMvvm;
import com.etbakhly_provider.mvvm.FragmentAddCategoryDishesMvvm;
import com.etbakhly_provider.preferences.Preferences;
import com.etbakhly_provider.share.Common;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

public class FragmentDialogAddCategory extends BottomSheetDialogFragment {
    private HomeActivity activity;
    private AddCategoryDialogFragmentBinding binding;
    private SpinnerDishCategoryAdapter adapter;
    private FragmentAddCategoryDishesMvvm mvvm;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (HomeActivity) context;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.add_category_dialog_fragment, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        mvvm = ViewModelProviders.of(this).get(FragmentAddCategoryDishesMvvm.class);
        adapter = new SpinnerDishCategoryAdapter(new ArrayList<>(), activity);
        binding.spinner.setAdapter(adapter);

        mvvm.getCategoryAddedSuccess().observe(this, isAdded -> {
            if (isAdded) {
                dismiss();
                Toast.makeText(activity, getString(R.string.succ), Toast.LENGTH_SHORT).show();
            }
        });
        mvvm.getOnCategorySuccess().observe(this, list -> {
            if (adapter != null) {
                adapter.updateList(list);
            }
        });

        binding.llClose.setOnClickListener(v -> {
            dismiss();
        });


        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (mvvm.getOnCategorySuccess().getValue() != null) {
                    mvvm.getOnSelectedCategorySuccess().setValue(mvvm.getOnCategorySuccess().getValue().get(position));

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.btnAdd.setOnClickListener(v -> {
            Preferences preferences = Preferences.getInstance();
            UserModel userModel = preferences.getUserData(activity);
            String name = binding.edtName.getText().toString();
            if (!name.isEmpty()) {
                binding.edtName.setError(null);
                Common.CloseKeyBoard(activity, binding.edtName);
                mvvm.addCategory(name, userModel.getData().getCaterer().getId(), activity);
            } else {
                binding.edtName.setError(getString(R.string.field_required));
            }
        });
    }
}
