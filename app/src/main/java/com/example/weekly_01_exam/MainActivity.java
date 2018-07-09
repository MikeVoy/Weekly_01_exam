package com.example.weekly_01_exam;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.weekly_01_exam.view.MyView;

public class MainActivity extends AppCompatActivity {

    private MyView my_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //获取控件
        my_view = findViewById(R.id.mv_view);



    }
}
