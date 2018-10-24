package tw.com.dean.istorybear;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


//import android.app.Fragment;

public class MainActivity extends AppCompatActivity {

    private Fragment_blog fragment_blog = new Fragment_blog();
    private Fragment_play fragment_play = new Fragment_play();
    private Fragment_story fragment_story = new Fragment_story();
    private Fragment_buy fragment_buy = new Fragment_buy();
    private Fragment_me fragment_me = new Fragment_me();
    private Fragment mContent;

    private PagerAdapter adapter;
    private LinearLayout container;
    private List<Fragment> viewPages = new ArrayList<>();

    private int BuyCheckItem = 0;
    private String[] items = new String[2];

    private Button xPlayBtn;
    public static LinearLayout xPlayer;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;

    //public static MediaPlayer mediaPlayer;


    public static int lastPosition = 0;


    // private Fragment[] fragments;
    // private int lastShowFragment = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MenuItem item;
        boolean logon = false;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        xPlayBtn = findViewById(R.id.playpauseBtn);
        xPlayer = (LinearLayout) findViewById(R.id.storyPlayerBar);

        mContent = fragment_blog;
        if (!logon) { //如果未登入, 則開啟LoginActivity
            Intent intent = new Intent();
            //將原本Activity的換成LoginActivity
            intent.setClass(this, LoginActivity.class);
            startActivity(intent);
        }
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setSelectedItemId(navigation.getMenu().getItem(2).getItemId()); // 預選故事頁

        /**
         * 动态获取权限，Android 6.0 新特性，一些保护权限，除了要在AndroidManifest中声明权限，还要使用如下代码动态获取
         **/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { // 如果裝置版本是6.0（包含）以上
            // 取得授權狀態，參數是請求授權的名稱
            int hasPermission = checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE);

