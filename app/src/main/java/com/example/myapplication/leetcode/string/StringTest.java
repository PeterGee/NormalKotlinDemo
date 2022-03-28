package com.example.myapplication.leetcode.string;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qipeng
 * @date 2022/3/16
 * @desc
 */
class StringTest {
    public static void main(String[] args) {
        String str = "ABC";
        //  strReverse(str);
        // permutation(str);
        String strOne = "this is my ticket";
        // System.out.println(reverseSentence(strOne));
        // System.out.println(leftRotateString(strOne, 4));

        String[] arr={"hello","happy","ha"};
        System.out.println(findLongestPrefix(arr));

    }

    /**
     * 字符串翻转
     *
     * @param str
     */
    private static void strReverse(String str) {
        if (str == null || str.length() < 2) return;
        StringBuilder builder = new StringBuilder();
        for (int i = str.length() - 1; i >= 0; i--) {
            builder.append(str.charAt(i));
        }
        System.out.println(builder);
    }


    /**
     * 输出给定字符串所有排列
     * 例如：给定ab,输出ab,ba
     *
     * @param str
     */
    private static void permutation(String str) {
        if (str == null || str.length() < 2) return;
        // 转为数组
        char[] arr = str.toCharArray();
        // 创建list存储排列
        List<String> list = new ArrayList<>();
        permutation(arr, 0, list);
        System.out.println(list);
    }

    /**
     * 进行排列
     *
     * @param arr
     * @param start 起始索引
     * @param list
     */
    private static void permutation(char[] arr, int start, List<String> list) {
        // 遍历完毕
        if (arr.length - 1 == start) {
            String result = new String(arr);
            if (!list.contains(result)) {
                list.add(result);
            }
        } else {
            for (int i = start; i < arr.length; i++) {
                // 交换首尾
                char temp = arr[start];
                arr[start] = arr[i];
                arr[i] = temp;
                // 递归
                permutation(arr, start + 1, list);
            }
        }
    }

    /**
     * 字符串转int值
     * <p>
     * 考虑点：
     * 1、字符串是否为空，长度是否为0
     * 2、字符串正负号问题
     *
     * @param str
     * @return
     */
    private static int strToInt(String str) {
        int result = 0;
        if (str == null || str.length() == 0)
            return result;

        // 转为数组
        char[] chars = str.toCharArray();

        // 正负符号flag
        int flag = 0;

        // 判断正负符号
        if (chars[0] == '+') {
            flag = 1;
        } else if (chars[0] == '-') {
            flag = -1;
        }

        // 通过判断是否存在符号位，决定遍历起始索引
        int startIndex = flag == 0 ? 0 : 1;

        for (int i = startIndex; i < chars.length; i++) {
            // 判断是否为数字
            if (Character.isDigit(chars[i])) {
                int temp = chars[i] - '0';
                result = result * 10 + temp;
            } else {
                // 非法
                return 0;
            }
        }
        return result;
    }


    /**
     * 翻转句子
     *
     * @param sentence
     */
    private static String reverseSentence(String sentence) {
        if (sentence == null || sentence.length() < 1 || !sentence.contains(" ")) return sentence;

        String[] arr = sentence.split(" ");
        StringBuilder result = new StringBuilder();
        for (int i = arr.length - 1; i >= 0; i--) {
            if (arr[i].equals("")) continue;
            result.append(arr[i] + " ");
        }
        return result.toString();
    }

    /**
     * 将字符串左旋转n个字符
     *
     * @param str
     * @param length 左旋长度
     * @return
     */
    private static String leftRotateString(String str, int length) {
        if (str == null || str.length() < 0 || str.length() < length) {
            return str;
        }
        String leftString = str.substring(0, length);
        String lastedString = str.substring(length);
        StringBuilder builder = new StringBuilder();
        return builder.append(lastedString).append(leftString).toString();
    }


    /**
     * 最长公共前缀
     *
     * @param str
     * @return
     */
    private static String findLongestPrefix(String[] str) {
        if (str == null || str.length == 0) return "";
        int length = str[0].length();
        int lengthLast = str[str.length - 1].length();
        int min = Math.min(length, lengthLast);

        StringBuilder result = new StringBuilder();
        // 遍历对比
        for (int i = 0; i < min; i++) {
            if (str[0].charAt(i) == str[str.length - 1].charAt(i)) {
                result.append(str[0].charAt(i));
            } else {
                break;
            }
        }
        return result.toString();

    }


}
