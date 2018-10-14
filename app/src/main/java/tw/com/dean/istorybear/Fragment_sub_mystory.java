package tw.com.dean.istorybear;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;


public class Fragment_sub_mystory extends Fragment {

    public Toolbar mSponsorStoryBar;
    public Toolbar mMyStoryBar;
    public Toolbar mHistoryBar;
    public Toolbar mScenesBar;
    public Toolbar mLoveStoryBar;

    public LinearLayout mSponsorStoryList;
    public LinearLayout mMyStrorList;
    public LinearLayout mHistoryList;
    public LinearLayout mScenesListr;
    public LinearLayout mLoveStoryList;

    public ImageButton mAddStory;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_story_mystory, container, false);

        mSponsorStoryBar =(Toolbar) view.findViewById(R.id.sponsorStoryBar);
        mMyStoryBar =(Toolbar) view.findViewById(R.id.myStrorBar);
        mHistoryBar =(Toolbar) view.findViewById(R.id.historyBar);
        mScenesBar =(Toolbar) view.findViewById(R.id.scenesBar);
        mLoveStoryBar =(Toolbar) view.findViewById(R.id.loveStoryBar);

        mSponsorStoryList =(LinearLayout) view.findViewById(R.id.sponsorStoryList);
        mMyStrorList =(LinearLayout) view.findViewById(R.id.myStrorList);
        mHistoryList =(LinearLayout) view.findViewById(R.id.historyList);
        mScenesListr =(LinearLayout) view.findViewById(R.id.scenesList);
        mLoveStoryList =(LinearLayout) view.findViewById(R.id.loveStoryList);

        mAddStory =(ImageButton) view.findViewById(R.id.AddStoryBtn);


        initListener();

        return view;
    }

    private void initListener() {
        mSponsorStoryBar.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(Fragment_sub_mystory.super.getContext(), "贊助故事", Toast.LENGTH_SHORT).show();
                mSponsorStoryList.setVisibility(View.VISIBLE);
                mMyStrorList.setVisibility(View.GONE);
                mHistoryList.setVisibility(View.GONE);
                mScenesListr.setVisibility(View.GONE);
                mLoveStoryList.setVisibility(View.GONE);
                //switch (event.getAction()) {//当前状态
                //case MotionEvent.ACTION_DOWN:
                // break;
                // case MotionEvent.ACTION_MOVE:
                // break;
                // case MotionEvent.ACTION_UP:
                // break;
                // default:
                // break;
                // }
                // return true;//还回为true,说明事件已经完成了，不会再被其他事件监听器调用
                // }
                // });
                return true;
            }
        });

        mMyStoryBar.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(Fragment_sub_mystory.super.getContext(), "我的故事", Toast.LENGTH_SHORT).show();
                mSponsorStoryList.setVisibility(View.GONE);
                mMyStrorList.setVisibility(View.VISIBLE);
                mHistoryList.setVisibility(View.GONE);
                mScenesListr.setVisibility(View.GONE);
                mLoveStoryList.setVisibility(View.GONE);
                return true;
            }
        });

        mHistoryBar.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(Fragment_sub_mystory.super.getContext(), "最近播放", Toast.LENGTH_SHORT).show();
                mSponsorStoryList.setVisibility(View.GONE);
                mMyStrorList.setVisibility(View.GONE);
                mHistoryList.setVisibility(View.VISIBLE);
                mScenesListr.setVisibility(View.GONE);
                mLoveStoryList.setVisibility(View.GONE);
                return true;
            }
        });

        mScenesBar.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(Fragment_sub_mystory.super.getContext(), "播放場景", Toast.LENGTH_SHORT).show();
                mSponsorStoryList.setVisibility(View.GONE);
                mMyStrorList.setVisibility(View.GONE);
                mHistoryList.setVisibility(View.GONE);
                mScenesListr.setVisibility(View.VISIBLE);
                mLoveStoryList.setVisibility(View.GONE);
                return true;
            }
        });

        mLoveStoryBar.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(Fragment_sub_mystory.super.getContext(), "故事收藏", Toast.LENGTH_SHORT).show();
                mSponsorStoryList.setVisibility(View.GONE);
                mMyStrorList.setVisibility(View.GONE);
                mHistoryList.setVisibility(View.GONE);
                mScenesListr.setVisibility(View.GONE);
                mLoveStoryList.setVisibility(View.VISIBLE);
                return true;
            }
        });

        mLoveStoryBar.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(Fragment_sub_mystory.super.getContext(), "故事收藏", Toast.LENGTH_SHORT).show();
                mSponsorStoryList.setVisibility(View.GONE);
                mMyStrorList.setVisibility(View.GONE);
                mHistoryList.setVisibility(View.GONE);
                mScenesListr.setVisibility(View.GONE);
                mLoveStoryList.setVisibility(View.VISIBLE);
                return true;
            }
        });

        /**
        mAddStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                Fragment_sub_mystory.super.setTargetFragment()
                transaction.replace(R.id.Storyfragment_container, Fragment_sub_mystory.super.setTargetFragment();fragment_sub_buystory).show(to).commit();
            }
        });
**/

    }




}