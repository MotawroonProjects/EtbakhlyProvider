package com.etbakhly_provider.service_uploading_images;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ServiceUploadImages extends Service {
    private List<String> uris = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        uris = new ArrayList<>();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        uris = (List<String>) intent.getSerializableExtra("data");
        uploadImages();
        return START_NOT_STICKY;
    }

    private void uploadImages() {

        stopSelf();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
