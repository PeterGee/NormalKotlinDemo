package com.example.myapplication.leetcode.tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author qipeng
 * @date 2022/2/23
 * @desc 二叉树
 */
class BinaryTree<T extends Comparable<T>> {

    // 根结点
    BSTNode<T> root;

    /**
     * 前序遍历  根左右
     *
     * @param tree
     */
    public void preOrder(BSTNode<T> tree) {
        if (tree != null) {
            System.out.println(tree.key);
            preOrder(tree.left);
            preOrder(tree.right);
        }
    }

    /**
     * 中序遍历 左中右
     *
     * @param tree
     */
    public void inOrder(BSTNode<T> tree) {
        if (tree != null) {
            inOrder(tree.left);
            System.out.println(tree.key);
            inOrder(tree.right);
        }
    }

    /**
     * 后序遍历 左右中
     *
     * @param tree
     */
    public void postOrder(BSTNode<T> tree) {
        if (tree != null) {
            postOrder(tree.left);
            postOrder(tree.right);
            System.out.println(tree.key);
        }
    }

    /**
     * 递归方式查找值
     *
     * @param tree
     * @param key
     * @return
     */
    public BSTNode<T> search(BSTNode<T> tree, T key) {
        if (tree == null) return null;

        int value = key.compareTo(tree.key);
        if (value > 0) {
            return search(tree.right, key);
        } else if (value < 0) {
            return search(tree.left, key);
        } else {
            return tree;
        }

    }

    /**
     * 移动指针方式
     *
     * @param tree
     * @param key
     * @return
     */
    public BSTNode<T> searchByNormalMethod(BSTNode<T> tree, T key) {
        while (tree != null) {
            int value = key.compareTo(tree.key);
            if (value > 0) {
                tree = tree.right;
            } else if (value < 0) {
                tree = tree.left;
            } else {
                return tree;
            }

        }
        return null;
    }

    /**
     * 查找最大结点
     *
     * @param tree
     * @return
     */
    public BSTNode<T> findMax(BSTNode<T> tree) {
        if (tree == null) return null;
        while (tree.right != null) {
            tree = tree.right;
        }
        return tree;
    }

    /**
     * 查找最小结点
     *
     * @param tree
     * @return
     */
    public BSTNode<T> findMinimum(BSTNode<T> tree) {
        if (tree == null) return null;
        while (tree.left != null) {
            tree = tree.left;
        }
        return tree;
    }


    /**
     * 查找当前结点的前驱结点
     *
     * @param node
     * @return
     */
    public BSTNode<T> findHeadNode(BSTNode<T> node) {
        if (node.left != null) {
            BSTNode<T> left = node.left;
            // 左子树最右结点
            while (left.right != null) {
                left = left.right;
            }
            return left;
        } else {
            // 当前结点为父节点的右子结点
            BSTNode<T> parent = node.parent;
            while (parent != null && parent.right != node) {
                node = parent;
                parent = node.parent;
            }
            return parent;
        }
    }


    /**
     * 查找结点的后继结点
     *
     * @param node
     * @return
     */
    public BSTNode<T> findTailNode(BSTNode<T> node) {
        // 查找右子树最左节点
        if (node.right != null) {
            BSTNode<T> right = node.right;
            while (right.left != null) {
                right = right.left;
            }
            return right;
        } else {
            // 当前结点为父节点的左子节点
            BSTNode<T> parent = node.parent;
            while (parent != null && parent.left != node) {
                node = parent;
                parent = node.parent;
            }
            return parent;
        }
    }

    /**
     * 通过前序遍历和中序遍历数组重建二叉树
     *
     * @param pre
     * @param in
     * @return
     */
    public static BSTNode<Integer> reConstructBinaryTree(int[] pre, int[] in) {
        if (pre == null || in == null || in.length < 1 || pre.length != in.length)
            return null;
        BSTNode<Integer> root = reConstructBinaryTree(pre, 0, pre.length - 1, in, 0, in.length - 1);
        return root;
    }

