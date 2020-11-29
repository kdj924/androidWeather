package com.example.myweather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {
    EditText edit;
    TextView text;
    String key = "9a2cdb92482fb060bcdc3bec358a713e";
    String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edit = (EditText) findViewById(R.id.edit);
        text = (TextView) findViewById(R.id.result);
    }

    public void mOnClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        data = getJsonData();
                        run();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                text.setText(data);
                            }
                        });
                    }
                }).start();
                break;
        }
    }

    String getJsonData() {
        StringBuffer buffer = new StringBuffer();
        String str = edit.getText().toString();
        String location = URLEncoder.encode(str);
        String queryUrl = "http://api.weatherstack.com/current?access_key=111453242708ba78c89a6250c4b07d83&query=" + location;
        try {
            URL url = new URL(queryUrl);
            InputStream is = null;
            is = new URL(queryUrl).openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String str1 = rd.readLine();
            System.out.println("testing");
            if (str1 == null) {
                buffer.append("not found");
            }
            //buffer.append(str2);
            JSONObject obj = new JSONObject(str1);
            JSONObject obj2 = obj.getJSONObject("current");
            int temp = obj2.getInt("temperature");
            System.out.println(temp);
            if (temp != 0) {
                buffer.append("The temperature in " + location.toUpperCase() + " is " + temp + "°c");
            }
            String receiveMsg = buffer.toString();
            System.out.println(receiveMsg);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error?");
        }


        return buffer.toString();
    }
}