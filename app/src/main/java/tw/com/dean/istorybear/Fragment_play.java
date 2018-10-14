package tw.com.dean.istorybear;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by tomoya on 4/5/17.
 */

public class Fragment_play extends Fragment {

    private TabLayout subTablayout;

    private Fragment_sub_playEvents fragment_sub_playEvents = new Fragment_sub_playEvents();
    private Fragment_sub_playAttractions fragment_sub_playAttractions = new Fragment_sub_playAttractions();
    private Fragment_sub_playToplay fragment_sub_playToplay = new Fragment_sub_playToplay();
    private Fragment playContent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_play, container, false);
        subTablayout = (TabLayout) view.findViewById(R.id.playTablayout);
       // playContent = fragment_sub_playEvents;
        switchSubContent(fragment_sub_playEvents);
        initListener();
        return view;
    }


    private void initListener() {
        subTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        Toast.makeText(Fragment_play.super.getContext(), "活動", Toast.LENGTH_SHORT).show();

                        switchSubContent(fragment_sub_playEvents);
                        break;
                    case 1:
                        Toast.makeText(Fragment_play.super.getContext(), "景點", Toast.LENGTH_SHORT).show();

                        switchSubContent(fragment_sub_playAttractions);

                        break;
                    case 2:
                        Toast.makeText(Fragment_play.super.getContext(), "邀請", Toast.LENGTH_SHORT).show();

                        switchSubContent(fragment_sub_playToplay);
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

        if (playContent == null) {
            transaction.replace(R.id.playfragment_container, to).show(to).commit();
        }
        else if (playContent != to) {
           // FragmentTransaction transaction = fragmentManager.beginTransaction();
            if (!to.isAdded()) { // 判断是否被add过
                // 隐藏当前的fragment，将 下一个fragment 添加进去
                transaction.hide(playContent).add(R.id.playfragment_container, to).commit();
            } else {
                // 隐藏当前的fragment，显示下一个fragment
                transaction.hide(playContent).show(to).commit();
            }

            playContent = to;
        }

    }

}