package com.example.myapplication.leetcode.tree;

/**
 * @author qipeng
 * @date 2022/2/23
 * @desc
 */
public class BSTNode<T extends Comparable<T>> {
    T key;
    BSTNode<T> left;
    BSTNode<T> right;
    BSTNode<T> parent;

    public BSTNode(T key, BSTNode<T> parent, BSTNode<T> left, BSTNode<T> right) {
        this.key = key;
        this.parent = parent;
        this.left = left;
        this.right = right;
    }
}
