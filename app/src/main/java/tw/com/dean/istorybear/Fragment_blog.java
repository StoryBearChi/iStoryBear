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

    public RelativeLayout mblogTopView;
    public NestedScrollView mBlogScrollView;
    public View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_blog, container, false);

        mBlogScrollView =(NestedScrollView) view.findViewById(R.id.blogScrollView);
        mblogTopView =(RelativeLayout) view.findViewById(R.id.blogTopView);

        initListener();
        return view;
    }


    private void initListener() {

        //设置ScrollView的滾動监听事件，用來關閉最上方的view
        mBlogScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(mblogTopView.getVisibility()==View.VISIBLE){
                    if (oldScrollY==0 & scrollY>oldScrollY) { //當一開始往上滾動時，就關掉TopView
                     //   Snackbar.make(view, Integer.toString(oldScrollY)+"->"+Integer.toString(scrollY), Snackbar.LENGTH_LONG)
                      //          .show();
                        mblogTopView.setVisibility(View.GONE);
                    }
                }
                else if(mblogTopView.getVisibility()==View.GONE) {
                    if (scrollY==0) { //當已經滾動到最上面時，重新顯示TopView
                      //  Snackbar.make(view, Integer.toString(oldScrollY)+"->"+Integer.toString(scrollY), Snackbar.LENGTH_LONG)
                      //          .show();
                        mblogTopView.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

    }


}