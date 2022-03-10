package com.etbakhly_provider.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.etbakhly_provider.R;
import com.etbakhly_provider.databinding.GalleryRowBinding;
import com.etbakhly_provider.model.KitchenModel;
import com.etbakhly_provider.tags.Tags;
import com.etbakhly_provider.uis.activity_kitchen.fragments_kitchen.FragmentGallery;

import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<KitchenModel.Photo> list;
    private Context context;
    private LayoutInflater inflater;
    private Fragment fragment;



    public GalleryAdapter(Context context,Fragment fragment) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.fragment = fragment;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        GalleryRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.gallery_row, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setModel(list.get(position));
        RequestOptions options = new RequestOptions();
        Glide.with(context).asBitmap()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .load(Tags.base_url+list.get(position).getPhoto())
                .apply(options)
                .into(myHolder.binding.image);

        myHolder.binding.cardViewDelete.setOnClickListener(v -> {
            if (fragment instanceof FragmentGallery){
                FragmentGallery fragmentGallery = (FragmentGallery) fragment;
                fragmentGallery.deleteImage(list.get(myHolder.getAdapterPosition()),myHolder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        private GalleryRowBinding binding;

        public MyHolder(GalleryRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }

    }

    public void updateList(List<KitchenModel.Photo> list) {
        if (list != null) {
            this.list = list;
        }
        notifyDataSetChanged();
    }
}
