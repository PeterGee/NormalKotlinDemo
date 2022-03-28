package com.example.myapplication.leetcode.link;

/**
 * @author qipeng
 * @date 2022/2/15
 * @desc 链表测试题
 */
class LinkListTest {
    public static void main(String[] args) {
        // methodStack();
        //  methodLink();
        // methodLinkCycle();
        // methodReversePrint();
        // findLastNPoint();
        // removeItemByIndex();
        findCommonIndex();
    }

    private static void findCommonIndex() {
        CustomLinkList mLink = new CustomLinkList();
        CustomLinkList mLinkTwo = new CustomLinkList();
        CustomLinkList linkThree = new CustomLinkList();
        for (int i = 0; i < 10; i++) {
            mLink.addData(i);
        }
        for (int i = 8; i < 15; i++) {
            mLinkTwo.addData(i);
        }
        // System.out.println("第一个公共节点是： " + linkThree.findFirstCommonNode(mLink.head, mLinkTwo.head));
         System.out.println("第一个公共节点是： " + linkThree.findFirstCommonNodeTwo(mLink.head, mLinkTwo.head).data);

    }

    private static void removeItemByIndex() {
        CustomLinkList mLink = new CustomLinkList();
        for (int i = 0; i < 10; i++) {
            mLink.addData(i);
        }
        mLink.printData(mLink.head);
        mLink.removeLinkItemByIndex(mLink.head, 10);
        mLink.printData(mLink.head);
    }

    private static void findLastNPoint() {
        CustomLinkList mLink = new CustomLinkList();
        for (int i = 0; i < 10; i++) {
            mLink.addData(i);
        }
        System.out.println("倒数第5个元素是： " + mLink.findItemByLastPoint(mLink.head, 5).data);
    }

    private static void methodReversePrint() {
        CustomLinkList mLink = new CustomLinkList();
        for (int i = 0; i < 5; i++) {
            mLink.addData(i);
        }
        // mLink.reversePrintByStack(mLink.head);
        mLink.reversePrintByRecursive(mLink.head);
    }


    private static void methodLinkCycle() {
        CustomLinkList list = new CustomLinkList();
        for (int i = 0; i < 5; i++) {
            list.addData(i);
        }
        list.addNode(list.head);
        System.out.println("isCycleLink=== " + list.isCycleLink(list.head));

        System.out.println("cycleLength=== " + list.getCycleLength(list.head));

        System.out.println("cyclePoint===" + list.getCyclePoint(list.head).data);

        System.out.println("cyclePoint===" + list.getCyclePointTwo(list.head).data);
    }

    private static void methodLink() {
        CustomLinkList listOne = new CustomLinkList();
        CustomLinkList listTwo = new CustomLinkList();
        // 向LinkList中添加数据
        for (int i = 0; i < 3; i++) {
            listOne.addData(i);
        }
        for (int i = 2; i < 5; i++) {
            listTwo.addData(i);
        }
        CustomLinkList listThree = new CustomLinkList();
        listThree.head = listThree.mergeData(listOne.head, listTwo.head);
        listThree.printData(listThree.head);

        // listThree.printData(listThree.reverseData(listThree.head));

        listThree.printData(listThree.reverseDataTwo(listThree.head));

    }

    private static void methodStack() {
        CustomStack customStack = new CustomStack();
        customStack.init();
        for (int i = 0; i < 4; i++) {
            customStack.push(i);
        }
        customStack.printStack();
        customStack.pop();
        System.out.println(" customStack is empty=== " + customStack.isStackEmpty());
        customStack.printStack();
        System.out.println(" customStack peek=== " + customStack.peek());
    }


}

