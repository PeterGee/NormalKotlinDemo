package com.example.myapplication.designMode.handlerMode;

import com.example.myapplication.designMode.subclass.FirstLevelManager;
import com.example.myapplication.designMode.subclass.SecondLevelManager;
import com.example.myapplication.designMode.subclass.ThirdLevelManager;

/**
 * @Author qipeng
 * @Date 2022/8/12
 * @Desc
 */
public class HandlerModelJava {
    public static void main(String[] args) {
        AbstractHandler levelOne= new FirstLevelManager();
        AbstractHandler levelTwo= new SecondLevelManager();
        AbstractHandler levelThird= new ThirdLevelManager();
        levelOne.setSuccessor(levelTwo);
        levelTwo.setSuccessor(levelThird);

        String resultOne=levelOne.handleRequest(2);
        String resultTwo=levelOne.handleRequest(6);
        String resultThree=levelOne.handleRequest(10);

        System.out.println("result=== "+resultOne);
        System.out.println("resultTwo=== "+resultTwo);
        System.out.println("resultThree=== "+resultThree);
    }
}
