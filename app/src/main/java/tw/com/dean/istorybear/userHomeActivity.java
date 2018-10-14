package tw.com.dean.istorybear;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import tw.com.dean.istorybear.ui.userhome.blogHomeFragment;
import tw.com.dean.istorybear.ui.userhome.myOverviewFragment;
import tw.com.dean.istorybear.ui.userhome.storyHomeFragment;

public class userHomeActivity extends AppCompatActivity {
    private TabLayout homeTablayout;
    private Fragment homeContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_userhome);
        homeTablayout = (TabLayout) findViewById(R.id.hometTabs);

       // if (savedInstanceState == null) {
       //     getSupportFragmentManager().beginTransaction()
        //            .replace(R.id.myhomelist, UserHomeFragment.newInstance())
       //             .commitNow();
     //   }

        Intent i = getIntent();
        String searchTag = i.getStringExtra("data");
            switch (searchTag) {
                case "myBlog":
                    homeTablayout.getTabAt(2).select();
                    homeContent = blogHomeFragment.newInstance();

                    break;

                case "myStory":
                    homeTablayout.getTabAt(0).select();
                    homeContent = storyHomeFragment.newInstance();

                    break;
            }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.myhomelist, homeContent)
                .commitNow();

        initListener();

        Toolbar aToolbar = (Toolbar) findViewById(R.id.userHomeBar);
        //设置左上角导航键的点击监听事件
        aToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initListener() {
        homeTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        switchSubContent(storyHomeFragment.newInstance());
                        break;
                    case 1:
                        switchSubContent(myOverviewFragment.newInstance());

                        break;
                    case 2:
                        switchSubContent(blogHomeFragment.newInstance());
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
    }


    private void switchSubContent(Fragment to) {

        if (homeContent == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.myhomelist, to)
                    .commitNow();
        }
        else if (homeContent != to) {
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

            homeContent = to;
        }

    }
}
