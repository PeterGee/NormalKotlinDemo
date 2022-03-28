package com.example.myapplication.leetcode.tree;

/**
 * @author qipeng
 * @date 2022/2/23
 * @desc
 */
class BinaryTreeTest {
    public static void main(String[] args) {
        int[] preorder = {3, 9, 20, 15, 7};
        int[] inorder = {9, 3, 15, 20, 7};
        BSTNode<Integer> tree = BinaryTree.reConstructBinaryTree(preorder, inorder);


        int[] preorderTwo = {1, 2, 3,4,2,4,3};
        int[] inorderTwo = {3,2,4,1,4,2,3};
        BSTNode<Integer> balance = BinaryTree.reConstructBinaryTree(preorderTwo, inorderTwo);


        int[] preorderThree = {1};
        int[] inorderThree = {1};
        BSTNode<Integer> balanceTwo = BinaryTree.reConstructBinaryTree(preorderThree, inorderThree);

       // printTree(tree);

       // BinaryTree.findPathBySum(tree,18);
       // BinaryTree.findPathBySum(tree,12);

       // System.out.println("Depth is: "+BinaryTree.getTreeDepth(tree));

        // System.out.println("isBalance tree== : "+BinaryTree.isBalanceTree(tree));

        // System.out.println("is Symmetrical tree=== :"+BinaryTree.isSymmetricalTree(tree));
        // System.out.println("is Symmetrical tree=== :"+BinaryTree.isSymmetricalTree(balance));

         // BinaryTree.printTreeMultiLines(tree);
        // BinaryTree.printTreeMultiLineRecursive(tree);

        BinaryTree.printTreeZhi(tree);


        // System.out.println(BinaryTree.findMaxKNode(tree,1).key);
        // System.out.println(BinaryTree.findMinKNode(tree,1).key);

        System.out.println(BinaryTree.hasChildTree(tree,balance));
        System.out.println(BinaryTree.hasChildTree(tree,tree));
        System.out.println(BinaryTree.hasChildTree(balance,balanceTwo));

    }

    private static void printTree(BSTNode<Integer> tree) {
        if (tree != null) {
            System.out.println(tree.key);
            printTree(tree.left);
            printTree(tree.right);
        }
    }
}
