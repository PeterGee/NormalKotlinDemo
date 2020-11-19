package com.example.myapplication.testAcivity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.myapplication.ARouterConstants;
import com.example.myapplication.R;

@Route(path = ARouterConstants.COM_ACTIVITY1)
public class ActivityOne extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);
    }
}
