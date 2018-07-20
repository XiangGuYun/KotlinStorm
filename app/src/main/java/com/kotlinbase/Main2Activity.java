package com.kotlinbase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        TextView tv1,tv2,tv3;
        tv1.setText("hello");
        tv2.setText("back");
        tv3.setText("66666");
        tv1.setTextColor(Color.RED);
        tv2.setTextColor(Color.GREEN);
        tv3.setTextColor(Color.BLUE);


    }
}
