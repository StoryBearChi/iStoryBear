package tw.com.dean.istorybear;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class Fragment_blog extends Fragment {

  //  public RelativeLayout mblogTopView;
    public NestedScrollView mBlogScrollView;
    public LinearLayout mBlogTopBar;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_blog, container, false);

        mBlogScrollView =(NestedScrollView) view.findViewById(R.id.blogScrollView);
        mBlogTopBar =(LinearLayout) view.findViewById(R.id.blogTopBar);

        initListener();
        return view;
    }


    private void initListener() {

        //设置ScrollView的滾動监听事件，用來關閉最上方的view
        mBlogScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(mBlogTopBar.getVisibility()==View.GONE){
                    if ( oldScrollY > MainActivity.topbar_visibility_height ) { //當往上滾動隱藏BtnBar時，就顯示TopBtnBar
                        mBlogTopBar.setVisibility(View.VISIBLE);
                    }
                }
                else if(mBlogTopBar.getVisibility()==View.VISIBLE) {
                    if ( oldScrollY < MainActivity.topbar_gone_height ) { //當往上滾動出現BtnBar時，就不顯示TopBtnBar
                        mBlogTopBar.setVisibility(View.GONE);
                    }
                }
            }
        });

    }


}