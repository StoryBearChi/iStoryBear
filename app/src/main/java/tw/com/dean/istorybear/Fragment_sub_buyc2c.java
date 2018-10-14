package tw.com.dean.istorybear;

import android.net.http.SslError;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
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
// 若加载的 html 里有JS 在执行动画等操作，会造成资源浪费（CPU、电量）
// 在 onStop 和 onResume 里分别把 setJavaScriptEnabled() 给设置成 false 和 true 即可

        webview.setWebViewClient(new WebViewClient());
        webview.loadUrl("https://www.wesmilegood.com/products/toy/books/toddler-books/");
        //webview.loadUrl("https://404page.missingkids.org.tw/");

//支持插件
      //  webSettings.setPluginsEnabled(true);

//设置自适应屏幕，两者合用
     //   webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
   //     webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

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

        //webview.loadUrl("https://404page.missingkids.org.tw/api?key=ReMVHyfCTY8mUzMBzyn");
        //webview.loadUrl("https://404page.missingkids.org.tw/");

        //设置不用系统浏览器打开,直接显示在当前Webview
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
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
        });

        return view;
    }

    /**
    @Override
    public void onUnhandledKeyEvent(WebView view, KeyEvent event) {
        if (event == KEYCODE_BACK && view.canGoBack()) {
            webview.goBack();
            return true;
        }
        return;
    }

**/
    //点击返回上一页面而不是退出浏览器
    /**
    @Override
    public boolean super.onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && webview.canGoBack()) {
            webview.goBack();
            return true;
        }

        return onKeyDown(keyCode, event);
    }
    **/

}