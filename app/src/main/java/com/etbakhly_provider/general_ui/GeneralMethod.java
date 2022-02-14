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

import com.etbakhly_provider.tags.Tags;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

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
    public static void orderStatus(Button btnStatus, String status) {
//        Log.e("ssss",status);
        if (status.equals("approval")) {
            btnStatus.setText(R.string.prepared);
        } else if (status.equals("making")) {
            btnStatus.setText(R.string.delivery_in_progress);

        }

    }

    @BindingAdapter("image3")
    public static void image3(View view, String imageUrl) {
        if (imageUrl != null) {
            String imageUrl1 = imageUrl;

            view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    view.getViewTreeObserver().removeOnGlobalLayoutListener(this);


                    if (view instanceof CircleImageView) {
                        CircleImageView imageView = (CircleImageView) view;
                        if (imageUrl1 != null) {
                            RequestOptions options = new RequestOptions().override(view.getWidth(), view.getHeight());
                            Glide.with(view.getContext()).asBitmap()
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .load(imageUrl1)
                                    //.centerCrop()
                                    .apply(options)
                                    .into(imageView);
                        }
                    } else if (view instanceof RoundedImageView) {
                        RoundedImageView imageView = (RoundedImageView) view;

                        if (imageUrl1 != null) {

                            RequestOptions options = new RequestOptions().override(view.getWidth(), view.getHeight());
                            Glide.with(view.getContext()).asBitmap()
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .load(imageUrl1)
                                    //.centerCrop()
                                    .apply(options)
                                    .into(imageView);

                        }
                    } else if (view instanceof ImageView) {
                        ImageView imageView = (ImageView) view;

                        if (imageUrl1 != null) {

                            RequestOptions options = new RequestOptions().override(view.getWidth(), view.getHeight());
                            Glide.with(view.getContext()).asBitmap()
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .load(imageUrl1)
                                    //.centerCrop()
                                    .apply(options)
                                    .into(imageView);
                        }
                    }

                }
            });
        }

    }

    @BindingAdapter("image2")
    public static void image2(View view, String imageUrl) {
        if (imageUrl != null) {
            String imageUrl1 = Tags.IMAGE_Maincategory_URL + imageUrl;

            view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    view.getViewTreeObserver().removeOnGlobalLayoutListener(this);


                    if (view instanceof CircleImageView) {
                        CircleImageView imageView = (CircleImageView) view;
                        if (imageUrl1 != null) {
                            RequestOptions options = new RequestOptions().override(view.getWidth(), view.getHeight());
                            Glide.with(view.getContext()).asBitmap()
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .load(imageUrl1)
                                    //.centerCrop()
                                    .apply(options)
                                    .into(imageView);
                        }
                    } else if (view instanceof RoundedImageView) {
                        RoundedImageView imageView = (RoundedImageView) view;

                        if (imageUrl1 != null) {

                            RequestOptions options = new RequestOptions().override(view.getWidth(), view.getHeight());
                            Glide.with(view.getContext()).asBitmap()
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .load(imageUrl1)
                                    //.centerCrop()
                                    .apply(options)
                                    .into(imageView);

                        }
                    } else if (view instanceof ImageView) {
                        ImageView imageView = (ImageView) view;

                        if (imageUrl1 != null) {

                            RequestOptions options = new RequestOptions().override(view.getWidth(), view.getHeight());
                            Glide.with(view.getContext()).asBitmap()
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .load(imageUrl1)
                                    //.centerCrop()
                                    .apply(options)
                                    .into(imageView);
                        }
                    }

                }
            });
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
                        if (imageUrl1 != null) {
                            RequestOptions options = new RequestOptions().override(view.getWidth(), view.getHeight());
                            Glide.with(view.getContext()).asBitmap()
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .load(imageUrl1)
                                    //.centerCrop()
                                    .apply(options)
                                    .into(imageView);
                        }
                    } else if (view instanceof RoundedImageView) {
                        RoundedImageView imageView = (RoundedImageView) view;

                        if (imageUrl1 != null) {

                            RequestOptions options = new RequestOptions().override(view.getWidth(), view.getHeight());
                            Glide.with(view.getContext()).asBitmap()
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .load(imageUrl1)
                                    //.centerCrop()
                                    .apply(options)
                                    .into(imageView);

                        }
                    } else if (view instanceof ImageView) {
                        ImageView imageView = (ImageView) view;

                        if (imageUrl1 != null) {

                            RequestOptions options = new RequestOptions().override(view.getWidth(), view.getHeight());
                            Glide.with(view.getContext()).asBitmap()
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .load(imageUrl1)
                                    //.centerCrop()
                                    .apply(options)
                                    .into(imageView);
                        }
                    }

                }
            });
        }

    }

    @BindingAdapter("user_image")
    public static void user_image(View view, String imageUrl) {

//
//        if (view instanceof CircleImageView) {
//            CircleImageView imageView = (CircleImageView) view;
//            if (imageUrl != null) {
//
//                Glide.with(view.getContext()).asBitmap()
//                        .diskCacheStrategy(DiskCacheStrategy.ALL)
//                        .placeholder(R.drawable.circle_avatar)
//                        .load(imageUrl)
//                        .centerCrop()
//                        .into(imageView);
//
//            }
//        } else if (view instanceof RoundedImageView) {
//            RoundedImageView imageView = (RoundedImageView) view;
//
//            if (imageUrl != null) {
//
//                Glide.with(view.getContext()).asBitmap()
//                        .diskCacheStrategy(DiskCacheStrategy.ALL)
//                        .placeholder(R.drawable.circle_avatar)
//                        .load(imageUrl)
//                        .centerCrop()
//                        .into(imageView);
//
//            }
//        } else if (view instanceof ImageView) {
//            ImageView imageView = (ImageView) view;
//
//            if (imageUrl != null) {
//
//                Glide.with(view.getContext()).asBitmap()
//                        .diskCacheStrategy(DiskCacheStrategy.ALL)
//                        .placeholder(R.drawable.circle_avatar)
//                        .load(imageUrl)
//                        .centerCrop()
//                        .into(imageView);
//            }
//        }

    }

    @BindingAdapter("qr_image")
    public static void qr_image(View view, String imageUrl) {

        if (view instanceof CircleImageView) {
            CircleImageView imageView = (CircleImageView) view;
            if (imageUrl != null) {
                RequestOptions options = new RequestOptions();
                Glide.with(view.getContext()).asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .load(imageUrl)
                        .apply(options)
                        .into(imageView);
            }
        } else if (view instanceof RoundedImageView) {
            RoundedImageView imageView = (RoundedImageView) view;

            if (imageUrl != null) {

                RequestOptions options = new RequestOptions();
                Glide.with(view.getContext()).asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .load(imageUrl)
                        .apply(options)
                        .into(imageView);

            }
        } else if (view instanceof ImageView) {
            ImageView imageView = (ImageView) view;

            if (imageUrl != null) {

                RequestOptions options = new RequestOptions();
                Glide.with(view.getContext()).asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .load(imageUrl)
                        .apply(options)
                        .into(imageView);
            }
        }


    }

    @BindingAdapter("departmentImage")
    public static void department_image(View view, String imageUrl) {
        if (view instanceof CircleImageView) {
            CircleImageView imageView = (CircleImageView) view;
            if (imageUrl != null && !imageUrl.isEmpty()) {
                Picasso.get().load(Uri.parse(imageUrl)).into(imageView);

            } else {
            }

        } else if (view instanceof RoundedImageView) {
            RoundedImageView imageView = (RoundedImageView) view;

            if (imageUrl != null && !imageUrl.isEmpty()) {
                Picasso.get().load(Uri.parse(imageUrl)).into(imageView);

            } else {
            }
        } else if (view instanceof ImageView) {
            ImageView imageView = (ImageView) view;

            if (imageUrl != null && !imageUrl.isEmpty()) {
                Picasso.get().load(Uri.parse(imageUrl)).into(imageView);

            } else {
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


}










