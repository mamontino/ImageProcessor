package com.cft.mamontov.imageprocessor.bg;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import com.cft.mamontov.imageprocessor.R;
import com.cft.mamontov.imageprocessor.data.Repository;
import com.cft.mamontov.imageprocessor.presentation.main.MainFragment;
import com.cft.mamontov.imageprocessor.utils.BitmapUtils;
import com.cft.mamontov.imageprocessor.utils.schedulers.BaseSchedulerProvider;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import dagger.android.DaggerIntentService;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.Okio;
import retrofit2.Response;

public class LoadingService extends DaggerIntentService {

    public static final String TAG = "LoadingService";

    public static final String NOTIFICATION_CHANEL = "com.cft.mamontov.imageprocessor.NOTIFICATION_CHANEL";
    public static final String NOTIFICATION_TITLE = "Download";
    public static final String NOTIFICATION_DESC = "Download image";

    public static final String EXTRA_SERVICE_URL = "EXTRA_SERVICE_URL";
    public static final String EXTRA_SERVICE_COMPLETE = "EXTRA_SERVICE_COMPLETE";
    public static final String EXTRA_SERVICE_FILE_NAME = "EXTRA_SERVICE_FILE_NAME";

    @Inject
    Repository mData;
    @Inject
    CompositeDisposable mDisposable;
    @Inject
    BaseSchedulerProvider mScheduler;

    private NotificationCompat.Builder mBuilder;
    private NotificationManager mManager;

    public LoadingService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String url = intent.getStringExtra(EXTRA_SERVICE_URL);
        if (url.isEmpty()) return;
        prepareNotifications();
        downloadImage(url);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        mManager.cancel(0);
    }

    private void prepareNotifications() {

        mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(
                    NOTIFICATION_CHANEL, NOTIFICATION_TITLE, NotificationManager.IMPORTANCE_LOW);
            notificationChannel.setDescription(NOTIFICATION_DESC);
            notificationChannel.setSound(null, null);
            notificationChannel.enableLights(false);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.enableVibration(false);
            mManager.createNotificationChannel(notificationChannel);
        }

        mBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANEL)
                .setSmallIcon(android.R.drawable.stat_sys_download)
                .setContentTitle(NOTIFICATION_TITLE)
                .setContentText(NOTIFICATION_DESC)
                .setDefaults(0)
                .setAutoCancel(true);
        mManager.notify(0, mBuilder.build());
    }

    private void downloadImage(String url) {
        mDisposable.add(mData.getImageFromUrl(url)
                .subscribeOn(mScheduler.io())
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
                .observeOn(mScheduler.ui())
                .subscribe(this::onSuccess, this::onError));
    }

    private void onSuccess(File file) {
        BitmapUtils.addPhotoToGallery(file.getAbsolutePath(), this);
        onDownloadComplete(file.getAbsolutePath());
    }

    private void onError(Throwable throwable) {
        Toast.makeText(this, throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }

    private void updateNotification(int currentProgress) {
        mBuilder.setProgress(100, currentProgress, false);
        mBuilder.setContentText("Downloaded: " + currentProgress + "%");
        mManager.notify(0, mBuilder.build());
    }

    private void sendProgressUpdate(boolean downloadComplete, String file) {
        Intent intent = new Intent(MainFragment.PROGRESS_UPDATE);
        intent.putExtra(EXTRA_SERVICE_COMPLETE, downloadComplete);
        intent.putExtra(EXTRA_SERVICE_FILE_NAME, file);
        LocalBroadcastManager.getInstance(LoadingService.this).sendBroadcast(intent);
    }

    private void onDownloadComplete(String file) {
        sendProgressUpdate(true, file);
        mManager.cancel(0);
        mBuilder.setProgress(0, 0, false);
        mBuilder.setContentText(getResources().getString(R.string.success_file_download_complete));
        mManager.notify(0, mBuilder.build());
    }
}