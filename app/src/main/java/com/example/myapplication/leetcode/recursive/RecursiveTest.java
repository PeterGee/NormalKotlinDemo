package com.example.myapplication.leetcode.recursive;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;

/**
 * @author qipeng
 * @date 2022/3/17
 * @desc
 */
class RecursiveTest {
    public static void main(String[] args) {
        //  System.out.println(fibonacci(10));
        // System.out.println(getNumber(10));
        // System.out.println(getNumberByFibonacci(10));
        // System.out.println(getFactorial(10));

        int[] arrOne = {1, 2, 3, 4, 5};
        int[] arrTwo = {2, 3, 6, 7, 9};
        int[] arrThree = {2, 3, 0, 4, 6};

        /*int[] arrResult = mergeSortedArray(arrOne, arrTwo);
        for (int i : arrResult) {
            System.out.print(i + " ");
        }*/

        // knapsackQuestion(arrOne, 12);
        // selectTeamQuestion(arrOne, 3);
        // hanoiQuestion(3, "A", "B", "C");
        // System.out.println(jumpFloor(10));
        // System.out.println(jumpFloorByForeach(10));
        // System.out.println(jumpFloorThree(10));
        // printOneToNDigits(2);

        // System.out.println(isStraight(arrOne));
        // System.out.println(isStraight(arrTwo));
        // System.out.println(isStraight(arrThree));

        System.out.println(getFirstCharAppearOnce("google"));
    }

    /**
     * 斐波那契数列
     *
     * @param i
     */
    private static int fibonacci(int i) {
        if (i <= 0) return 0;
        if (i == 1 || i == 2) return 1;
        return fibonacci(i - 2) + fibonacci(i - 1);
    }


    /**
     * 等差数列求和
     * 例如 1到50
     *
     * @param n
     * @return
     */
    private static int getNumber(int n) {
        // 最小值 min
        int min = 1;
        // 等差
        int d = 1;
        // 最大值
        int max = min + (n - 1) * d;
        // 等差数列公式  Sn=n*(a1+an)/2
        return n * (min + max) / 2;
    }

    /**
     * 通过斐波那契求和
     *
     * @param n
     * @return
     */
    private static int getNumberByFibonacci(int n) {
        if (n <= 0) return 0;
        return n + getNumberByFibonacci(n - 1);
    }

    /**
     * 求n 的阶乘
     *
     * @param n
     * @return
     */
    private static BigInteger getFactorial(int n) {
        if (n <= 0) return BigInteger.ONE;
        return BigInteger.valueOf(n).multiply(getFactorial(n - 1));
    }


    /**
     * 合并两个有序数组
     *
     * @param arrOne
     * @param arrTwo
     * @return
     */
    public static int[] mergeSortedArray(int[] arrOne, int[] arrTwo) {
        if (arrOne == null && arrTwo == null) return new int[0];
        // 创建结果数组
        int[] resultArray = new int[arrOne.length + arrTwo.length];
        // 初始化索引
        int m = 0, n = 0, index = 0;
        // 合并
        while (m < arrOne.length && n < arrTwo.length) {
            if (arrOne[m] < arrTwo[n]) {
                resultArray[index++] = arrOne[m++];
            } else {
                resultArray[index++] = arrOne[n++];
            }
        }

        // 合并剩余部分
        while (m < arrOne.length) {
            resultArray[index++] = arrOne[m++];
        }
        while (n < arrTwo.length) {
            resultArray[index++] = arrTwo[n++];
        }
        return resultArray;
    }


    /**
     * 单一背包问题
     *
     * @param arr
     * @param total
     */
    public static void knapsackQuestion(int[] arr, int total) {
        if (arr == null || arr.length < 0 || total < 0) return;
        boolean[] selected = new boolean[arr.length];
        knapsack(arr, selected, total, 0);
    }

    /**
     * 找出符合承重重量的组合
     *
     * @param total 总重量
     * @param index 可供选择的重量下标
     */
    public static void knapsack(int[] arr, boolean[] selects, int total, int index) {
        if (total < 0 || total > 0 && index >= arr.length) {
            return; // 没找到解决办法，直接返回
        }
        // 总重量为0，则找到解决办法了
        if (total == 0) {
            for (int i = 0; i < index; i++) {
                if (selects[i]) {
                    System.out.print(arr[i] + " ");
                }
            }
            System.out.println();
            return;
        }
        // 第一个合适
        selects[index] = true;
        knapsack(arr, selects, total - arr[index], index + 1);

        // 沒有找到合适数据，重置，从第二个开始查找
        selects[index] = false;
        knapsack(arr, selects, total, index + 1);
    }


    /**
     * 从一组人中选择一只队伍
     *
     * @param arr
     * @param number
     */
    public static void selectTeamQuestion(int[] arr, int number) {
        if (arr == null || arr.length < 0 || number < 0) return;

        boolean[] selected = new boolean[arr.length];
        selectTeam(arr, selected, number, 0);
    }


    private static void selectTeam(int[] arr, boolean[] selected, int number, int index) {
        // 查询到合适的组合
        if (number == 0) {
            for (int i = 0; i < selected.length; i++) {
                if (selected[i]) {
                    System.out.print(arr[i] + " ");
                }
            }
            System.out.println();
            return;
        }
        // 防止越界
        if (index >= arr.length) {
            return;
        }
        // 第一个合适
        selected[index] = true;
        selectTeam(arr, selected, number - 1, index + 1);

        // 第一个不合适
        selected[index] = false;
        selectTeam(arr, selected, number, index + 1);
    }

