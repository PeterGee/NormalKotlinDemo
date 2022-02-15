package com.example.myapplication.leetcode.array;

import java.util.HashMap;

/**
 * @author qipeng
 * @date 2022/2/8
 * @desc
 */
class ArrayTest {

    public static void main(String[] args) {
        int[] arr = {1, 1, 1, 2, 5, 7, 8, 8, 8, 8, 10};
        // System.out.println("majorData is: " + getMajorElement(arr));
        int[] arr2 = {1, 1, 10, -5, 5, 2, 7, 5, 7, 8, 8, 8, 10};
        // System.out.println("maxSubArrayValue is :"+getMaxSubArrayValue(arr2));

        // printArray(arr);
        // reorderArray(arr);
        // printArray(arr);

        // String[] arr3 = {"3", "32", "321"};
        // System.out.println(getMinNumber(arr3));

        // System.out.println("count is " + getCountInSortedArray(arr2, 8));

        // printArray(findItemAppearanceOnce(arr2));

        int[] arr3 = {1, 2, 4, 7, 11, 15};
        // printArray(getItemWithSum(arr3, 15));

        int[] arr4 = {2, 3, 1, 0, 2, 5, 3};
        System.out.println(getDuplicateItem(arr4));
    }


    public static void printArray(int[] arr) {
        for (int i : arr) {
            System.out.print(i + " ");
        }
        System.out.println();
    }


    /**
     * 问题如下所示：
     * - 给你一个长度为n的数组，其中有一个数字出现的次数至少为n/2，找出这个数字
     * - 注意，需要的是排序的数组
     * 相等时，增加栈大小；不相等时，减少栈大小
     */

    // 解法
    public static int getMajorElement(int[] arr) {
        if (arr == null || arr.length == 0) return 0;
        int candidate = -1; // 默认元素值
        int count = 0; // 栈大小
        for (int j : arr) {
            if (count == 0) {
                candidate = j;
                count++;
            } else if (candidate == j) {
                count++;
            } else {
                count--;
            }
        }
        return candidate;
    }


    /**
     * 问题如下所示：
     * - 输入一个整型数组，数组里有正数也有负数。数组中一个或连续的多个整数组成一个子数组。求所有子数组的和的最大值。要求时间复杂度为O(n)。
     */

