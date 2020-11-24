package com.example.myapplication.testAcivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.myapplication.router.ARouterConstants;
import com.example.myapplication.R;

@Route(path = ARouterConstants.COM_ACTIVITY_RESULT)
public class ActivityResult extends AppCompatActivity {

    public static final String RESULT = "result";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
    }

    public void onButtonClick(View view){
        Intent data = new Intent();
        data.putExtra(RESULT,"success");
        setResult(Activity.RESULT_OK, data);
        finish();
    }
}
