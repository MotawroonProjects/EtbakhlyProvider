package com.etbakhly_provider.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.databinding.DataBindingUtil;

import com.etbakhly_provider.R;

import com.etbakhly_provider.databinding.SpinnerCategoryDishRowBinding;
import com.etbakhly_provider.model.BuffetModel;


import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;

public class SpinnerDishCategoryAdapter extends BaseAdapter {
    private List<BuffetModel.Category> dataList;
    private Context context;

    public SpinnerDishCategoryAdapter(List<BuffetModel.Category> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public int getCount() {
        if (dataList == null)
            return 0;
        else
            return dataList.size();
    }

    @Override
    public Object getItem(int i) {
        return dataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        @SuppressLint("ViewHolder") SpinnerCategoryDishRowBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.spinner_category_dish_row, viewGroup, false);
         binding.setTitle(dataList.get(i).getTitel());
        return binding.getRoot();
    }

    public void updateList(List<BuffetModel.Category> dataList){
        if (dataList!=null){
            this.dataList = dataList;
            Log.e("sda",this.dataList.size()+"");
        }
        notifyDataSetChanged();
    }


}
