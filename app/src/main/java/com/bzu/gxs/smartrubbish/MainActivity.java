package com.bzu.gxs.smartrubbish;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.Text;
import com.amap.api.services.core.LatLonPoint;
import com.bzu.gxs.smartrubbish.Data.user_data;
import com.bzu.gxs.smartrubbish.chenjin.StatusBarUtil;
import com.bzu.gxs.smartrubbish.get.getDateSx;
import com.bzu.gxs.smartrubbish.views.Adapter;
import com.bzu.gxs.smartrubbish.views.GifView;
import com.bzu.gxs.smartrubbish.views.SlideViewPager;
import com.idtk.smallchart.chart.PieChart;
import com.idtk.smallchart.data.PieData;
import com.idtk.smallchart.interfaces.iData.IPieData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends Activity {

    @Bind(R.id.vp_horizontal_ntb)
    ViewPager vpHorizontalNtb;
    @Bind(R.id.bg_ntb_horizontal)
    View bgNtbHorizontal;
    @Bind(R.id.ntb_horizontal)
    NavigationTabBar ntbHorizontal;
    @Bind(R.id.wrapper_ntb_horizontal)
    FrameLayout wrapperNtbHorizontal;

    SharedPreferences sp;

    NavigationTabBar navigationTabBar;
    SlideViewPager viewPager;
    private TextView textView;
    Bundle savedInstanceState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.savedInstanceState=savedInstanceState;
        ButterKnife.bind(this);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        //当FitsSystemWindows设置 true 时，会在屏幕最上方预留出状态栏高度的 padding
//        StatusBarUtil.setRootViewFitsSystemWindows(this,true);
        //设置状态栏透明
        StatusBarUtil.setTranslucentStatus(this);
        //一般的手机的状态栏文字和图标都是白色的, 可如果你的应用也是纯白色的, 或导致状态栏文字看不清
        //所以如果你是这种情况,请使用以下代码, 设置状态使用深色文字图标风格, 否则你可以选择性注释掉这个if内容
        if (!StatusBarUtil.setStatusBarDarkTheme(this, true)) {
            //如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
            //这样半透明+白=灰, 状态栏的文字能看得清
            StatusBarUtil.setStatusBarColor(this,0x55000000);
        }


        initUI();
    }






    private void initUI() {

//        final ViewPager viewPager = (ViewPager) findViewById(R.id.vp_horizontal_ntb);
        viewPager = (SlideViewPager) findViewById(R.id.vp_horizontal_ntb);

        viewPager.setOffscreenPageLimit(4);//这里是设置了viewpaper的最小页数 如果增删了页数 也要吧这个改一下 这样就不会频繁的销毁和创建view 就不会很卡顿了

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position==0){
                    textView.setText("您当前的积分为："+ user_data.integral);
                    viewPager.setIsScanScroll(true);//viewpaper可以滑动
                }else if(position==1){//地图页被选中
                    viewPager.setIsScanScroll(false);//viewpaper不能滑动
                }else{
                    viewPager.setIsScanScroll(true);//viewpaper可以滑动
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });




        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return 4;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view.equals(object);
            }

            @Override
            public void destroyItem(final View container, final int position, final Object object) {
//                ((ViewPager) container).removeView((View) object);
            }

            @Override
            public Object instantiateItem(final ViewGroup container, final int position) {
//                final View  view = LayoutInflater.from(getBaseContext()).inflate(R.layout.activity_item,null,false);
//
//                final TextView textView = (TextView) view.findViewById(R.id.txt_vp_item_page);
//                textView.setText(String.format("界面 %d",position));
//                final View view=LayoutInflater.from(getBaseContext()).inflate(R.layout.main_paper,null,false);
//                final TextView textView=(TextView)view.findViewById(R.id.mainpapertext);
//                textView.setText(String.format("界面 %d",position));



                View view;
                if (position==0){//index
                    Log.i("qdqwdqdq",String.valueOf(position));
                    view = LayoutInflater.from(getBaseContext()).inflate(R.layout.main_paper, null, false);
                    container.addView(view);
                    Button search=(Button)findViewById(R.id.search_button);
                    search.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(MainActivity.this, SearchDemo.class));
                        }
                    });
                    initindex(view,position);
                    initguset_yourlike(view,position);
                    initchart();//初始化图表
                    initChartText();//初始化图表右边的文字


                }else if (position==1){//地图
                    view = LayoutInflater.from(getBaseContext()).inflate(R.layout.location_paper, null, false);
                    container.addView(view);
                    initLocation();
                }else if (position==2){//商城
//                    Log.i("qdqwdqdq",String.valueOf(position));
                    view = LayoutInflater.from(getBaseContext()).inflate(R.layout.buy_paper, null, false);
                    container.addView(view);
                    initbuy(view,position);
                }
