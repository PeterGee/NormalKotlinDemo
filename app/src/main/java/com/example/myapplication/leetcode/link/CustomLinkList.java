package com.example.myapplication.leetcode.link;

import java.io.FileReader;
import java.util.Stack;

/**
 * @author qipeng
 * @date 2022/2/16
 * @desc
 */
class CustomLinkList {
    // 头指针
    public Node head;
    // 当前指针
    public Node current;

    class Node {
        // 指针
        Node next;
        // 数据
        int data;

        public Node(int data) {
            this.data = data;
        }
    }

    // 添加数据
    public void addData(int data) {
        // 空栈
        if (head == null) {
            head = new Node(data);
            current = head;
        } else {
            current.next = new Node(data);
            current = current.next;
        }
    }

    // 添加链表
    public void addNode(Node node) {
        if (head == null) {
            return;
        }
        if (head == null) {
            head = node;
            current = head;
        } else {
            current.next = node;
            current = current.next;
        }
    }

    // 合并数据
    public Node mergeData(Node nodeOne, Node nodeTwo) {
        if (nodeOne == null && nodeTwo == null) {
            return null;
        }
        if (nodeOne == null) {
            return nodeTwo;
        }
        if (nodeTwo == null) {
            return nodeOne;
        }

        Node head;
        Node current;

        // 设置头节点
        if (nodeOne.data < nodeTwo.data) {
            head = nodeOne;
            current = nodeOne;
            nodeOne = nodeOne.next;
        } else {
            head = nodeTwo;
            current = nodeTwo;
            nodeTwo = nodeTwo.next;
        }

        // 合并元素
        while (nodeOne != null && nodeTwo != null) {
            if (nodeOne.data < nodeTwo.data) {
                current.next = nodeOne;
                current = current.next;
                nodeOne = nodeOne.next;
            } else {
                current.next = nodeTwo;
                current = current.next;
                nodeTwo = nodeTwo.next;
            }
        }

        // 合并剩余的元素
        if (nodeOne != null) {
            current.next = nodeOne;
        }
        if (nodeTwo != null) {
            current.next = nodeTwo;
        }
        return head;
    }

    // 打印链表
    public void printData(Node head) {
        Node node = head;
        while (node != null) {
            System.out.print("node is : " + node.data + "   ");
            node = node.next;
        }
        System.out.println();
    }

    /**
     * 反转链表
     *
     * @param head
     */
    public Node reverseData(Node head) {
        // 原始链表头结点
        Node headNode = head;
        // 反转链表头结点
        Node revHead = null;

        while (headNode != null) {
            // 存储next节点
            Node temp = headNode.next;
            // 反转
            headNode.next = revHead;
            // 反转链表头结点后移
            revHead = headNode;
            // 头结点后移
            headNode = temp;
        }
        return revHead;
    }

    /**
     * 递归实现
     *
     * @param head
     * @return
     */
    public Node reverseDataTwo(Node head) {
        if (head == null || head.next == null) return head;
        Node next = head.next;
        // 查找最后一个节点
        Node lastNode = reverseDataTwo(next);
        // 反转
        next.next = head;
        head.next = null;
        return lastNode;
    }

    /**
     * 是否是回环
     *
     * @param node
     * @return
     */
    public boolean isCycleLink(Node node) {
        if (node == null) return false;
        // 慢指针
        Node slow = node;
        // 快指针
        Node fast = node;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取环长度
     *
     * @param node
     * @return
     */
    public int getCycleLength(Node node) {
        int length = 0;
        if (!isCycleLink(node)) return length;
        Node slow = node;
        Node fast = node;
        while (fast != null && fast.next != null) {
            length++;
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                break;
            }
        }
        return length;

    }

    /**
     * 获取环的入口
     *
     * @param node
     * @return
     */
    public Node getCyclePoint(Node node) {
        if (!isCycleLink(node)) return null;
        Node slow = node;
        Node fast = node;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                return fast;
            }
        }
        return fast;
    }

    /**
     * 获取环入口方法二
     *
     * @param node
     * @return
     */
    public Node getCyclePointTwo(Node node) {
        if (!isCycleLink(node)) return null;
        Node current = node;
        while (current != null && current.next != null) {
            current = current.next;
            if (current == node) {
                return current;
            }
        }
        return current;
    }

    /**
     * 通过栈实现逆序打印
     *
     * @param node
     */
    public void reversePrintByStack(Node node) {
        if (node == null) return;
        Stack mStack = new Stack();

        // addData
        while (node != null) {
            mStack.push(node.data);
            node = node.next;
        }

        // printStack
        while (!mStack.empty()) {
            System.out.print(mStack.pop() + " ");
        }
    }

    /**
     * 通过递归形式实现逆序打印
     *
     * @param node
     */
    public void reversePrintByRecursive(Node node) {
        if (node == null) return;
        reversePrintByStack(node.next);
        System.out.println(node.data);
    }

    /**
     * 查询链表中倒数第k个节点
     *
     * @param head 链表
     * @param k    倒数第几个元素
     * @return
     */
    public Node findItemByLastPoint(Node head, int k) {
        if (head == null || k == 0) return null;
        Node slow = head;
        Node fast = head;

        // 从前往后移动k-1个节点
        for (int i = 0; i < k - 1; i++) {
            fast = fast.next;
            // 超出长度
            if (fast == null) {
                return null;
            }
        }

        // 移动slow,输出slow
        while (fast.next != null) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow;

    }

    /**
     * 移除链表中第k个元素
     *
     * @param head 链表
     * @param k    索引
     * @return 新链表
     */
    public Node removeLinkItemByIndex(Node head, int k) {
        if (head == null || k == 0) return null;
        Node temp = new Node(0);
        temp.next = head;

        // 查找第k-1个元素
        for (int i = 0; i < k - 1; i++) {
            temp = temp.next;
            // 越界
            if (temp == null) {
                return null;
            }
        }
        // 修改指针
        temp.next = temp.next.next;
        return temp;
    }

    /**
     * 查找两个链表的第一个公共节点
     *
     * @param headOne
     * @param headTwo
     * @return
     */
    public Node findFirstCommonNode(Node headOne, Node headTwo) {
        int nodeOneLength = getLength(headOne);
        int nodeTwoLength = getLength(headTwo);

        // 长链表头节点
        Node nodeLong = headOne;
        // 短链表头结点
        Node nodeShort = headTwo;

        //  对比长度
        int diffLength;
        if (nodeOneLength > nodeTwoLength) {
            diffLength = nodeOneLength - nodeTwoLength;
        } else {
            nodeLong = headTwo;
            nodeShort = headOne;
            diffLength = nodeTwoLength - nodeOneLength;
        }

        // 移动头指针
        for (int i = 0; i < diffLength; i++) {
            nodeLong = nodeLong.next;
        }
        // 遍历比对
        while (nodeLong != null && nodeShort != null && nodeLong != nodeShort) {
            nodeLong = nodeLong.next;
            nodeShort = nodeShort.next;
        }
        return nodeLong;

    }

    private static int getLength(Node head) {
        int length = 0;
        while (head != null) {
            length++;
            head = head.next;
        }
        return length;
    }

    /**
     * 查询第一个公共节点
     *
     * @param nodeOne
     * @param nodeTwo
     * @return
     */
    public Node findFirstCommonNodeTwo(Node nodeOne, Node nodeTwo) {
        if (nodeOne == null || nodeTwo == null) return null;
        Node first = nodeOne;
        Node second = nodeTwo;
        while (first != second) {
            first = (first == null ? second : first.next);
            second = (second == null ? first : second.next);
        }
        return first;

    }

}
