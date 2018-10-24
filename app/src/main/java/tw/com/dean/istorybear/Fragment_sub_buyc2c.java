package tw.com.dean.istorybear;

import android.annotation.TargetApi;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Fragment_sub_buyc2c extends Fragment {

    WebView webview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_buy_c2c, container, false);

        webview = (WebView) view.findViewById(R.id.C2Cwebview);
        WebSettings webSettings = webview.getSettings();


//如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
        //  webview.getSettings().setJavaScriptEnabled(true);
// 若加载的 html 里有JS 在执行动画等操作，会造成资源浪费（CPU、电量）
// 在 onStop 和 onResume 里分别把 setJavaScriptEnabled() 给设置成 false 和 true 即可


//支持插件
        //  webSettings.setPluginsEnabled(true);

//设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

//缩放操作
        // webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        //  webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        //  webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

//其他细节操作
        //  webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        //  webSettings.setAllowFileAccess(true); //设置可以访问文件
        //  webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        //  webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        //  webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式

        /** 成功攔截KeyEvent.KEYCODE_BACK 並且消費掉 **/
        webview.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
                    webview.goBack();
                    return true;
                }
                return false;
            }
        });

        //设置不用系统浏览器打开,直接显示在当前Webview
        webview.setWebViewClient(new WebViewClient() {

            @Override   //擋掉跑到missingkids首頁
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.contains("404page.missingkids.org.tw")) {
                    view.loadUrl("https://404page.missingkids.org.tw/api?key=ReMVHyfCTY8mUzMBzyn");
                } else {
                    view.loadUrl(url);
                }
                return true;
            }

            @Override  //讓webview能打開https網頁
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {

                handler.proceed();
            }

            // 旧版本，会在新版本中也可能被调用，所以加上一个判断，防止重复显示
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    return;
                }
                //發生網頁錯誤，就去開404page.missingkids
                view.loadUrl("https://404page.missingkids.org.tw/api?key=ReMVHyfCTY8mUzMBzyn");
            }

            // 新版本，只会在Android6及以上调用
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                if (request.isForMainFrame()){ // 或者： if(request.getUrl().toString() .equals(getUrl()))
                    //發生網頁錯誤，就去開404page.missingkids
                    view.loadUrl("https://404page.missingkids.org.tw/api?key=ReMVHyfCTY8mUzMBzyn");
                }
            }

            //步骤1：写一个html文件（error_handle.html），用于出错时展示给用户看的提示页面
            // 步骤2：将该html文件放置到代码根目录的assets文件夹下
            //步骤3：复写WebViewClient的onRecievedError方法
            //该方法传回了错误码，根据错误类型可以进行不同的错误分类处理
            //    @Override
            //   public void onReceivedError(WebView view, int errorCode, String description, String failingUrl){
            // switch(errorCode)
            //  {
            // case HttpStatus.SC_NOT_FOUND:
            //     view.loadUrl("https://404page.missingkids.org.tw/api?key=ReMVHyfCTY8mUzMBzyn");
            //     break;
            //   }
            //      view.loadUrl("https://404page.missingkids.org.tw/api?key=ReMVHyfCTY8mUzMBzyn");
            //   };


            //     @Override
            //     public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
            //        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && view.canGoBack()) {
            //             view.goBack();
            //             return true;
            //         }
            //         return false;
            //     }

        });
        // webview.setWebViewClient(new WebViewClient());
        webview.loadUrl("https://www.wesmilegood.com/products/toy/books/toddler-books/");
        return view;
    }


    //点击返回上一页面而不是退出浏览器
    /**
     @Override public boolean super.onKeyDown(int keyCode, KeyEvent event) {

     if (keyCode == KeyEvent.KEYCODE_BACK && webview.canGoBack()) {
     webview.goBack();
     return true;
     }

     return onKeyDown(keyCode, event);
     }
     **/

}