package com.etbakhly_provider.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.etbakhly_provider.R;
import com.etbakhly_provider.databinding.AddBuffetTitleRowBinding;
import com.etbakhly_provider.databinding.AddFeastsTitleRowBinding;
import com.etbakhly_provider.uis.activity_add_buffet.AddBuffetActivity;

import java.util.List;

public class AddBuffetTitlesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Object> list;
    private Context context;
    private LayoutInflater inflater;
    private AddBuffetDetailsAdapter buffetDetailsAdapter;

    public AddBuffetTitlesAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AddBuffetTitleRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.add_buffet_title_row, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        buffetDetailsAdapter = new AddBuffetDetailsAdapter(context);
        myHolder.binding.recAddBuffetDetails.setLayoutManager(new GridLayoutManager(context, 2));
        myHolder.binding.recAddBuffetDetails.setAdapter(buffetDetailsAdapter);

        myHolder.binding.llAddNew.setOnClickListener(view -> {
            AddBuffetActivity addBuffetActivity =(AddBuffetActivity) context;
            addBuffetActivity.navigateToAddNewDish();
        });
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
        private AddBuffetTitleRowBinding binding;

        public MyHolder(AddBuffetTitleRowBinding binding) {
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
