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

public class Fragment_sub_playToplay extends Fragment {

    private TabLayout subTablayout;

    private Fragment_sub_toplaySponsor fragment_sub_toplaySponsor = new Fragment_sub_toplaySponsor();
    private Fragment_sub_toplayInvite fragment_sub_toplayInvite = new Fragment_sub_toplayInvite();
    private Fragment_sub_toplayHost fragment_sub_toplayHost = new Fragment_sub_toplayHost();
    private Fragment toplayContent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_play_toplay, container, false);
        subTablayout = (TabLayout) view.findViewById(R.id.playSubTablayout);
        switchSubContent(fragment_sub_toplaySponsor);
        initListener();
        return view;
    }


    private void initListener() {
        subTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        switchSubContent(fragment_sub_toplaySponsor);
                        break;

                    case 1:
                        switchSubContent(fragment_sub_toplayInvite);

                        break;

                    case 2:
                        switchSubContent(fragment_sub_toplayHost);
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

        if (toplayContent == null) {
            transaction.replace(R.id.toplaySubfragment_container, to).show(to).commit();
        }
        else if (toplayContent != to) {
            // FragmentTransaction transaction = fragmentManager.beginTransaction();
            if (!to.isAdded()) { // 判断是否被add过
                // 隐藏当前的fragment，将 下一个fragment 添加进去
                transaction.hide(toplayContent).add(R.id.toplaySubfragment_container, to).commit();
            } else {
                // 隐藏当前的fragment，显示下一个fragment
                transaction.hide(toplayContent).show(to).commit();
            }

            toplayContent = to;
        }

    }
}