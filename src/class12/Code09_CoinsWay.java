package class12;

/*
* 硬币凑数问题
* 有一个数组里面装了硬币的面值，如[3, 6, 10, 25, 100]
* 假设硬币面值不重复，且面值为正
* 此时给定一个总数，每一种面值的硬币可以随意想拿多少拿多少，则凑齐该值的方法有多少种方法？
*
* 递归函数：当前取硬币位置为index，待完成数量为rest，返回从index位置到最后完成rest的所有组合数
* -> process1(int[] arr, int index, int rest)
*
* 转为记忆化搜索？ 据实际例子，查看是否有重复计算，如：[10, 20, 50, 100], aim=1000 => f(3, 600)
* */

public class Code09_CoinsWay {

	// arr中都是正数且无重复值，返回组成aim的方法数
	public static int ways1(int[] arr, int aim) {
		if (arr == null || arr.length == 0 || aim < 0) {
			return 0;
		}
		return process1(arr, 0, aim);
	}

	public static int process1(int[] arr, int index, int rest) {
		if(index == arr.length) {
			return rest == 0 ? 1 : 0 ;	
		}
		int ways = 0;
		for(int zhang = 0;  zhang * arr[index] <= rest ;zhang++) {
			ways += process1(arr, index + 1, rest -  (zhang * arr[index])  );
		}
		return ways;
	}

	// 记忆化搜索
	public static int ways2(int[] arr, int aim) {
		if (arr == null || arr.length == 0 || aim < 0) {
			return 0;
		}
		int[][] dp = new int[arr.length+1][aim+1];
		// 一开始所有的过程，都没有计算呢
		// dp[..][..]  = -1
		for(int i = 0 ; i < dp.length; i++) {
			for(int j = 0 ; j < dp[0].length; j++) {
				dp[i][j] = -1;
			}
		}
		return process2(arr, 0, aim , dp);
	}
	
	// 如果index和rest的参数组合，是没算过的，dp[index][rest] == -1
	// 如果index和rest的参数组合，是算过的，dp[index][rest]  > - 1
	// 单纯的做记忆搜索，不管组织形式
	public static int process2(int[] arr, 
			int index, int rest,
			int[][] dp) {
		if(dp[index][rest] != -1) {
			return dp[index][rest];
		}
		if(index == arr.length) {
			dp[index][rest] = rest == 0 ? 1 :0;
			return  dp[index][rest];	
		}
		int ways = 0;
		for(int zhang = 0;  zhang * arr[index] <= rest ;zhang++) {
			ways += process2(arr, index + 1, rest -  (zhang * arr[index]) , dp );
		}
		dp[index][rest] = ways;
		return ways;
	}

	// 动态规划
	// 由ways += process2(arr, index + 1, rest -  (zhang * arr[index]) , dp )可知，前面值是依赖于后面值的，所以先算后面值，再算前面值
	public static int ways3(int[] arr, int aim) {
		if (arr == null || arr.length == 0 || aim < 0) {
			return 0;
		}
		int N = arr.length;
		int[][] dp = new int[N + 1][aim + 1];
		dp[N][0] = 1;// dp[N][1...aim] = 0;
		for(int index = N - 1; index >= 0; index--) {
			for(int rest = 0; rest <= aim; rest++) {//虽然这里是以1为粒度的，但实际上在dp[index, rest]rest是以arr[index]为跨度的
				int ways = 0;
				for(int zhang = 0;  zhang * arr[index] <= rest ;zhang++) {
					ways += dp[index + 1] [rest -  (zhang * arr[index])];
				}
				dp[index][rest] = ways;
			}
		}
		return dp[0][aim];
	}

	// ways3只在index方向上有优化
	// 但是在观察具体计算的过程中发现在同一行（index）的不同列(rest)，组合数与下一行（index+1）有关dp[index+1][0, rest]：ways += dp[index + 1] [rest -  (zhang * arr[index])]
	// 所以可以进一步优化rest方向上的计算
	public static int ways4(int[] arr, int aim) {
		if (arr == null || arr.length == 0 || aim < 0) {
			return 0;
		}
		int N = arr.length;
		int[][] dp = new int[N + 1][aim + 1];
		dp[N][0] = 1;// dp[N][1...aim] = 0;
		for(int index = N - 1; index >= 0; index--) {
			for(int rest = 0; rest <= aim; rest++) {
				//dp[index][rest] = ?
				dp[index][rest] = dp[index+1][rest];//0张dp[index],依赖剩下的d[index+1]去解决rest，给dp[index][rest]赋初始值
				if(rest - arr[index] >= 0) {
					dp[index][rest] += dp[index][rest - arr[index]];//重点理解：实际上dp[index][x1, x2 ...],x1与x2是以arr[index]为粒度的
				}
				/*int ways = 0;
				for(int zhang = 0;  zhang * arr[index] <= rest ;zhang++) {
					ways += dp[index + 1] [rest -  (zhang * arr[index])];
				}
				dp[index][rest] = ways;*/

			}
		}
		return dp[0][aim];
	}
	

	public static void main(String[] args) {
		int[] arr = { 5, 10,50,100 };
		int sum = 1000;
		System.out.println(ways1(arr, sum));
		System.out.println(ways2(arr, sum));
		System.out.println(ways3(arr, sum));
		System.out.println(ways4(arr, sum));
	}

}
