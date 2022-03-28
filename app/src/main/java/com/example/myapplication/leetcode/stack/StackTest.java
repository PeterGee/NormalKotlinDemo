package com.example.myapplication.leetcode.stack;

import java.util.Stack;

/**
 * @author qipeng
 * @date 2022/2/22
 * @desc
 */
class StackTest {
    public static void main(String[] args) {
        // methodOne();
        // methodTwo();


        String s1 = "aabaa";
        String s2 = "aabba";
        System.out.println(isPalindromic(s1));
        System.out.println(isPalindromic(s2));
    }

    private static void methodTwo() {
        int[] arrOne = {1, 2, 3, 4, 5};
        int[] arrTwo = {4, 5, 3, 2, 1};
        int[] arrThree = {4, 3, 5, 1, 2};
        System.out.println(isValidStackOrder(arrOne, arrTwo));
        System.out.println(isValidStackOrder(arrOne, arrThree));
    }

    private static void methodOne() {
        StackList mStackList = new StackList();
        for (int i = 0; i < 5; i++) {
            mStackList.push(i);
        }
        while (!mStackList.isEmpty()) {
            System.out.println("item is ===" + mStackList.pop() + " ");
        }
    }

    /**
     * 判断括号是否匹配
     * 例如：{()[]} :匹配
     * {{[()}} :不匹配
     **/
    private static boolean judgeBracket(String s) {
        if (s.length() == 0) return false;
        Stack<Character> stack = new Stack();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '{' || c == '(' || c == '[') {
                stack.push(c);
            }
            if (c == '}') {
                return !stack.isEmpty() && (stack.pop() == '{');
            }

            if (c == ')') {
                return !stack.isEmpty() && (stack.pop() == '(');
            }

            if (c == ']') {
                return !stack.isEmpty() && (stack.pop() == '[');
            }
        }
        return stack.isEmpty();
    }

    /**
     * 判断入栈、出栈顺序
     *
     * @param push
     * @param pop
     * @return
     */
    public static boolean isValidStackOrder(int[] push, int[] pop) {
        if (push.length == 0 || pop.length == 0) return false;
        Stack<Integer> mStack = new Stack<>();
        int index = 0;
        for (int i = 0; i < push.length; i++) {
            mStack.push(push[i]);
            // 栈顶元素与弹出元素相同，弹出
            while (!mStack.isEmpty() && mStack.peek() == pop[index]) {
                mStack.pop();
                index++;
            }
        }
        return mStack.isEmpty();
    }

    /**
     * 判断输入的字符串是否为回文字符串
     *
     * @param s
     * @return
     */
    public static boolean isPalindromic(String s) {
        if (s == null || s.length() == 0) return false;
        Stack<Character> mStack = new Stack();

        // 找出中心点索引
        int mid = s.length() / 2 - 1;
        int next;

        // 找出中间点后一个元素
        if (s.length() % 2 == 0) {
            next = mid + 1;
        } else {
            next = mid + 2;
        }

        // 前半段入栈
        for (int i = 0; i <= mid; i++) {
            mStack.push(s.charAt(i));
        }

        // 出栈比对值
        for (int j = next; j < s.length(); j++) {
            if (mStack.pop() != s.charAt(j)) {
                break;
            }
        }
        return mStack.isEmpty();
    }


}
