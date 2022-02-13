package com.etbakhly_provider.uis.order_details;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.etbakhly_provider.R;
import com.etbakhly_provider.adapter.OrderDetailsAdapter;
import com.etbakhly_provider.adapter.OrderTitlesAdapter;
import com.etbakhly_provider.databinding.ActivityOrderDetailsBinding;
import com.etbakhly_provider.model.SingleOrderModel;
import com.etbakhly_provider.mvvm.ActivityOrderDetailsMvvm;
import com.etbakhly_provider.mvvm.ActivitymapMvvm;
import com.etbakhly_provider.mvvm.FragmentNewOrdersMvvm;
import com.etbakhly_provider.uis.activity_base.BaseActivity;
import com.etbakhly_provider.uis.activity_base.FragmentMapTouchListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class OrderDetailsActivity extends BaseActivity implements OnMapReadyCallback {
    private ActivityOrderDetailsBinding binding;
    private OrderTitlesAdapter titlesAdapter;
    private GoogleMap mMap;
    private float zoom = 15.0f;
    private ActivityResultLauncher<String> permissionLauncher;
    private ActivitymapMvvm activitymapMvvm;
    private ActivityOrderDetailsMvvm mvvm;
    private SingleOrderModel singleOrderModel;
    private String order_id = "249";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_details);
        initView();
    }

    private void initView() {

        binding.setLang(getLang());

        binding.btnBack.setOnClickListener(view -> finish());
        mvvm = ViewModelProviders.of(this).get(ActivityOrderDetailsMvvm.class);
        mvvm.getIsOrderDataLoading().observe(this, isLoading -> {
            binding.progBar.setVisibility(View.VISIBLE);
            binding.nestedView.setVisibility(View.GONE);
        });
        mvvm.getOnOrderDetailsSuccess().observe(this, singleOrderModels -> {
            if(singleOrderModels!=null){
            binding.progBar.setVisibility(View.GONE);
            binding.nestedView.setVisibility(View.VISIBLE);
            this.singleOrderModel=singleOrderModels;
            binding.setModel(singleOrderModel);

                checkData();
            }
        });
        mvvm.getOrderDetails(order_id);
        titlesAdapter = new OrderTitlesAdapter(this);
        binding.recyclerOrderDetails.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        binding.recyclerOrderDetails.setAdapter(titlesAdapter);

        permissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
            if (isGranted) {
//                activitymapMvvm.initGoogleApi();

            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void checkData() {
        Log.e("kkkkk",singleOrderModel.getStatus_order());
        if(singleOrderModel.getStatus_order().equals("new")){
            updateUI1();
        }else if (singleOrderModel.getStatus_order().equals("approval")){
            updateUI2();
        }else if (singleOrderModel.getStatus_order().equals("making")){
            updateUI3();
        }else if (singleOrderModel.getStatus_order().equals("delivery")){
            updateUI4();
        }
    }

    private void updateUI1() {
        binding.txtNew.setVisibility(View.VISIBLE);
        binding.txtApproval.setVisibility(View.GONE);
        binding.txtDelivery.setVisibility(View.GONE);
        binding.txtMaking.setVisibility(View.GONE);

        binding.view1.setBackground(getResources().getDrawable(R.drawable.horizontal_line3));
        binding.view2.setBackground(getResources().getDrawable(R.drawable.horizontal_line3));
        binding.view3.setBackground(getResources().getDrawable(R.drawable.horizontal_line3));


        binding.imgApproval.setColorFilter(ContextCompat.getColor(this,R.color.color00), PorterDuff.Mode.SRC_IN);
        binding.imgMaking.setColorFilter(ContextCompat.getColor(this,R.color.color00),PorterDuff.Mode.SRC_IN);
        binding.imgDelivery.setColorFilter(ContextCompat.getColor(this,R.color.color00),PorterDuff.Mode.SRC_IN);

        binding.llNewOrderBtns.setVisibility(View.VISIBLE);
        binding.btnDelvCompleted.setVisibility(View.GONE);

    }
    private void updateUI2() {
        binding.txtNew.setVisibility(View.VISIBLE);
        binding.txtApproval.setVisibility(View.VISIBLE);
        binding.txtDelivery.setVisibility(View.GONE);
        binding.txtMaking.setVisibility(View.GONE);

        binding.view1.setBackground(getResources().getDrawable(R.drawable.horizontal_line));
        binding.view2.setBackground(getResources().getDrawable(R.drawable.horizontal_line3));
        binding.view3.setBackground(getResources().getDrawable(R.drawable.horizontal_line3));


        binding.imgApproval.setColorFilter(ContextCompat.getColor(this,R.color.color13), PorterDuff.Mode.SRC_IN);
        binding.imgMaking.setColorFilter(ContextCompat.getColor(this,R.color.color00),PorterDuff.Mode.SRC_IN);
        binding.imgDelivery.setColorFilter(ContextCompat.getColor(this,R.color.color00),PorterDuff.Mode.SRC_IN);

        binding.llNewOrderBtns.setVisibility(View.GONE);
        binding.btnDelvCompleted.setVisibility(View.VISIBLE);
        binding.btnDelvCompleted.setText(R.string.prepared);
    }
    private void updateUI3() {
        binding.txtNew.setVisibility(View.VISIBLE);
        binding.txtApproval.setVisibility(View.VISIBLE);
        binding.txtMaking.setVisibility(View.VISIBLE);
        binding.txtDelivery.setVisibility(View.GONE);

        binding.view1.setBackground(getResources().getDrawable(R.drawable.horizontal_line));
        binding.view2.setBackground(getResources().getDrawable(R.drawable.horizontal_line));
        binding.view3.setBackground(getResources().getDrawable(R.drawable.horizontal_line3));


        binding.imgApproval.setColorFilter(ContextCompat.getColor(this,R.color.colorAccent), PorterDuff.Mode.SRC_IN);
        binding.imgMaking.setColorFilter(ContextCompat.getColor(this,R.color.colorAccent),PorterDuff.Mode.SRC_IN);
        binding.imgDelivery.setColorFilter(ContextCompat.getColor(this,R.color.color00),PorterDuff.Mode.SRC_IN);

        binding.llNewOrderBtns.setVisibility(View.GONE);
        binding.btnDelvCompleted.setVisibility(View.VISIBLE);
        binding.btnDelvCompleted.setText(R.string.completed);
    }
    private void updateUI4() {
        binding.txtNew.setVisibility(View.VISIBLE);
        binding.txtApproval.setVisibility(View.VISIBLE);
        binding.txtMaking.setVisibility(View.VISIBLE);
        binding.txtDelivery.setVisibility(View.VISIBLE);

        binding.view1.setBackground(getResources().getDrawable(R.drawable.horizontal_line));
        binding.view2.setBackground(getResources().getDrawable(R.drawable.horizontal_line));
        binding.view3.setBackground(getResources().getDrawable(R.drawable.horizontal_line));


        binding.imgApproval.setColorFilter(ContextCompat.getColor(this,R.color.colorAccent), PorterDuff.Mode.SRC_IN);
        binding.imgMaking.setColorFilter(ContextCompat.getColor(this,R.color.colorAccent),PorterDuff.Mode.SRC_IN);
        binding.imgDelivery.setColorFilter(ContextCompat.getColor(this,R.color.colorAccent),PorterDuff.Mode.SRC_IN);

        binding.llNewOrderBtns.setVisibility(View.GONE);
        binding.btnDelvCompleted.setVisibility(View.GONE);
    }

    private void updateUI() {
        SupportMapFragment supportMapFragment = SupportMapFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.map, supportMapFragment).commit();
        supportMapFragment.getMapAsync(this);

        FragmentMapTouchListener fragmentMapTouchListener = (FragmentMapTouchListener) getSupportFragmentManager().findFragmentById(R.id.map);
        fragmentMapTouchListener.setListener(() -> {
        });


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        if (googleMap != null) {
            mMap = googleMap;
            mMap.setTrafficEnabled(false);
            mMap.setBuildingsEnabled(false);
            mMap.setIndoorEnabled(true);
            if (activitymapMvvm.getGoogleMap().getValue() == null) {
                activitymapMvvm.setmMap(mMap);

            }


        }
    }

    private void addMarker(double lat, double lng) {
        if (activitymapMvvm.getGoogleMap().getValue() != null) {
            mMap = activitymapMvvm.getGoogleMap().getValue();
        }
        mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), zoom));

    }
}