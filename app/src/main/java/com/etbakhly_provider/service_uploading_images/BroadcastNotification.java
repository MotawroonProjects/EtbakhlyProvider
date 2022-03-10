package com.etbakhly_provider.service_uploading_images;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BroadcastNotification extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent!=null){
            Intent cancelIntent = new Intent(context,ServiceUploadImages.class);
            context.stopService(cancelIntent);

        }

    }
}
