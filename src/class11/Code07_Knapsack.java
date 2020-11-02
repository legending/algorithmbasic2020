package class11;

/*
* 从左往右的尝试模型2
* 给定两个长度都为N的数组weights和values,
* weights[i]和values[i]分别代表i号物品的重量和价值。给定一个正数bag，表示一个载重bag的袋子,你装的物品不能超过这个重量。
* 返回你能装下最多的价值是多少?
* 默认每个货物只有一件
*
* 思路；从左往右，某个物品要还是不要
* maxValue是经典的递归方法 -> process(int[] w, int[] v, int index, int rest)
* 要清楚的直到递归函数的含义: process(int[] w, int[] v, int index, int rest) -> 对index做出决策后(要还是不要)，后续一坨带来的最大价值
*
* 通过因为base-case是if (index == w.length) return 0;
* 所以一定是前面的结果依赖于后面的结果，所以在使用动态规划时，一定要先算后面的值（index从大到小）
* */

public class Code07_Knapsack {

	public static int getMaxValue(int[] w, int[] v, int bag) {
		return process(w, v, 0, 0, bag);
	}

	// 不变 ： w[]  v[]  bag
	// index... 最大价值
	// 0..index-1上做了货物的选择，使得你已经达到的重量是多少alreadyW
	// 如果返回-1，认为没有方案
	// 如果不返回-1，认为返回的值是真实价值
	public static int process(int[] w, int[] v, int index, int alreadyW, int bag) {
		if (alreadyW > bag) {
			return -1;
		}
		// 重量没超，但没货了，index之后返回的价值为0
		if (index == w.length) {
			return 0;
		}
		int p1 = process(w, v, index + 1, alreadyW, bag);//没有要当前货物的时候后续选择带来的价值
		int p2next = process(w, v, index + 1, alreadyW + w[index], bag);//选择index货物后，从(index+1)开始的后续选择带来的价值
		int p2 = -1;
		if (p2next != -1) {//要保证后续的选择是对的，才能真正确定选当前货物的价值p2，p1之所以不用监测，因为没有选择，而一开始保证了alreadyW > bag
			p2 = v[index] + p2next;
		}
		return Math.max(p1, p2);

	}

	public static int maxValue(int[] w, int[] v, int bag) {
		return process(w, v, 0, bag);
	}

	// 只剩下rest的空间了，
	// index...货物自由选择，但是剩余空间不要小于0
	// 返回 index...货物能够获得的最大价值
	public static int process(int[] w, int[] v, int index, int rest) {
		if (rest < 0) { // base case 1
			return -1;
		}
		// rest >=0
		if (index == w.length) { // base case 2，货物已取完，认为方案依旧有效，但只是返回价值为0
			return 0;
		}
		// 有货也有空间
		int p1 = process(w, v, index + 1, rest);//不选当前货物返回的后续价值
		int p2 = -1;
		int p2Next = process(w, v, index + 1, rest - w[index]);//选择当前货物返回的后续价值（从index+1 ... length-1）
		if(p2Next != -1) {//判断当前方案是否有效
			p2 = v[index] + p2Next;
		}
		return Math.max(p1, p2);
	}

	public static int dpWay(int[] w, int[] v, int bag) {
		int N = w.length;
		int[][] dp = new int[N + 1][bag + 1];
		//dp[N][0 1 ... bag] = 0 -> 因为当index=N说明已经物品已经用完了，此时直接返回0
		for (int index = N - 1; index >= 0; index--) { //必须是逆序的，因为上层递归是依赖于下层递归的
			for (int rest = 0; rest <= bag; rest++) { //rest从1开始，容量默认为正整数
				dp[index][rest] = dp[index + 1][rest];//如果不要当前物品
				if (rest >= w[index]) {//若剩余容量大于当前物品
					dp[index][rest] = Math.max(dp[index][rest], v[index] + dp[index + 1][rest - w[index]]);
				}
			}
		}
		return dp[0][bag];
	}

	public static void main(String[] args) {
		int[] weights = { 3, 2, 4, 7 };
		int[] values = { 5, 6, 3, 19 };
		int bag = 11;
		System.out.println(maxValue(weights, values, bag));
		System.out.println(dpWay(weights, values, bag));
	}

}
