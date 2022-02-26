package com.etbakhly_provider.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.etbakhly_provider.R;
import com.etbakhly_provider.databinding.AddZoneRowBinding;
import com.etbakhly_provider.databinding.CityRowBinding;
import com.etbakhly_provider.model.AddZoneModel;
import com.etbakhly_provider.model.CountryModel;
import com.etbakhly_provider.uis.activity_city.CityActivity;
import com.etbakhly_provider.uis.activity_signup.fragments_sign_up_navigation.FragmentSignup1;

import java.util.List;

public class AddZoneAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<AddZoneModel> list;
    private Context context;
    private LayoutInflater inflater;
    private Fragment fragment;
    public AddZoneAdapter(Context context,Fragment fragment) {
        this.context = context;
        inflater = LayoutInflater.from(context);
       this.fragment = fragment;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        AddZoneRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.add_zone_row, parent, false);
        return new MyHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setModel(list.get(position));
        myHolder.binding.delete.setOnClickListener(v -> {
            if (fragment instanceof FragmentSignup1) {
               FragmentSignup1 fragmentSignup1 = (FragmentSignup1) fragment;
               fragmentSignup1.deleteItem(myHolder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }


    public static class MyHolder extends RecyclerView.ViewHolder {
        private AddZoneRowBinding binding;

        public MyHolder(AddZoneRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }

    }

    public void updateList(List<AddZoneModel> list) {
        if (list != null) {
            this.list = list;
        }
        notifyDataSetChanged();
    }



    public void removeItem(int pos) {
        list.remove(pos);
        notifyItemRemoved(pos);

    }

    public void addItem(AddZoneModel addZoneModel) {
        list.add(0,addZoneModel);
        notifyItemInserted(0);
    }


}
