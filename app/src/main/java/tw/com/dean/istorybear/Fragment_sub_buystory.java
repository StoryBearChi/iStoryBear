package tw.com.dean.istorybear;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * Created by tomoya on 4/5/17.
 */

public class Fragment_sub_buystory extends Fragment {

    public RelativeLayout mBuyStoryTopView;
    public NestedScrollView mStoryScrollView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_story_buystory, container, false);

        mBuyStoryTopView =(RelativeLayout) view.findViewById(R.id.buyStoryTopView);
        mStoryScrollView =(NestedScrollView) view.findViewById(R.id.buyStoryScrollView);

        initListener();

        return view;
    }


    private void initListener() {

        //设置ScrollView的滾動监听事件，用來關閉最上方的view
        mStoryScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(mBuyStoryTopView.getVisibility()==View.VISIBLE){
                    if (oldScrollY==0 & scrollY>oldScrollY) { //當一開始往上滾動時，就關掉TopView
                        mBuyStoryTopView.setVisibility(View.GONE);
                    }
                }
                else if(mBuyStoryTopView.getVisibility()==View.GONE) {
                    if (scrollY==0) { //當已經滾動到最上面時，重新顯示TopView
                        mBuyStoryTopView.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

    }


}