package class04;

import java.util.Arrays;
import java.util.PriorityQueue;

/*
* 一个数组任意一个数相比排序后的结果，它的位置相差不超过k，用堆排序进行排序
* */

public class Code05_SortArrayDistanceLessK {

	public static void sortedArrDistanceLessK(int[] arr, int k) {
		if (k == 0) {
			return;
		}
		// 默认小根堆
		PriorityQueue<Integer> heap = new PriorityQueue<>();
		int index = 0;
		// 0...K-1，先组成一个用于循环的堆，并保存前K个值，从而把arr[0~(k-1)]的空间省出来，用来存放已经排好序的值
		for (; index <= Math.min(arr.length - 1, k - 1); index++) {
			heap.add(arr[index]);
		}
		int i = 0;
		for (; index < arr.length; i++, index++) {
			heap.add(arr[index]);//这一条语句与紧接着的语句顺序和互换，因为哪怕是K+1个元素的堆，因为位置相差不超过5，所以当前堆的最小值一定出自前面K个
			arr[i] = heap.poll();
		}
		//处理最后剩下的一组元素
		while (!heap.isEmpty()) {
			arr[i++] = heap.poll();
		}
	}

	// for test
	public static void comparator(int[] arr, int k) {
		Arrays.sort(arr);
	}

	// for test
	public static int[] randomArrayNoMoveMoreK(int maxSize, int maxValue, int K) {
		int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
		}
		// 先排个序
		Arrays.sort(arr);
		// 然后开始随意交换，但是保证每个数距离不超过K
		// swap[i] == true, 表示i位置已经参与过交换
		// swap[i] == false, 表示i位置没有参与过交换
		boolean[] isSwap = new boolean[arr.length];
		for (int i = 0; i < arr.length; i++) {
			int j = Math.min(i + (int) (Math.random() * (K + 1)), arr.length - 1);
			if (!isSwap[i] && !isSwap[j]) {
				isSwap[i] = true;
				isSwap[j] = true;
				int tmp = arr[i];
				arr[i] = arr[j];
				arr[j] = tmp;
			}
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
		System.out.println("test begin");
		int testTime = 500000;
		int maxSize = 100;
		int maxValue = 100;
		boolean succeed = true;
		for (int i = 0; i < testTime; i++) {
			int k = (int) (Math.random() * maxSize) + 1;
			int[] arr = randomArrayNoMoveMoreK(maxSize, maxValue, k);
			int[] arr1 = copyArray(arr);
			int[] arr2 = copyArray(arr);
			sortedArrDistanceLessK(arr1, k);
			comparator(arr2, k);
			if (!isEqual(arr1, arr2)) {
				succeed = false;
				System.out.println("K : " + k);
				printArray(arr);
				printArray(arr1);
				printArray(arr2);
				break;
			}
		}
		System.out.println(succeed ? "Nice!" : "Fucking fucked!");
	}

}