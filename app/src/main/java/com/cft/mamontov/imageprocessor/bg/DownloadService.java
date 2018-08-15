package com.cft.mamontov.imageprocessor.bg;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.StrictMode;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.cft.mamontov.imageprocessor.data.Repository;
import com.cft.mamontov.imageprocessor.presentation.main.MainFragment;
import com.cft.mamontov.imageprocessor.utils.BitmapUtils;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import dagger.android.DaggerIntentService;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.Okio;
import retrofit2.Response;

public class DownloadService extends DaggerIntentService {

    @Inject
    Repository mData;

    private NotificationCompat.Builder notificationBuilder;
    private NotificationManager notificationManager;

    public DownloadService() {
        super("Service");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String url = intent.getStringExtra("Service");
        if (url.isEmpty()) return;
        initNotifications();
        initRetrofit(url);
    }

    private void initNotifications() {
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel("id", "an", NotificationManager.IMPORTANCE_LOW);
            notificationChannel.setDescription("no sound");
            notificationChannel.setSound(null, null);
            notificationChannel.enableLights(false);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.enableVibration(false);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        notificationBuilder = new NotificationCompat.Builder(this, "id")
                .setSmallIcon(android.R.drawable.stat_sys_download)
                .setContentTitle("Download")
                .setContentText("Downloading Image")
                .setDefaults(0)
                .setAutoCancel(true);
        notificationManager.notify(0, notificationBuilder.build());
    }

    private void initRetrofit(String url) {
        Log.e("Service", url);
        CompositeDisposable disposable = new CompositeDisposable();
        disposable.add(mData.getImageFromUrl(url)
                .subscribeOn(Schedulers.io())
                .flatMap((Response<ResponseBody> response) -> {
                    try {
                        ResponseBody body = response.body();
                        File file = BitmapUtils.createImageFile(this);
                        BufferedSink sink = Okio.buffer(Okio.sink(file));
                        assert body != null;
                        sink.writeAll(body.source());
                        sink.close();
                        return Observable.just(file);
                    } catch (IOException e) {
                        return Observable.error(e);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSuccess, this::onError));
    }

    private void onSuccess(File file) {
        Log.e("Service", "onSuccess");
        BitmapUtils.addPhotoToGallery(file.getAbsolutePath(), this);
        onDownloadComplete(true, file.getAbsolutePath());
    }

    private void onError(Throwable throwable) {
        Log.e("Throwable: ", throwable.getLocalizedMessage());
        throwable.printStackTrace();
    }

    private void updateNotification(int currentProgress) {
        notificationBuilder.setProgress(100, currentProgress, false);
        notificationBuilder.setContentText("Downloaded: " + currentProgress + "%");
        notificationManager.notify(0, notificationBuilder.build());
    }

    private void sendProgressUpdate(boolean downloadComplete, String file) {
        Intent intent = new Intent(MainFragment.PROGRESS_UPDATE);
        intent.putExtra("downloadComplete", downloadComplete);
        intent.putExtra("fileName", file);
        LocalBroadcastManager.getInstance(DownloadService.this).sendBroadcast(intent);
    }

    private void onDownloadComplete(boolean downloadComplete, String file) {
        sendProgressUpdate(downloadComplete, file);
        notificationManager.cancel(0);
        notificationBuilder.setProgress(0, 0, false);
        notificationBuilder.setContentText("Image Download Complete");
        notificationManager.notify(0, notificationBuilder.build());
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        notificationManager.cancel(0);
    }
}