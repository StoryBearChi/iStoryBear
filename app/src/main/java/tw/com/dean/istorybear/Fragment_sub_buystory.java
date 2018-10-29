package tw.com.dean.istorybear;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by tomoya on 4/5/17.
 */

public class Fragment_sub_buystory extends Fragment {

   // public RelativeLayout mBuyStoryTopView;
    public NestedScrollView mStoryScrollView;
    public LinearLayout mBuyStoryTopBar;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_story_buystory, container, false);

     //   mBuyStoryTopView =(RelativeLayout) view.findViewById(R.id.buyStoryTopView);
        mStoryScrollView =(NestedScrollView) view.findViewById(R.id.buyStoryScrollView);

        mBuyStoryTopBar =(LinearLayout) view.findViewById(R.id.buyStoryTopBar);

        initListener();

        return view;
    }


    private void initListener() {

        //设置ScrollView的滾動监听事件，用來關閉最上方的view
        mStoryScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(mBuyStoryTopBar.getVisibility()==View.GONE){
                    if ( oldScrollY > MainActivity.topbar_visibility_height ) { //當往上滾動隱藏BtnBar時，就顯示TopBtnBar
                        mBuyStoryTopBar.setVisibility(View.VISIBLE);
                    }
                }
                else if(mBuyStoryTopBar.getVisibility()==View.VISIBLE) {
                    if ( oldScrollY < MainActivity.topbar_gone_height ) { //當往上滾動出現BtnBar時，就不顯示TopBtnBar
                        mBuyStoryTopBar.setVisibility(View.GONE);
                    }
                }
            }
        });

    }


}