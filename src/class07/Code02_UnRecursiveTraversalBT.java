package class07;

import java.util.Stack;

/*
* 非递归方法遍历二叉树
*
* 强记！！！
*
* 画图写程序便于记住逻辑
* 要点：使用栈记录访问路径，弹出时打印
*
* 先序遍历：使用栈记录最近访问的左右节点，初始栈内加入头节点，只要栈不为空就弹出打印，同时将左右孩子入栈（先右后左，这样弹出才可能是先左后右）
* 中序遍历：用栈记录走过但未打印的节点，根据给定的头节点一直往左走，循环里面分head为null和不为null的情况，
* 		  head不为空push，往左走（head=head.left）
*         head为空pop,打印,往右走(head=head.right)
* 后续遍历：使用两个栈，辅栈用于查找节点，主栈记录后序遍历的节点顺序，最后遍历主栈并打印
*         只要辅栈不为空循环就继续，左孩子不为空就入栈左孩子，右孩子不为空就入栈右孩子
*         主栈只加入辅栈弹出的元素，自己本身不弹出
* * */

public class Code02_UnRecursiveTraversalBT {

	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int v) {
			value = v;
		}
	}

	public static void pre(Node head) {
		System.out.print("pre-order: ");
		if (head != null) {
			Stack<Node> stack = new Stack<Node>();
			stack.add(head);
			while (!stack.isEmpty()) {
				head = stack.pop();
				System.out.print(head.value + " ");
				if (head.right != null) {
					stack.push(head.right);
				}
				if (head.left != null) {
					stack.push(head.left);
				}
			}
		}
		System.out.println();
	}

	public static void in(Node head) {
		System.out.print("in-order: ");
		if (head != null) {
			Stack<Node> stack = new Stack<Node>();
			while (!stack.isEmpty() || head != null) {
				if (head != null) {
					stack.push(head);
					head = head.left;
				} else {
					head = stack.pop();
					System.out.print(head.value + " ");
					head = head.right;
				}
			}
		}
		System.out.println();
	}

	public static void pos1(Node head) {
		System.out.print("pos-order: ");
		if (head != null) {
			Stack<Node> s1 = new Stack<Node>();
			Stack<Node> s2 = new Stack<Node>();
			s1.push(head);
			while (!s1.isEmpty()) {
				head = s1.pop();
				s2.push(head);
				if (head.left != null) {
					s1.push(head.left);
				}
				if (head.right != null) {
					s1.push(head.right);
				}
			}
			while (!s2.isEmpty()) {
				System.out.print(s2.pop().value + " ");
			}
		}
		System.out.println();
	}

	public static void pos2(Node h) {
		System.out.print("pos-order: ");
		if (h != null) {
			Stack<Node> stack = new Stack<Node>();
			stack.push(h);
			Node c = null;
			while (!stack.isEmpty()) {
				c = stack.peek();
				if (c.left != null && h != c.left && h != c.right) {//判断c左边是否处理
					stack.push(c.left);
				} else if (c.right != null && h != c.right) {//判断c右边是否处理
					stack.push(c.right);
				} else {
					System.out.print(stack.pop().value + " ");
					h = c;//hb被赋予真正的意义，记录上次被弹出的节点
				}
			}
		}
		System.out.println();
	}

	public static void main(String[] args) {
		Node head = new Node(1);
		head.left = new Node(2);
		head.right = new Node(3);
		head.left.left = new Node(4);
		head.left.right = new Node(5);
		head.right.left = new Node(6);
		head.right.right = new Node(7);

		pre(head);
		System.out.println("========");
		in(head);
		System.out.println("========");
		pos1(head);
		System.out.println("========");
		pos2(head);
		System.out.println("========");
	}

}
