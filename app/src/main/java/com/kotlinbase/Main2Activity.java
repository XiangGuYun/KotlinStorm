package com.kotlinbase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Button btnTest;

        btnTest.setOnClickListener(view->{
            startActivity(new Intent(this, SecondActivity.class)
                    .putExtra("name","lilei")
                    .putExtra("age",12)
                    .putExtra("isMale",false));
        });


    }
}
