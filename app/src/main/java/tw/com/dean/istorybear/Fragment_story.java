package tw.com.dean.istorybear;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by tomoya on 4/5/17.
 */

public class Fragment_story extends Fragment {

    private TabLayout subTablayout;

    private Fragment_sub_mystory fragment_sub_mystory = new Fragment_sub_mystory();
    private Fragment_sub_buystory fragment_sub_buystory = new Fragment_sub_buystory();
    private Fragment_sub_upstory fragment_sub_upstory = new Fragment_sub_upstory();

    private Fragment subContent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_story, container, false);
        subTablayout = (TabLayout) view.findViewById(R.id.storyTablayout);
        switchSubContent(fragment_sub_mystory);

        // storyContent = fragment_sub_mystory;
        initListener();

        return view;
    }

    private void initListener() {
        subTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        Toast.makeText(Fragment_story.super.getContext(), "我的故事", Toast.LENGTH_SHORT).show();

                        switchSubContent(fragment_sub_mystory);
                        break;
                    case 1:
                        Toast.makeText(Fragment_story.super.getContext(), "故事屋", Toast.LENGTH_SHORT).show();

                        switchSubContent(fragment_sub_buystory);

                        break;
                    case 2:
                        Toast.makeText(Fragment_story.super.getContext(), "創作提案", Toast.LENGTH_SHORT).show();

                        switchSubContent(fragment_sub_upstory);
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

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if (subContent == null) { transaction.replace(R.id.Storyfragment_container, to).show(to).commit(); }

        else if (subContent != to) {
           // FragmentTransaction transaction = fragmentManager.beginTransaction();
            if (!to.isAdded()) { // 判断是否被add过
                // 隐藏当前的fragment，将 下一个fragment 添加进去
                transaction.hide(subContent).add(R.id.Storyfragment_container, to).show(to).commit();
            } else {
                // 隐藏当前的fragment，显示下一个fragment
                transaction.hide(subContent).show(to).commit();
            }

            subContent = to;
        }

    }


}
