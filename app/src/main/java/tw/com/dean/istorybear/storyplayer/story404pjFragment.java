package tw.com.dean.istorybear.storyplayer;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import tw.com.dean.istorybear.R;

public class story404pjFragment extends Fragment {

    private StoryPlayViewModel mViewModel;

    public static story404pjFragment newInstance() {
        return new story404pjFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_playstory_404pj, container, false);

        WebView webview = (WebView) view.findViewById(R.id.missingkids3);
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);

        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        //设置不用系统浏览器打开,直接显示在当前Webview
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.contains("404page.missingkids.org.tw")) {
                    view.loadUrl("https://404page.missingkids.org.tw/api?key=ReMVHyfCTY8mUzMBzyn");

                }else if (url.contains("404page.missingkids.org.tw/api?key=ReMVHyfCTY8mUzMBzyn")){
                    view.loadUrl(url);
                }else {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                }
                return true;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
        });
        // webview.setWebViewClient(new WebViewClient());
        webview.loadUrl("https://404page.missingkids.org.tw/api?key=ReMVHyfCTY8mUzMBzyn");

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(StoryPlayViewModel.class);
        // TODO: Use the ViewModel
    }

}
