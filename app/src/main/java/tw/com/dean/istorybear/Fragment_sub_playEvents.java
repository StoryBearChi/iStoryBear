package tw.com.dean.istorybear;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class Fragment_sub_playEvents extends Fragment {

   // public RelativeLayout mEventTopView;
    public NestedScrollView mEventScrollView;

    public LinearLayout mEventTopBtnBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_play_events, container, false);

       // mEventTopView =(RelativeLayout) view.findViewById(R.id.eventTopView);
        mEventScrollView =(NestedScrollView) view.findViewById(R.id.eventScrollView);

        mEventTopBtnBar =(LinearLayout) view.findViewById(R.id.eventTopBtnBar);


        initListener();

        return view;
    }

    private void initListener() {

        //设置ScrollView的滾動监听事件，用來關閉最上方的view
        mEventScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                if(mEventTopBtnBar.getVisibility()==View.GONE){
                    if ( oldScrollY > MainActivity.topbar_visibility_height ) { //當往上滾動隱藏BtnBar時，就顯示TopBtnBar
                        mEventTopBtnBar.setVisibility(View.VISIBLE);
                    }
                }
                else if(mEventTopBtnBar.getVisibility()==View.VISIBLE) {
                    if ( oldScrollY < MainActivity.topbar_gone_height ) { //當往上滾動出現BtnBar時，就不顯示TopBtnBar
                        mEventTopBtnBar.setVisibility(View.GONE);
                    }
                }
                /*
                if(mEventTopView.getVisibility()==View.VISIBLE){
                    if (oldScrollY==0 & scrollY>oldScrollY) { //當一開始往上滾動時，就關掉TopView
                        mEventTopView.setVisibility(View.GONE);
                    }
                }
                else if(mEventTopView.getVisibility()==View.GONE) {
                    if (scrollY==0) { //當已經滾動到最上面時，重新顯示TopView
                        mEventTopView.setVisibility(View.VISIBLE);
                    }
                }
                */
            }
        });

    }

}