package com.etbakhly_provider.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.etbakhly_provider.R;


import com.etbakhly_provider.databinding.AddFeastsTitleRowBinding;

import java.util.List;

public class AddFeastsTitlesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Object> list;
    private Context context;
    private LayoutInflater inflater;
    private AddFeastsDetailsAdapter feastsDetailsAdapter;

    public AddFeastsTitlesAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AddFeastsTitleRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.add_feasts_title_row, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        feastsDetailsAdapter = new AddFeastsDetailsAdapter(context);
        myHolder.binding.recAddBuffetDetails.setLayoutManager(new GridLayoutManager(context, 2));
        myHolder.binding.recAddBuffetDetails.setAdapter(feastsDetailsAdapter);
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        } else {
            return 3;
        }
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        private AddFeastsTitleRowBinding binding;

        public MyHolder(AddFeastsTitleRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public void updateList(List<Object> list) {
        if (list != null) {
            this.list = list;
        }
        notifyDataSetChanged();
    }
}
