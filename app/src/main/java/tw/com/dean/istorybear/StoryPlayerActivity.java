package tw.com.dean.istorybear;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.media.session.MediaButtonReceiver;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.media.app.NotificationCompat.MediaStyle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import tw.com.dean.istorybear.storyplayer.story404pjFragment;
import tw.com.dean.istorybear.storyplayer.storyOverviewFragment;
import tw.com.dean.istorybear.storyplayer.storyPlaylistFragment;
import tw.com.dean.istorybear.storyplayer.storySponsorFragment;


public class StoryPlayerActivity extends AppCompatActivity {
    private Button mBackBtn, mNextBtn, mHeartBtn, mStoryListBtn;
    public static LinearLayout xPlayer;
    public static Button mPlayBtn, barPlaypauseBtn, mPlayAllBtn;
    private Fragment homeContent, tempContent;
    private Fragment[] storypage = new Fragment[4];
    private Toolbar sToolbar;
    private RatingBar sRatingBar;
    private TabLayout playerTablayout;
    private SeekBar mseekBar;

    public static TextView mplayMin, mtotalTime, story_name, recording_Name, barStoryName;

    private Boolean isplaying = false;
    private static final int UPDATE_TIME = 2;
    public static final int mId = 1;

    public static MediaPlayer mediaPlayer;
    private static final String CHANNEL_ID = "故事播放控制";

