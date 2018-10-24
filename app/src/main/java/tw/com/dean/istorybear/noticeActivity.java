package tw.com.dean.istorybear;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class noticeActivity extends AppCompatActivity {
    private TabLayout noticeTablayout;
    private Fragment noticeContent;
    private Fragment[] noticepage = new Fragment[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_me_notice);
        noticeTablayout = (TabLayout) findViewById(R.id.noticeTabs);

        noticepage[0] = noticeListFragment.newInstance();
        noticepage[1] = noticeADFragment.newInstance();
        noticepage[2] = noticeOrderFragment.newInstance();

        Intent i = getIntent();
        String searchTag = i.getStringExtra("data");
        switch (searchTag) {
            case "personalNotice":
                noticeTablayout.getTabAt(0).select();
                switchSubContent(noticepage[0]);

                break;

        }


        initListener();

        Toolbar aToolbar = (Toolbar) findViewById(R.id.myNoticeBar);
        //设置左上角导航键的点击监听事件
        aToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initListener() {
        noticeTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switchSubContent(noticepage[tab.getPosition()]);
/*
                switch (tab.getPosition()) {
                    case 0:
                        switchSubContent(noticeListFragment.newInstance());
                        break;
                    case 1:
                        switchSubContent(noticeADFragment.newInstance());

                        break;
                    case 2:
                        switchSubContent(noticeOrderFragment.newInstance());
                        break;
                }
 */
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

        if (noticeContent == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.noticelist, to)
                    .commitNow();
        } else if (noticeContent != to) {
            // FragmentTransaction transaction = fragmentManager.beginTransaction();
            if (!to.isAdded()) { // 判断是否被add过
                // 隐藏当前的fragment，将 下一个fragment 添加进去
                getSupportFragmentManager().beginTransaction()
                        .hide(noticeContent)
                        .add(R.id.noticelist, to)
                        .commitNow();
            } else {
                // 隐藏当前的fragment，显示下一个fragment
                getSupportFragmentManager().beginTransaction()
                        .hide(noticeContent)
                        .show(to)
                        .commitNow();
            }
        }
        noticeContent = to;

    }
}
