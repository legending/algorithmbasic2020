package class02;

/*
* 如果递归的子问题的规模是一致的，则时间复杂度为：T(n)=a*T(n/b)+O(n^d),其中a,b,d为常数,其中a为一次递归中递归被调用的次数，b为一次递归中问题被划分的块数，O(n^d)是除了递归调用之外其他操作所占用的时间
* 这样的算法的时间复杂度可以直接计算
* （1）log(b,a)>d, T(n)=O(n^log(b,a))
* （2）log(b,a)<d, T(n)=O(n^d)
* （3）log(b,a)=d，T(n)=O(n^d*log(n))
* 当前时间复杂度：T(n)=2*T(n/2)+O(n^0) --> a=2,b=2,d=0 --> (1) --> T(n)=O(n) --> 二分法查找最大值与遍历查找最大值的时间复杂度是一样的
* */

public class Code08_GetMax {

	// 求arr中的最大值
	public static int getMax(int[] arr) {
		return process(arr, 0, arr.length - 1);
	}

	// arr[L..R]范围上求最大值  L ... R   N
	public static int process(int[] arr, int L, int R) {
		if (L == R) { // arr[L..R]范围上只有一个数，直接返回，base case
			return arr[L];
		}
		int mid = L + ((R - L) >> 1); // 中点   	1
		int leftMax = process(arr, L, mid);
		int rightMax = process(arr, mid + 1, R);
		return Math.max(leftMax, rightMax);
	}

}