    // 解法
    public static int getMaxSubArrayValue(int[] arr) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException(" illegalArgument");
        }

        // 记录最大值
        int max = Integer.MIN_VALUE;
        // 当前最大值
        int currentMaxValue = 0;
        for (int i : arr) {
            if (currentMaxValue <= 0) {
                currentMaxValue = i;
            } else {
                currentMaxValue += i;
            }

            if (max < currentMaxValue) {
                max = currentMaxValue;
            }
        }
        return max;
    }


    /**
     * 问题如下所示：
     * - 输入一个整数数组，实现一个函数来调整该数组中数字的顺序，使得所有奇数位于数组的前半部分，所有偶数位予数组的后半部分。
     */

    public static void reorderArray(int[] arr) {
        // 长度小于2 无需处理
        if (arr == null || arr.length < 2) return;

        // 初始化索引
        int start = 0;
        int end = arr.length - 1;

        // 换位置
        while (start < end) {
            // 奇数在前  从前往后查询偶数
            while (start < end && arr[start] % 2 != 0) {
                start++;
            }

            // 偶数在后 从后往前查找奇数
            while (start < end && arr[end] % 2 == 0) {
                end--;
            }

            // 查询到奇数和偶数 调换位置
            int temp = arr[start];
            arr[start] = arr[end];
            arr[end] = temp;
        }
    }

    /**
     * 问题如下所示：
     * - 输入一个正整数数组，把数组里所有数字拼接起来排成一个数，打印能拼接出的所有数字中最小的一个。
     * - 示例 :
     * 例如输入数组{3， 32, 321}，则扫描输出这3 个数字能排成的最小数字321323。
     */
    public static String getMinNumber(String[] arr) {
        Mcomparator mcomparator = new Mcomparator();
        quickSortArray(arr, 0, arr.length - 1, mcomparator);
        StringBuilder builder = new StringBuilder();
        for (String i : arr) {
            builder.append(i);
        }
        return builder.toString();
    }

    private static void quickSortArray(String[] arr, int start, int end, Mcomparator comparator) {
        if (start < end) {
            String pivot = arr[start];
            int left = start;
            int right = end;

            while (start < end) {
                while (start < end && comparator.compare(arr[end], pivot) >= 0) {
                    end--;
                }
                arr[start] = arr[end];
                while (start < end && comparator.compare(arr[start], pivot) <= 0) {
                    start++;
                }
                arr[end] = arr[start];
            }
            arr[start] = pivot;
            quickSortArray(arr, left, start - 1, comparator);
            quickSortArray(arr, start + 1, right, comparator);
        }
    }

    /**
     * - 问题如下所示：
     * - 统计一个数字：在排序数组中出现的次数。
     * - 示例 :
     * - 例如输入排序数组｛ 1, 2, 3, 3, 3, 3, 4, 5｝和数字3 ，由于3 在这个数组中出现了4 次，因此输出4 。
     */

    public static int getCountInSortedArray(int[] arr, int k) {
        int count = 0;
        if (arr == null || arr.length == 0) return 0;
        int firstIndex = findFirstIndex(arr, k, 0, arr.length - 1);
        int lastIndex = findLastIndex(arr, k, 0, arr.length - 1);
        System.out.println("firstIndex=== " + firstIndex + "  lastIndex=== " + lastIndex);
        if (firstIndex > -1 && lastIndex > -1) {
            count = lastIndex - firstIndex + 1;
        }
        return count;
    }


    /**
     * 采用二分查找查询index
     *
     * @param arr
     * @param k
     * @param start
     * @param end
     * @return
     */
    private static int findFirstIndex(int[] arr, int k, int start, int end) {
        if (start > end) return -1;
        int midIndex = start + (end - start) / 2;
        int midValue = arr[midIndex];
        if (midValue == k) {
            if (midIndex > 0 && arr[midIndex - 1] != k || midIndex == 0) {
                return midIndex;
            } else {
                // 从中间向前查找
                end = midIndex - 1;
            }
        } else if (midValue > k) {
            end = midIndex - 1;
        } else {
            start = midIndex + 1;
        }
        return findFirstIndex(arr, k, start, end);
    }

    /**
     * 查找最后一个index
     *
     * @param arr
     * @param k
     * @param start
     * @param end
     * @return
     */
    private static int findLastIndex(int[] arr, int k, int start, int end) {
        if (start > end) return -1;
        int midIndex = start + (end - start) / 2;
        int midValue = arr[midIndex];
        if (midValue == k) {
            if (midIndex + 1 < arr.length && arr[midIndex + 1] != k || midIndex == arr.length - 1) {
                return midIndex;
            } else {
                // 从中间向后查询
                start = midIndex + 1;
            }
        } else if (midValue > k) {
            end = midIndex - 1;
        } else {
            start = midIndex + 1;
        }
        return findLastIndex(arr, k, start, end);
    }


    /**
     * 问题如下所示：
     * - 一个整型数组里除了两个数字之外，其他的数字都出现了两次，请写程序找出这两个只出现一次的数字。要求时间复杂度是O(n)，空间复杂度是O(1)。
     */
    public static int[] findItemAppearanceOnce(int[] arr) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("arr is invalid");
        }
        HashMap<Integer, Integer> data = new HashMap<>(arr.length);

        // 遍历添加数据
        for (int k : arr) {
            if (data.containsKey(k)) {
                data.put(k, 2);
            } else {
                data.put(k, 1);
            }
        }

        int[] num = new int[2];
        int count = 0;

        // 找出出现一次的数据存储到数组中
        for (int j : arr) {
            if (data.get(j) == 1) {
                if (count == 0) {
                    num[0] = j;
                    count++;
                } else {
                    num[1] = j;
                }
            }
        }
        return num;
    }

    /**
     * ### 题目要求
     * - 问题如下所示：
     * - 输入一个递增排序的数组和一个数字s，在数组中查找两个数，得它们的和正好是s。如果有多对数字的和等于s，输出任意一对即可。
     * - 示例 :
     * - 例如输入数组｛1 、2 、4、7 、11 、15 ｝和数字15. 由于4+ 11 = 15 ，因此输出4 和11 。
     * 问题分析：
     * 我们先在数组中选择两个数字，如果它们的和等于输入的s，我们就找到了要找的两个数字。如果和小于s 呢？我们希望两个数字的和再大一点。
     * 由于数组已经排好序了，我们可以考虑选择较小的数字后面的数字。因为排在后面的数字要大一些，那么两个数字的和也要大一些，
     * 就有可能等于输入的数字s 了。同样，当两个数字的和大于输入的数字的时候，我们可以选择较大数字前面的数字，因为排在数组前面的数字要小一些。
     */

    public static int[] getItemWithSum(int[] arr, int sum) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("invalid argument arr");
        }
        int[] items = new int[2];

        int start = 0;
        int end = arr.length - 1;
        long currentSum;
        while (start < end) {
            currentSum = arr[start] + arr[end];
            if (currentSum == sum) {
                items[0] = arr[start];
                items[1] = arr[end];
                break;
            } else if (currentSum > sum) {
                end--;
            } else {
                start++;
            }
        }
        return items;
    }

    /**
     * - 问题如下所示：
     * - 在一个长度为n的数组里的所有数字都在0到n-1的范围内。数组中某些数字是重复的，但不知道有几个数字重复了，
     * 也不知道每个数字重复了几次。请找出数组中任意一个重复的数字。
     * - 示例 :
     * - 例如，如果输入长度为7的数组{2,3,1,0,2,5,3}，那么对应的输出是重复的数字2或者3。
     */

    public static int getDuplicateItem(int[] arr) {
        if (arr == null || arr.length == 0) return -1;

        // 查询是否存在
        for (int i : arr) {
            if (i < 0 || i > arr.length) {
                return -1;
            }
        }

        // 第i个索引元素，不等于i，将元素与索引为当前元素值的条目进行交换
        for (int i = 0; i < arr.length; i++) {
            while (arr[i] != i) {
                if (arr[arr[i]] == arr[i]) {
                    return arr[i];
                } else {
                    int temp = arr[i];
                    arr[i] = arr[temp];
                    arr[temp] = temp;
                }
            }
        }
        return -1;

    }

}
