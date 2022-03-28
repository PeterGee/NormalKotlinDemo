package com.example.myapplication.leetcode.sortAlgotithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author qipeng
 * @date 2022/3/9
 * @desc 排序算法
 */
class SortTest {
    public static void main(String[] args) {
        int[] arr = {1, 4, 3, 22, 22, 18, 90};
        printArray(arr);
        // BubbleSort(arr);
        // selectSort(arr);
        // insertSort(arr);
        // quickSort(arr, 0, arr.length - 1);
        // shellSort(arr);
        // mergeSort(arr);
        // heapSort(arr);

        // printArray(countSort(arr));
        // printArray(countSortOptimize(arr));
        // bucketSort(arr);
        radixSort(arr);
        printArray(arr);
    }

    private static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + "  ");
        }
        System.out.println();
    }

    /**
     * 冒泡排序
     *
     * @param arr
     */
    public static void BubbleSort(int[] arr) {
        if (arr == null || arr.length < 2) return;

        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }


    /**
     * 选择排序
     *
     * @param arr
     */
    public static void selectSort(int[] arr) {
        if (arr == null || arr.length < 2) return;
        int min;
        for (int i = 0; i < arr.length; i++) {
            min = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[min]) {
                    min = j;
                }
            }
            if (min != i) {
                swap(arr, i, min);
            }

        }
    }

    private static void swap(int[] arr, int i, int min) {
        int temp = arr[i];
        arr[i] = arr[min];
        arr[min] = temp;
    }


    /**
     * 插入排序
     *
     * @param arr
     */
    public static void insertSort(int[] arr) {
        if (arr == null || arr.length < 2) return;
        int temp;
        for (int i = 1; i < arr.length; i++) {
            // 取出变量
            temp = arr[i];
            while (i >= 1 && arr[i - 1] > temp) {
                // 后退一个位置
                arr[i] = arr[i - 1];
                i--;
            }
            // 插入
            arr[i] = temp;
        }
    }

    /**
     * 快速排序
     *
     * @param arr
     * @param start
     * @param end
     */
    public static void quickSort(int[] arr, int start, int end) {
        if (start < end) {
            int index = findIndex(arr, start, end);
            // 前半段排序
            quickSort(arr, start, index - 1);
            // 后半段排序
            quickSort(arr, index + 1, end);
        }
    }

    private static int findIndex(int[] arr, int start, int end) {
        // 基准数据
        int temp = arr[start];
        while (start < end) {
            // 从后向前查询
            if (start < end && arr[end] >= temp) {
                end--;
            }
            arr[start] = arr[end];

            // 从前向后查询
            if (start < end && arr[start] <= temp) {
                start++;
            }
            arr[end] = arr[start];
        }
        // 跳出循环时start和end相等,此时的start或end就是tmp的正确索引位置
        arr[start] = temp;
        return start;
    }

    /**
     * 希尔排序
     *
     * @param arr
     */
    public static void shellSort(int[] arr) {
        if (arr == null || arr.length < 2) return;
        // 设置步长
        int step = arr.length / 2;
        while (true) {
            for (int i = 0; i < step; i++) {
                for (int j = i; j + step < arr.length; j += step) {
                    if (arr[j] > arr[j + step]) {
                        int temp = arr[j];
                        arr[j] = arr[j + step];
                        arr[j + step] = temp;
                    }
                }

            }
            // 步长为1退出循环
            if (step == 1) {
                break;
            }
            step--;
        }


    }

    /**
     * 归并排序
     * 属于稳定排序  时间复杂度为O(nlogn)
     *
     * @param arr
     */
    public static void mergeSort(int[] arr) {
        // 创建临时数组
        int[] tempArr = new int[arr.length];
        // 排序
        innerSort(arr, 0, arr.length - 1, tempArr);
    }

    private static void innerSort(int[] arr, int start, int end, int[] tempArr) {
        int mid = (start + end) / 2;
        if (start < end) {
            // 前半段
            innerSort(arr, start, mid, tempArr);
            // 后半段
            innerSort(arr, mid + 1, end, tempArr);
            // merge
            mergeArray(arr, start, mid, end, tempArr);
        }
    }


    private static void mergeArray(int[] arr, int start, int mid, int end, int[] tempArr) {
        // 前段数组起始index
        int i = start;
        // 后半段数组起始index
        int j = mid + 1;
        // 临时数组index
        int index = 0;

        while (i <= mid && j <= end) {
            // 从小到大添加
            if (arr[i] < arr[j]) {
                tempArr[index++] = arr[i++];
            } else {
                tempArr[index++] = arr[j++];
            }
        }

        // 合并剩余部分
        while (i <= mid) {
            tempArr[index++] = arr[i++];
        }
        while (j <= end) {
            tempArr[index++] = arr[j++];
        }

        index = 0;

        // 数据回填
        while (start <= end) {
            arr[start++] = tempArr[index++];
        }
    }


    /**
     * 堆排序
     *
     * @param arr
     */
    public static void heapSort(int[] arr) {
        if (arr == null) return;
        // 构建大顶堆
        for (int i = arr.length / 2 - 1; i >= 0; i--) {
            adjustHeap(arr, i, arr.length);
        }

        for (int i = arr.length - 1; i > 0; i--) {
            // 交换堆顶元素与末尾元素
            swap(arr, 0, i);
            // 调整堆
            adjustHeap(arr, 0, i);
        }

    }

    /**
     * 调整堆
     *
     * @param arr
     * @param i
     * @param length
     */
    private static void adjustHeap(int[] arr, int i, int length) {
        // 取出当前元素
        int temp = arr[i];
        // 从左结节点开始遍历
        for (int l = 2 * i + 1; l < length; l = 2 * l + 1) {
            // 左子结点小于右子结点指向右子结点
            if (l + 1 < length && arr[l] < arr[l + 1]) {
                l++;
            }

            // 大于根结点,将结点值赋值给根结点
            if (arr[l] > temp) {
                arr[i] = arr[l];
                i = l;
            } else {
                break;
            }
        }
        // 将temp值放到最终的位置
        arr[i] = temp;
    }

    /**
     * 计数排序 稳定排序时间复杂度为O(n),以空间换时间
     *
     * @param arr
     * @return
     */
    public static int[] countSort(int[] arr) {
        if (arr == null) return null;
        // 查询最大值
        int max = findMaxValue(arr);
        // 创建计数数组
        int[] count = new int[max + 1];
        // 计数数组赋值
        for (int i : arr) {
            count[i]++;
        }
        // 遍历计数数组
        int index = 0;
        // 创建排序后结果数组
        int[] result = new int[arr.length];
        for (int i = 0; i < count.length; i++) {
            while (count[i] > 0) {
                result[index++] = i;
                count[i]--;
            }

        }
        return result;
    }

    private static int findMaxValue(int[] arr) {
        int temp = arr[0];
        for (int item : arr) {
            if (item > temp) {
                temp = item;
            }
        }
        return temp;
    }

    /**
     * 优化版 节约内存空间
     *
     * @param arr
     * @return
     */
    public static int[] countSortOptimize(int[] arr) {
        if (arr == null) return null;
        // 查询最大、最小值
        int max = findMaxValue(arr);
        int min = findMinValue(arr);
        // 创建计数数组
        int[] count = new int[max - min + 1];
        // 计数器赋值
        for (int i : arr) {
            count[i - min]++;
        }
        // 创建排序后的数组
        int index = 0;
        int[] result = new int[arr.length];
        for (int i = 0; i < count.length; i++) {
            while (count[i] > 0) {
                result[index++] = i + min;
                count[i]--;
            }
        }
        return result;

    }

    private static int findMinValue(int[] arr) {
        int min = arr[0];
        for (int item : arr) {
            if (item < min) {
                min = item;
            }
        }
        return min;
    }


    /**
     * 桶排序
     * <p>
     * 属于不稳定排序 时间复杂度：O(N + C) 额外空间复杂度：O(N + M)
     *
     * @param arr
     */
    public static void bucketSort(int[] arr) {
        if (arr == null || arr.length < 2) return;

        // 获取最大值、最小值
        int max = findMaxValue(arr);
        int min = findMinValue(arr);

        // 获取桶数量
        int bucketCount = (max - min) / arr.length + 1;
        ArrayList<LinkedList<Integer>> bucketList = new ArrayList<>(bucketCount);

        // 桶列表中添加list
        for (int i = 0; i < bucketCount; i++) {
            bucketList.add(new LinkedList<>());
        }

        // 将数据填充到桶中
        for (int i = 0, length = arr.length; i < length; i++) {
            int bucketIndex = (arr[i] - min) / length;
            bucketList.get(bucketIndex).add(arr[i]);
        }

        // 对桶内数据进行排序
        for (int i = 0, length = bucketList.size(); i < length; i++) {
            Collections.sort(bucketList.get(i));
        }

        // 将排序好的数据填充到数组中
        int index = 0;
        for (int i = 0, length = bucketList.size(); i < length; i++) {
            for (int j = 0; j < bucketList.get(i).size(); j++) {
                arr[index++] = bucketList.get(i).get(j);
            }
        }

    }

    /**
     * 基数排序
     *
     * @param arr
     */
    public static void radixSort(int[] arr) {
        if (arr == null || arr.length < 2) return;

        // 查询最大值
        int max = findMaxValue(arr);
        // 查询次数
        int times = getTimes(max);
        // 基数数量 0-9共10位
        int radixNumber = 10;
        ArrayList<ArrayList<Integer>> radixList = new ArrayList<>();

        // 添加链表用于存储数据
        for (int i = 0; i < radixNumber; i++) {
            radixList.add(new ArrayList<>());
        }
        // 将数据填充到基数list中
        for (int i = 0; i < times; i++) {
            for (int j = 0; j < arr.length; j++) {
                // 找出每个数对应的位的数值
                int x = arr[j] % (int) Math.pow(10, i + 1) / (int) Math.pow(10, i);
                radixList.get(x).add(arr[j]);
            }
            int index = 0;
            for (int k = 0; k < radixNumber; k++) {
                while (radixList.get(k).size() > 0) {
                    ArrayList<Integer> array = radixList.get(k);
                    // 将该数组的第一个元素添加到arr中
                    arr[index] = array.get(0);
                    // 移除子数组中的第一个元素，这样就能在第一个元素被使用之后，后面元素替换
                    array.remove(0);
                    // 将arr数组中下标也后移一位
                    index++;
                }
            }
        }
    }

    /**
     * 获取循环次数
     *
     * @param max
     * @return
     */
    private static int getTimes(int max) {
        int times = 0;
        while (max > 0) {
            max /= 10;
            times++;
        }
        return times;
    }

    public static void basicSort(int[] array) {
        //创建叠加数组
        List<ArrayList> dyadic = new ArrayList<>();
        //给大数组dyadic添加子数组
        for (int i = 0; i < 10; i++) {
            ArrayList<Integer> arr = new ArrayList<>();
            dyadic.add(arr);
        }

        //找出数组中的最大值
        int max = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }

        //判断最大值为几位数，其位数就是应该循环的次数
        int times = 0;
        while (max > 0) {
            max /= 10;
            times++;
        }
        System.out.println("times===" + times);
        System.out.println(dyadic);
        //循环times次，每次将对应位的数分配到相应的自数组中
        for (int i = 0; i < times; i++) {
            for (int j = 0; j < array.length; j++) {
                //找出每个数对应的位的数值
                int x = array[j] % (int) Math.pow(10, i + 1) / (int) Math.pow(10, i);
                System.out.print(" x=== " + x);
                //将该数组作为下标，找到对应的子数组
                ArrayList arr = dyadic.get(x);
                //将该元素添加到子数组中
                arr.add(array[j]);
                //因为子数组改变，因此更新大数组
                dyadic.set(x, arr);
            }

            System.out.println(dyadic);

            //将重新排好的子数组的值依次将需要被排序的数组的值覆盖
            int index = 0;   //用index作为数组array的下标
            //将子数组依次遍历，将每个子数组中的元素添加到array中
            for (int k = 0; k < 10; k++) {
                //当下标为k的子数组中有元素时
                while (dyadic.get(k).size() > 0) {
                    //得到该数组
                    ArrayList arr = dyadic.get(k);
                    ///将该数组的第一个元素添加到array中
                    array[index] = (int) arr.get(0);
                    //移除子数组中的第一个元素，这样就能在第一个元素被使用之后，后面元素替换
                    arr.remove(0);
                    //将array数组中下标也后移一位
                    index++;
                }
            }
        }
    }


}