//                else if (position==2){
//                    view = LayoutInflater.from(getBaseContext()).inflate(R.layout.location_paper, null, false);
//                    container.addView(view);
//                    initLocation();
//                }
                else{//me
//                    Log.i("qdqwdqdq",String.valueOf(position));
                    view = LayoutInflater.from(getBaseContext()).inflate(R.layout.me_paper, null, false);
                    container.addView(view);
                    initme(view,position);

                }
//                else{
//                    view = LayoutInflater.from(getBaseContext()).inflate(R.layout.activity_item,null,false);
//
//                    final TextView textView = (TextView) view.findViewById(R.id.txt_vp_item_page);
//                    textView.setText(String.format("界面 %d",position));
//                    container.addView(view);
//            }

                return view;
            }
        });

        //
        final String[] colors = getResources().getStringArray(R.array.default_preview);
//        final NavigationTabBar navigationTabBar = (NavigationTabBar) findViewById(R.id.ntb_horizontal);
        navigationTabBar = (NavigationTabBar) findViewById(R.id.ntb_horizontal);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();

        models.add(new NavigationTabBar.Model(
                getResources().getDrawable(R.drawable.ic_home_black_24dp), Color.parseColor(colors[0]), "One"));
//        models.add(new NavigationTabBar.Model(
//                getResources().getDrawable(R.drawable.ic_mainback1), Color.parseColor(colors[1]), "Two"));
//        models.add(new NavigationTabBar.Model(
//                getResources().getDrawable(R.drawable.ic_location),Color.parseColor(colors[1]),"Two"));
        models.add(new NavigationTabBar.Model(
                getResources().getDrawable(R.drawable.ic_location), Color.parseColor(colors[1]), "Two"));
        models.add(new NavigationTabBar.Model(
                getResources().getDrawable(R.drawable.ic_mainback2), Color.parseColor(colors[2]), "Third"));
        models.add(new NavigationTabBar.Model(
                getResources().getDrawable(R.drawable.ic_main3), Color.parseColor(colors[3]), "Fourth"));
//        models.add(new NavigationTabBar.Model(
//                getResources().getDrawable(R.drawable.ic_fifth),Color.parseColor(colors[4]),"Fifth"));

        navigationTabBar.setModels(models);
        navigationTabBar.setViewPager(viewPager, 0);//set index
        navigationTabBar.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
                navigationTabBar.getModels().get(position).hideBadge();
            }

            @Override
            public void onPageScrollStateChanged(final int state) {

            }
        });

        navigationTabBar.post(new Runnable() {
            @Override
            public void run() {
                final View bgNavigationTaBar = findViewById(R.id.bg_ntb_horizontal);
                bgNavigationTaBar.getLayoutParams().height = (int) navigationTabBar.getHeight();

            }
        });

        navigationTabBar.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < navigationTabBar.getModels().size(); i++) {
                    Log.i("kkjjkjkj",String.valueOf(i));
                    final NavigationTabBar.Model model = navigationTabBar.getModels().get(i);
                    switch (i) {
                        case 0:
                            model.setBadgeTitle("Gxs1");
                            break;
                        case 1:
                            model.setBadgeTitle("Gxs2");
                            break;
                        case 2:
                            model.setBadgeTitle("Gxs3");
                            break;
                        case 3:
                            model.setBadgeTitle("Gxs4");
                            break;
//                        case 4:
//                            model.setBadgeTitle("Gxs5");
//                            break;
                        default:
                            break;
                    }
                    navigationTabBar.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            model.showBadge();
                        }
                    }, i * 100);
                }
            }
        }, 500);



    }



    ArrayList<IPieData> mPieDataList = new ArrayList<>();
