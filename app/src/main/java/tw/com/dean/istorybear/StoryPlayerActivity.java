package tw.com.dean.istorybear;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

public class StoryPlayerActivity extends AppCompatActivity {
    Button mPlayAllBtn ;
    Button mBackBtn;
    Button mPlayBtn;
    Button mNextBtn;
    Button mStoryListBtn;

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




        // sToolbar.setTitle("本故事由遠傳電信獨家贊助");
        setSupportActionBar(sToolbar);

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
                float curNum = rating;

                if (curNum==1.0){
                    Toast.makeText(StoryPlayerActivity.this, "抱歉！讓您感到不適\n我們將盡力改善", Toast.LENGTH_SHORT).show();

                }
                else if (curNum==2.0){
                    Toast.makeText(StoryPlayerActivity.this, "謝謝！您給 " + curNum + " 顆星的評分，\n讓我們知道需要改善", Toast.LENGTH_SHORT).show();

                }
                else if (curNum==3.0){
                    Toast.makeText(StoryPlayerActivity.this, "感謝！您給 " + curNum + " 顆星的評分", Toast.LENGTH_SHORT).show();

                }
                else if (curNum==4.0){
                    Toast.makeText(StoryPlayerActivity.this, "感謝♥ 您給 " + curNum + " 顆星的評分", Toast.LENGTH_SHORT).show();

                }
                else if (curNum==5.0){
                    Toast.makeText(StoryPlayerActivity.this, "哇~哇~哇~ ‧★,:*:‧\\(￣▽￣)/‧:*‧°★* \n感謝您給的 " + curNum + " 顆星的最高評分", Toast.LENGTH_LONG).show();

                }

            }
        });

        mHeartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mHeartBtn.isSelected()){ /* 取消收藏，按下後變回空心，收藏數-1 */
                    mHeartBtn.setSelected(false);

                    Toast.makeText(StoryPlayerActivity.this, "已為您取消收藏", Toast.LENGTH_SHORT).show();
                } else { /* 收藏愛心，按下後維持紅心，收藏數+1 */
                    mHeartBtn.setSelected(true);

                    Toast.makeText(StoryPlayerActivity.this, "感謝！您收藏本故事", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    public void playerBtnClk(View v) {
        String vtag = v.getTag().toString();

        switch (vtag) {
            case "playAllBtn":
                if (mPlayAllBtn.isSelected()){ /* 不循環播放 */
                    mPlayAllBtn.setSelected(false);

                    Toast.makeText(StoryPlayerActivity.this, "不循環播放", Toast.LENGTH_SHORT).show();
                } else { /* 循環播放 */
                    mPlayAllBtn.setSelected(true);

                    Toast.makeText(StoryPlayerActivity.this, "循環播放", Toast.LENGTH_SHORT).show();
                }
                break;

            case "backBtn":

                Toast.makeText(StoryPlayerActivity.this, "跳到上一個故事", Toast.LENGTH_SHORT).show();

                break;

            case "playBtn":
                if (mPlayBtn.isSelected()){ /* 暫停播放 */
                    mPlayBtn.setSelected(false);

                    Toast.makeText(StoryPlayerActivity.this, "暫停播放", Toast.LENGTH_SHORT).show();
                } else { /* 開始播放 */
                    mPlayBtn.setSelected(true);

                    Toast.makeText(StoryPlayerActivity.this, "開始播放", Toast.LENGTH_SHORT).show();
                }
                break;

            case "nextBtn":
                Toast.makeText(StoryPlayerActivity.this, "跳到下一個故事", Toast.LENGTH_SHORT).show();

                break;

            case "storyListBtn":
                if (mStoryListBtn.isSelected()){ /* 關閉播放故事列表 */
                    mStoryListBtn.setSelected(false);

                    Toast.makeText(StoryPlayerActivity.this, "隱藏播放故事列表", Toast.LENGTH_SHORT).show();
                } else { /* 列出播放故事列表 */
                    mStoryListBtn.setSelected(true);

                    Toast.makeText(StoryPlayerActivity.this, "列出播放故事列表", Toast.LENGTH_SHORT).show();
                }

                break;

            case "StorySearch":

                Intent i = new Intent(StoryPlayerActivity.this,SearchActivity.class);
                //把按下的Btn tag傳給SearchActivity
                i.putExtra("data", vtag);
                //將原本Activity的換成SearchActivity
                startActivity(i);
                break;



        }
    }

}
