package tw.com.dean.istorybear;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class Fragment_sub_buygroupbuy extends Fragment {

    public RelativeLayout mGroupbuyTopView;
    public NestedScrollView mGroupbuyScrollView;
    public View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_buy_groupbuy, container, false);

        mGroupbuyTopView =(RelativeLayout) view.findViewById(R.id.groupbuyTopView);
        mGroupbuyScrollView =(NestedScrollView) view.findViewById(R.id.groupbuyScrollView);

        initListener();

        return view;
    }

    private void initListener() {

        //设置ScrollView的滾動监听事件，用來關閉最上方的view
        mGroupbuyScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(mGroupbuyTopView.getVisibility()==View.VISIBLE){
                    if (oldScrollY==0 & scrollY>oldScrollY) { //當一開始往上滾動時，就關掉TopView
                        mGroupbuyTopView.setVisibility(View.GONE);
                    }
                }
                else if(mGroupbuyTopView.getVisibility()==View.GONE) {
                    if (scrollY==0) { //當已經滾動到最上面時，重新顯示TopView
                        mGroupbuyTopView.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

    }


}