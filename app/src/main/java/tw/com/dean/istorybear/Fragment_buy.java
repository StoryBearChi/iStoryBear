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

public class Fragment_buy extends Fragment {

    private TabLayout subTablayout;

    private Fragment_sub_buyc2c fragment_sub_buyc2c = new Fragment_sub_buyc2c();
    private Fragment_sub_buygroupbuy fragment_sub_buygroupbuy = new Fragment_sub_buygroupbuy();
    private Fragment mContent;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_buy, container, false);
        subTablayout = (TabLayout) view.findViewById(R.id.buyTablayout);
        switchSubContent(fragment_sub_buygroupbuy);

       // mContent = fragment_sub_buygroupbuy;
        initListener();
        return view;
    }


    private void initListener() {
        subTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:

                        Toast.makeText(Fragment_buy.super.getContext(), "團購", Toast.LENGTH_SHORT).show();

                        //Toast.makeText(Fragment_buy.super.getContext(), "團購", Toast.LENGTH_SHORT).show();

                        switchSubContent(fragment_sub_buygroupbuy);
                        break;
                    case 1:
                        Toast.makeText(Fragment_buy.super.getContext(), "合作商城", Toast.LENGTH_SHORT).show();

                        switchSubContent(fragment_sub_buyc2c);

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

        if (mContent == null) { transaction.replace(R.id.buyfragment_container, to).show(to).commit(); }

        else if (mContent != to) {
           // FragmentTransaction transaction = fragmentManager.beginTransaction();
            if (!to.isAdded()) { // 判断是否被add过
                // 隐藏当前的fragment，将 下一个fragment 添加进去
                //transaction.replace(R.id.buyfragment_container, to).commit();
                transaction.hide(mContent).add(R.id.buyfragment_container, to).commit();
            } else {
                // 隐藏当前的fragment，显示下一个fragment
                transaction.hide(mContent).show(to).commit();
            }

            mContent = to;
        }

    }

}