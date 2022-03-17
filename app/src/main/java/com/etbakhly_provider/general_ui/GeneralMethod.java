package com.etbakhly_provider.general_ui;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;


import com.etbakhly_provider.R;

import com.etbakhly_provider.model.NotificationModel;
import com.etbakhly_provider.model.OrderModel;
import com.etbakhly_provider.tags.Tags;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import de.hdodenhof.circleimageview.CircleImageView;

public class GeneralMethod {

    @BindingAdapter("error")
    public static void errorValidation(View view, String error) {
        if (view instanceof EditText) {
            EditText ed = (EditText) view;
            ed.setError(error);
        } else if (view instanceof TextView) {
            TextView tv = (TextView) view;
            tv.setError(error);


        }
    }

    @BindingAdapter("order_status")
    public static void orderStatus(Button btnStatus, OrderModel orderModel) {
        if (orderModel != null) {
            String status = orderModel.getStatus_order();

            if (status.equals("new")) {
                btnStatus.setText(R.string.accept);

            } else if (status.equals("approval")) {
                btnStatus.setText(R.string.prepared);
            } else if (status.equals("making")) {
                if (orderModel.getCaterer().getIs_delivry().equals("delivry")) {
                    btnStatus.setText(R.string.delivery_in_progress);

                } else {
                    btnStatus.setText(R.string.delivery_completed);

                }

            } else if (status.equals("delivery")) {
                btnStatus.setText(R.string.delivery_completed);

            }
        }


    }


