package com.etbakhly_provider.share;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationManagerCompat;
import androidx.multidex.MultiDexApplication;

import com.etbakhly_provider.language.Language;


public class App extends MultiDexApplication {
    public static final String channel_id_image = "1zxsw12";
    public static final String channel_name_image = "uploading photos";

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(Language.updateResources(newBase, "ar"));
    }


    @Override
    public void onCreate() {
        super.onCreate();
        TypefaceUtil.setDefaultFont(this, "DEFAULT", "fonts/ar_font.ttf");
        TypefaceUtil.setDefaultFont(this, "MONOSPACE", "fonts/ar_font.ttf");
        TypefaceUtil.setDefaultFont(this, "SERIF", "fonts/ar_font.ttf");
        TypefaceUtil.setDefaultFont(this, "SANS_SERIF", "fonts/ar_font.ttf");
        createChannelForUploadingImages();
    }


    private void createChannelForUploadingImages() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            NotificationChannel channel = new NotificationChannel(channel_id_image, channel_name_image, NotificationManager.IMPORTANCE_LOW);
            channel.setDescription("channel uploading images");
            manager.createNotificationChannel(channel);
        }
    }
}

