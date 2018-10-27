package tw.com.dean.istorybear;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.v4.content.ContextCompat;

import static android.support.v4.app.ActivityCompat.requestPermissions;

public class StoryPlayer {
    /**
     * 取得權限(for Android 6.0)
     *
     * @param activity
     * @param permission
     * @param permissionCode
     */
    public static void getPermission(Activity activity, String permission, int permissionCode) {

        if (ContextCompat.checkSelfPermission(activity.getApplicationContext(), permission)  // 取得授權狀態，參數是請求授權的名稱
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(activity, new String[]{permission}, permissionCode);

        }
    }

}
/*
public class activity_play extends Service {

    public void onCreate() {
        super.onCreate();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        someTask(intent.getAction());
        return super.onStartCommand(intent, flags, startId);
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    void someTask(String cmd) {
        final String fcmd = cmd;
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                if(fcmd.equals("playPause")) StoryPlayerActivity.mediaPlayer.start();
                if(fcmd.equals("next")) MainActivity.nextbtn.performClick();
                if(fcmd.equals("prev")) MainActivity.prevbtn.performClick();
            }
        });
    }
}
*/