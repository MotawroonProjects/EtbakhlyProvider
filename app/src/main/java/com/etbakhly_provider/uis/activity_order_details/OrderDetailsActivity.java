package com.etbakhly_provider.uis.activity_order_details;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.etbakhly_provider.R;
import com.etbakhly_provider.adapter.OrderDetailsAdapter;
import com.etbakhly_provider.databinding.ActivityOrderDetailsBinding;
import com.etbakhly_provider.databinding.DialogAlertBinding;
import com.etbakhly_provider.model.ChatUserModel;
import com.etbakhly_provider.model.OrderModel;
import com.etbakhly_provider.model.UserModel;
import com.etbakhly_provider.mvvm.ActivityOrderDetailsMvvm;
import com.etbakhly_provider.share.Common;
import com.etbakhly_provider.uis.activity_base.BaseActivity;
import com.etbakhly_provider.uis.activity_chat.ChatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class OrderDetailsActivity extends BaseActivity {
    private ActivityOrderDetailsBinding binding;
    private ActivityOrderDetailsMvvm mvvm;
    private String order_id = "";
    private OrderModel model;
    private String reason = "";
    private OrderDetailsAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_details);
        getDataFromIntent();
        initView();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        order_id = intent.getStringExtra("order_id");
    }

    private void initView() {

        mvvm = ViewModelProviders.of(this).get(ActivityOrderDetailsMvvm.class);
        binding.setModel(null);
        binding.recView.setLayoutManager(new GridLayoutManager(this,2));
        adapter = new OrderDetailsAdapter(this);
        binding.recView.setAdapter(adapter);

        mvvm.getIsOrderDataLoading().observe(this, isLoading -> {
            if (isLoading) {
                binding.flLoader.setVisibility(View.VISIBLE);
            } else {
                binding.flLoader.setVisibility(View.GONE);

            }
        });
        mvvm.getOnOrderDetailsSuccess().observe(this, orderModel -> {
            this.model = orderModel;
            binding.setModel(model);
            updateUi();

        });
        mvvm.getOrderDetails(order_id);


        mvvm.getOnOrderStatusSuccess().observe(this, status -> {

            if (status.equals("approval")) {
                Intent intent = getIntent();
                intent.putExtra("order_status", status);
                setResult(RESULT_OK, intent);
                finish();
            } else if (status.equals("refusal")) {
                Intent intent = getIntent();
                intent.putExtra("order_status", status);
                setResult(RESULT_OK, intent);
                finish();
            } else if (status.equals("completed")) {
                Intent intent = getIntent();
                intent.putExtra("order_status", status);
                setResult(RESULT_OK, intent);
                finish();
            } else {
                mvvm.getOrderDetails(order_id);

            }

        });
        binding.setLang(getLang());
        binding.llBack.setOnClickListener(v -> onBackPressed());

        binding.btnAction.setOnClickListener(v -> {
            String actionStatus = "";
            if (model.getStatus_order().equals("new")) {
                actionStatus = "approval";
            } else if (model.getStatus_order().equals("approval")) {
                actionStatus = "making";


            } else if (model.getStatus_order().equals("making")) {
                if (model.getCaterer().getIs_delivry().equals("delivry")) {
                    actionStatus = "delivery";

                } else {
                    actionStatus = "completed";

                }


            } else if (model.getStatus_order().equals("delivery")) {
                actionStatus = "completed";

            }

            mvvm.changeStatusOrder(actionStatus, order_id, null, this);
        });

        binding.btnRefuse.setOnClickListener(v -> {
            createReasonDialog("refusal", order_id);
        });

        binding.chat.setOnClickListener(v -> navigateToChatActivity(model));


    }

    public void navigateToChatActivity(OrderModel orderModel) {
        UserModel.Data user = orderModel.getUser();
        UserModel.Data catererUser = orderModel.getCaterer().getUser();

        ChatUserModel model = new ChatUserModel(user.getId(), user.getName(), user.getPhoto() + user.getPhone(), user.getPhoto(), getUserModel().getData().getId(), getUserModel().getData().getName(), getUserModel().getData().getPhone_code() + getUserModel().getData().getPhone(), getUserModel().getData().getPhoto(), orderModel.getCaterer().getAddress(), orderModel.getCaterer().getLatitude(), orderModel.getCaterer().getLongitude(), orderModel.getId(), orderModel.getTotal());
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra("data", model);
        startActivity(intent);
    }

    private void createReasonDialog(String status, String order_id) {
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .create();

        DialogAlertBinding binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_alert, null, false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setView(binding.getRoot());
        dialog.show();


        binding.radio1.setOnClickListener(view -> {
            reason = "";
            binding.anotherReason.setVisibility(View.GONE);
            binding.anotherReason.setText(null);
            binding.anotherReason.setError(null);

        });
        binding.radio2.setOnClickListener(view -> {
            reason = "";
            binding.anotherReason.setVisibility(View.GONE);
            binding.anotherReason.setText(null);
            binding.anotherReason.setError(null);


        });

        binding.radio3.setOnClickListener(view -> {
            reason = "";
            binding.anotherReason.setVisibility(View.VISIBLE);
        });

        binding.btnDone.setOnClickListener(view -> {
            if (binding.radio3.isChecked()) {
                reason = binding.anotherReason.getText().toString();
                if (!reason.isEmpty()) {
                    binding.anotherReason.setError(null);
                    Common.CloseKeyBoard(this, binding.anotherReason);
                    mvvm.changeStatusOrder(status, order_id, reason, this);

                } else {
                    binding.anotherReason.setError(getString(R.string.field_required));
                }
            } else {
                mvvm.changeStatusOrder(status, order_id, reason, this);

            }
            dialog.dismiss();

        });
    }


    private void updateUi() {
        adapter.updateList(model.getOrder_details());

        if (model.getCaterer().getIs_delivry().equals("delivry")) {
            updateStateHasDelivery();
        } else {
            updateStateHasNoDelivery();

        }

        Log.e("status",model.getStatus_order());
        if (model.getStatus_order().equals("new") || model.getStatus_order().equals("completed")) {
            binding.chat.setVisibility(View.GONE);
        } else {
            binding.chat.setVisibility(View.VISIBLE);

        }


    }

    private void updateStateHasDelivery() {
        if (model.getStatus_order().equals("approval")) {
            updateState1Step1();
        } else if (model.getStatus_order().equals("making")) {
            updateState1Step2();

        } else if (model.getStatus_order().equals("delivery")) {
            updateState1Step3();

        } else if (model.getStatus_order().equals("completed")) {
            updateState1Step4();

        }

    }


    private void updateStateHasNoDelivery() {
        if (model.getStatus_order().equals("approval")) {
            updateState2Step1();
        } else if (model.getStatus_order().equals("making")) {
            updateState2Step2();

        } else if (model.getStatus_order().equals("completed")) {
            updateState2Step3();


        }
    }


    private void updateState1Step1() {
        binding.step1.imageDateAccepted.setImageResource(R.drawable.circle_primary);
        binding.step1.imagePending.setImageResource(R.drawable.circle_gray4);
        binding.step1.imageDelivering.setImageResource(R.drawable.circle_gray4);
        binding.step1.imageDelivered.setImageResource(R.drawable.circle_gray4);

        binding.step1.line1.setBackgroundResource(R.color.gray4);
        binding.step1.line2.setBackgroundResource(R.color.gray4);
        binding.step1.line3.setBackgroundResource(R.color.gray4);

        binding.step1.tvDateAccepted.setText(getDate(model.getUpdated_at()));
        binding.step1.tvDatePending.setText(null);
        binding.step1.tvDateDelivering.setText(null);
        binding.step1.tvDateDelivered.setText(null);


    }

    private void updateState1Step2() {
        binding.step1.imageDateAccepted.setImageResource(R.drawable.circle_primary);
        binding.step1.imagePending.setImageResource(R.drawable.circle_primary);
        binding.step1.imageDelivering.setImageResource(R.drawable.circle_gray4);
        binding.step1.imageDelivered.setImageResource(R.drawable.circle_gray4);

        binding.step1.line1.setBackgroundResource(R.color.colorPrimary);
        binding.step1.line2.setBackgroundResource(R.color.gray4);
        binding.step1.line3.setBackgroundResource(R.color.gray4);

        binding.step1.tvDateAccepted.setText(null);
        binding.step1.tvDatePending.setText(getDate(model.getUpdated_at()));
        binding.step1.tvDateDelivering.setText(null);
        binding.step1.tvDateDelivered.setText(null);

    }

    private void updateState1Step3() {
        binding.step1.imageDateAccepted.setImageResource(R.drawable.circle_primary);
        binding.step1.imagePending.setImageResource(R.drawable.circle_primary);
        binding.step1.imageDelivering.setImageResource(R.drawable.circle_primary);
        binding.step1.imageDelivered.setImageResource(R.drawable.circle_gray4);

        binding.step1.line1.setBackgroundResource(R.color.colorPrimary);
        binding.step1.line2.setBackgroundResource(R.color.colorPrimary);
        binding.step1.line3.setBackgroundResource(R.color.gray4);

        binding.step1.tvDateAccepted.setText(null);
        binding.step1.tvDatePending.setText(null);
        binding.step1.tvDateDelivering.setText(getDate(model.getUpdated_at()));
        binding.step1.tvDateDelivered.setText(null);
    }

    private void updateState1Step4() {
        binding.step1.imageDateAccepted.setImageResource(R.drawable.circle_primary);
        binding.step1.imagePending.setImageResource(R.drawable.circle_primary);
        binding.step1.imageDelivering.setImageResource(R.drawable.circle_primary);
        binding.step1.imageDelivered.setImageResource(R.drawable.circle_primary);

        binding.step1.line1.setBackgroundResource(R.color.colorPrimary);
        binding.step1.line2.setBackgroundResource(R.color.colorPrimary);
        binding.step1.line3.setBackgroundResource(R.color.colorPrimary);

        binding.step1.tvDateAccepted.setText(null);
        binding.step1.tvDatePending.setText(null);
        binding.step1.tvDateDelivering.setText(null);
        binding.step1.tvDateDelivered.setText(getDate(model.getUpdated_at()));

    }


    private void updateState2Step1() {
        binding.step2.imageDateAccepted.setImageResource(R.drawable.circle_primary);
        binding.step2.imagePending.setImageResource(R.drawable.circle_gray4);
        binding.step2.imageDone.setImageResource(R.drawable.circle_gray4);

        binding.step2.line1.setBackgroundResource(R.color.gray4);
        binding.step2.line2.setBackgroundResource(R.color.gray4);

        binding.step2.tvDateAccepted.setText(getDate(model.getUpdated_at()));
        binding.step2.tvDatePending.setText(null);
        binding.step2.tvDateDone.setText(null);


    }

    private void updateState2Step2() {
        binding.step2.imageDateAccepted.setImageResource(R.drawable.circle_primary);
        binding.step2.imagePending.setImageResource(R.drawable.circle_primary);
        binding.step2.imageDone.setImageResource(R.drawable.circle_gray4);

        binding.step2.line1.setBackgroundResource(R.color.colorPrimary);
        binding.step2.line2.setBackgroundResource(R.color.gray4);

        binding.step2.tvDateAccepted.setText(null);
        binding.step2.tvDatePending.setText(getDate(model.getUpdated_at()));
        binding.step2.tvDateDone.setText(null);


    }

    private void updateState2Step3() {
        binding.step2.imageDateAccepted.setImageResource(R.drawable.circle_primary);
        binding.step2.imagePending.setImageResource(R.drawable.circle_primary);
        binding.step2.imageDone.setImageResource(R.drawable.circle_primary);

        binding.step2.line1.setBackgroundResource(R.color.colorPrimary);
        binding.step2.line2.setBackgroundResource(R.color.colorPrimary);

        binding.step2.tvDateAccepted.setText(null);
        binding.step2.tvDatePending.setText(null);
        binding.step2.tvDateDone.setText(getDate(model.getUpdated_at()));

    }

    private String getDate(String updateAt) {
        String date = "";
        if (updateAt != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            try {
                Date d = simpleDateFormat.parse(updateAt);

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd\nhh:mm aa", Locale.ENGLISH);
                dateFormat.setTimeZone(TimeZone.getDefault());
                date = dateFormat.format(d);
            } catch (ParseException e) {
                e.printStackTrace();
            }


        }
        return date;
    }

    @Override
    public void onBackPressed() {
        if (model != null && model.getStatus_order() != null && !model.getStatus_order().isEmpty()) {
            Intent intent = getIntent();
            intent.putExtra("order_status", model.getStatus_order());
            setResult(RESULT_OK, intent);
        }

        finish();
    }
}