    private static BSTNode<Integer> reConstructBinaryTree(int[] pre, int startPre, int endPre, int[] in, int startIn, int endIn) {
        if (startPre > endPre || startIn > endIn)
            return null;
        BSTNode<Integer> root = new BSTNode<>(pre[startPre], null, null, null);

        for (int i = startIn; i <= endIn; i++)
            if (in[i] == pre[startPre]) {
                int leftCount = i - startIn;
                root.left = reConstructBinaryTree(pre, startPre + 1, startPre + leftCount, in, startIn, i - 1);
                root.right = reConstructBinaryTree(pre, leftCount + startPre + 1, endPre, in, i + 1, endIn);
            }

        return root;
    }


    /**
     * 输入一棵二叉树和一个整数， 打印出二叉树中结点值的和为输入整数的所有路径
     * 从树的根结点开始往下一直到叶结点所经过的结点形成一条路径
     *
     * @param tree     二叉树
     * @param expected 期望值
     */
    public static void findPathBySum(BSTNode<Integer> tree, int expected) {
        if (tree == null) return;
        List<Integer> mList = new ArrayList<>();
        int currentSum = 0;
        findPath(tree, mList, currentSum, expected);

    }

    private static void findPath(BSTNode<Integer> tree, List<Integer> mList, int currentSum, int expected) {
        if (tree != null) {
            currentSum += tree.key;
            mList.add(tree.key);

            if (currentSum < expected) {
                // 递归查找左子树
                findPath(tree.left, mList, currentSum, expected);
                // 递归查找右子树
                findPath(tree.right, mList, currentSum, expected);
            } else if (currentSum == expected) {
                // 走至叶子节点
                if (tree.left == null && tree.right == null) {
                    System.out.println(mList);
                }
            }
            // 回退
            mList.remove(mList.size() - 1);

        }

    }

    /**
     * 获取树的深度，树的深度为左子树深度和右子树深度较大值加1
     *
     * @param tree
     * @return
     */
    public static int getTreeDepth(BSTNode<Integer> tree) {
        if (tree == null) return 0;
        int leftDepth = getTreeDepth(tree.left);
        int rightDepth = getTreeDepth(tree.right);
        return leftDepth > rightDepth ? leftDepth + 1 : rightDepth + 1;
    }


    /**
     * 判断是否平衡树
     * <p>
     * 平衡树： 左子树和右子树深度差小于1
     *
     * @param tree
     * @return
     */
    public static boolean isBalanceTree(BSTNode<Integer> tree) {
        if (tree == null) return true;

        int leftDepth = getTreeDepth(tree.left);
        int rightDepth = getTreeDepth(tree.right);

        if (Math.abs(leftDepth - rightDepth) > 1) return false;
        return isBalanceTree(tree.left) && isBalanceTree(tree.right);
    }


    public static boolean isSymmetricalTree(BSTNode<Integer> tree) {
        if (tree == null) return true;
        return isSymmetricalTree(tree.left, tree.right);
    }

    /**
     * A
     * /      \
     * B         B              非平衡
     * C     D    C    D
     * <p>
     * <p>
     * A
     * /       \
     * B         B              平衡
     * D   C    C    D
     * <p>
     * 判断条件：
     * 深度相同的左子树的左结点与右子树的右结点值相同
     * 深度相同的左子树的右结点与右子树的左结点值相同
     *
     * @param left
     * @param right
     * @return
     */
    private static boolean isSymmetricalTree(BSTNode<Integer> left, BSTNode<Integer> right) {
        if (left == null && right == null) return true;
        if (left == null || right == null) return false;
        if (!left.key.equals(right.key)) return false;
        return isSymmetricalTree(left.left, right.right) && isSymmetricalTree(left.right, right.left);
    }

    /**
     * 从上到下按层打印二叉树，同一层的结点按从左到右的顺序打印，每一层打印一行
     *
     * @param tree
     */
    public static void printTreeMultiLines(BSTNode<Integer> tree) {
        if (tree == null) {
            return;
        }

        List<BSTNode> mList = new LinkedList<>();
        int number = 1;
        int size = 0;
        mList.add(tree);
        while (mList.size() > 0) {
            // 当前结点
            BSTNode currentNode = mList.remove(0);
            number--;
            System.out.print(currentNode.key + "  ");

            // 左子树
            if (currentNode.left != null) {
                mList.add(currentNode.left);
                size++;
            }

            // 右子树
            if (currentNode.right != null) {
                mList.add(currentNode.right);
                size++;
            }
            // 重置
            if (number == 0) {
                System.out.println();
                number = size;
                size = 0;
            }

        }

    }

