package com.example.myapplication.leetcode.array;

import java.util.Comparator;

/**
 * @author qipeng
 * @date 2022/2/10
 * @desc
 */
class Mcomparator implements Comparator<String> {
    @Override
    public int compare(String o1, String o2) {
        return (o1 + o2).compareTo(o2 + o1);
    }
}
