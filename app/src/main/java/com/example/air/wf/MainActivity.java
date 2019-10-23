package com.example.air.wf;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.air.wf.cotroller.ServiceRequest;
import com.example.air.wf.model.WeatherModel;
import com.zhy.android.percent.support.PercentLinearLayout;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    TextView date;
    TextView temp;
    TextView curWeather;
    TextView tempRange;
    TextView weather;
    TextView wid;
    TextView times;
    TextView city;
    PercentLinearLayout bg;
    Drawable mm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        date = findViewById(R.id.date);
        temp = findViewById(R.id.temp);
        curWeather = findViewById(R.id.curWeather);
        tempRange = findViewById(R.id.tempRange);
        weather = findViewById(R.id.weather);
        wid = findViewById(R.id.wid);
        times = findViewById(R.id.times);
        bg = findViewById(R.id.backgroud);
        city = findViewById(R.id.city);
        sendRequestWithHttpURLConnection();
        mm = ContextCompat.getDrawable(super.getBaseContext(),R.drawable.bg1);


    }
    private void sendRequestWithHttpURLConnection() {
        //开启线程来发起网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                WeatherModel w = null;
                ServiceRequest r;
                Bundle bundle = getIntent().getExtras();
                String city;
                try{
                    city = bundle.getString("city");

                } catch (Exception e){

                    SharedPreferences sp = getSharedPreferences("User", 0);
                    city = sp.getString("city","");
                    if(city == null){
                        city = "北京";
                    }
                }
                r = new ServiceRequest(city);


                while (true){
                    try {
                        ServiceRequest.requestTimes++;
                        show("-- 更新"+ServiceRequest.requestTimes+"次 --");
                        w = r.getModel();
                        showResponse(w);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    try {
                        Thread.sleep(10000000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
    }
    //android 时不允许在子线程里面更新UI的 我们需要通过这个方法 将线程切换到主线程，然后再更新UI元素
    private void show(final String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                times.setText(str);
            }
        });
    }
    private void showResponse(final WeatherModel w) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                date.setText(w.getDate());
                temp.setText(""+w.getTemp());
                curWeather.setText(w.getCurWeather()+" 实时");
                tempRange.setText(w.getRange());
                weather.setText(w.getWeather());
                wid.setText(w.getWid());
                city.setText(w.getCity());
                if(w.getIsDayT() <= 0){
                    weather.setTextColor(Color.rgb(255, 255, 255));
                    tempRange.setTextColor(Color.rgb(255, 255, 255));
                    wid.setTextColor(Color.rgb(255, 255, 255));

                    bg.setBackground(mm);
                }
            }
        });
    }
    public void cityView(View v){
        // 添加点击响应事件
        Intent intent=new Intent(MainActivity.this,CityActivity.class);
        //启动
        startActivity(intent);

    }

}
