package class02;

/*
* 数组实现栈很简单，只需要记录一个index即可
* 但是数组实现队列则没那么简单
* RingArray中增加了size属性来判断队列是满还是空，从而避免了通过pushi与polli的追及问题来判断队列的空与满
* */

public class Code04_RingArray {

	public static class MyQueue {
		private int[] arr;
		private int pushi;
		private int polli;
		private int size;
		private final int limit;

		public MyQueue(int limit) {
			arr = new int[limit];
			pushi = 0;
			polli = 0;
			size = 0;
			this.limit = limit;
		}

		public void push(int value) {
			if (size == limit) {
				throw new RuntimeException("栈满了，不能再加了");
			}
			size++;
			arr[pushi] = value;
			pushi = nextIndex(pushi);
		}

		public int pop() {
			if (size == 0) {
				throw new RuntimeException("栈空了，不能再拿了");
			}
			size--;
			int ans = arr[polli];
			polli = nextIndex(polli);
			return ans;
		}

		public boolean isEmpty() {
			return size == 0;
		}

		// 如果现在的下标是i，返回下一个位置-->形成一个环的关键逻辑
		private int nextIndex(int i) {
			return i < limit - 1 ? i + 1 : 0;
		}

	}

}
