package tw.com.dean.istorybear;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import tw.com.dean.istorybear.ui.userhome.blogHomeFragment;
import tw.com.dean.istorybear.ui.userhome.myOverviewFragment;
import tw.com.dean.istorybear.ui.userhome.storyHomeFragment;

public class userHomeActivity extends AppCompatActivity {
    private TabLayout homeTablayout;
    private Fragment homeContent;
    private FloatingActionButton FABtn;
    private Toolbar aToolbar;
    private Button loveThis;
    private TextView authorName;
    private Boolean editable = false;
    private Fragment[] homepage = new Fragment[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_userhome);
        homeTablayout = (TabLayout) findViewById(R.id.homeTabs);
        aToolbar = (Toolbar) findViewById(R.id.userHomeBar);
        FABtn = (FloatingActionButton) findViewById(R.id.add_edit_fABtn);
        loveThis = (Button) findViewById(R.id.loveBtn);
        authorName = (TextView) findViewById(R.id.userName);


        homepage[0] = storyHomeFragment.newInstance();
        homepage[1] = myOverviewFragment.newInstance();
        homepage[2] = blogHomeFragment.newInstance();

        Intent i = getIntent();
        String searchTag = i.getStringExtra("data");
        switch (searchTag) {
            case "myStory":
                homeTablayout.getTabAt(0).select();
                // switchSubContent(storyHomeFragment.newInstance());
                switchSubContent(homepage[0]); //以登入者會員ID讀取該會員資料，最好寫入本地暫存，然後更新資料 -> 還沒做
                FABtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_add_black_24dp, null));
                FABtn.setVisibility(View.VISIBLE);

                break;

            case "myOverview":
                homeTablayout.getTabAt(1).select();
                switchSubContent(homepage[1]); //以登入者會員ID讀取該會員資料，最好寫入本地暫存，然後更新資料 -> 還沒做
                FABtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_edit_black_24dp, null));
                FABtn.setVisibility(View.VISIBLE);

                break;

            case "myBlog":
                homeTablayout.getTabAt(2).select();
                switchSubContent(homepage[2]); //以登入者會員ID讀取該會員資料，最好寫入本地暫存，然後更新資料 -> 還沒做
                FABtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_add_black_24dp, null));
                FABtn.setVisibility(View.VISIBLE);

                break;

            case "otherUser": // 非本人預設FABtn is gone，收藏鈕出現
                homeTablayout.getTabAt(1).select();
                switchSubContent(homepage[1]); //以Tag會員ID讀取該會員資料，然後更新資料 -> 還沒做
                FABtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_edit_black_24dp, null));
                loveThis.setVisibility(View.VISIBLE);
                break;
        }

        initListener();

    }

    private void initListener() {
        homeTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        switchSubContent(homepage[0]);
                        FABtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_add_black_24dp, null));
                        editable = false;
                        break;
                    case 1:
                        switchSubContent(homepage[1]);
                        FABtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_edit_black_24dp, null));
                        editable = true;
                        break;
                    case 2:
                        switchSubContent(homepage[2]);
                        FABtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_add_black_24dp, null));
                        editable = false;

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
        aToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        loveThis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loveThis.isSelected()) { /* 取消收藏，按下後變回空心，收藏數-1 */
                    loveThis.setSelected(false);

                    Toast.makeText(userHomeActivity.this, R.string.cancelFollow, Toast.LENGTH_SHORT).show();
                } else { /* 收藏愛心，按下後維持紅心，收藏數+1 */
                    loveThis.setSelected(true);

                    Toast.makeText(userHomeActivity.this, getString(R.string.followHe, authorName.getText()), Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


    private void switchSubContent(Fragment to) {

        if (homeContent == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.myhomelist, to)
                    .commitNow();
        } else if (homeContent != to) {
            // FragmentTransaction transaction = fragmentManager.beginTransaction();
            if (!to.isAdded()) { // 判断是否被add过
                // 隐藏当前的fragment，将 下一个fragment 添加进去
                getSupportFragmentManager().beginTransaction()
                        .hide(homeContent)
                        .add(R.id.myhomelist, to)
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

    public void FABtnOnClk(View v) {
        Intent i = new Intent(userHomeActivity.this, ActActivity.class);
        switch (homeTablayout.getSelectedTabPosition()) {
            case 0:
                //把tag傳給ActActivity
                i.putExtra("data", "UpStory");
                startActivity(i); // 將原本Activity的換成ActActivity
                break;
            case 1:
                EditText myOverview = (EditText) findViewById(R.id.user_overview);
                if (!editable) { //原本是不可編輯模式
                    Toast.makeText(userHomeActivity.this, "您可開始變更照片、簡介", Toast.LENGTH_SHORT).show();
                    FABtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_edit_white_24dp, null));
                    myOverview.requestFocus();
                } else {
                    Toast.makeText(userHomeActivity.this, "停止修改", Toast.LENGTH_SHORT).show();
                    FABtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_edit_black_24dp, null));
                }
                editable = !editable;
                myOverview.setFocusableInTouchMode(editable);
                myOverview.setFocusable(editable);
                break;
            case 2:
                i.putExtra("data", "Post");
                startActivity(i); // 將原本Activity的換成ActActivity
                break;
        }


    }

}