    /**
     * 汉诺塔问题
     *
     * @param count 总数
     * @param from  起始塔
     * @param end   目标塔
     * @param temp  中介塔
     */
    public static void hanoiQuestion(int count, String from, String end, String temp) {
        if (count == 1) {
            System.out.println("index: " + count + " form:" + from + "  to: " + end);
        } else {
            hanoiQuestion(count - 1, from, end, temp);
            System.out.println("index: " + count + " form:" + from + "  to: " + end);
            hanoiQuestion(count - 1, temp, end, from);
        }
    }

    /**
     * 跳台阶问题
     * <p>
     * 一次可以跳一个台阶，也可以跳两个台阶，N个台阶有多少种跳法
     *
     * @param number
     * @return
     */
    public static int jumpFloor(int number) {
        if (number <= 0) return 0;
        if (number <= 2) return number;
        return jumpFloor(number - 1) + jumpFloor(number - 2);
    }


    /**
     * 跳台阶问题
     * 通过动态规划方式实现
     *
     * @param number
     * @return
     */
    public static int jumpFloorByForeach(int number) {
        if (number <= 0) return 0;
        if (number <= 2) return number;

        // 定义三个变量
        int a = 1, b = 2, c = 0;
        // 自顶向下遍历累加
        for (int i = 3; i <= number; i++) {
            c = a + b;
            a = b;
            b = c;
        }
        return c;
    }

    /**
     * 一只青蛙一次可以跳上1级台阶，也可以跳上2级……它也可以跳上n级。求该青蛙跳上一个n级的台阶总共有多少种跳法
     * |  0，n = 0
     * f(n)   =  |  1, n = 1
     * |  2 * f(n-1) , n >= 2
     */

    public static int jumpFloorThree(int number) {
        if (number <= 0) return 0;
        if (number == 1) return number;

        // 计数器
        int c = 1;
        for (int i = 2; i <= number; i++) {
            c = 2 * c;
        }
        return c;
    }

    /**
     * 求给定整数中二进制数表示后1的个数
     *
     * @param n
     * @return
     */
    public static int getOneCount(int n) {
        int result = 0;
        while (n != 0) {
            result++;
            n = n & (n - 1);
        }
        return result;
    }

    /**
     * 输入数字n，按顺序打印出从1最大的n位十进制数。比如输入3，则打印出1、2、3 一直到最大的3位数即999
     *
     * @param count 位数
     */
    public static void printOneToNDigits(int count) {
        if (count < 1) return;

        // 创建数组长度为count
        char[] chars = new char[count];
        // 初始化chars
        Arrays.fill(chars, '0');
        // 赋值chars[0]
        for (int i = 0; i < 10; i++) {
            chars[0] = (char) (i + '0');
            // 打印数值
            printNumber(chars, count, 0);
        }

    }

    /**
     * 打印数值
     *
     * @param chars 数组
     * @param count 总长度
     * @param index 索引
     */
    private static void printNumber(char[] chars, int count, int index) {
        if (index == count - 1) {
            printPerNumber(chars);
            return;
        }
        // 设置其他索引位值
        for (int i = 0; i < 10; i++) {
            chars[index + 1] = (char) (i + '0');
            printNumber(chars, count, index + 1);
        }
    }

    /**
     * 打印单个数
     *
     * @param chars
     */
    private static void printPerNumber(char[] chars) {
        boolean isStartZero = true;
        for (char aChar : chars) {
            if (isStartZero && aChar == '0') {
                continue;
            }
            isStartZero = false;
            System.out.print(aChar);
        }
        System.out.println();
    }

    /**
     * 扑克牌是否为顺子
     * 题目：从扑克牌中随机抽5张牌，判断是不是一个顺子， 即这5张牌是不是连续的。
     * 2～10为数字本身， A为1。 J为11、Q为12、 为13。大小王可以看成任意数字。
     *
     * @param arr
     * @return
     */
    public static boolean isStraight(int[] arr) {
        if (arr == null || arr.length != 5) return false;

        // 排序
        Arrays.sort(arr);
        // 0的数量
        int zeroCount = 0;
        // 间隔数量
        int gapCount = 0;
        // 查询0的数量
        for (int j : arr) {
            if (j == 0) {
                zeroCount++;
            }
        }
        // 一副扑克中王的数量最多两个
        if (zeroCount > 2) return false;

        int small = zeroCount;
        int big = small + 1;
        // 查询间隔数量
        while (big < arr.length) {
            if (arr[small] == arr[big]) {
                return false;
            }
            gapCount += (arr[big] - arr[small] - 1);
            small = big;
            big++;
        }
        return zeroCount >= gapCount;
    }

    /**
     * 写一个函数，求两个整数之和，要求在函数体内不得使用＋、－、×、÷四则运算符号
     * <p>
     * 位异或：相同为0，不同为1
     * 位与：有0为0，同1为1
     */
    public static int addFunction(int a, int b) {
        if (a == 0) return b;
        if (b == 0) return a;
        return addFunction(a ^ b, a & b << 1);
    }

    /**
     * 请实现一个函数用来找出字符流中第一个只出现一次的字符
     * 例如，当从字符流中只读出前两个字符“Go”时，第一个只出现一次的字符是‘g’。
     * 当从该字符流中读出前六个字符“google”时，第一个只出现1次的字符是”l”
     */

    public static Character getFirstCharAppearOnce(String str) {
        if (str == null) return null;
        Character firstOnceChar = null;
        char[] arr = str.toCharArray();
        HashMap<Character, Integer> charMap = new HashMap<>();

        // 遍历填充数据
        for (char c : arr) {
            if (charMap.containsKey(c)) {
                charMap.put(c, 2);
            } else {
                charMap.put(c, 1);
            }
        }

        // 查找第一个出现1次的值返回
        for (char c : arr) {
            if (charMap.get(c) == 1) {
                firstOnceChar = c;
                break;
            }
        }
        return firstOnceChar;

    }


}
