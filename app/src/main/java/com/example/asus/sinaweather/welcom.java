package com.example.asus.sinaweather;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class welcom extends AppCompatActivity {
    EditText city;
    Button show;
    Context mcontext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcom);
        mcontext=this;

        bindview();
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cityname=city.getText().toString();
                Intent intent=new Intent(mcontext,MainActivity.class);
                intent.putExtra("city",cityname);
                startActivity(intent);

            }
        });


    }

    private void bindview() {
        city=(EditText) findViewById(R.id.city);
        show=(Button) findViewById(R.id.show);
    }

}
