package com.example.myapplication.designMode.subclass;

import com.example.myapplication.designMode.handlerMode.AbstractHandler;

/**
 * @Author qipeng
 * @Date 2022/8/12
 * @Desc ThirdLevelManager 处理thirdLevel
 */
public class ThirdLevelManager extends AbstractHandler {

    private String msg;

    @Override
    public String handleRequest(int value) {
        msg = " ThirdLevelManager done";
        return msg;
    }
}
