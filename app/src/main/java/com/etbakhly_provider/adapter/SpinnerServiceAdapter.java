
package com.etbakhly_provider.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.databinding.DataBindingUtil;


import com.etbakhly_provider.R;
import com.etbakhly_provider.databinding.SpinnerRowBinding;

import java.util.List;

public class SpinnerServiceAdapter extends BaseAdapter {
    private List<String> list;
    private Context context;

    public SpinnerServiceAdapter(Context context) {
        this.context = context;

    }

    @Override
    public int getCount() {
        if (list == null)
            return 0;
        else
            return list.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        @SuppressLint("ViewHolder") SpinnerRowBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.spinner_row, viewGroup, false);

        binding.setTitle(list.get(i));

        return binding.getRoot();
    }

    public void updateData(List<String> list) {
        this.list = list;
        notifyDataSetChanged();
    }
}