    @BindingAdapter("orderDetailsImage")
    public static void orderDetailsImage(View view, OrderModel.OrderDetail model) {
        if (model != null) {

            String imageUrl = "";
            if (model.getBuffet() != null) {
                imageUrl = model.getBuffet().getPhoto();
            } else if (model.getFeast() != null) {
                imageUrl = model.getFeast().getPhoto();

            } else if (model.getOffer() != null) {
                imageUrl = model.getOffer().getPhoto();
            } else if (model.getDishes() != null) {
                imageUrl = model.getDishes().getPhoto();
            }

            String imageUrl1 = Tags.base_url + imageUrl;

            view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    view.getViewTreeObserver().removeOnGlobalLayoutListener(this);


                    if (view instanceof CircleImageView) {
                        CircleImageView imageView = (CircleImageView) view;
                        RequestOptions options = new RequestOptions().override(view.getWidth(), view.getHeight());
                        Glide.with(view.getContext()).asBitmap()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .load(imageUrl1)
                                .centerCrop()
                                .apply(options)
                                .into(imageView);
                    } else if (view instanceof RoundedImageView) {
                        RoundedImageView imageView = (RoundedImageView) view;

                        RequestOptions options = new RequestOptions().override(view.getWidth(), view.getHeight());
                        Glide.with(view.getContext()).asBitmap()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .load(imageUrl1)
                                .centerCrop()
                                .apply(options)
                                .into(imageView);
                    } else if (view instanceof ImageView) {
                        ImageView imageView = (ImageView) view;

                        RequestOptions options = new RequestOptions().override(view.getWidth(), view.getHeight());
                        Glide.with(view.getContext()).asBitmap()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .load(imageUrl1)
                                .centerCrop()
                                .apply(options)
                                .into(imageView);
                    }

                }
            });
        }


    }


    @BindingAdapter("orderDetailsTitle")
    public static void orderDetailsTitle(TextView textView, OrderModel.OrderDetail model) {
        if (model != null) {

            String title = "";
            if (model.getBuffet() != null) {
                title = model.getBuffet().getTitel();
            } else if (model.getFeast() != null) {
                title = model.getFeast().getTitel();

            } else if (model.getOffer() != null) {
                title = model.getOffer().getTitle();
            } else if (model.getDishes() != null) {
                title = model.getDishes().getTitel();
            }

            textView.setText(title);

        }


    }


    @BindingAdapter("image")
    public static void image(View view, String imageUrl) {
        if (imageUrl != null) {
            String imageUrl1 = Tags.base_url + imageUrl;

            view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    view.getViewTreeObserver().removeOnGlobalLayoutListener(this);


                    if (view instanceof CircleImageView) {
                        CircleImageView imageView = (CircleImageView) view;
                        RequestOptions options = new RequestOptions().override(view.getWidth(), view.getHeight());
                        Glide.with(view.getContext()).asBitmap()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .load(imageUrl1)
                                .centerCrop()
                                .apply(options)
                                .into(imageView);
                    } else if (view instanceof RoundedImageView) {
                        RoundedImageView imageView = (RoundedImageView) view;

                        RequestOptions options = new RequestOptions().override(view.getWidth(), view.getHeight());
                        Glide.with(view.getContext()).asBitmap()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .load(imageUrl1)
                                .centerCrop()
                                .apply(options)
                                .into(imageView);
                    } else if (view instanceof ImageView) {
                        ImageView imageView = (ImageView) view;

                        RequestOptions options = new RequestOptions().override(view.getWidth(), view.getHeight());
                        Glide.with(view.getContext()).asBitmap()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .load(imageUrl1)
                                .centerCrop()
                                .apply(options)
                                .into(imageView);
                    }

                }
            });
        }

    }

    @BindingAdapter("user_image")
    public static void user_image(View view, String imageUrl) {


        if (view instanceof CircleImageView) {
            CircleImageView imageView = (CircleImageView) view;
            if (imageUrl != null) {

                Glide.with(view.getContext()).asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.circle_avatar)
                        .load(Tags.base_url + imageUrl)
                        .centerCrop()
                        .into(imageView);

            }
        } else if (view instanceof RoundedImageView) {
            RoundedImageView imageView = (RoundedImageView) view;

            if (imageUrl != null) {

                Glide.with(view.getContext()).asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.circle_avatar)
                        .load(Tags.base_url + imageUrl)
                        .centerCrop()
                        .into(imageView);

            }
        } else if (view instanceof ImageView) {
            ImageView imageView = (ImageView) view;

            if (imageUrl != null) {

                Glide.with(view.getContext()).asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.circle_avatar)
                        .load(Tags.base_url + imageUrl)
                        .centerCrop()
                        .into(imageView);
            }
        }

    }


    @BindingAdapter("createAt")
    public static void dateCreateAt(TextView textView, String s) {
        if (s != null) {
            try {
                String[] dates = s.split("T");
                textView.setText(dates[0]);
            } catch (Exception e) {

            }

        }

    }

    @BindingAdapter("providerType")
    public static void providerType(TextView textView, String type) {
        if (type != null) {
            if (type.equals("man")) {
                textView.setText(R.string.men);
            } else if (type.equals("women")) {
                textView.setText(R.string.women);

            } else if (type.equals("man_and_women")) {
                textView.setText(R.string.men_women);

            } else if (type.equals("not_found")) {
                textView.setText(R.string.undefined);

            }
        }


    }


    @BindingAdapter("notification")
    public static void notification(TextView textView, NotificationModel model) {
        if (model != null) {
            Context context = textView.getContext();
            String text = "";
            if (model.getOrder_id() != null && !model.getOrder_id().isEmpty()) {
                if (model.getBody().equals("new")) {
                    text = model.getUser_name() + "\n" + context.getString(R.string.sent_order) + " " + context.getString(R.string.order_num) + " #" + model.getOrder_id();
                } else {
                    text = model.getBody();

                }

                textView.setText(text);

            } else {
                textView.setText(model.getBody());

            }
        }

    }

    @BindingAdapter("createAtMsg")
    public static void dateCreateAtMsg(TextView textView, String s) {
        if (s != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            try {
                Date date = simpleDateFormat.parse(s);

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm aa", Locale.ENGLISH);
                dateFormat.setTimeZone(TimeZone.getDefault());
                String d = dateFormat.format(date);
                textView.setText(d);
            } catch (ParseException e) {
                e.printStackTrace();
            }


        }

    }

}










