package com.example.myapplication.designMode.subclass;

import com.example.myapplication.designMode.handlerMode.AbstractHandler;

/**
 * @Author qipeng
 * @Date 2022/8/12
 * @Desc SecondLevelManager 处理SecondLevel
 */
public class SecondLevelManager extends AbstractHandler {

    private String msg;
    @Override
    public String handleRequest(int value) {
        // 处理优先级最高的
        if (value < 7) {
            msg = "SecondLevelManager done";
        } else {
            if (getSuccessor() != null) {
                return getSuccessor().handleRequest(value);
            }
        }
        return msg;
    }
}
