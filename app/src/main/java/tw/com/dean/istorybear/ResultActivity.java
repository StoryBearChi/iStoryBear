package tw.com.dean.istorybear;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


//AppCompatActivity
public class ResultActivity extends Activity {
    private String searchTag;
    private WebView webview;
    private Toolbar rToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.com_page_result_list);
        rToolbar = (Toolbar) findViewById(R.id.resultToolbar);
        ButtonBarLayout spinner = (ButtonBarLayout) findViewById(R.id.spinnerLayout);
        NestedScrollView rScrollView =(NestedScrollView) findViewById(R.id.rScrollView);

        //设置左上角导航键的点击监听事件
        rToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //设置ScrollView的滾動监听事件，用來關閉網頁
        rScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
              //  Toast.makeText(ResultActivity.this, "onScrollChanged", Toast.LENGTH_SHORT).show();
                webview.setVisibility(View.GONE);
            }
        });

        Intent i = getIntent();
        searchTag = i.getStringExtra("data");

        switch (searchTag) {
            case "ArticleSearch":
                //   spinner.setVisibility(View.INVISIBLE);
                //rToolbar.setTitle("搜尋文章");
                break;

            case "BlogSearch":
                spinner.setVisibility(View.INVISIBLE);
                break;

            case "EventSearch":
                spinner.setVisibility(View.INVISIBLE);
                break;

            case "AttraSearch":
                spinner.setVisibility(View.INVISIBLE);
                break;

            case "StorySearch":
                spinner.setVisibility(View.INVISIBLE);
                break;

            case "BookSearch":
                spinner.setVisibility(View.INVISIBLE);
                break;

            case "ArticleLove":
                Toast.makeText(this, "列出已收藏文章", Toast.LENGTH_SHORT).show();
                rToolbar.setTitle("我收藏的文章");
                break;

            case "BlogLove":
                Toast.makeText(this, "列出已關注專欄", Toast.LENGTH_SHORT).show();
                rToolbar.setTitle("我關注的專欄");
                break;

            case "EventLove":
                Toast.makeText(this, "列出收藏活動", Toast.LENGTH_SHORT).show();
                rToolbar.setTitle("我收藏的活動");
                break;

            case "AttraLove":
                Toast.makeText(this, "列出收藏景點", Toast.LENGTH_SHORT).show();
                rToolbar.setTitle("我收藏的景點");
                break;

            case "StoryPromotion":
                Toast.makeText(this, "列出促銷故事", Toast.LENGTH_SHORT).show();
                rToolbar.setTitle(R.string.storyPromotion);
                break;

            case "StoryLove":
                Toast.makeText(this, "列出收藏故事", Toast.LENGTH_SHORT).show();
                rToolbar.setTitle("我收藏的故事");
                break;

            case "BookPromotion":
                Toast.makeText(this, "列出限時優惠", Toast.LENGTH_SHORT).show();
                rToolbar.setTitle(R.string.storyPromotion);
                break;

            case "BookLove":
                Toast.makeText(this, "列出收藏商品", Toast.LENGTH_SHORT).show();
                rToolbar.setTitle(R.string.myLoveGoods);
                break;

            case "AddStory":  // 找不到在哪裡
                Toast.makeText(this, "列出收藏商品", Toast.LENGTH_SHORT).show();
                rToolbar.setTitle("搜尋故事");
                break;

            case "moreNewEvents":
                Toast.makeText(this, "列出最新活動", Toast.LENGTH_SHORT).show();
                rToolbar.setTitle(R.string.eventNew);
                break;

            case "eventSponsorList":
                Toast.makeText(this, "列出推播活動", Toast.LENGTH_SHORT).show();
                rToolbar.setTitle(R.string.eventSponsor);
                break;

            case "moreNewAttra":
                Toast.makeText(this, "列出最新景點", Toast.LENGTH_SHORT).show();
                rToolbar.setTitle(R.string.attractionNew);
                break;

            case "attraSponsorList":
                Toast.makeText(this, "列出推播景點", Toast.LENGTH_SHORT).show();
                rToolbar.setTitle(R.string.attractionSponsor);
                break;

            case "notblogMore":
                Toast.makeText(this, "列出熱門專欄", Toast.LENGTH_SHORT).show();
                rToolbar.setTitle(R.string.hotblog);
                break;

            case "newStoryMore":
                Toast.makeText(this, "列出最新故事", Toast.LENGTH_SHORT).show();
                rToolbar.setTitle(R.string.new_story);
                break;

            case "newGroupbuyMore":
                Toast.makeText(this, "列出最新團購", Toast.LENGTH_SHORT).show();
                rToolbar.setTitle(R.string.newGroupbuy);
                break;

            case "hotGroupbuyMore":
                Toast.makeText(this, "列出熱門團購", Toast.LENGTH_SHORT).show();
                rToolbar.setTitle(R.string.hotGroupbuy);
                break;

            case "timeGroupbuyMore":
                Toast.makeText(this, "列出限時團購", Toast.LENGTH_SHORT).show();
                rToolbar.setTitle(R.string.timeGroupbuy);
                break;

            case "limitedGroupbuyMore":
                Toast.makeText(this, "列出限量團購", Toast.LENGTH_SHORT).show();
                rToolbar.setTitle(R.string.limitedGroupbuy);
                break;
        }

        /**廣告頁處理 **/

        webview = (WebView) findViewById(R.id.missingkids2);
        WebSettings webSettings = webview.getSettings();

        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        // 若加载的 html 里有JS 在执行动画等操作，会造成资源浪费（CPU、电量）
        // 在 onStop 和 onResume 里分别把 setJavaScriptEnabled() 给设置成 false 和 true 即可
        webSettings.setJavaScriptEnabled(true);

        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        webview.setBackgroundColor(Color.TRANSPARENT);

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


}