            if (hasPermission != PackageManager.PERMISSION_GRANTED) { // 如果未授權
                // 請求授權
                //     第一個參數是請求授權的名稱
                //     第二個參數是請求代碼,自訂，requestCode，主要用于回调的时候检测。
                //      可以从方法名 requestPermissions 以及第二个参数看出，是支持一次性申请多个权限的，系统会通过对话框 逐一 询问用户是否授权。
                //      設定的requestCode必須在0~255之間，不然就會得到錯誤
                requestPermissions( new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
            }
        }

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            //点击BottomNavigationView的Item项，切换ViewPager页面
            //menu/navigation.xml里加的android:orderInCategory属性就是下面item.getOrder()取的值
            //false＝為防止隔頁切換時,滑過中間頁面的問題,去除頁面切換緩慢滑動的動畫效果
            //viewPager.setCurrentItem(item.getOrder(), false);
            switch (item.getItemId()) {
                case R.id.navigation_blog:
                    Toast.makeText(MainActivity.this, "切到專欄", Toast.LENGTH_SHORT).show();
                    switchContent(fragment_blog);
                    return true;
                case R.id.navigation_play:
                    Toast.makeText(MainActivity.this, "切到哪玩", Toast.LENGTH_SHORT).show();
                    switchContent(fragment_play);

                    return true;
                case R.id.navigation_story:
                    Toast.makeText(MainActivity.this, "切到故事", Toast.LENGTH_SHORT).show();
                    switchContent(fragment_story);
                    return true;
                case R.id.navigation_buy:
                    Toast.makeText(MainActivity.this, "切到購物", Toast.LENGTH_SHORT).show();
                    switchContent(fragment_buy);
                    return true;
                case R.id.navigation_me:
                    Toast.makeText(MainActivity.this, "切到會員", Toast.LENGTH_SHORT).show();
                    switchContent(fragment_me);
                    return true;
            }
            return false;
        }

    };

    private void switchContent(Fragment to) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (mContent != to) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            if (!to.isAdded()) { // 判断是否被add过
                // 隐藏当前的fragment，将 下一个fragment 添加进去
                transaction.hide(mContent).add(R.id.fragment_container, to).commit();
            } else {
                // 隐藏当前的fragment，显示下一个fragment
                transaction.hide(mContent).show(to).commit();
            }
            mContent = to;
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {//捕捉返回鍵
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            ConfirmExit();//按返回鍵，則執行退出確認
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void ConfirmExit() {//退出確認
        AlertDialog.Builder ad = new AlertDialog.Builder(MainActivity.this);
        ad.setTitle("離開");
        ad.setMessage("確定要離開此程式嗎?");
        ad.setPositiveButton("是", new DialogInterface.OnClickListener() {//退出按鈕
            public void onClick(DialogInterface dialog, int i) {
                // TODO Auto-generated method stub
                // MainActivity.this.finish();//關閉activity
                System.exit(0);
            }
        });
        ad.setNegativeButton("否", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int i) {
                //不退出不用執行任何操作
            }
        });
        ad.show();//顯示對話框
    }

    /**
     * public boolean onKeyDown(int keyCode, KeyEvent event) {
     * if ((keyCode == KEYCODE_BACK) && mWebView.canGoBack()) {
     * mWebView.goBack();
     * return true;
     * }
     * return super.onKeyDown(keyCode, event);
     * }
     **/

    public void toStoryPlayerBtnClk(View v) {
        // String vtag = v.getTag().toString();

        Intent i = new Intent(MainActivity.this, StoryPlayerActivity.class);
        //把按下的Btn tag傳給StoryPlayerActivity
        //  i.putExtra("data", vtag);
        //將原本Activity的換成StoryPlayerActivity
        startActivity(i);
    }

    public void SearchBtnClk(View v) {
        String vtag = v.getTag().toString();

        Intent i = new Intent(MainActivity.this, SearchActivity.class);
        //把按下的Btn tag傳給SearchActivity
        i.putExtra("data", vtag);
        //將原本Activity的換成SearchActivity
        startActivity(i);
    }

    public void ActlistBtnClk(View v) {
        String vtag = v.getTag().toString();

        Intent i = new Intent(MainActivity.this, ResultActivity.class);
        //把按下的Btn tag傳給ResultActivity
        i.putExtra("data", vtag);
        //將原本Activity的換成ResultActivity
        startActivity(i);
    }

    public void myHomeBtnClk(View v) {
        String vtag = v.getTag().toString();
        Intent i = new Intent(MainActivity.this, userHomeActivity.class);
        //把按下的Btn tag傳給userHomeActivity
        i.putExtra("data", vtag);
        //將原本Activity的換成userHomeActivity
        startActivity(i);
    }

    public void noticeBtnClk(View v) {
        String vtag = v.getTag().toString();
        Intent i = new Intent(MainActivity.this, noticeActivity.class);
        //把按下的Btn tag傳給noticeActivity
        i.putExtra("data", vtag);
        //將原本Activity的換成noticeActivity
        startActivity(i);
    }

    public void ActItemBtnClk(View v) {

        String vtag = v.getTag().toString();

        switch (vtag) {
            case "Logout":
                ConfirmExit();//按返回鍵，則執行退出確認

                break;

            case "buyPoints":
                setDialogBroadcast(vtag);

                break;

            case "toCash":
                setDialogBroadcast(vtag);

                break;

            case "toPoints":
                setDialogBroadcast(vtag);

                break;

            default:
                Intent i = new Intent(MainActivity.this, ActActivity.class);
                //把按下的Btn tag傳給ActActivity
                i.putExtra("data", vtag);
                //將原本Activity的換成ActActivity
                startActivity(i);

        }


    }

    public void xPlayerBtnClk(View v) {
        String vtag = v.getTag().toString();
     //   mediaPlayer = StoryPlayerActivity.mediaPlayer;

        switch (vtag) {

            case "backBtn":
                Toast.makeText(MainActivity.this, R.string.playback, Toast.LENGTH_SHORT).show();

                break;

            case "playBtn":
                if (xPlayBtn.isSelected()) { /* 暫停播放 */
                    xPlayBtn.setSelected(false);
                    StoryPlayerActivity.mediaPlayer.pause();
                    Toast.makeText(MainActivity.this, R.string.paused, Toast.LENGTH_SHORT).show();

                } else { /* 開始播放 */
                    xPlayBtn.setSelected(true);
                    StoryPlayerActivity.mediaPlayer.start();
                    Toast.makeText(MainActivity.this, R.string.playing, Toast.LENGTH_SHORT).show();
                }
                break;

            case "nextBtn":
                Toast.makeText(MainActivity.this, R.string.playnext, Toast.LENGTH_SHORT).show();

                break;

            case "killPlayer":
                Toast.makeText(MainActivity.this, R.string.killPlaying, Toast.LENGTH_SHORT).show();
                xPlayBtn.setSelected(false); /* 暫停播放 */
                xPlayer.setVisibility(View.GONE);
                StoryPlayerActivity.mediaPlayer.pause();
               // StoryPlayerActivity.mediaPlayer.release();
              //  StoryPlayerActivity.mediaPlayer = null;
                break;


        }
    }

    private void setDialogBroadcast(String vtag) {
        String mTitle = null;
        BuyCheckItem = 0;
        switch (vtag) {
            case "buyPoints":
                mTitle = getString(R.string.buyPoints);
                items[0] = getString(R.string.buy100points);
                items[1] = getString(R.string.buy550points);
                break;
            case "toPoints":
                mTitle = getString(R.string.toPoints);
                items[0] = getString(R.string.to120Points);
                items[1] = getString(R.string.to600Points);
                break;

            case "toCash":
                mTitle = getString(R.string.toCash);
                items[0] = getString(R.string.to1000cash);
                items[1] = getString(R.string.to5000cash);
                break;
        }

        new AlertDialog.Builder(MainActivity.this)
                //    .setView(viewDialogBroadcast)
                .setTitle(mTitle)
                .setIcon(R.drawable.ic_logo_round_colors_24dp)
                .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        BuyCheckItem = which;

                    }
                })
                .setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, items[BuyCheckItem], Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })

                .create().show();

    }
}

