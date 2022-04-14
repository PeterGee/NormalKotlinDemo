package com.example.myapplication.leetcode.hot100;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author qipeng
 * @date 2022/4/11
 * @desc
 */
class Hot100Subject {
    public static void main(String[] args) {
        // twoNumberSum();
        // longestSubstring();
        // findMiddleNumber();
        // findMaxArea();
        threeNumberSum();
    }

    private static void threeNumberSum() {
        int[] arr = {1, 2, 3, -1, -2, 4, 0};
        System.out.println(findThreeNumber(arr));
    }

    private static List<List<Integer>> findThreeNumber(int[] arr) {
        List<List<Integer>> result = new ArrayList<>();
        int length = arr.length;
        if (arr == null || length < 3) return result;
        // 数组排序
        Arrays.sort(arr);
        // 遍历查找
        for (int i = 0; i < length; i++) {
            // 第一个数就大于0，不可能存在三数等于0情况
            if (arr[i] > 0) break;
            // 去重
            if (i > 0 && arr[i] == arr[i - 1]) continue;
            // 定义左、右指针
            int left = i + 1;
            int right = length - 1;

            // 移动指针进行匹配
            while (left < right) {
                int sum = arr[i] + arr[left] + arr[right];
                // 将符合条件的添加到result中
                if (sum == 0) {
                    result.add(Arrays.asList(arr[i], arr[left], arr[right]));
                    // 去重
                    while (left < right && arr[left] == arr[left + 1]) left++;
                    while (left < right && arr[right] == arr[right - 1]) right--;
                    left++;
                    right--;
                } else if (sum > 0) {
                    right--;
                } else left++;
            }
        }
        return result;

    }

    /**
     * 时间复杂度O（N）
     * 空间复杂度O（1）
     */
    private static void findMaxArea() {
        int[] arr = {1, 8, 3, 5, 9, 6};
        System.out.println(findMaxArea(arr));
    }

    private static int findMaxArea(int[] arr) {
        if (arr == null || arr.length < 1) return 0;
        // 初始化起点指针，终点指针，面积
        int start = 0, end = arr.length - 1, area = 0;
        // 通过移动指针求面积
        while (start < end) {
            if (arr[start] < arr[end]) {
                // 从前往后移动
                area = Math.max(area, (end - start) * arr[start++]);
            } else {
                // 从后往前移动
                area = Math.max(area, (end - start) * arr[end--]);
            }
        }
        return area;
    }

    private static void findMiddleNumber() {
        // String s = "qwerq";
        String s = "q";
        System.out.println(longestPalindrome(s));
    }

    private static String longestPalindrome(String s) {
        if (s == null || s.length() < 1) return "";

        int start = 0;
        int end = 0;
        for (int i = 0; i < s.length(); i++) {
            // 奇数长度,中心点是同一个值
            int oddLength = expandCenter(s, i, i);
            // 偶数长度,中心点是相邻的两个值
            int evenLength = expandCenter(s, i, i + 1);
            // 最大长度
            int maxLength = Math.max(oddLength, evenLength);
            // 修改字符串起点和终点索引
            if (maxLength > end - start) {
                start = i - (maxLength - 1) / 2;
                end = i + maxLength / 2;
            }

        }
        return s.substring(start, end + 1);
    }

    private static int expandCenter(String s, int start, int end) {
        // 是回文,从中间位置向两侧延伸
        while (start >= 0 && end < s.length() && s.charAt(start) == s.charAt(end)) {
            start--;
            end++;
        }
        return end - start - 1;
    }

    /**
     * 最长无重复字符
     */
    private static void longestSubstring() {
        // String str = "qwerq";
        String str = "qqqqq";
        System.out.println(getMaxLength(str));
    }

    /**
     * 给定一个字符串 s ，请你找出其中不含有重复字符的 最长子串 的长度
     *
     * @param str
     */
    private static int getMaxLength(String str) {
        if (str == null || str.length() == 0) return 0;
        int length = 0;
        int left = 0;
        HashMap<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < str.length(); i++) {

            // map中包含重复字符串，更新left位置,index从0开始
            if (map.containsKey(str.charAt(i))) {
                left = Math.max(left, map.get(str.charAt(i)) + 1);
            }
            // 将字符作为键
            map.put(str.charAt(i), i);
            // 更新长度
            length = Math.max(length, i - left + 1);
        }
        return length;
    }

    private static void twoNumberSum() {
        /**
         * 两个链表求和
         *
         * 给你两个非空 的链表，表示两个非负的整数。它们每位数字都是按照逆序的方式存储的，并且每个节点只能存储一位数字。
         *
         * 请你将两个数相加，并以相同形式返回一个表示和的链表。
         *
         * 你可以假设除了数字 0 之外，这两个数都不会以 0开头
         *
         */
        ListNode nodeOne = new ListNode(9);
        ListNode nodeTwo = new ListNode(9);
        printNode(addTwoNumbers(nodeOne, nodeTwo));
    }

    private static void printNode(ListNode nodeOne) {
        if (nodeOne == null) return;
        while (nodeOne != null) {
            System.out.println(nodeOne.val);
            nodeOne = nodeOne.next;
        }
    }

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode pre = new ListNode(0);
        ListNode current = pre;
        // 进位标志
        int carry = 0;
        while (l1 != null || l2 != null) {
            int x = l1 == null ? 0 : l1.val;
            int y = l2 == null ? 0 : l2.val;
            int sum = x + y + carry;

            carry = sum / 10;
            sum = sum % 10;

            // 创建新节点,并连接
            current.next = new ListNode(sum);
            // 移位
            current = current.next;
            if (l1 != null) {
                l1 = l1.next;
            }
            if (l2 != null) {
                l2 = l2.next;
            }

        }

        if (carry == 1) {
            current.next = new ListNode(carry);
        }
        return pre.next;
    }


}


