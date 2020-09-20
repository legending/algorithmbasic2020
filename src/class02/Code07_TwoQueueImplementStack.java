package class02;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/*
* 要点
* （1）准备两个队列queue,help（辅助队列），当push数据时直接往queue里push
* （2）当poll或peek时，先往help里倒数据，然后在queue中poll(如果是peek则将peek到的数据继续倒入help)，最后queue与help互换
* */

public class Code07_TwoQueueImplementStack {

	public static class TwoQueueStack<T> {
		public Queue<T> queue;
		public Queue<T> help;

		public TwoQueueStack() {
			queue = new LinkedList<>();
			help = new LinkedList<>();
		}

		public void push(T value) {
			queue.offer(value);
		}

		public T poll() {
			while (queue.size() > 1) {
				help.offer(queue.poll());
			}
			T ans = queue.poll();
			Queue<T> tmp = queue;
			queue = help;
			help = tmp;
			return ans;
		}

		public T peek() {
			while (queue.size() > 1) {
				help.offer(queue.poll());
			}
			T ans = queue.poll();
			help.offer(ans);
			Queue<T> tmp = queue;
			queue = help;
			help = tmp;
			return ans;
		}

		public boolean isEmpty() {
			return queue.isEmpty();
		}

	}

	public static void main(String[] args) {
		System.out.println("test begin");
		TwoQueueStack<Integer> myStack = new TwoQueueStack<>();
		Stack<Integer> test = new Stack<>();
		int testTime = 1000000;
		int max = 1000000;
		for (int i = 0; i < testTime; i++) {
			if (myStack.isEmpty()) {
				if (!test.isEmpty()) {
					System.out.println("Oops");
				}
				int num = (int) (Math.random() * max);
				myStack.push(num);
				test.push(num);
			} else {
				if (Math.random() < 0.25) {
					int num = (int) (Math.random() * max);
					myStack.push(num);
					test.push(num);
				} else if (Math.random() < 0.5) {
					if (!myStack.peek().equals(test.peek())) {
						System.out.println("Oops");
					}
				} else if (Math.random() < 0.75) {
					if (!myStack.poll().equals(test.pop())) {
						System.out.println("Oops");
					}
				} else {
					if (myStack.isEmpty() != test.isEmpty()) {
						System.out.println("Oops");
					}
				}
			}
		}

		System.out.println("test finish!");

	}

}
