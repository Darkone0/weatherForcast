package com.example.air.wf;

import android.arch.lifecycle.ViewModelStoreOwner;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class CityActivity extends AppCompatActivity {
    private String[] data={"北京","上海"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(CityActivity.this,android.R.layout.simple_list_item_1,data);
        final ListView listView=(ListView) findViewById(R.id.Alist);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String city = data[position];
                Intent intent = new Intent();
                Bundle bund = new  Bundle();
                bund.putString("city",city);
                intent.putExtras(bund);
                intent.setClass(CityActivity.this,MainActivity.class);
                SharedPreferences sp = getSharedPreferences("User",0);
                SharedPreferences.Editor ed = sp.edit();
                ed.putString("city" ,city);
                ed.commit();
                startActivity(intent);
            }
        });


    }

    public void findCity(View v){

        Intent intent = new Intent();
        Bundle bund = new  Bundle();
        bund.putString("city",((Button)v).getText().toString());
        intent.putExtras(bund);
        intent.setClass(CityActivity.this,MainActivity.class);

        SharedPreferences sp = getSharedPreferences("User",0);
        SharedPreferences.Editor ed = sp.edit();
        ed.putString("city" ,((Button)v).getText().toString());
        ed.commit();
        startActivity(intent);


    }
}
