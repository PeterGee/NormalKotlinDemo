package com.example.myapplication.leetcode.stack;

import java.util.Stack;

/**
 * @author qipeng
 * @date 2022/2/22
 * @desc
 */
class StackList {
    private Stack<Object> stackOne = new Stack<>();
    private Stack<Object> stackTwo = new Stack<>();

    public void push(Object o) {
        stackOne.push(o);
    }

    public Object pop() {
        if (stackOne.isEmpty() && stackTwo.isEmpty()) {
            throw new RuntimeException(" stack is empty");
        }
        if (stackTwo.isEmpty()) {
            // 添加数据
            while (!stackOne.isEmpty()) {
                stackTwo.push(stackOne.pop());
            }

        }
        return stackTwo.pop();
    }

    public boolean isEmpty() {
        return stackOne.isEmpty() && stackTwo.isEmpty();
    }

}
