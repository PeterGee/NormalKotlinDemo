package com.example.myapplication.designMode.handlerMode;

/**
 * @Author qipeng
 * @Date 2022/8/12
 * @Desc 抽象handler
 */
public abstract class AbstractHandler {

    protected AbstractHandler mSuccessor=null;

    public AbstractHandler getSuccessor(){
        return mSuccessor;
    }

    public void setSuccessor(AbstractHandler successor){
        this.mSuccessor=successor;
    }

    public abstract String handleRequest(int value);
}