    /**
     * 通过递归方式多行打印树
     *
     * @param tree
     */
    public static void printTreeMultiLineRecursive(BSTNode<Integer> tree) {
        List<ArrayList<Integer>> list = new ArrayList<>();
        depthAddData(tree, 1, list);
        for (ArrayList i : list) {
            System.out.println(i);
        }
    }

    private static void depthAddData(BSTNode<Integer> tree, int depth, List<ArrayList<Integer>> list) {
        if (tree == null) return;
        if (depth > list.size()) {
            list.add(new ArrayList<>());
        }
        list.get(depth - 1).add(tree.key);

        depthAddData(tree.left, depth + 1, list);
        depthAddData(tree.right, depth + 1, list);
    }


    /**
     * 之字形打印树
     *
     * @param tree
     */
    public static void printTreeZhi(BSTNode<Integer> tree) {
        if (tree == null) return;

        // 定义两个list
        List<BSTNode> order = new LinkedList<>();
        List<BSTNode> reverse = new LinkedList<>();
        // 顺序逆序flag
        boolean flag = true;
        order.add(tree);
        while (!order.isEmpty()) {
            // 从最后一个索引开始取
            BSTNode current = order.remove(order.size() - 1);
            System.out.print(current.key + " ");

            // 顺序
            if (flag) {
                if (current.left != null) {
                    reverse.add(current.left);
                }
                if (current.right != null) {
                    reverse.add(current.right);
                }
            } else {
                // 逆序
                if (current.right != null) {
                    reverse.add(current.right);
                }
                if (current.left != null) {
                    reverse.add(current.left);
                }
            }

            // 换行
            if (order.size() == 0) {
                flag = !flag;
                // 两个list 数据互换
                List<BSTNode> temp = reverse;
                reverse = order;
                order = temp;
                System.out.println();
            }


        }
    }


    /**
     * 查找第k大结点
     *
     * @param tree
     * @param k    第几个
     * @return
     */
    public static BSTNode findMaxKNode(BSTNode<Integer> tree, int k) {
        if (tree == null || k < 1) return null;
        // 中序遍历tree
        ArrayList<BSTNode> nodeList = new ArrayList();
        inOrder(tree, nodeList);
        if (nodeList.size() < k) return null;
        return nodeList.get(k - 1);
    }


    /**
     * 查找第k小结点
     *
     * @param tree
     * @param k    第几个
     * @return
     */
    public static BSTNode findMinKNode(BSTNode<Integer> tree, int k) {
        if (tree == null || k < 1) return null;
        // 中序遍历tree
        ArrayList<BSTNode> nodeList = new ArrayList();
        inOrder(tree, nodeList);
        if (nodeList.size() < k) return null;
        return nodeList.get((nodeList.size() - k));
    }

    private static void inOrder(BSTNode<Integer> tree, ArrayList<BSTNode> nodeList) {
        if (tree != null) {
            inOrder(tree.left, nodeList);
            nodeList.add(tree);
            inOrder(tree.right, nodeList);
        }

    }

    /**
     * 二叉树镜像
     *
     * 思路： 将二叉树所有左子结点和右子节点互换即可
     *
     * @param tree
     * @return
     */
    public static BSTNode<Integer> mirrorTree(BSTNode<Integer> tree) {
        if (tree != null) {
            BSTNode temp = tree.left;
            tree.left = tree.right;
            tree.right = temp;
            // 递归
            mirrorTree(tree.left);
            mirrorTree(tree.right);
        }
        return tree;
    }


    public static boolean hasChildTree(BSTNode parent, BSTNode childTree) {
        boolean hasChildTree = false;
        if (parent != null && childTree != null) {
            // 根结点
            if (parent.key == childTree.key) {
                hasChildTree = isChildTree(parent, childTree);
            }

            // 左子树
            if (!hasChildTree) {
                hasChildTree = isChildTree(parent.left, childTree);
            }

            // 右子树
            if (!hasChildTree) {
                hasChildTree = isChildTree(parent.right, childTree);
            }

        }
        return hasChildTree;
    }

    private static boolean isChildTree(BSTNode parent, BSTNode childTree) {
        if (childTree == null) return true;
        if (parent == null) return false;
        if (parent.key != childTree.key) return false;
        return isChildTree(parent.left, childTree.left) && isChildTree(parent.right, childTree.right);
    }


}
