package tw.com.dean.istorybear;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Toast;

public class StoryPlayerActivity extends AppCompatActivity {
    Button mBackBtn;
    Button mPlayBtn;
    Button mNextBtn;
    Button mStoryListBtn;
    Button mPlayAllBtn;
    public static LinearLayout xPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_player);
        Toolbar sToolbar = (Toolbar) findViewById(R.id.sPlayerBar);
        RatingBar sRatingBar = findViewById(R.id.ratingBar);
        final Button mHeartBtn = findViewById(R.id.heartButton);

        mPlayAllBtn = findViewById(R.id.playAllBtn);
        mBackBtn = findViewById(R.id.backBtn);
        mPlayBtn = findViewById(R.id.playBtn);
        mNextBtn = findViewById(R.id.nextBtn);
        mStoryListBtn = findViewById(R.id.storyListBtn);
        xPlayer = MainActivity.xPlayer;


        // sToolbar.setTitle("本故事由遠傳電信獨家贊助");
        setSupportActionBar(sToolbar);

        if (xPlayer.findViewById(R.id.playpauseBtn).isSelected()) { // 與MainActivity.xPlayer狀態同步
            mPlayBtn.setSelected(true);  /* 循環播放 */
        } else {
            mPlayBtn.setSelected(false); /* 不循環播放 */
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent i = new Intent(StoryPlayerActivity.this, SearchActivity.class);
                //把按下的Btn tag傳給SearchActivity
                i.putExtra("data", "StorySearch");
                //將原本Activity的換成SearchActivity
                startActivity(i);

            }
        });
        //设置左上角导航键的点击监听事件
        sToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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

        mHeartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mHeartBtn.isSelected()) { /* 取消收藏，按下後變回空心，收藏數-1 */
                    mHeartBtn.setSelected(false);

                    Toast.makeText(StoryPlayerActivity.this, R.string.cancelLove, Toast.LENGTH_SHORT).show();
                } else { /* 收藏愛心，按下後維持紅心，收藏數+1 */
                    mHeartBtn.setSelected(true);

                    Toast.makeText(StoryPlayerActivity.this, R.string.loveThis, Toast.LENGTH_SHORT).show();
                }

            }
        });
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
                    mPlayBtn.setSelected(false);
                    xPlayer.setVisibility(View.GONE);
                    xPlayer.findViewById(R.id.playpauseBtn).setSelected(false); // 將MainActivity.xPlayer狀態同步

                    Toast.makeText(StoryPlayerActivity.this, R.string.paused, Toast.LENGTH_SHORT).show();
                } else { /* 開始播放 */
                    mPlayBtn.setSelected(true);
                    xPlayer.findViewById(R.id.playpauseBtn).setSelected(true);
                    xPlayer.setVisibility(View.VISIBLE);
                    Toast.makeText(StoryPlayerActivity.this, R.string.playing, Toast.LENGTH_SHORT).show();
                }
                break;

            case "nextBtn":
                Toast.makeText(StoryPlayerActivity.this, R.string.playnext, Toast.LENGTH_SHORT).show();

                break;

            case "storyListBtn":
                if (mStoryListBtn.isSelected()) { /* 關閉播放故事列表 */
                    mStoryListBtn.setSelected(false);

                    Toast.makeText(StoryPlayerActivity.this, R.string.hidePlayingList, Toast.LENGTH_SHORT).show();
                } else { /* 列出播放故事列表 */
                    mStoryListBtn.setSelected(true);

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
