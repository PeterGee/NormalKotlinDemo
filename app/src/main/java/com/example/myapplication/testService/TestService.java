package com.example.myapplication.testService;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.template.IProvider;

/**
 * @author xujun  on 2/7/2018.
 */
// 实现接口
@Route(path = "/service/single")
public class TestService implements IProvider {


    public String sayHello(String name) {
        // Toast.makeText(app.getApp(),this.getClass().getSimpleName()+": sayHello"+" "+name, Toast.LENGTH_SHORT).show();
        return "hello, " + name;
    }

    @Override
    public void init(Context context) {

    }
}

