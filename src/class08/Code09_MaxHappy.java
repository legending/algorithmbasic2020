package class08;

import java.util.ArrayList;
import java.util.List;

/*
* 派对的最大快乐值
* 这个公司现在要办party，你可以决定哪些员工来，哪些员工不来，规则:
* 1.如果某个员工来了，那么这个员工的所有直接下级都不能来
* 2.派对的整体快乐值是所有到场员工快乐值的累加
* 3.你的目标是让派对的整体快乐值尽量大
* 给定一棵多叉树的头节点boss，请返回派对的最大快乐值。
* 没发请柬一定不来，发了请柬
* */

public class Code09_MaxHappy {

	public static class Employee {
		public int happy;
		public List<Employee> nexts;

		public Employee(int h) {
			happy = h;
			nexts = new ArrayList<>();
		}

	}

	public static int maxHappy1(Employee boss) {
		if (boss == null) {
			return 0;
		}
		return process1(boss, false);
	}

	// 当前来到的节点叫cur，
	// up表示cur的上级是否来，
	// 该函数含义：
	// 如果up为true，表示在cur上级已经确定来，的情况下，cur整棵树能够提供最大的快乐值是多少？
	// 如果up为false，表示在cur上级已经确定不来，的情况下，cur整棵树能够提供最大的快乐值是多少？
	public static int process1(Employee cur, boolean up) {
		if (up) { // 如果cur的上级来的话，cur没得选，只能不来
			int ans = 0;
			for (Employee next : cur.nexts) {
				ans += process1(next, false);
			}
			return ans;
		} else { // 如果cur的上级不来的话，cur可以选，可以来也可以不来
			int p1 = cur.happy;
			int p2 = 0;
			for (Employee next : cur.nexts) {
				p1 += process1(next, true);
				p2 += process1(next, false);
			}
			return Math.max(p1, p2);
		}
	}

	public static int maxHappy2(Employee boss) {
		if (boss == null) {
			return 0;
		}
		Info all = process2(boss);
		return Math.max(all.yes, all.no);
	}

	public static class Info {
		public int yes;
		public int no;

		public Info(int y, int n) {
			yes = y;
			no = n;
		}
	}

	public static Info process2(Employee x) {
		if (x.nexts.isEmpty()) {
			return new Info(x.happy, 0);
		}
		int yes = x.happy;//给x发请柬
		int no = 0;//不给x发请柬
		for (Employee next : x.nexts) {
			Info nextInfo = process2(next);//
			yes += nextInfo.no;//每个直接下级不来时，以该下级为头的整棵树的最大快乐值
			no += Math.max(nextInfo.yes, nextInfo.no);//每个直接下级来或不来，以当前下级为头的整棵树的最大快乐值，因为发了请柬不一定要来
		}
		return new Info(yes, no);
	}

	// for test
	public static Employee genarateBoss(int maxLevel, int maxNexts, int maxHappy) {
		if (Math.random() < 0.02) {
			return null;
		}
		Employee boss = new Employee((int) (Math.random() * (maxHappy + 1)));
		genarateNexts(boss, 1, maxLevel, maxNexts, maxHappy);
		return boss;
	}

	// for test
	public static void genarateNexts(Employee e, int level, int maxLevel, int maxNexts, int maxHappy) {
		if (level > maxLevel) {
			return;
		}
		int nextsSize = (int) (Math.random() * (maxNexts + 1));
		for (int i = 0; i < nextsSize; i++) {
			Employee next = new Employee((int) (Math.random() * (maxHappy + 1)));
			e.nexts.add(next);
			genarateNexts(next, level + 1, maxLevel, maxNexts, maxHappy);
		}
	}

	public static void main(String[] args) {
		int maxLevel = 4;
		int maxNexts = 7;
		int maxHappy = 100;
		int testTimes = 100000;
		for (int i = 0; i < testTimes; i++) {
			Employee boss = genarateBoss(maxLevel, maxNexts, maxHappy);
			if (maxHappy1(boss) != maxHappy2(boss)) {
				System.out.println("Oops!");
			}
		}
		System.out.println("finish!");
	}

}
