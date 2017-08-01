package com.example.asus.sinaweather;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.sinaweather.yahoomodel.Yahoomodel;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

import static android.R.attr.value;

public class MainActivity extends AppCompatActivity {
    TextView cityname;
    TextView date;
    TextView condition;
    TextView temp;
    TextView speed;
    TextView direction;
    TextView sunrise;
    TextView sunset;
    ProgressDialog dialog;
    String cityvalue=null;
    Context mcontext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mcontext=this;
        bindview();

        Intent mintent = getIntent();
        cityvalue = mintent.getStringExtra("city").toString();

        cityname.setText(cityvalue);

        String url="https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D\""+cityvalue+"%2C%20ir\")&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";

        getdata(url);

        dialog = new ProgressDialog(this);
        dialog.setTitle("pls watting...!");
        dialog.setMessage("pls wate for provide your data.");



    }

    private void getdata(String url) {


        AsyncHttpClient client=new AsyncHttpClient();
        client.get(url, new TextHttpResponseHandler() {
            @Override
            public void onFinish() {
                super.onFinish();
                dialog.dismiss();
            }

            @Override
            public void onStart() {
                super.onStart();
                dialog.show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(mcontext, "pls check your internet connection an try again", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                parseandsetdata(responseString );

            }
        });
    }

    private void parseandsetdata(String responseString) {

        Gson gson=new Gson();
        Yahoomodel yahoo=gson.fromJson(responseString,Yahoomodel.class);

        String datejson=yahoo.getQuery().getResults().getChannel().getItem().getCondition().getDate();
        date.setText(""+datejson);

        String conditionjson=yahoo.getQuery().getResults().getChannel().getItem().getCondition().getText();
        condition.setText(""+conditionjson);

        String tempjson=yahoo.getQuery().getResults().getChannel().getItem().getCondition().getTemp();
        Double c=Double.parseDouble(tempjson);
        c=(c-32)/1.8;

        int N = 0;
        double d = c*Math.pow(10,N);
        int i = (int) d;
        double f2 = i/Math.pow(10,N);
        temp.setText( f2+"°C");

        String speedjson=yahoo.getQuery().getResults().getChannel().getWind().getSpeed();
        speed.setText(speedjson+" "+"mph"+"sw");

        String directionjson=yahoo.getQuery().getResults().getChannel().getWind().getDirection();
        direction.setText(directionjson);

        String sunrisjson=yahoo.getQuery().getResults().getChannel().getAstronomy().getSunrise();
        sunrise.setText(sunrisjson);

        String sunsetjson=yahoo.getQuery().getResults().getChannel().getAstronomy().getSunset();
        sunset.setText(sunsetjson);











    }










    private void bindview() {

        cityname=(TextView) findViewById(R.id.cityname);
        date=(TextView) findViewById(R.id.date);
        condition=(TextView) findViewById(R.id.condition);
        temp=(TextView) findViewById(R.id.temp);
        speed=(TextView) findViewById(R.id.speed);
        direction=(TextView) findViewById(R.id.direction);
        sunrise=(TextView) findViewById(R.id.sunrise);
        sunset=(TextView) findViewById(R.id.sunset);


    }


}
