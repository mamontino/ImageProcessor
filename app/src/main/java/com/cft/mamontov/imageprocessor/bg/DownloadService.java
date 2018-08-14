//package com.cft.mamontov.imageprocessor.bg;
//
//import android.app.IntentService;
//import android.app.NotificationManager;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Environment;
//import android.support.v4.app.NotificationCompat;
//import android.support.v4.content.LocalBroadcastManager;
//import android.widget.Toast;
//
//import com.cft.mamontov.imageprocessor.R;
//import com.cft.mamontov.imageprocessor.data.Repository;
//import com.cft.mamontov.imageprocessor.data.models.Download;
//import com.cft.mamontov.imageprocessor.presentation.main.MainFragment;
//
//import java.io.BufferedInputStream;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//
//import javax.inject.Inject;
//
//import io.reactivex.android.schedulers.AndroidSchedulers;
//import io.reactivex.disposables.CompositeDisposable;
//import io.reactivex.schedulers.Schedulers;
//import okhttp3.ResponseBody;
//import retrofit2.Response;
//
//public class DownloadService extends IntentService {
//
//    @Inject
//    Repository mRepository;
//
//    @Inject
//    public DownloadService() {
//        super("Download Service");
//    }
//
//    private NotificationCompat.Builder notificationBuilder;
//    private NotificationManager notificationManager;
//    private int totalFileSize;
//
//    @Override
//    protected void onHandleIntent(Intent intent) {
//        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationBuilder = new NotificationCompat.Builder(this, "Download Service")
//                .setSmallIcon(R.drawable.ic_file_download)
//                .setContentTitle("Download")
//                .setContentText("Downloading File")
//                .setAutoCancel(true);
//        notificationManager.notify(0, notificationBuilder.build());
//        initDownload();
//    }
//
//    private void initDownload() {
//        CompositeDisposable disposable = new CompositeDisposable();
//        disposable.add(mRepository.download()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(this::onSuccess, this::onError));
//    }
//
//    private void onError(Throwable throwable) {
//        Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
//    }
//
//    private void onSuccess(Response<ResponseBody> response) {
//        try {
//            saveFile(response.body());
//        } catch (IOException e) {
//            e.printStackTrace();
//            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private void saveFile(ResponseBody body) throws IOException {
//        int count;
//        byte data[] = new byte[1024 * 4];
//        long fileSize = body.contentLength();
//        InputStream bis = new BufferedInputStream(body.byteStream(), 1024 * 8);
//        File outputFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "file.zip");
//        OutputStream output = new FileOutputStream(outputFile);
//        long total = 0;
//        long startTime = System.currentTimeMillis();
//        int timeCount = 1;
//        while ((count = bis.read(data)) != -1) {
//
//            total += count;
//            totalFileSize = (int) (fileSize / (Math.pow(1024, 2)));
//            double current = Math.round(total / (Math.pow(1024, 2)));
//
//            int progress = (int) ((total * 100) / fileSize);
//
//            long currentTime = System.currentTimeMillis() - startTime;
//
//            Download download = new Download();
//            download.setTotalFileSize(totalFileSize);
//
//            if (currentTime > 1000 * timeCount) {
//
//                download.setCurrentFileSize((int) current);
//                download.setProgress(progress);
//                sendNotification(download);
//                timeCount++;
//            }
//            output.write(data, 0, count);
//        }
//        onDownloadComplete();
//        output.flush();
//        output.close();
//        bis.close();
//    }
//
//    private void sendNotification(Download download) {
//        sendIntent(download);
//        notificationBuilder.setProgress(100, download.getProgress(), false);
//        notificationBuilder.setContentText("Downloading file " + download.getCurrentFileSize() + "/" + totalFileSize + " MB");
//        notificationManager.notify(0, notificationBuilder.build());
//    }
//
//    private void sendIntent(Download download) {
//        Intent intent = new Intent(MainFragment.MESSAGE_PROGRESS);
//        intent.putExtra("download", download);
//        LocalBroadcastManager.getInstance(DownloadService.this).sendBroadcast(intent);
//    }
//
//    private void onDownloadComplete() {
//        Download download = new Download();
//        download.setProgress(100);
//        sendIntent(download);
//        notificationManager.cancel(0);
//        notificationBuilder.setProgress(0, 0, false);
//        notificationBuilder.setContentText("File Downloaded");
//        notificationManager.notify(0, notificationBuilder.build());
//
//    }
//
//    @Override
//    public void onTaskRemoved(Intent rootIntent) {
//        notificationManager.cancel(0);
//    }
//}