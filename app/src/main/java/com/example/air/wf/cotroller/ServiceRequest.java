package com.example.air.wf.cotroller;

import com.example.air.wf.model.WeatherModel;
import com.example.air.wf.tools.Lunar;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

public class ServiceRequest {
    public static final String DEF_CHATSET = "UTF-8";
    public static final int DEF_CONN_TIMEOUT = 30000;
    public static final int DEF_READ_TIMEOUT = 30000;
    public String userAgent =  "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";
    private String urlStr;
    private String city;
    public static int requestTimes = 0;

    public ServiceRequest(){
        city = "北京";
        urlStr = "https://apis.juhe.cn/simpleWeather/query?key=78b54e7d4fc9f060348ce2976b27c8ae&city=北京";
    }
    public ServiceRequest(String city){
        this.city = city;
        urlStr = "https://apis.juhe.cn/simpleWeather/query?key=78b54e7d4fc9f060348ce2976b27c8ae&city="+ city;
    }

    public String DataUtl() throws IOException {
        URL url = new URL(urlStr);
        StringBuffer sb = new StringBuffer();
        BufferedReader reader = null;
        String rs = null;
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("User-agent", userAgent);
        conn.setUseCaches(false);
        conn.setConnectTimeout(DEF_CONN_TIMEOUT);
        conn.setReadTimeout(DEF_READ_TIMEOUT);
        conn.setInstanceFollowRedirects(false);
        conn.connect();
        InputStream is = conn.getInputStream();
        reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
        String strRead = null;
        while ((strRead = reader.readLine()) != null) {
            sb.append(strRead);
        }
        rs = sb.toString();
        if (reader != null) {
            reader.close();
        }
        if (conn != null) {
            conn.disconnect();
        }
        return rs;
    }
    public WeatherModel weatherDAO(String jstr){
        JSONObject object = JSONObject.fromObject(jstr);
        JSONObject result = JSONObject.fromObject(object.get("result"));
        JSONObject realtime = JSONObject.fromObject(result.get("realtime"));
        WeatherModel rs = new WeatherModel();
        rs.setCity(city);
        rs.setTemp(realtime.getInt("temperature"));
        rs.setCurWeather(realtime.getString("info"));
        JSONObject today = JSONArray.fromObject(result.get("future")).getJSONObject(0);
        JSONObject widCast = JSONObject.fromObject(today.get("wid"));

        rs.setRange(today.getString("temperature"));
        rs.setWeather(today.getString("weather"));
        rs.setWid(today.getString("direct")+" "+widCast.getString("day")+"级～"+widCast.getString("night")+"级");

        Calendar cale = null;
        cale = Calendar.getInstance();
        Lunar l = new Lunar(cale);
        String str = cale.get(Calendar.MONTH)+1 + "月" +cale.get(Calendar.DATE)+"日";
        int time = cale.get(Calendar.HOUR_OF_DAY);
        if (time > 17 || time < 6){
            rs.setIsDayT(0);
        }
        else
            rs.setIsDayT(1);
        rs.setDate(str + " " + Lunar.getWeek()+" 农历"+l.toString());

        //JSONObject bean = JSONObject.fromObject(rs);
        //System.out.println(bean);
        return rs;
    }
    public WeatherModel getModel() throws IOException {
        String str = DataUtl();
        return weatherDAO(str);
    }
    public static void main(String[] args) throws IOException {
        ServiceRequest s = new ServiceRequest();
        String str = s.DataUtl();
        System.out.println(str);
        s.weatherDAO(str);
    }
}
