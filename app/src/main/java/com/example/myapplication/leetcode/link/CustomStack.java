package com.example.myapplication.leetcode.link;

/**
 * @author qipeng
 * @date 2022/2/16
 * @desc 单链表实现栈
 */

class CustomStack {

    // 定义单链表
    class Node {
        private Node next;
        private Object data;
    }

    private Node head = null;

    // 初始化栈
    void init() {
        head = new Node();
        head.next = null;
        head.data = null;
    }

    // 添加元素
    public void push(Object element) {
        Node e = new Node();
        e.data = element;
        // 非空栈处理
        if (head.next != null) {
            e.next = head.next;
        }
        head.next = e;
    }

    // 弹出元素
    public Object pop() {
        Object e = null;
        if (head.next != null) {
            e = head.next.data;
            head.next = head.next.next;
        }
        return e;
    }

    // 栈是否为空
    public boolean isStackEmpty() {
        return head.next == null;
    }

    // 打印元素
    public void printStack() {
        Node topNode=head;
        while (topNode.next != null) {
            System.out.println(topNode.next.data);
            topNode= topNode.next;
        }
    }

    // 获取栈顶元素
    public Object peek() {
        Object o = null;
        if (head.next.data != null) {
            o = head.next.data;
        }
        return o;
    }


}
