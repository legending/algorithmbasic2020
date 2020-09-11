package class02;

/*
* java删除链表节点只需要将next指针跳过要删除的节点然后指向下一个节点就OK（不用手动释放被删除节点的内存空间，而C/C++需要手动释放）
* 如果是删除双向列表中的节点呢？
* next指向被删除节点的下一个节点，last指向null
* */

public class Code02_DeleteGivenValue {

	public static class Node {
		public int value;
		public Node next;

		public Node(int data) {
			this.value = data;
		}
	}

	public static Node removeValue(Node head, int num) {
		// head来到第一个不需要删的位置
		while (head != null) {
			if (head.value != num) {
				break;
			}
			head = head.next;
		}
		Node pre = head;
		Node cur = head;
		while (cur != null) {
			if (cur.value == num) {
				pre.next = cur.next;
			} else {
				pre = cur;
			}
			cur = cur.next;
		}
		return head;
	}

}
