package tw.com.dean.istorybear;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
    private Button mBackBtn, mPlayBtn, mNextBtn, mPlayAllBtn, mHeartBtn, mStoryListBtn;
    public static LinearLayout xPlayer;
    private Fragment homeContent, tempContent;
    private Fragment[] storypage = new Fragment[4];
    private Toolbar sToolbar;
    private RatingBar sRatingBar;
    private TabLayout playerTablayout;
    private SeekBar mseekBar;

    private TextView mplayMin, mtotalTime, story_name;


    private Boolean isplaying;
    private static final int UPDATE_TIME = 2;
    public static MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_player);
        sToolbar = (Toolbar) findViewById(R.id.sPlayerBar);
        sRatingBar = findViewById(R.id.ratingBar);
        mHeartBtn = findViewById(R.id.heartButton);
        playerTablayout = (TabLayout) findViewById(R.id.storyplayerTabs);
        mseekBar = (SeekBar) findViewById(R.id.seekBar);
        mplayMin = (TextView) findViewById(R.id.playMin);
        mtotalTime = (TextView) findViewById(R.id.totalTime);
        story_name = (TextView) findViewById(R.id.storyName);

        mPlayAllBtn = findViewById(R.id.playAllBtn);
        mBackBtn = findViewById(R.id.backBtn);
        mPlayBtn = findViewById(R.id.playBtn);
        mNextBtn = findViewById(R.id.nextBtn);
        mStoryListBtn = findViewById(R.id.storyListBtn);
        xPlayer = MainActivity.xPlayer;

        storypage[0] = storyOverviewFragment.newInstance();
        storypage[1] = story404pjFragment.newInstance();
        storypage[2] = storySponsorFragment.newInstance();
        storypage[3] = storyPlaylistFragment.newInstance();

        // sToolbar.setTitle("本故事由遠傳電信獨家贊助");
        setSupportActionBar(sToolbar);

        if (homeContent == null) {  //預設在故事介紹頁
            switchSubContent(storypage[0]);
        }

        if (xPlayer.findViewById(R.id.playpauseBtn).isSelected()) { // 與MainActivity.xPlayer狀態同步
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

                    mediaPlayer.setDataSource(Environment.
                            getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + "/test.mp3");
                    // mediaPlayer.setDataSource("/sdcard/Download/test.mp3");
                    mediaPlayer.prepare();

                    mPlayBtn.setSelected(true);

                    mediaPlayer.start();

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
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }

                        }
                    }.start();

                    mediaPlayer.setLooping(false); // 預設重複播放

                } catch (Exception e) {
                    Log.i("mediaPlayer", "onError-=-->");
                    e.printStackTrace(); //把原始错误信息顯示出来
                }
            }
        }).start();
        xPlayer.setVisibility(View.VISIBLE);
        xPlayer.findViewById(R.id.playpauseBtn).setSelected(true);

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
                paramMediaPlayer.pause();
                mPlayBtn.setSelected(false);
                xPlayer.findViewById(R.id.playpauseBtn).setSelected(false); // 將MainActivity.xPlayer狀態同步
                xPlayer.setVisibility(View.GONE);
                Toast.makeText(StoryPlayerActivity.this, "故事說完了❤️", Toast.LENGTH_SHORT).show();
                //   mediaPlayer.release();
                //   mediaPlayer = null;
                //   callOver.setOnFinishListen(1);
            }
        });
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

    /**
     * 停止並釋放mediaPlayer
     **/
    public void stop() {  //停止並釋放mediaPlayer
        if (mediaPlayer != null) {
            //  mediaPlayer.stop();
            //  mediaPlayer.release();
            // mediaPlayer.reset();
            //  mediaPlayer = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!mediaPlayer.isPlaying()) {
            stop();
        }
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
