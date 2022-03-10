package com.etbakhly_provider.service_uploading_images;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.etbakhly_provider.R;
import com.etbakhly_provider.model.NotResponse;
import com.etbakhly_provider.model.StatusResponse;
import com.etbakhly_provider.model.UserModel;
import com.etbakhly_provider.preferences.Preferences;
import com.etbakhly_provider.remote.Api;
import com.etbakhly_provider.share.App;
import com.etbakhly_provider.share.Common;
import com.etbakhly_provider.tags.Tags;


import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

public class ServiceUploadImages extends Service {
    private List<String> uris = new ArrayList<>();
    private Preferences preferences;
    private UserModel userModel;
    private CompositeDisposable disposable = new CompositeDisposable();

    @Override
    public void onCreate() {
        super.onCreate();
        uris = new ArrayList<>();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        uris = (List<String>) intent.getSerializableExtra("data");
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(this);
        uploadImages();
        return START_NOT_STICKY;
    }

    private void uploadImages() {
        createNotification(false, getString(R.string.uploading));

        RequestBody caterer_id = Common.getRequestBodyText(userModel.getData().getCaterer().getId());
        List<MultipartBody.Part> parts = new ArrayList<>();
        for (String uri : uris) {
            MultipartBody.Part part = Common.getMultiPart(this, Uri.parse(uri), "photo[]");
            parts.add(part);
        }
        Api.getService(Tags.base_url).uploadGalleryImages(caterer_id, parts)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<StatusResponse>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<StatusResponse> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatus() == 200) {
                                EventBus.getDefault().post(new NotResponse(true));
                                stopSelf();
                            }
                        } else {
                            createNotification(true, "Failed");
                            try {
                                Log.e("error", response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        createNotification(true, getString(R.string.failed));
                        Log.e("error", e.getMessage());
                    }
                });
    }

    private void createNotification(boolean isFinished, String msg) {
        int notification_id = 19800;
        Intent cancelIntent = new Intent(this, BroadcastNotification.class);
        cancelIntent.putExtra("notification_id", notification_id);
        PendingIntent pendingIntentDismiss = PendingIntent.getBroadcast(this, 0, cancelIntent, PendingIntent.FLAG_CANCEL_CURRENT);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, App.channel_id_image);
        builder.setAutoCancel(true);
        builder.setOngoing(true);
        builder.setOnlyAlertOnce(true);
        builder.setContentTitle(getString(R.string.upload));

        builder.addAction(R.drawable.ic_close, getString(R.string.dismiss), pendingIntentDismiss);
        if (!isFinished) {
            builder.setContentText(msg);
            builder.setProgress(100, 100, true);

        } else {

            builder.setContentText(msg);

        }
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setSmallIcon(R.drawable.ic_dish);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (manager != null) {
            manager.notify(notification_id, builder.build());
        }

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("service","destroyed");
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager != null) {
            manager.cancel(19800);
        }

    }
}