/**
 * @Override public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
 * <p>
 * }
 * @Override public void onPageSelected(int position) {
 * //页面滑动的时候，改变BottomNavigationView的Item高亮
 * navigation.getMenu().getItem(position).setChecked(true);
 * }
 * @Override public void onPageScrollStateChanged(int state) {
 * <p>
 * }
 * <p>
 * 切換Fragment
 * @param lastIndex 上個顯示Fragment的索引
 * @param index     需要顯示的Fragment的索引
 * <p>
 * private void initFragments() {
 * fragment_blog = new Fragment_blog();
 * fragment_play = new Fragment_play();
 * fragment_story = new Fragment_story();
 * fragment_buy = new Fragment_buy();
 * fragment_me = new Fragment_me();
 * <p>
 * fragments = new Fragment[]{(Fragment)fragment_blog, (Fragment)fragment_play, (Fragment)fragment_story,(Fragment)fragment_buy,(Fragment)fragment_me};
 * lastShowFragment = 2;
 * getSupportFragmentManager()
 * .beginTransaction()
 * .add(R.id.fragment_container, fragment_story)
 * .show(fragment_story)
 * .commit();
 * <p>
 * }
 * <p>
 * 切換Fragment
 * @param lastIndex 上個顯示Fragment的索引
 * @param index     需要顯示的Fragment的索引
 * <p>
 * private void initFragments() {
 * fragment_blog = new Fragment_blog();
 * fragment_play = new Fragment_play();
 * fragment_story = new Fragment_story();
 * fragment_buy = new Fragment_buy();
 * fragment_me = new Fragment_me();
 * <p>
 * fragments = new Fragment[]{(Fragment)fragment_blog, (Fragment)fragment_play, (Fragment)fragment_story,(Fragment)fragment_buy,(Fragment)fragment_me};
 * lastShowFragment = 2;
 * getSupportFragmentManager()
 * .beginTransaction()
 * .add(R.id.fragment_container, fragment_story)
 * .show(fragment_story)
 * .commit();
 * <p>
 * }
 * <p>
 * 切換Fragment
 * @param lastIndex 上個顯示Fragment的索引
 * @param index     需要顯示的Fragment的索引
 * <p>
 * private void initFragments() {
 * fragment_blog = new Fragment_blog();
 * fragment_play = new Fragment_play();
 * fragment_story = new Fragment_story();
 * fragment_buy = new Fragment_buy();
 * fragment_me = new Fragment_me();
 * <p>
 * fragments = new Fragment[]{(Fragment)fragment_blog, (Fragment)fragment_play, (Fragment)fragment_story,(Fragment)fragment_buy,(Fragment)fragment_me};
 * lastShowFragment = 2;
 * getSupportFragmentManager()
 * .beginTransaction()
 * .add(R.id.fragment_container, fragment_story)
 * .show(fragment_story)
 * .commit();
 * <p>
 * }
 **/
/**
 * 切換Fragment
 *
 * @param lastIndex 上個顯示Fragment的索引
 * @param index     需要顯示的Fragment的索引
 */

/**
 private void initFragments() {
 fragment_blog = new Fragment_blog();
 fragment_play = new Fragment_play();
 fragment_story = new Fragment_story();
 fragment_buy = new Fragment_buy();
 fragment_me = new Fragment_me();

 fragments = new Fragment[]{(Fragment)fragment_blog, (Fragment)fragment_play, (Fragment)fragment_story,(Fragment)fragment_buy,(Fragment)fragment_me};
 lastShowFragment = 2;
 getSupportFragmentManager()
 .beginTransaction()
 .add(R.id.fragment_container, fragment_story)
 .show(fragment_story)
 .commit();

 }
 **/
