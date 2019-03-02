package com.home.workmanagerdemo;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class TestWorker extends Worker {

    public TestWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        displayNotification("鄧麗君", "本名鄧麗筠，臺灣著名流行音樂歌手。是臺灣至亞洲地區均有廣大影響力的指標性音樂人物。");
        return Result.success();
    }

    /**
     * 從Android 8.0(API 26)開始，所有的Notification都要指定頻道(通道), 否則通知是不會顯示的(系統會自動記錄錯誤)
     * Android 8.0及以上是使用NotificationManager.IMPORTANCE_*, Android 7.1及以下是使用NotificationCompat.PRIORITY_*, 它們都是定義的常量
     */
    private void displayNotification(String channelTitle, String channelContent) {
        NotificationManager manager = // 取得NotificationManager物件
                (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // 如果當前設備的系統版本號, 是Android8.0以上的話
            NotificationChannel channel = // 創建一個會發出通知聲音, 並且通知欄有通知的NotificationChannel對象
                    new NotificationChannel("channel", "channel", NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel); // 添加Channel
        }
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(getApplicationContext(), "channel") // 建立NotificationCompat.Builder物件, 並賦予所屬頻道Id
                .setContentTitle(channelTitle) // 設定內容標題
                .setContentText(channelContent) // 設定內容訊息
                .setSmallIcon(R.mipmap.ic_launcher); // 設定小圖示
        manager.notify(1, builder.build());
    }
}