//    int[] mColors = {0xFFCCFF00, 0xFF6495ED, 0xFFE32636, 0xFF800000, 0xFF808000, 0xFFFF8C69, 0xFF808080,
//            0xFFE6B800, 0xFF7CFC00};
    int[] mColors = {0xFF009FE9, 0xFFF21E46, 0xFF15B368, 0xFF575958};//可回收物蓝色，有害垃圾红色，餐余垃圾绿色，其他垃圾灰色
    private void initchart() {
        initData();
        PieChart pieChart = (PieChart)findViewById(R.id.pieChart);
        pieChart.setDataList(mPieDataList);
        pieChart.setAxisColor(Color.WHITE);
        pieChart.setAxisTextSize(30);
    }
    private void initData(){

        PieData pieData1 = new PieData();
        PieData pieData2 = new PieData();
        PieData pieData3 = new PieData();
        PieData pieData4 = new PieData();
        if (user_data.kehuishou_num.equals("0") && user_data.youhai_num.equals("0") && user_data.canyu_num.equals("0") && user_data.qita_num.equals("0")){
            //为了避免图表不显示 如果四个都是0的话，那就都设为1
            pieData1.setValue(1);
            pieData2.setValue(1);
            pieData3.setValue(1);
            pieData4.setValue(1);
        }else{
            pieData1.setValue(Integer.valueOf(user_data.kehuishou_num));
            pieData2.setValue(Integer.valueOf(user_data.youhai_num));
            pieData3.setValue(Integer.valueOf(user_data.canyu_num));
            pieData4.setValue(Integer.valueOf(user_data.qita_num));
        }
        pieData1.setName("可回收");
        pieData1.setColor(mColors[0]);
        mPieDataList.add(pieData1);

        pieData2.setName("有害");
        pieData2.setColor(mColors[1]);
        mPieDataList.add(pieData2);

        pieData3.setName("餐余");
        pieData3.setColor(mColors[2]);
        mPieDataList.add(pieData3);

        pieData4.setName("其他");
        pieData4.setColor(mColors[3]);
        mPieDataList.add(pieData4);

    }

    private void initChartText() {
        TextView kehuishou=(TextView)findViewById(R.id.kehuishou);
        TextView youhai=(TextView)findViewById(R.id.youhai);
        TextView canyu=(TextView)findViewById(R.id.canyu);
        TextView qita=(TextView)findViewById(R.id.qita);
        kehuishou.setText("可回收垃圾："+user_data.kehuishou_num+"件");
        youhai.setText("有害垃圾："+user_data.youhai_num+"件");
        canyu.setText("餐余垃圾："+user_data.canyu_num+"件");
        qita.setText("其他垃圾："+user_data.qita_num+"件");
    }

    private MapView mapView;
    private AMap aMap;
    private LatLonPoint centerpoint = new LatLonPoint(39.06215,117.12630);
    Boolean followMove;


    private void initLocation() {
        viewPager.setIsScanScroll(false);
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(convertToLatLng(centerpoint),13));


        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，定位点依照设备方向旋转，并且蓝点会跟随设备移动。
