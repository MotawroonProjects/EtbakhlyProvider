package com.etbakhly_provider.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
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
import com.etbakhly_provider.databinding.LocalGalleryRowBinding;
import com.etbakhly_provider.model.KitchenModel;
import com.etbakhly_provider.tags.Tags;
import com.etbakhly_provider.uis.activity_kitchen.fragments_kitchen.FragmentGallery;

import java.util.List;

public class LocalGalleryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<String> list;
    private Context context;
    private LayoutInflater inflater;
    private Fragment fragment;



    public LocalGalleryAdapter(Context context,Fragment fragment,List<String> list) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.fragment = fragment;
        this.list = list;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LocalGalleryRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.local_gallery_row, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        RequestOptions options = new RequestOptions();
        Glide.with(context).asBitmap()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .load(Uri.parse(list.get(myHolder.getAdapterPosition())))
                .apply(options)
                .into(myHolder.binding.image);

        myHolder.binding.deleteImage.setOnClickListener(v -> {
            if (fragment instanceof FragmentGallery){
                FragmentGallery fragmentGallery = (FragmentGallery) fragment;
                fragmentGallery.deleteLocalImage(myHolder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        private LocalGalleryRowBinding binding;

        public MyHolder(LocalGalleryRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }

    }


}
