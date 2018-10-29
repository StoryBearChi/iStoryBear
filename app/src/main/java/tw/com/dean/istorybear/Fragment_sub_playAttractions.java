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
import android.widget.Toast;


public class Fragment_sub_playAttractions extends Fragment {

   // public RelativeLayout mAttraTopView;
    public NestedScrollView mAttraScrollView;
    public LinearLayout mAttraTopBtnBar;
   // public View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_play_attractions, container, false);

      //  mAttraTopView =(RelativeLayout) view.findViewById(R.id.attraTopView);
        mAttraScrollView =(NestedScrollView) view.findViewById(R.id.attraScrollView);

        mAttraTopBtnBar =(LinearLayout) view.findViewById(R.id.attraTopBtnBar);

        initListener();

        return view;
    }

    private void initListener() {

        //设置ScrollView的滾動监听事件，用來關閉最上方的view
        mAttraScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                /*
                if(mAttraTopView.getVisibility()==View.VISIBLE){
                    if (oldScrollY==0 & scrollY>oldScrollY) { //當一開始往上滾動時，就關掉TopView
                        mAttraTopView.setVisibility(View.GONE);
                    }
                }
                else if(mAttraTopView.getVisibility()==View.GONE) {
                    if (scrollY==0) { //當已經滾動到最上面時，重新顯示TopView
                        mAttraTopView.setVisibility(View.VISIBLE);
                    }
                }
*/
                if(mAttraTopBtnBar.getVisibility()==View.GONE){
                    if ( oldScrollY > MainActivity.topbar_visibility_height ) { //當往上滾動隱藏BtnBar時，就顯示TopBtnBar
                        mAttraTopBtnBar.setVisibility(View.VISIBLE);
                    }
                }
                else if(mAttraTopBtnBar.getVisibility()==View.VISIBLE) {
                    if ( oldScrollY < MainActivity.topbar_gone_height ) { //當往上滾動出現BtnBar時，就不顯示TopBtnBar
                        mAttraTopBtnBar.setVisibility(View.GONE);
                    }
                }

            }
        });

    }


}