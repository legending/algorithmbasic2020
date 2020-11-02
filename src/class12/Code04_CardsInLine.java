package class12;

/*
 * 范围上尝试的模型
 * 给定一个整型数组arr，代表数值不同的纸牌排成一条线,玩家A和玩家B依次拿走每张纸牌，
 * 规定玩家A先拿，玩家B后拿，
 * 但是每个玩家每次只能拿走最左或最右的纸牌，
 * 玩家A和玩家B都绝顶聪明，直到最后牌拿完，谁手里的牌的数字相加最大谁获胜，请返回最后获胜者的分数。
 *
 * 动态规划
 * 1. L R范围变小，其中L<=R
 * 2. 两个函数：f,s ，所以dp是两张正方形表，大约一半的区域无效（不包含对角线上的值）
 *
 * */

public class Code04_CardsInLine {

	public static int win1(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		return Math.max(f(arr, 0, arr.length - 1), s(arr, 0, arr.length - 1));
	}

	public static int f(int[] arr, int L, int R) {
		if (L == R) {
			return arr[L];
		}
		return Math.max(arr[L] + s(arr, L + 1, R), arr[R] + s(arr, L, R - 1));
	}

	public static int s(int[] arr, int L, int R) {
		if (L == R) {
			return 0;
		}
		return Math.min(f(arr, L + 1, R), f(arr, L, R - 1));
	}

	public static int windp(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int N = arr.length;
		int[][] f = new int[N][N];
		int[][] s = new int[N][N];
		for (int i = 0; i < N; i++) {
			f[i][i] = arr[i];
		}
		// 0,0 右下方移动
		// 0,1
		// 0,2
		// 0,N-1
		for (int col = 1; col < N; col++) {
			// 对角线出发位置(0,col)
			int L = 0;
			int R = col;
			while (L < N && R < N) {
				f[L][R] = Math.max(arr[L] + s[L + 1][R], arr[R] + s[L][R - 1]);
				s[L][R] = Math.min(f[L + 1][R], f[L][R - 1]);
				L++;
				R++;
			}
		}
		return Math.max(f[0][N - 1], s[0][N - 1]);
	}

	public static void main(String[] args) {
		int[] arr = { 5, 7, 4, 5, 8, 1, 6, 0, 3, 4, 6, 1, 7 };
		System.out.println(win1(arr));
		System.out.println(windp(arr));

	}

}
