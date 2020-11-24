package com.example.myapplication.testAcivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.myapplication.router.ARouterConstants;
import com.example.myapplication.R;

@Route(path = ARouterConstants.TEST_ACTIVITY1)
public class TestActivity1 extends AppCompatActivity {

    private TextView mTv;
    private TextView mTvPara;

    @Autowired
    String name;

    @Autowired
    int age;

    @Autowired
    boolean boy;

    @Autowired
    int high;

    @Autowired
    String obj ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);
        ARouter.getInstance().inject(this);
        initView();
        initData();
    }

    private void initData() {
        mTv.setText(this.getClass().getSimpleName());
        Intent intent = getIntent();
        StringBuilder sb = new StringBuilder();
        Bundle extras = intent.getExtras();
        String parmas = "参数是： " + "name: " + name + "  age: " + age + " boy: " + boy;
        if(obj!=null){
            parmas = parmas + " obj: " + obj.toString();
        }

        mTvPara.setText(parmas);

    }

    private void initView() {
        mTv = (TextView) findViewById(R.id.tv);
        mTvPara = (TextView) findViewById(R.id.tv_para);
    }
}
