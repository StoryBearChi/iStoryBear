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

public class Fragment_sub_buygroupbuy extends Fragment {

  //  public RelativeLayout mGroupbuyTopView;
    public NestedScrollView mGroupbuyScrollView;
    public LinearLayout mGroupbuyTopBar;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buy_groupbuy, container, false);

       // mGroupbuyTopView =(RelativeLayout) view.findViewById(R.id.groupbuyTopView);
        mGroupbuyScrollView =(NestedScrollView) view.findViewById(R.id.groupbuyScrollView);
        mGroupbuyTopBar =(LinearLayout) view.findViewById(R.id.groupbuyTopBar);

        initListener();

        return view;
    }

    private void initListener() {

        //设置ScrollView的滾動监听事件，用來關閉最上方的view
        mGroupbuyScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(mGroupbuyTopBar.getVisibility()==View.GONE){
                    if ( oldScrollY > MainActivity.topbar_visibility_height ) { //當往上滾動隱藏BtnBar時，就顯示TopBtnBar
                        mGroupbuyTopBar.setVisibility(View.VISIBLE);
                    }
                }
                else if(mGroupbuyTopBar.getVisibility()==View.VISIBLE) {
                    if ( oldScrollY < MainActivity.topbar_gone_height ) { //當往上滾動出現BtnBar時，就不顯示TopBtnBar
                        mGroupbuyTopBar.setVisibility(View.GONE);
                    }
                }
            }
        });

    }


}