    public static NotificationManager mNotificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = getIntent();
        String cmd = i.getAction();
        // String ActTag = i.getStringExtra("ActBtn");
        if (cmd.equals("new")) {
            setContentView(R.layout.activity_story_player);
            sToolbar = (Toolbar) findViewById(R.id.sPlayerBar);
            sRatingBar = findViewById(R.id.ratingBar);
            mHeartBtn = findViewById(R.id.heartButton);
            playerTablayout = (TabLayout) findViewById(R.id.storyplayerTabs);
            mseekBar = (SeekBar) findViewById(R.id.seekBar);
            mplayMin = (TextView) findViewById(R.id.playMin);
            mtotalTime = (TextView) findViewById(R.id.totalTime);
            story_name = (TextView) findViewById(R.id.storyName);
            recording_Name = (TextView) findViewById(R.id.recordingName);
            mPlayBtn = findViewById(R.id.playBtn);

            mPlayAllBtn = findViewById(R.id.playAllBtn);
            mBackBtn = findViewById(R.id.backBtn);
            mNextBtn = findViewById(R.id.nextBtn);
            mStoryListBtn = findViewById(R.id.storyListBtn);

            xPlayer = MainActivity.xPlayer;
            barStoryName = xPlayer.findViewById(R.id.playStoryName);
            barPlaypauseBtn = xPlayer.findViewById(R.id.playpauseBtn);

            storypage[0] = storyOverviewFragment.newInstance();
            storypage[1] = story404pjFragment.newInstance();
            storypage[2] = storySponsorFragment.newInstance();
            storypage[3] = storyPlaylistFragment.newInstance();

            // sToolbar.setTitle("本故事由遠傳電信獨家贊助");
            setSupportActionBar(sToolbar);

            if (homeContent == null) {  //預設在故事介紹頁
                switchSubContent(storypage[0]);
            }

            if (barPlaypauseBtn.isSelected()) { // 與MainActivity.xPlayer狀態同步
                mPlayBtn.setSelected(true);  /* 播放 */
            } else {
                mPlayBtn.setSelected(false); /* 暫停 */
            }
/**
 new Thread(new Runnable() { //初始化播放暂停键
 @Override public void run() {
 Message message = new Message();
 message.what = 1;
 handler.sendMessage(message);
 }
 }).start();
 **/
            if (mediaPlayer == null) {
                initMediaPlayer();
            } else {
                conMediaPlayer();
            }

            initListener();

            //  if (isplaying) {
            mediaPlayerNotific();
            //   }
        } else if (mediaPlayer != null) {
          //  Toast.makeText(StoryPlayerActivity.this,
          //          cmd, Toast.LENGTH_LONG).show();
            switch (cmd) {
                case "cancelBtn":
                    mediaPlayerNotific();  //先重送，後面取消才部會因null閃退
                    mNotificationManager.cancel(mId);
                    StoryPlayerActivity.mediaPlayer.pause();
                    StoryPlayerActivity.mPlayBtn.setSelected(false);
                    barPlaypauseBtn.setSelected(false); // 將MainActivity.xPlayer狀態同步
                    break;
                case "playBtn":
                    if (mPlayBtn.isSelected()) { /* 暫停播放 */
                        StoryPlayerActivity.mediaPlayer.pause();
                        StoryPlayerActivity.mPlayBtn.setSelected(false);
                        barPlaypauseBtn.setSelected(false); // 將MainActivity.xPlayer狀態同步
                        // xPlayer.setVisibility(View.GONE);
                        Toast.makeText(StoryPlayerActivity.this, R.string.paused, Toast.LENGTH_SHORT).show();
                    } else { /* 開始播放 */
                        StoryPlayerActivity.mediaPlayer.start();
                        StoryPlayerActivity.mPlayBtn.setSelected(true);
                        // xPlayer.setVisibility(View.VISIBLE);
                        barPlaypauseBtn.setSelected(true);
                        Toast.makeText(StoryPlayerActivity.this, R.string.playing, Toast.LENGTH_SHORT).show();
                    }
                    mediaPlayerNotific();
                    break;
                case "nextBtn":
                    Toast.makeText(StoryPlayerActivity.this, R.string.playnext, Toast.LENGTH_SHORT).show();

                    break;
                case "new":
                    break;
            }
            StoryPlayerActivity.this.finish(); //更新按鍵狀態後就關掉
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!mediaPlayer.isPlaying()) {
            stop();
        }
    }
    /**
     * 停止並釋放mediaPlayer
     **/
    public void stop() {  //停止並釋放mediaPlayer
        if (mediaPlayer != null) {
            //  mediaPlayer.stop();
            //mediaPlayer.reset();
            //mediaPlayer.release();
            //mediaPlayer=null;
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationManager
                mNotificationManager =
                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        CharSequence name = "故事播放控制";
        // 用户看到的渠道描述
        String description = "用來背景控制故事播放";
        int importance = NotificationManager.IMPORTANCE_LOW;
        NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance); // 用户看到的渠道名字
        // 渠道的配置
        mChannel.setDescription(description);
        mChannel.setShowBadge(false);
        mChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        mNotificationManager.createNotificationChannel(mChannel);
    }


    private void mediaPlayerNotific() {
        int playpause;

        // 你只需要在 API 26 以上的版本创建渠道
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }

        if (mPlayBtn.isSelected()) {
            playpause = R.drawable.ic_pause_black_24dp;
        } else { /* 開始播放 */
            playpause = R.drawable.ic_play_black_24dp;
        }

        Intent intent = new Intent(this, StoryPlayerActivity.class);

        intent.setAction("cancelBtn");
        // intent.putExtra("ActBtn", "cancelBtn");
        PendingIntent cancelIntent = PendingIntent.getActivity(this, 1, intent, 0);
        // PendingIntent pauseIntent = PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        intent.setAction("playBtn");
        //  intent.putExtra("ActBtn", "playBtn");
        PendingIntent playIntent = PendingIntent.getActivity(this, 2, intent, 0);
        //PendingIntent playIntent = PendingIntent.getBroadcast(this, 2, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        intent.setAction("nextBtn");
        //  intent.putExtra("ActBtn", "nextBtn");
        PendingIntent nextIntent = PendingIntent.getActivity(this, 3, intent, 0);
        // PendingIntent nextIntent = PendingIntent.getBroadcast(this, 3, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        // PendingIntent pIntent = PendingIntent.getActivity(this, 1, intent, 0);

        /**debug**
         Toast.makeText(StoryPlayerActivity.this,
         mPlayBtn.isSelected() +
         "\nBTN:" + playpause +
         "\npause:" + R.drawable.ic_pause_black_24dp +
         "\nplay:" + R.drawable.ic_play_black_24dp, Toast.LENGTH_LONG).show();
         **debug**/
        final NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_logo_black_24dp)
                        //  .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_logo_round_colors_24dp))
                        // .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .setPriority(Notification.PRIORITY_MAX) //設置優先級
                        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                        .setColor(ContextCompat.getColor(this, R.color.Bear_Orange_dark))
                        .setContentTitle(story_name.getText())
                        .setContentText("錄/" + recording_Name.getText())
                        // .addAction(R.drawable.ic_back_white_24dp, "", null)
                        .addAction(R.drawable.ic_cancel_black_16dp, "", cancelIntent)
                        .addAction(playpause, "", playIntent)
                        .addAction(R.drawable.btn_next, "", nextIntent)
                        .setOngoing(true)  //用户不能取消，效果类似FLAG_NO_CLEAR
                        .setStyle(new android.support.v4.media.app.NotificationCompat.MediaStyle()
                                .setMediaSession(new MediaSessionCompat(this, "MediaSession",
                                        new ComponentName(this, Intent.ACTION_MEDIA_BUTTON), null)
                                        .getSessionToken())
                                //设置要现实在通知右方的图标 最多三个
                                .setShowActionsInCompactView(0, 1, 2));
        // .setCancelButtonIntent(cancelIntent) //CancelButton在5.0以下的机器有效
        //  .setShowCancelButton(true));
        //送出訊息
        mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(mId, mBuilder.build());
        // Start a lengthy operation in a background thread
        /**
         new Thread(
         new Runnable() {
        @Override public void run() {
        int incr;
        for (incr = 0; incr <= 100; incr+=5) {
        mBuilder.setProgress(100, incr, false);
        mNotificationManager.notify(mId, mBuilder.build());
        try {
        // Sleep for 5 seconds
        Thread.sleep(5*1000);
        } catch (InterruptedException e) {
        e.printStackTrace();
        //Log.d(TAG, "sleep failure");
        }
        }
        mBuilder.setContentText("Download complete")//下载完成
        .setProgress(0,0,false);    //移除进度条
        mNotificationManager.notify(mId, mBuilder.build());
        }
        }
         ).start();
         **/

    }


    private void conMediaPlayer() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                initMediaPlayerListener();

                handler.sendEmptyMessage(UPDATE_TIME);  //更新播放時間
                int max = mediaPlayer.getDuration();    //取得音檔長度
                mseekBar.setMax(max);  //設定seekBar長度

                new Thread() {  //开启进程控制SeekBar
                    public void run() {
                        isplaying = true;
                        while (isplaying) {
                            int position = mediaPlayer.getCurrentPosition();
                            mseekBar.setProgress(position);
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }
                }.start();
            }
        }).start();
    }


    private void initMediaPlayer() {

        // Intent intent = getIntent();
        //  url = intent.getStringExtra("address");
        //  String name = intent.getStringExtra("name");
        //   story_name.setText(name);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.reset(); //避免錯誤，先重置
                    String story = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + "/test.mp3";
                    mediaPlayer.setDataSource(story);
                    // mediaPlayer.setDataSource("/sdcard/Download/test.mp3");
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    barStoryName.setText(story_name.getText()); //同步故事名稱到playerBar

                    initMediaPlayerListener();

                    handler.sendEmptyMessage(UPDATE_TIME);  //更新播放時間
                    int max = mediaPlayer.getDuration();    //取得音檔長度
                    mseekBar.setMax(max);  //設定seekBar長度

                    new Thread() {  //开启进程控制SeekBar
                        public void run() {
                            isplaying = true;
                            while (isplaying) {
                                int position = mediaPlayer.getCurrentPosition();
                                mseekBar.setProgress(position);
                                try {
                                    Thread.sleep(500);
                                } catch (InterruptedException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }

                        }
                    }.start();
                    mediaPlayer.setLooping(false); // 預設重複播放

                } catch (Exception e) {
                    e.printStackTrace(); //把原始错误信息顯示出来
                }
            }
        }).start();
        xPlayer.setVisibility(View.VISIBLE);
        mPlayBtn.setSelected(true);
        barPlaypauseBtn.setSelected(true);
    }

    private void initMediaPlayerListener() {
        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {  //顯示播放錯誤碼
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {

                Log.i("TEST", "onError-=-->" + what);
                return false;
            }
        });
        //监听MediaPlayer播放完成
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer paramMediaPlayer) {
                if (mPlayAllBtn.isSelected()) { // 循環播放
                    paramMediaPlayer.start();
                    Toast.makeText(StoryPlayerActivity.this, "繼續播放❤️", Toast.LENGTH_SHORT).show();
                } else {
                    paramMediaPlayer.pause();
                    Toast.makeText(StoryPlayerActivity.this, "故事說完了😊️", Toast.LENGTH_SHORT).show();
                    mPlayBtn.setSelected(false);
                    xPlayer.findViewById(R.id.playpauseBtn).setSelected(false); // 將MainActivity.xPlayer狀態同步
                    xPlayer.setVisibility(View.GONE);
                }
                //   mediaPlayer.release();
                //   mediaPlayer = null;
                //   callOver.setOnFinishListen(1);
            }
        });

        //監聽播放進度條被拉動
        mseekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int process = seekBar.getProgress();
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.seekTo(process);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                // TODO Auto-generated method stub

            }
        });
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    mPlayBtn.setSelected(false);
                    xPlayer.setVisibility(View.GONE);
                    xPlayer.findViewById(R.id.playpauseBtn).setSelected(false); // 將MainActivity.xPlayer狀態同步
                    break;
                case UPDATE_TIME: {
                    /**
                     * 更新时间
                     */
                    int position = mediaPlayer.getCurrentPosition();
                    int totalduration = mediaPlayer.getDuration();

                    updateTime(mplayMin, position);
                    updateTime(mtotalTime, totalduration);

                    handler.sendEmptyMessageDelayed(UPDATE_TIME, 500);

                }
                break;


            }
        }
    };

    public void myHomeBtnClk(View v) {
        String vtag = v.getTag().toString();
        Intent i = new Intent(StoryPlayerActivity.this, userHomeActivity.class);
        //把按下的Btn tag傳給userHomeActivity
        i.putExtra("data", vtag);
        //將原本Activity的換成userHomeActivity
        startActivity(i);
    }

    private void updateTime(TextView textView, int millisecond) {
        int second = millisecond / 1000;
        int hh = second / 3600;
        int mm = second % 3600 / 60;
        int ss = second % 60;

        String str = null;
        if (hh != 0) {
            str = String.format("%02d:%02d:%02d", hh, mm, ss);

        } else {
            str = String.format("%02d:%02d", mm, ss);
        }
        textView.setText(str);
    }

    private void initListener() {

        /**要改用viewpage用滑的**/
        playerTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        switchSubContent(storypage[0]);
                        break;
                    case 1:
                        switchSubContent(storypage[1]);
                        break;
                    case 2:
                        switchSubContent(storypage[2]);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //设置左上角导航键的点击监听事件
        sToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        mHeartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mHeartBtn.isSelected()) { // 取消收藏，按下後變回空心，收藏數-1
                    mHeartBtn.setSelected(false);
                    Toast.makeText(StoryPlayerActivity.this, R.string.cancelLove, Toast.LENGTH_SHORT).show();
                } else { // 收藏愛心，按下後維持紅心，收藏數+1
                    mHeartBtn.setSelected(true);
                    Toast.makeText(StoryPlayerActivity.this, R.string.loveThis, Toast.LENGTH_SHORT).show();
                }
            }
        });

        sRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                // int numStars = ratingBar.getNumStars();
                int curNum = (int) rating;
                switch (curNum) {
                    case 1:
                        Toast.makeText(StoryPlayerActivity.this, R.string.star1, Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(StoryPlayerActivity.this, getString(R.string.star2, curNum), Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(StoryPlayerActivity.this, getString(R.string.star3, curNum), Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        Toast.makeText(StoryPlayerActivity.this, getString(R.string.star4, curNum), Toast.LENGTH_SHORT).show();
                        break;
                    case 5:
                        Toast.makeText(StoryPlayerActivity.this, getString(R.string.star5, curNum), Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });
    }

    private void switchSubContent(Fragment to) {

        if (homeContent == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.storyplayPage, to)
                    .commitNow();
        } else if (homeContent != to) {
            // FragmentTransaction transaction = fragmentManager.beginTransaction();
            if (!to.isAdded()) { // 判断是否被add过
                // 隐藏当前的fragment，将 下一个fragment 添加进去
                getSupportFragmentManager().beginTransaction()
                        .hide(homeContent)
                        .add(R.id.storyplayPage, to)
                        .commitNow();
            } else {
                // 隐藏当前的fragment，显示下一个fragment
                getSupportFragmentManager().beginTransaction()
                        .hide(homeContent)
                        .show(to)
                        .commitNow();
            }
        }
        homeContent = to;
    }

    public void playerBtnClk(View v) {
        String vtag = v.getTag().toString();

        switch (vtag) {
            case "playAllBtn":
                if (mPlayAllBtn.isSelected()) { /* 不循環播放 */
                    mPlayAllBtn.setSelected(false);

                    Toast.makeText(StoryPlayerActivity.this, R.string.nonlooPlay, Toast.LENGTH_SHORT).show();
                } else { /* 循環播放 */
                    mPlayAllBtn.setSelected(true);

                    Toast.makeText(StoryPlayerActivity.this, R.string.looPlay, Toast.LENGTH_SHORT).show();
                }
                break;

            case "backBtn":

                Toast.makeText(StoryPlayerActivity.this, R.string.playback, Toast.LENGTH_SHORT).show();

                break;

            case "playBtn":
                if (mPlayBtn.isSelected()) { /* 暫停播放 */
                    mediaPlayer.pause();
                    mPlayBtn.setSelected(false);
                    xPlayer.findViewById(R.id.playpauseBtn).setSelected(false); // 將MainActivity.xPlayer狀態同步
                    xPlayer.setVisibility(View.GONE);
                    Toast.makeText(StoryPlayerActivity.this, R.string.paused, Toast.LENGTH_SHORT).show();
                } else { /* 開始播放 */
                    mediaPlayer.start();
                    mPlayBtn.setSelected(true);
                    xPlayer.setVisibility(View.VISIBLE);
                    xPlayer.findViewById(R.id.playpauseBtn).setSelected(true);
                    Toast.makeText(StoryPlayerActivity.this, R.string.playing, Toast.LENGTH_SHORT).show();
                }
                mediaPlayerNotific();
                break;

            case "nextBtn":
                Toast.makeText(StoryPlayerActivity.this, R.string.playnext, Toast.LENGTH_SHORT).show();

                break;

            case "storyListBtn":
                if (mStoryListBtn.isSelected()) { /* 關閉播放故事列表 */
                    mStoryListBtn.setSelected(false);
                    if (tempContent == null) {
                        switchSubContent(storypage[0]);
                    } else {
                        switchSubContent(tempContent);
                    }
                    Toast.makeText(StoryPlayerActivity.this, R.string.hidePlayingList, Toast.LENGTH_SHORT).show();
                } else { /* 列出播放故事列表 */
                    mStoryListBtn.setSelected(true);
                    tempContent = homeContent;
                    switchSubContent(storypage[3]);

                    Toast.makeText(StoryPlayerActivity.this, R.string.showPlayingList, Toast.LENGTH_SHORT).show();
                }

                break;

            case "StorySearch": /* 跳出故事搜尋頁用以新增故事 */

                Intent i = new Intent(StoryPlayerActivity.this, SearchActivity.class);
                //把按下的Btn tag傳給SearchActivity
                i.putExtra("data", vtag);
                //將原本Activity的換成SearchActivity
                startActivity(i);
                break;

        }
    }

}
