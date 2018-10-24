package tw.com.dean.istorybear;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

import static android.support.v4.app.ActivityCompat.requestPermissions;

public class dean {
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
