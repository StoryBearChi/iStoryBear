package tw.com.dean.istorybear;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.http.SslError;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ActActivity extends AppCompatActivity {
    private String searchTag;
    private Toolbar aToolbar;
    private Switch childSex;
    private ImageView mChildImage;

    private int BuyCheckItem = 0;
    private String[] items = new String[2];

    private int childNUM = 2;
    private String childMod = "ADD";

    //  private static Date BDayDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent i = getIntent();
        searchTag = i.getStringExtra("data");

        switch (searchTag) {
            case "AddEvent":
                setContentView(R.layout.page_event_addevent);
                aToolbar = (Toolbar) findViewById(R.id.AddEventBar);
                break;
            case "AddAttra":
                setContentView(R.layout.page_attraction_addattra);
                aToolbar = (Toolbar) findViewById(R.id.AddAttraBar);
                break;

            case "UpStory":
                setContentView(R.layout.page_story_up);
                aToolbar = (Toolbar) findViewById(R.id.upStoryBar);
                break;


            //   case "myBlog":
            //    setContentView(R.layout.page_userhome);
            //   aToolbar = (Toolbar) findViewById(R.id.userHomeBar);

            //     break;

            // case "myStory":
            //     setContentView(R.layout.page_userhome);
            //     aToolbar = (Toolbar) findViewById(R.id.userHomeBar);
            //     break;

            case "addFriend":
                setContentView(R.layout.page_me_addfriend);
                aToolbar = (Toolbar) findViewById(R.id.addFriend);
                break;

            case "myNotice":
                setContentView(R.layout.page_me_notice);
                aToolbar = (Toolbar) findViewById(R.id.myNoticeBar);
                break;

            case "userProfile":

                userProfile();
                break;

            case "myChild":
                setContentView(R.layout.page_me_addchild);
                aToolbar = (Toolbar) findViewById(R.id.addChild);
                childSex = (Switch) findViewById(R.id.sex_Switch);
                mChildImage = (ImageView) findViewById(R.id.myChildImage);
                //mChildImage.setAlpha(100); //灰暗處理
                break;

            case "myPoints":
                setContentView(R.layout.page_me_mypoints);
                aToolbar = (Toolbar) findViewById(R.id.myPointsBar);
                break;
            case "myEvents":
                setContentView(R.layout.com_page_result_list);
                aToolbar = (Toolbar) findViewById(R.id.resultToolbar);
                break;
            case "myRoyalty":
                setContentView(R.layout.page_me_myroyalty);
                aToolbar = (Toolbar) findViewById(R.id.myRoyaltyBar);
                break;


            case "scenesEditBtn0":
                setContentView(R.layout.dialog_mystory_scenes);
                aToolbar = (Toolbar) findViewById(R.id.scenesBar);
                break;

            case "attraLbs":
                mapLBS(searchTag);

                break;

            case "eventLbs":
                mapLBS(searchTag);

                break;


        }
        //设置左上角导航键的点击监听事件
        aToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void childAddMod(View v) {

        CharSequence name, BDay;
        TextView ChildName = null, ChildBday = null, tBday = null;
        ImageView ChildImage = null;
        CardView Big = null;

        EditText ChildNameEdit = (EditText) findViewById(R.id.childName_edit);
        EditText ChildBdayEdit = (EditText) findViewById(R.id.childBday_edit);

        // String vtag = v.getTag().toString();

        switch (childMod) {
            case "childEdit1":
                ChildName = (TextView) findViewById(R.id.childName1);
                tBday = (TextView) findViewById(R.id.childBdayT1);
                ChildBday = (TextView) findViewById(R.id.childBday1);
                ChildImage = (ImageView) findViewById(R.id.chidImage1);
                break;

            case "childEdit2":
                ChildName = (TextView) findViewById(R.id.childName2);
                tBday = (TextView) findViewById(R.id.childBdayT2);
                ChildBday = (TextView) findViewById(R.id.childBday2);
                ChildImage = (ImageView) findViewById(R.id.chidImage2);
                break;

            case "childEdit3":
                ChildName = (TextView) findViewById(R.id.childName3);
                tBday = (TextView) findViewById(R.id.childBdayT3);
                ChildBday = (TextView) findViewById(R.id.childBday3);
                ChildImage = (ImageView) findViewById(R.id.chidImage3);
                break;

            case "childEdit4":
                ChildName = (TextView) findViewById(R.id.childName4);
                tBday = (TextView) findViewById(R.id.childBdayT4);
                ChildBday = (TextView) findViewById(R.id.childBday4);
                ChildImage = (ImageView) findViewById(R.id.chidImage4);
                break;

            case "childEdit5":
                ChildName = (TextView) findViewById(R.id.childName5);
                tBday = (TextView) findViewById(R.id.childBdayT5);
                ChildBday = (TextView) findViewById(R.id.childBday5);
                ChildImage = (ImageView) findViewById(R.id.chidImage5);
                break;

            default:
                switch (childNUM) {
                    case 0:
                        Big = (CardView) findViewById(R.id.big1);
                        ChildName = (TextView) findViewById(R.id.childName1);
                        tBday = (TextView) findViewById(R.id.childBdayT1);
                        ChildBday = (TextView) findViewById(R.id.childBday1);
                        ChildImage = (ImageView) findViewById(R.id.chidImage1);
                        childNUM = 1;
                        break;

                    case 1:
                        Big = (CardView) findViewById(R.id.big2);
                        ChildName = (TextView) findViewById(R.id.childName2);
                        tBday = (TextView) findViewById(R.id.childBdayT2);
                        ChildBday = (TextView) findViewById(R.id.childBday2);
                        ChildImage = (ImageView) findViewById(R.id.chidImage2);
                        childNUM = 2;
                        break;

                    case 2:
                        Big = (CardView) findViewById(R.id.big3);
                        ChildName = (TextView) findViewById(R.id.childName3);
                        tBday = (TextView) findViewById(R.id.childBdayT3);
                        ChildBday = (TextView) findViewById(R.id.childBday3);
                        ChildImage = (ImageView) findViewById(R.id.chidImage3);
                        childNUM = 3;
                        break;

                    case 3:
                        Big = (CardView) findViewById(R.id.big4);
                        ChildName = (TextView) findViewById(R.id.childName4);
                        tBday = (TextView) findViewById(R.id.childBdayT4);
                        ChildBday = (TextView) findViewById(R.id.childBday4);
                        ChildImage = (ImageView) findViewById(R.id.chidImage4);
                        childNUM = 4;
                        break;

                    case 4:
                        Big = (CardView) findViewById(R.id.big5);
                        ChildName = (TextView) findViewById(R.id.childName5);
                        tBday = (TextView) findViewById(R.id.childBdayT5);
                        ChildBday = (TextView) findViewById(R.id.childBday5);
                        ChildImage = (ImageView) findViewById(R.id.chidImage5);
                        childNUM = 5;
                        break;

                    case 5:
                        childNUM = 6;
                        Toast.makeText(ActActivity.this, "抱歉，目前系統最多只讓您輸入5個小孩資料", Toast.LENGTH_SHORT).show();
                        break;
                }
        }

        name = ChildNameEdit.getText();
        BDay = ChildBdayEdit.getText();

        if ((!childMod.equals("ADD")) || (childNUM <= 5)) {
            if (BDay.toString().equals("") || BDay.toString().equals("")) {
                Toast.makeText(ActActivity.this, "抱歉，姓名及生日不能是空白", Toast.LENGTH_SHORT).show();
                if (childMod.equals("ADD")) {
                    childNUM--;
                }
            } else {

                if (childMod.equals("ADD")) {
                    Big.setVisibility(View.VISIBLE);
                }
                if (childSex.isChecked()) {  /* 女孩 */
                    ChildImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_girl_colors_24dp, null));
                    ChildImage.setTag("girl");

                } else { /* 男孩 */
                    ChildImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_boy_colors_24dp, null));
                    ChildImage.setTag("Boy");
                }


                ChildName.setText(name);
                ChildBday.setText(BDay);


                String dateString = BDay.toString();
                Date BDayDate = DTStringtoDate(dateString);
                Date nowDT = new Date();
                long diff = nowDT.getTime() - BDayDate.getTime();//这样得到的差值是微秒级别
                if (diff < 0) {
                    tBday.setText(getResources().getString(R.string.dueDate));
                    //xchildImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_pregnancy_colors_24dp,null));
                    // xchildImage.setImageDrawable(ContextCompat.getDrawable(ActActivity.this,R.drawable.ic_pregnancy_colors_24dp));
                } else {
                    tBday.setText(getResources().getString(R.string.Bdayis));
                }
            }
        }


        childMod = "ADD";
        if (childNUM >= 6) {
            childNUM = 5;
        }

    }

    public void childMod(View v) {

        CharSequence name, BDay;
        String ChildSex = null;
        TextView ChildName = null, ChildBday = null;
        ImageView ChildImage = null;

        EditText ChildNameEdit = (EditText) findViewById(R.id.childName_edit);
        EditText ChildBdayEdit = (EditText) findViewById(R.id.childBday_edit);
        ImageView ChildImageEdit = (ImageView) findViewById(R.id.myChildImage);

        String vtag = v.getTag().toString();

        switch (vtag) {
            case "childEdit1":
                ChildName = (TextView) findViewById(R.id.childName1);
                ChildBday = (TextView) findViewById(R.id.childBday1);
                ChildImage = (ImageView) findViewById(R.id.chidImage1);
                childMod = vtag;
                break;

            case "childEdit2":
                ChildName = (TextView) findViewById(R.id.childName2);
                ChildBday = (TextView) findViewById(R.id.childBday2);
                ChildImage = (ImageView) findViewById(R.id.chidImage2);
                childMod = vtag;
                break;

            case "childEdit3":
                ChildName = (TextView) findViewById(R.id.childName3);
                ChildBday = (TextView) findViewById(R.id.childBday3);
                ChildImage = (ImageView) findViewById(R.id.chidImage3);
                childMod = vtag;

                break;

            case "childEdit4":
                ChildName = (TextView) findViewById(R.id.childName4);
                ChildBday = (TextView) findViewById(R.id.childBday4);
                ChildImage = (ImageView) findViewById(R.id.chidImage4);
                childMod = vtag;
                break;

            case "childEdit5":
                ChildName = (TextView) findViewById(R.id.childName5);
                ChildBday = (TextView) findViewById(R.id.childBday5);
                ChildImage = (ImageView) findViewById(R.id.chidImage5);
                childMod = vtag;
                break;
        }

        if (ChildImage != null) {
            ChildSex = ChildImage.getTag().toString();
        }

        if (ChildSex.equals("girl")) { /* 女孩 */
            ChildImageEdit.setImageDrawable(getResources().getDrawable(R.drawable.ic_girl_colors_24dp, null));
            childSex.setChecked(true);
        } else { /* 男孩 */
            ChildImageEdit.setImageDrawable(getResources().getDrawable(R.drawable.ic_boy_colors_24dp, null));
            childSex.setChecked(false);

        }
        name = ChildName.getText();
        BDay = ChildBday.getText();
        ChildNameEdit.setText(name);
        ChildBdayEdit.setText(BDay);

    }

    public void childDetail(View v) {
        CharSequence name, BDay;
        String dateString, ChildSex = null;
        TextView ChildName = null, ChildBday = null;
        ImageView ChildImage = null;

        String vtag = v.getTag().toString();

        switch (vtag) {
            case "big1":
                ChildName = (TextView) findViewById(R.id.childName1);
                ChildBday = (TextView) findViewById(R.id.childBday1);
                ChildImage = (ImageView) findViewById(R.id.chidImage1);
                break;

            case "big2":
                ChildName = (TextView) findViewById(R.id.childName2);
                ChildBday = (TextView) findViewById(R.id.childBday2);
                ChildImage = (ImageView) findViewById(R.id.chidImage2);
                break;

            case "big3":
                ChildName = (TextView) findViewById(R.id.childName3);
                ChildBday = (TextView) findViewById(R.id.childBday3);
                ChildImage = (ImageView) findViewById(R.id.chidImage3);
                break;

            case "big4":
                ChildName = (TextView) findViewById(R.id.childName4);
                ChildBday = (TextView) findViewById(R.id.childBday4);
                ChildImage = (ImageView) findViewById(R.id.chidImage4);
                break;

            case "big5":
                ChildName = (TextView) findViewById(R.id.childName5);
                ChildBday = (TextView) findViewById(R.id.childBday5);
                ChildImage = (ImageView) findViewById(R.id.chidImage5);
                break;
        }


        name = ChildName.getText();
        BDay = ChildBday.getText();

        setContentView(R.layout.page_me_addchild_detail);
        Toolbar cToolbar = (Toolbar) findViewById(R.id.childDetailBar);
        TextView nowChildBday = (TextView) findViewById(R.id.nowChildBday);
        TextView tBday = (TextView) findViewById(R.id.BdayT);
        TextView AfterDay = (TextView) findViewById(R.id.afterDay);
        TextView DeliveryDay = (TextView) findViewById(R.id.deliveryDay);
        ImageView xchildImage = (ImageView) findViewById(R.id.myChildImage);
        TextView WeekText = (TextView) findViewById(R.id.weekText);
        TextView yrOld = (TextView) findViewById(R.id.YrlOld);


        if (ChildImage != null) {
            ChildSex = ChildImage.getTag().toString();

        }
        if (ChildSex.equals("girl")) { /* 女孩 */
            xchildImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_girl_colors_24dp, null));
        } else { /* 男孩 */
            xchildImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_boy_colors_24dp, null));

        }

        cToolbar.setTitle(name);

        nowChildBday.setText(BDay);
        dateString = BDay.toString();
        Date BDayDate = DTStringtoDate(dateString);
        Date nowDT = new Date();

        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        String nowMonth = sdf.format(nowDT);
        sdf = new SimpleDateFormat("dd");
        String nowDayOfMonth = sdf.format(nowDT);
        sdf = new SimpleDateFormat("MM");
        String BdayMonth = sdf.format(BDayDate);
        sdf = new SimpleDateFormat("dd");
        String BDayOfMonth = sdf.format(BDayDate);


        long diff = nowDT.getTime() - BDayDate.getTime();//这样得到的差值是微秒级别

        long days = Math.abs(diff / (1000 * 60 * 60 * 24));  //出生日與現在之天數差
        double weeks = Math.floor(days / 7);  //出生日與現在之週數差
        //  double ndays = days-weeks*7;
        long passDays = 280 - days;  //已經懷孕幾天
        double passWeeks = passDays / 7;  //已經懷孕幾週
        double passDay = passDays - passWeeks * 7;  //已經懷孕幾週又幾天
        float numYr = (float) days / 365;

        if (numYr > 0) {
            DecimalFormat df = new DecimalFormat("0.0");
            String Yr = df.format(numYr);
            yrOld.setText(Yr + "歲");
        }

        if (diff < 0) {
            tBday.setText(getResources().getString(R.string.dueDate));
            yrOld.setVisibility(View.INVISIBLE);
            if (weeks < 40) {
                AfterDay.setText("恭喜❤️您已懷孕" + String.valueOf((int) passWeeks) + "週又" + String.valueOf((int) passDay) + "天");
                DeliveryDay.setText("(再過" + String.valueOf((int) days) + "天，" + name + " 就要出生了)");
                // xchildImage.setColorFilter(Color.LTGRAY,PorterDuff.Mode.DARKEN);

                //xchildImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_pregnancy_colors_24dp,null));
                // xchildImage.setImageDrawable(ContextCompat.getDrawable(ActActivity.this,R.drawable.ic_pregnancy_colors_24dp));

                String[] Items = getResources().getStringArray(R.array.pregnancyText);
                WeekText.setText(Items[(int) passWeeks]);
            } else {
                AfterDay.setText("祝福️您能如你所規劃懷孕，並在" + String.valueOf((int) days) + "天後，生下 " + name);
                DeliveryDay.setVisibility(View.INVISIBLE);
            }
        } else if (days == 0) {
            tBday.setText("誕生:");
            yrOld.setVisibility(View.INVISIBLE);
            AfterDay.setText("恭喜" + name + "今天出生了❤️ \n祝福生產順利，平安健康");
            DeliveryDay.setVisibility(View.INVISIBLE);
        } else if (nowMonth.equals(BdayMonth) && nowDayOfMonth.equals(BDayOfMonth)) {
            //   int Yr = Math.round(days/365);
            tBday.setText("生日:");
            AfterDay.setText("恭喜 " + name + " 今天 " + Math.round(numYr) + "歲了，生日快樂🎂️");
            DeliveryDay.setVisibility(View.INVISIBLE);
        } else if (diff > 0) {
            tBday.setText("生日:");
            long n = days - (days / 365) * 365;
            AfterDay.setText("再過" + String.valueOf((int) n) + "天， " + name + "就要生日了❤️");
            DeliveryDay.setVisibility(View.INVISIBLE);
        }

        //设置左上角导航键的点击监听事件
        cToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private static Date DTStringtoDate(String dtToDate) {
        //設定日期格式
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        ParsePosition pos = new ParsePosition(0);
        java.util.Date datetime = formatter.parse(dtToDate, pos);
        java.sql.Timestamp ts = null;
        if (datetime != null) {
            ts = new java.sql.Timestamp(datetime.getTime());
        }
        return ts;
    }

    private void userProfile() {

        // String[] cItems;
        //  String[] dItems;
        setContentView(R.layout.page_me_userprofile);
        aToolbar = (Toolbar) findViewById(R.id.userProfileBar);
        Spinner TWCity22Spinner = (Spinner) findViewById(R.id.city_spinner);

        // cItems = getResources().getStringArray(R.array.TWCity22);
        // ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, cItems);

        //設定下拉選單的樣式
        // TWCity22Spinner.setAdapter(adapter);


        // ArrayAdapter<CharSequence> lunchList = ArrayAdapter.createFromResource(this,
        //         R.array.TaipeiCity,
        //         android.R.layout.simple_spinner_dropdown_item);
        // mDistrictSpinner.setAdapter(lunchList);

        //設定項目被選取之後的動作

        TWCity22Spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView adapterView, View view, int position, long id) {

                Spinner mDistrictSpinner = findViewById(R.id.district_spinner);
                ArrayAdapter<CharSequence> lunchList = null;
                switch (adapterView.getSelectedItem().toString()) {
                    case "臺北市":
                        lunchList = ArrayAdapter.createFromResource(ActActivity.this,
                                R.array.TaipeiCity,
                                android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case "新北市":
                        lunchList = ArrayAdapter.createFromResource(ActActivity.this,
                                R.array.NewTaipeiCity,
                                android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case "臺中市":
                        lunchList = ArrayAdapter.createFromResource(ActActivity.this,
                                R.array.TaichungCity,
                                android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case "高雄市":
                        lunchList = ArrayAdapter.createFromResource(ActActivity.this,
                                R.array.KaohsiungCity,
                                android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case "桃園市":
                        lunchList = ArrayAdapter.createFromResource(ActActivity.this,
                                R.array.TaoyuanCity,
                                android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case "臺南市":
                        lunchList = ArrayAdapter.createFromResource(ActActivity.this,
                                R.array.TainanCity,
                                android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case "彰化縣":
                        lunchList = ArrayAdapter.createFromResource(ActActivity.this,
                                R.array.ChanghuaCounty,
                                android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case "屏東縣":
                        lunchList = ArrayAdapter.createFromResource(ActActivity.this,
                                R.array.PingtungCounty,
                                android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case "雲林縣":
                        lunchList = ArrayAdapter.createFromResource(ActActivity.this,
                                R.array.YunlinCounty,
                                android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case "苗栗縣":
                        lunchList = ArrayAdapter.createFromResource(ActActivity.this,
                                R.array.MiaoliCounty,
                                android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case "新竹縣":
                        lunchList = ArrayAdapter.createFromResource(ActActivity.this,
                                R.array.HsinchuCounty,
                                android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case "新竹市":
                        lunchList = ArrayAdapter.createFromResource(ActActivity.this,
                                R.array.HsinchuCity,
                                android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case "嘉義縣":
                        lunchList = ArrayAdapter.createFromResource(ActActivity.this,
                                R.array.ChiayiCounty,
                                android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case "嘉義市":
                        lunchList = ArrayAdapter.createFromResource(ActActivity.this,
                                R.array.ChiayiCity,
                                android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case "南投縣":
                        lunchList = ArrayAdapter.createFromResource(ActActivity.this,
                                R.array.NantouCounty,
                                android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case "宜蘭縣":
                        lunchList = ArrayAdapter.createFromResource(ActActivity.this,
                                R.array.YilanCounty,
                                android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case "基隆市":
                        lunchList = ArrayAdapter.createFromResource(ActActivity.this,
                                R.array.KeelungCity,
                                android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case "花蓮縣":
                        lunchList = ArrayAdapter.createFromResource(ActActivity.this,
                                R.array.HualienCounty,
                                android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case "臺東縣":
                        lunchList = ArrayAdapter.createFromResource(ActActivity.this,
                                R.array.TaitungCounty,
                                android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case "金門縣":
                        lunchList = ArrayAdapter.createFromResource(ActActivity.this,
                                R.array.KinmenCounty,
                                android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case "澎湖縣":
                        lunchList = ArrayAdapter.createFromResource(ActActivity.this,
                                R.array.PenghuCounty,
                                android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case "連江縣":
                        lunchList = ArrayAdapter.createFromResource(ActActivity.this,
                                R.array.LienchiangCounty,
                                android.R.layout.simple_spinner_dropdown_item);
                        break;
                }

                mDistrictSpinner.setAdapter(lunchList);

                Toast.makeText(ActActivity.this, "您選擇" + adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }

            public void onNothingSelected(AdapterView arg0) {
                Toast.makeText(ActActivity.this, "您沒有選擇任何項目", Toast.LENGTH_SHORT).show();
            }

        });

    }

    public void mapLBS(String searchTag) {

        // String[] mItems;
        setContentView(R.layout.page_map_lbs);
        aToolbar = (Toolbar) findViewById(R.id.mapsLbsBar);
        Spinner mMapSpinner = findViewById(R.id.mapSpinner);
        ArrayAdapter<CharSequence> lunchList = null;


        switch (searchTag) {
            case "attraLbs":
                aToolbar.setTitle("附近景點");
                //建立一個ArrayAdapter物件，並放置下拉選單的內容
                lunchList = ArrayAdapter.createFromResource(ActActivity.this,
                        R.array.attraClass_LBS,
                        android.R.layout.simple_spinner_dropdown_item);
                break;
            case "eventLbs":
                aToolbar.setTitle("附近活動");
                lunchList = ArrayAdapter.createFromResource(ActActivity.this,
                        R.array.eventClass_LBS,
                        android.R.layout.simple_spinner_dropdown_item);
                break;
        }
        mMapSpinner.setAdapter(lunchList); //設定下拉選單的樣式


        //    if (searchTag.equals("attraLbs")){ /* 附近景點 */
        //建立一個ArrayAdapter物件，並放置下拉選單的內容
        //        aToolbar.setTitle("附近景點");
        //        mItems = getResources().getStringArray(R.array.attraClass_LBS);
        //    } else { /* 附近活動 */
        //         aToolbar.setTitle("附近活動");
        //     mItems = getResources().getStringArray(R.array.eventClass_LBS);
        // }
        // ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, mItems);
        //設定下拉選單的樣式
        //  mMapSpinner.setAdapter(adapter);

        //設定項目被選取之後的動作
        mMapSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView adapterView, View view, int position, long id) {
                Toast.makeText(ActActivity.this, "您選擇" + adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }

            public void onNothingSelected(AdapterView arg0) {
                Toast.makeText(ActActivity.this, "您沒有選擇任何項目", Toast.LENGTH_SHORT).show();
            }
        });

        /**假裝有Maps**/

        WebView webview = (WebView) findViewById(R.id.myMap);
        WebSettings webSettings = webview.getSettings();

        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        // 若加载的 html 里有JS 在执行动画等操作，会造成资源浪费（CPU、电量）
        // 在 onStop 和 onResume 里分别把 setJavaScriptEnabled() 给设置成 false 和 true 即可
        webSettings.setJavaScriptEnabled(true);

        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        webview.setWebViewClient(new WebViewClient());
        webview.loadUrl("https://www.google.com.tw/maps?hl=zh-TW");

        webview.setWebViewClient(new WebViewClient() {
            //设置不用系统浏览器打开,直接显示在当前Webview
            //   @Override
            //  public boolean shouldOverrideUrlLoading(WebView view, String url) {
            //       view.loadUrl(url);
            //       return true;
            //   }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }

        });


    }

    public void setSix(View v) {

        if (childSex.isChecked()) { /* 選擇女孩 */
            mChildImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_girl_colors_24dp, null));
            //mChildImage.setAlpha(255);
            Toast.makeText(ActActivity.this, "已選擇女孩", Toast.LENGTH_SHORT).show();
        } else { /* 選擇男孩 */
            mChildImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_boy_colors_24dp, null));
            //mChildImage.setAlpha(255);
            Toast.makeText(ActActivity.this, "已選擇男孩", Toast.LENGTH_SHORT).show();
        }

    }

    public void ActDialogBtnClk(View v) {

        String vtag = v.getTag().toString();
        switch (vtag) {
            case "buyPoints":
                setDialogBroadcast(vtag);

                break;

            case "toCash":
                setDialogBroadcast(vtag);

                break;

            case "toPoints":
                setDialogBroadcast(vtag);

                break;

            case "childBday":
/*
                EditText ChildBdayEdit = (EditText) findViewById(R.id.childBday_edit);
                CharSequence BDayEdit = ChildBdayEdit.getText();
                String dateString = BDayEdit.toString();
                BDayDate=DTStringtoDate(dateString);
*/
                new DateDialog(ActActivity.this, new DateDialog.DateCallBack() {
                    @Override
                    public void onClick(String date) {
                        EditText date_textview = (EditText) findViewById(R.id.childBday_edit);
                        date_textview.setText(date);

                        Toast.makeText(ActActivity.this, date + "", Toast.LENGTH_LONG).show();
                    }
                });

                //setDatePickerDialog();

                break;

        }


    }

    public static class DateDialog implements DatePicker.OnDateChangedListener {
        private String dateTime;
        private Context context;
        private DateCallBack listener;
        private DatePicker datePicker;
        private View view;

        /**
         * 日期弹出选择框构造函数
         *
         * @param context：获取组件的上下文
         */
        public DateDialog(Context context, DateCallBack listener) {
            this.context = context;
            this.listener = listener;
            //初始化视图中的控件
            initPicker();
            //初始化Dialog
            initDialog();

        }

        /**
         * 初始化Picker和主视图
         */
        private void initPicker() {
            view = LayoutInflater.from(context).inflate(R.layout.dialog_gosetdate, null);

            datePicker = (DatePicker) view.findViewById(R.id.date);

            /*
            EditText ChildBdayEdit = (EditText) findViewById(R.id.childBday_edit);
            CharSequence BDayEdit = ChildBdayEdit.getText();
            String dateString = BDayEdit.toString();
            Date BDayDate=DTStringtoDate(dateString);
            */

            Calendar calendar = Calendar.getInstance();
            //calendar.setTime(BDayDate);
            //隐藏年份 spinner 控件,也可以隐藏月份或日份
            //hidYear(datePicker);
            datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH), this);
        }

        /**
         * 弹出日期时间选择框方法
         */
        public void initDialog() {
            //初始化Dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setIcon(R.drawable.ic_logo_round_colors_24dp);
            builder.setView(view);
            builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    listener.onClick(dateTime);
                }
            });
            builder.setNegativeButton("取消", null).create();
            builder.show();
            //刷新数据
            onDateChanged(null, 0, 0, 0);
        }

        @Override
        public void onDateChanged(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
            Calendar calendar = Calendar.getInstance();

            calendar.set(datePicker.getYear(), datePicker.getMonth(),
                    datePicker.getDayOfMonth());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            dateTime = sdf.format(calendar.getTime());
        }

        //采用接口回调的方式 获取点击dialog的'设置'之后的数据
        public interface DateCallBack {
            public void onClick(String date);
        }
    }

    private void setDialogBroadcast(String vtag) {
        String mTitle = "a title";
        BuyCheckItem = 0;
        switch (vtag) {
            case "buyPoints":
                mTitle = "信用卡購點";
                items[0] = "購買100點($100)";
                items[1] = "購買550點($500) \n* 加贈送10% *";
                break;
            case "toPoints":
                mTitle = "版稅兌點";
                items[0] = "$100 兌換 120點 \n* 加贈送20% *";
                items[1] = "$500 兌換 600點 \n* 加贈送20% *";
                break;

            case "toCash":
                mTitle = "版稅兌現";
                items[0] = "兌現＄1000";
                items[1] = "兌現＄5000";
                break;
        }


        new AlertDialog.Builder(ActActivity.this)
                //    .setView(viewDialogBroadcast)
                .setTitle(mTitle)
                .setIcon(R.drawable.ic_logo_round_colors_24dp)
                .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        BuyCheckItem = which;

                    }
                })
                .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(ActActivity.this, items[BuyCheckItem], Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })

                .create().show();

    }

/*
    private void setDatePickerDialog() {



        Calendar nowdate = Calendar.getInstance();
       // int mYear = nowdate.get(Calendar.YEAR);
       // int mMonth = nowdate.get(Calendar.MONTH);
       // int mDay = nowdate.get(Calendar.DAY_OF_MONTH);


        new DatePickerDialog(ActActivity.this,DatePickerDialog.THEME_DEVICE_DEFAULT_DARK, onDateSetListener,
                nowdate.get(Calendar.YEAR), nowdate.get(Calendar.MONTH), nowdate.get(Calendar.DAY_OF_MONTH)).show();
       // new DatePickerDialog(ActActivity.this, onDateSetListener, mYear, mMonth, mDay).show();



    }

    private DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            int mYear = year;
            int mMonth = monthOfYear+1;
            int mDay = dayOfMonth;
            EditText date_textview = (EditText) findViewById(R.id.childBday_edit);
            String days = new StringBuffer().append(mYear).append("/").append(mMonth).append("/").append(mDay).toString();
            date_textview.setText(days);
        }
    };
*/

}
