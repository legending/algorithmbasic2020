package class03;

/*
* 归并排序
* 利用递归，将数据规模不断二分
* */

public class Code01_MergeSort {

	// 递归方法实现
	public static void mergeSort1(int[] arr) {
		if (arr == null || arr.length < 2) {
			return;
		}
		process(arr, 0, arr.length - 1);
	}

	// arr[L...R]范围上，变成有序的
	// L...R    N    T(N) = 2*T(N/2) + O(N)  -> a=2, b=2, d=1 -> log(a, b) = d -> T(n) = O(n*log(n))
	public static void process(int[] arr, int L, int R) {
		if (L == R) { // base case
			return;
		}
		int mid = L + ((R - L) >> 1);
		process(arr, L, mid);//迭代的目的在于把问题大而化小，小而化了，但迭代的时候的下标是全局下标而不是局部下标
		process(arr, mid + 1, R);
		merge(arr, L, mid, R); //每次迭代的实际操作：把左组与右组合并，真正开始计算是从最内存开始计算的，所以可以保证merge的左组与右组都是有序的
	}

	public static void merge(int[] arr, int L, int M, int R) {//merge的前提是左组右组已经有序
		int[] help = new int[R - L + 1];
		int i = 0;
		int p1 = L;
		int p2 = M + 1;
		while (p1 <= M && p2 <= R) {
			help[i++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
		}
		// 要么p1越界了，要么p2越界了
		while (p1 <= M) {
			help[i++] = arr[p1++];
		}
		while (p2 <= R) {
			help[i++] = arr[p2++];
		}
		for (i = 0; i < help.length; i++)  {
			arr[L + i] = help[i];
		}
	}

	// 非递归方法实现
	public static void mergeSort2(int[] arr) {
		if (arr == null || arr.length < 2) {
			return;
		}
		int N = arr.length;
		int mergeSize = 1;// 当前有序的，左组长度，只有从1开始才能保证左组右组是有序的
		while (mergeSize < N) { // log N
			int L = 0;
			// 0.... 
			while (L < N) {
				// L...M  左组（mergeSize）
				int M = L + mergeSize - 1;//M取的是下标
				if (M >= N) {//如果没有右组那么左组一定有序
					break;
				}
				//  L...M   M+1...R(mergeSize)
				int R = Math.min(M + mergeSize, N - 1);//为什么是(M + mergeSize)而不是(M + mergeSize - 1)？M代表的是下标
				merge(arr, L, M, R);
				L = R + 1;
			}
			if (mergeSize > N / 2) {
				break;
			}
			mergeSize <<= 1;
		}
	}

	// for test
	public static int[] generateRandomArray(int maxSize, int maxValue) {
		int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
		}
		return arr;
	}

	// for test
	public static int[] copyArray(int[] arr) {
		if (arr == null) {
			return null;
		}
		int[] res = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			res[i] = arr[i];
		}
		return res;
	}

	// for test
	public static boolean isEqual(int[] arr1, int[] arr2) {
		if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
			return false;
		}
		if (arr1 == null && arr2 == null) {
			return true;
		}
		if (arr1.length != arr2.length) {
			return false;
		}
		for (int i = 0; i < arr1.length; i++) {
			if (arr1[i] != arr2[i]) {
				return false;
			}
		}
		return true;
	}

	// for test
	public static void printArray(int[] arr) {
		if (arr == null) {
			return;
		}
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}

	// for test
	public static void main(String[] args) {
		int testTime = 500000;
		int maxSize = 100;
		int maxValue = 100;
		boolean succeed = true;
		for (int i = 0; i < testTime; i++) {
			int[] arr1 = generateRandomArray(maxSize, maxValue);
			int[] arr2 = copyArray(arr1);
			mergeSort1(arr1);
			mergeSort2(arr2);
			if (!isEqual(arr1, arr2)) {
				succeed = false;
				printArray(arr1);
				printArray(arr2);
				break;
			}
		}
		System.out.println(succeed ? "Nice!" : "Oops!");
	}

}