//        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。

        aMap.setOnMyLocationChangeListener(new AMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                Log.i("sssssss",latitude+",,,"+longitude);
                LatLng latLng = new LatLng(latitude, longitude);
                if(followMove){
                    aMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                }

            }
        });

        aMap.setOnMapTouchListener(new AMap.OnMapTouchListener() {
            @Override
            public void onTouch(MotionEvent motionEvent) {
                //用户拖动地图后，不再跟随移动，直到用户点击定位按钮
                followMove =false;
            }
        });

        aMap.clear();// 清理之前的图标
        addMarkerfromSQL();
//        LatLng lng=new LatLng(39.983178,116.464348);
//        addMarker(lng,"center");

    }

    private void addMarkerfromSQL() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("addMarkerfromSQL","1111");
                HttpURLConnection connection=null;
                BufferedReader reader=null;
                String account= user_data.account;
                String password=user_data.pwd;
                if (account.equals("")){
                    toast("请输入用户名");
                }else if (password.equals("")){
                    toast("请输入密码");
                }else{
                    try{
                        String url1 = "http://47.102.201.183:8080/test3/LocationServlet?account="+account+"&password="+password;
                        //String tag = "Login";
                        URL url=new URL(url1);
                        connection=(HttpURLConnection)url.openConnection();
                        connection.setRequestMethod("GET");
                        connection.setConnectTimeout(8000);
                        connection.setReadTimeout(8000);
                        InputStream in=connection.getInputStream();
                        //下面对获取到的输入流进行读取
                        reader=new BufferedReader(new InputStreamReader(in));
                        StringBuilder response=new StringBuilder();
                        String line;
                        while ((line=reader.readLine())!=null){
                            response.append(line);
                        }
                        LocationJSON(response.toString());


                    }catch (Exception e){
                        toast("数据库连接失败，请联系我们");
                        Log.i("debugg",Log.getStackTraceString(e));
                        e.printStackTrace();
                    }finally {
                        if(reader!=null){
                            try {
                                reader.close();
                            }catch (IOException e){
                                e.printStackTrace();
                            }
                        }
                        if (connection!=null){
                            connection.disconnect();
                        }
                    }
                }

            }
        }).start();
    }

    String DeviceId;
    String latitude;
    String longitude;
    String location;

    private void LocationJSON(String s) throws JSONException {
        JSONObject jsonObject=new JSONObject(s);
        int i=1;
        while (true){
            try{
                JSONObject js2=jsonObject.getJSONObject(String.valueOf(i));//如果到第i个数据的时候找不到了 就说明到头了 进入catch
                String DeviceId=js2.getString("DeviceId");
                String latitude=js2.getString("latitude");
                String longitude=js2.getString("longitude");
                String location=js2.getString("location");
                String smallLocation=js2.getString("smallLocation");
                LatLng lng=new LatLng(Double.valueOf(latitude),Double.valueOf(longitude));
                addMarker(lng,smallLocation,location);
                i++;
            }catch (Exception e){
//                Log.i("debugg",Log.getStackTraceString(e));
                e.printStackTrace();
                break;//这时候退出
            }
        }




    }

    private void toast(final String value){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this,value,Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 把LatLonPoint对象转化为LatLon对象
     */
    public static LatLng convertToLatLng(LatLonPoint latLonPoint) {
        if (latLonPoint ==null){
            return null;
        }
        return new LatLng(latLonPoint.getLatitude(), latLonPoint.getLongitude());
    }

    private void addMarker(LatLng position, final String title,String snippet) {//snippet:详情
        if (position != null){
            //初始化marker内容
            MarkerOptions markerOptions = new MarkerOptions();
            //这里很简单就加了一个TextView，根据需求可以加载复杂的View
            View view = View.inflate(this, R.layout.custom_view, null);

            TextView textView = ((TextView) view.findViewById(R.id.title));
            textView.setText(title);
            BitmapDescriptor markerIcon = BitmapDescriptorFactory.fromView(view);
            markerOptions.position(position).icon(markerIcon).snippet(snippet);
            aMap.addMarker(markerOptions);

        }
    }

//    private void initLocation() {
//        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
//        SDKInitializer.initialize(this);
//        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
//        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
//        SDKInitializer.setCoordType(CoordType.BD09LL);
//        MapView mMapView = null;
//        //获取地图控件引用
//        mMapView = (MapView) findViewById(R.id.bmapView);
//        mMapView.onResume();
//
//    }


    private void initguset_yourlike(View view,int position) {
        final List<MyGoodsdata> goodsList = new ArrayList<>();
        goodsList.clear();
        goodsList.add(new MyGoodsdata("多肉植物", R.drawable.duorou, "$ 666"));
        goodsList.add(new MyGoodsdata("仙人球", R.drawable.xianrenqiu, "$ 766"));
        goodsList.add(new MyGoodsdata("仙人掌", R.drawable.xianrenzhang, "$ 766"));
        goodsList.add(new MyGoodsdata("文竹盆栽", R.drawable.wenzhu, "$ 866"));
//        goodsList.add(new MyGoodsdata("小米9透明尊享版12+512G", R.drawable.mi9, "$ 999999"));
//        goodsList.add(new MyGoodsdata("华为P30pro 8+128G", R.drawable.huaweip30p, "$ 999999"));
//        goodsList.add(new MyGoodsdata("小米MIX3 6+128G", R.drawable.mimix3, "$ 888888"));

        Adapter_goods adapter_goods = new Adapter_goods(MainActivity.this, R.layout.buydata, goodsList);
        ListView listView = (ListView) view.findViewById(R.id.guest_yourlike);
        listView.setAdapter(adapter_goods);
        int totalHeight = 0;

        for (int i = 0; i < adapter_goods.getCount(); i++) {
            View listItem = adapter_goods.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight
                + (listView.getDividerHeight() * (adapter_goods.getCount() - 1));

        listView.setLayoutParams(params);

    }

    private void initbuy(View view,int position){
        final List<MyGoodsdata> goodsList=new ArrayList<>();
        goodsList.clear();
        goodsList.add(new MyGoodsdata("多肉植物", R.drawable.duorou, "$ 666"));
        goodsList.add(new MyGoodsdata("仙人球", R.drawable.xianrenqiu, "$ 766"));
        goodsList.add(new MyGoodsdata("仙人掌", R.drawable.xianrenzhang, "$ 766"));
        goodsList.add(new MyGoodsdata("文竹盆栽", R.drawable.wenzhu, "$ 866"));
//        goodsList.add(new MyGoodsdata("小米9透明尊享版12+512G", R.drawable.mi9, "$ 999999"));
//        goodsList.add(new MyGoodsdata("华为P30pro 8+128G", R.drawable.huaweip30p, "$ 999999"));
//        goodsList.add(new MyGoodsdata("小米MIX3 6+128G", R.drawable.mimix3, "$ 888888"));
        Adapter_goods adapter_goods=new Adapter_goods(MainActivity.this,R.layout.buydata,goodsList);
        ListView listView=(ListView)view.findViewById(R.id.list_goods);
        listView.setAdapter(adapter_goods);

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                final MyGoodsdata z = goodsList.get(position);
////                Toast.makeText(MySettingActivity.this,z.getName(),Toast.LENGTH_SHORT).show();
//                if (z.getName().equals("小米9透明尊享版12+512G")) {
//                    Toast.makeText(MainActivity.this,"you set mi9",Toast.LENGTH_SHORT).show();
//                    Goods_Config.goodsList.add(new MyGoodsdata("小米9透明尊享版12+512G",R.drawable.mi9,"$ 999999"));
//
//                }
//                if (z.getName().equals("华为P30pro 8+128G")) {
//                    Toast.makeText(MainActivity.this,"you set p30",Toast.LENGTH_SHORT).show();
//                    Goods_Config.goodsList.add(new MyGoodsdata("华为P30pro 8+128G",R.drawable.huaweip30p,"$ 999999"));
//                }
//                if (z.getName().equals("小米MIX3 6+128G")) {
//                    Toast.makeText(MainActivity.this,"you set mix3",Toast.LENGTH_SHORT).show();
//                    Goods_Config.goodsList.add(new MyGoodsdata("小米MIX3 6+128G",R.drawable.mimix3,"$ 888888"));
//
//                }
//            }
//        });
    }


    private void initme(View view, int position) {
        TextView accountname=(TextView)view.findViewById(R.id.accountname);
        accountname.setText(user_data.account);
        final List<MySettingdata> settingList = new ArrayList<>();
        settingList.clear();
        settingList.add(new MySettingdata("绑定账户",R.drawable.ic_bangding));
        settingList.add(new MySettingdata("积分变动记录",R.drawable.ic_lishijilu));
        settingList.add(new MySettingdata("我的订单",R.drawable.ic_wodedingdan));
        settingList.add(new MySettingdata("反馈建议",R.drawable.ic_fankiujianyi));
        settingList.add(new MySettingdata("退出登陆",R.drawable.ic_reply));
        Adapter adapter = new Adapter(MainActivity.this, R.layout.settingdata, settingList);

        ListView listView = (ListView) view.findViewById(R.id.list_view);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final MySettingdata z = settingList.get(position);
//                Toast.makeText(MySettingActivity.this,z.getName(),Toast.LENGTH_SHORT).show();
                if (z.getName().equals("绑定账户")) {
                    final EditText inputServer = new EditText(MainActivity.this);
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    inputServer.setText(user_data.uid);
                    builder.setTitle("请输入您校园卡的UID号").setIcon(R.drawable.ic_uid).setView(inputServer)
                            .setNegativeButton("Cancel", null);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                bangdingRequest(inputServer.getText().toString());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                    builder.show();

                }
                if (z.getName().equals("积分变动记录")) {
                    Intent intent=new Intent(MainActivity.this,IntegralchangeActivity.class);
                    startActivity(intent);

                }
                if (z.getName().equals("我的订单")) {
                    Intent intent=new Intent(MainActivity.this,BoughtActivity.class);
                    startActivity(intent);

                }
                if (z.getName().equals("反馈建议")) {
                    Intent intent=new Intent(MainActivity.this,SuggestionActivity.class);
                    startActivity(intent);
                }
                if (z.getName().equals("退出登陆")){
                    sp.edit().putBoolean("AUTO_ISCHECK", false).commit();
                    Intent intent=new Intent(MainActivity.this,LoginActivity2.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void initindex(View view,int position) {
        ImageView mainBackground=(ImageView)findViewById(R.id.main_background);
        TextView timetext=(TextView)findViewById(R.id.timetext);
        GifView workGif=(GifView)findViewById(R.id.work_gif);
        workGif.startAnimation();
        if (getDateSx.get().equals("zaoshang")) {
            mainBackground.setImageResource(R.drawable.xml_zaoshang);
            timetext.setText("早上好");
            workGif.setGifid(R.raw.zaoshang);
            workGif.initializeView();//刷新view视图
        }
        if (getDateSx.get().equals("shangwu")) {
            mainBackground.setImageResource(R.drawable.xml_zaoshang);
            timetext.setText("上午好");
            workGif.setGifid(R.raw.zaoshang);
            workGif.initializeView();//刷新view视图
        }

        if (getDateSx.get().equals("zhongwu")) {
            mainBackground.setImageResource(R.drawable.xml_zhongwu);
            timetext.setText("中午好");
            workGif.setGifid(R.raw.zhongwu2);
            workGif.initializeView();//刷新view视图
        }

        if (getDateSx.get().equals("xiawu")) {
            mainBackground.setImageResource(R.drawable.xml_xiawu);
            timetext.setText("下午好");
            workGif.setGifid(R.raw.xiawu2);
            workGif.initializeView();//刷新view视图
        }

        if (getDateSx.get().equals("wanshang")) {
            mainBackground.setImageResource(R.drawable.xml_wanshang);
            timetext.setText("晚上好");
            workGif.setGifid(R.raw.wanshang);
            workGif.initializeView();//刷新view视图
            Log.i("kkk", "2222");
        }

        textView = (TextView) view.findViewById(R.id.integral_station);
//        textView.setText(String.format("界面 %d",position));
        textView.setText("您当前的积分为："+ user_data.integral);

        workGif.initializeView();//刷新view视图

        ImageView lijicanyu=(ImageView)findViewById(R.id.iv_huanhaoli);
        lijicanyu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigationTabBar.setViewPager(viewPager, 2);//set index
            }
        });

    }

    public void bangdingRequest(final String uid) throws IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("poi2","123");
                HttpURLConnection connection=null;
                BufferedReader reader=null;
                String account=user_data.account;
                String password=user_data.pwd;
                try{
                    String url1 = "http://47.102.201.183:8080/test3/BangdingServlet?account="+account+"&password="+password+"&uid="+uid;
                    //String tag = "Login";
                    URL url=new URL(url1);
                    connection=(HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in=connection.getInputStream();
                    //下面对获取到的输入流进行读取
                    reader=new BufferedReader(new InputStreamReader(in));
                    StringBuilder response=new StringBuilder();
                    String line;
                    while ((line=reader.readLine())!=null){
                        response.append(line);
                    }
                    String result="";
                    result=makeJSON(response.toString());
                    Log.i("result",result);

                    if (result.equals("")){
                        //数据库连接不上
                        Looper.prepare();
                        Toast.makeText(MainActivity.this,"配置失败，请重试",Toast.LENGTH_SHORT).show();//一般不会这样
                        Looper.loop();
//                        toast("数据库连接失败，请联系我们");//一般不会这样
                    }else if (result.equals("成功")){
                        //成功写入
                        Looper.prepare();
                        Toast.makeText(MainActivity.this,"配置成功",Toast.LENGTH_SHORT).show();
                        user_data.uid=uid;
                        Toast.makeText(MainActivity.this,"修改成功"+uid,Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }else if(result.equals("失败")){
                        //数据库写入失败(这里最好加上把加上的设备删除)
                        Looper.prepare();
                        Toast.makeText(MainActivity.this,"未知原因：配置失败，请重试",Toast.LENGTH_SHORT).show();//一般不会这样
                        Looper.loop();
//                        toast("未知原因：数据库写入失败，请联系我们");
                    }
                }catch (Exception e){
                    Log.i("debugg",Log.getStackTraceString(e));
                    Looper.prepare();
                    Toast.makeText(MainActivity.this,"配置失败，请重试",Toast.LENGTH_SHORT).show();//一般不会这样
                    Looper.loop();
                    e.printStackTrace();
                }finally {
                    if(reader!=null){
                        try {
                            reader.close();
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                    if (connection!=null){
                        connection.disconnect();
                    }

                }


            }
        }).start();
    }



    private String makeJSON(String jsondata){//都可以用
        try{
            Log.i("jsondata",jsondata);
            JSONObject js=new JSONObject(jsondata);
            String jsonObject=js.getJSONObject("params").toString();
            Log.i("3232:",jsonObject);
            JSONObject object=new JSONObject(jsonObject);
            String ob=object.optString("Result");
            Log.i("ob",ob);
            return ob;

        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }




//    /*==============进度条==============*/
//    private ProgressHUD mProgressHUD;
//
//    private void initProgressBar() {
//        if (mProgressHUD == null) {
//            mProgressHUD = ProgressHUD.newInstance(this, "正在登录...", false, null);
//        }
//        mProgressHUD.setMessage("正在登录...");
//    }
//
//    public void showProgressBar() {
//        if (mProgressHUD != null && mProgressHUD.isShowing())
//            return;
////        initProgressBar();
//        mProgressHUD.show();
//    }
//
//    public void stopProgressBar() {
//        if (mProgressHUD != null && mProgressHUD.isShowing()) {
//            mProgressHUD.dismiss();
//        }
//    }







}
