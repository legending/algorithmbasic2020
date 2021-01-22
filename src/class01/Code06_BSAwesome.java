package class01;

/*
* 局部最小（找到一个就停止）：一个相邻不等的无序数组中，找出 value<左边 && value<右边 元素，其中arr[0]<arr[1]或arr[arr.length-1]< arr[arr.length-2]也算局部最小
* 因为相邻不等，且确定无序，则顺序连接所有节点必有拐点
* ---> 不一定有序才能二分
* */

public class Code06_BSAwesome {

	public static int getLessIndex(int[] arr) {
		if (arr == null || arr.length == 0) {
			return -1; // no exist
		}
		if (arr.length == 1 || arr[0] < arr[1]) {
			return 0;
		}
		if (arr[arr.length - 1] < arr[arr.length - 2]) {
			return arr.length - 1;
		}
		int left = 1;
		int right = arr.length - 2;
		int mid = 0;
		while (left < right) {
			mid = (left + right) / 2;
			if (arr[mid] > arr[mid - 1]) {//往较小的值的方向前进，缩小范围
				right = mid - 1;
			} else if (arr[mid] > arr[mid + 1]) {//往较小的值的方向前进，缩小范围
				left = mid + 1;
			} else { //根据这一步的条件来确定前面两个的条件
				return mid;
			}
		}
		return left;
	}

}
