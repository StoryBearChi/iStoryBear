package tw.com.dean.istorybear;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;


//AppCompatActivity
public class SearchActivity extends Activity {
    private String searchTag;

    int keyWordArrayCount=8;
    TextView[] keyWordArray = new TextView[keyWordArrayCount];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.com_page_search);

        Toolbar toolbar = (Toolbar) findViewById(R.id.sToolbar);
        keyWordArray[0] = (TextView) findViewById(R.id.hotAttra0);
        keyWordArray[1] = (TextView) findViewById(R.id.hotAttra1);
        keyWordArray[2] = (TextView) findViewById(R.id.hotAttra2);
        keyWordArray[3] = (TextView) findViewById(R.id.hotAttra3);
        keyWordArray[4] = (TextView) findViewById(R.id.hotAttra4);
        keyWordArray[5] = (TextView) findViewById(R.id.hotAttra5);
        keyWordArray[6] = (TextView) findViewById(R.id.hotAttra6);
        keyWordArray[7] = (TextView) findViewById(R.id.hotAttra7);
        TableLayout storyClass =(TableLayout) findViewById(R.id.storyClassLayout);
        WebView webview = (WebView) findViewById(R.id.missingkids);


        //setSupportActionBar(toolbar);
        //设置左上角导航键的点击监听事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { finish(); }
        });

        Intent i=getIntent();

        searchTag = i.getStringExtra("data");
        switch (searchTag)
        {
            case "ArticleSearch":
                toolbar.setTitle("搜尋文章");
                keyWordArray[0].setText("嬰兒水中毒");
                keyWordArray[1].setText("副食品");
                keyWordArray[2].setText("腸病毒");
                break;

            case "BlogSearch":
                toolbar.setTitle("搜尋專欄");
                keyWordArray[0].setText("陳櫻慧");
                keyWordArray[1].setText("盧怡方");
                keyWordArray[2].setText("陳彥如");
                keyWordArray[3].setText("周姵慈");
                break;

            case "EventSearch":
                toolbar.setTitle("搜尋活動");
                keyWordArray[0].setText("說故事");
                keyWordArray[1].setText("方方老師");
                keyWordArray[2].setText("鬆餅姐姐");
                keyWordArray[3].setText("吐司姐姐");
                keyWordArray[4].setText("蠟筆哥哥");
                break;

            case "AttraSearch":
                toolbar.setTitle("搜尋景點");
                keyWordArray[0].setText("溜滑梯");
                keyWordArray[1].setText("餵魚");
                keyWordArray[2].setText("野餐");
                keyWordArray[3].setText("露營");
                break;

            case "StorySearch":
                toolbar.setTitle("搜尋故事");
                /** 顯示故事分類欄 **/
                storyClass.setVisibility(View.VISIBLE);
                /** 關掉廣告頁 **/
                webview.setVisibility(View.INVISIBLE);
                keyWordArray[0].setText("陳櫻慧");
                keyWordArray[1].setText("盧怡方");
                keyWordArray[2].setText("骨頭島");
                keyWordArray[3].setText("好好狐狸");
                keyWordArray[4].setText("寵物離家記");
                keyWordArray[5].setText("大野狼別吃我");
                keyWordArray[6].setText("阿墨的故事屋");
                keyWordArray[7].setText("孤單的毛毛蟲");
                break;

            case "BookSearch":
                toolbar.setTitle("搜尋繪本");
                keyWordArray[0].setText("陳櫻慧");
                keyWordArray[1].setText("盧怡方");
                keyWordArray[2].setText("骨頭島");
                keyWordArray[3].setText("好好狐狸");
                keyWordArray[4].setText("大野狼別吃我");
                keyWordArray[5].setText("阿墨的故事屋");
                keyWordArray[6].setText("寵物離家記");
                break;

        }

        /** 把關鍵字顯示出來 **/
        for(int n = 0; n < keyWordArrayCount; n++) {
            if (keyWordArray[n].getText() != ""){
                keyWordArray[n].setVisibility(View.VISIBLE);
            }
            else {
                n=keyWordArrayCount;
            }
        }



        /**廣告頁處理 **/

        WebSettings webSettings = webview.getSettings();

        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        // 若加载的 html 里有JS 在执行动画等操作，会造成资源浪费（CPU、电量）
        // 在 onStop 和 onResume 里分别把 setJavaScriptEnabled() 给设置成 false 和 true 即可
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

        /**廣告頁處理 **/

    }

    public void searchGoBtn(View v)
    {
        Intent i=new Intent(SearchActivity.this,ResultActivity.class);
        //把按下的Btn tag傳給ResultActivity
        i.putExtra("data",searchTag);
        //將原本Activity的換成ResultActivity
        startActivity(i);
    }


}
