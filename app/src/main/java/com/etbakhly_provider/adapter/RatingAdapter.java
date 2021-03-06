package com.etbakhly_provider.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.etbakhly_provider.R;
import com.etbakhly_provider.databinding.RatingRowBinding;
import com.etbakhly_provider.model.RateModel;

import java.util.List;

public class RatingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<RateModel> list;
    private Context context;
    private LayoutInflater inflater;

    public RatingAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RatingRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.rating_row, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setModel(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }


    public static class MyHolder extends RecyclerView.ViewHolder {
        private RatingRowBinding binding;

        public MyHolder(RatingRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }

    public void updateList(List<RateModel> list) {
        if (list != null) {
            this.list = list;
        }
        notifyDataSetChanged();
    }
}
