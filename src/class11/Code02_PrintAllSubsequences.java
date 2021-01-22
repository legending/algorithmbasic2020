package class11;

/*
* 打印字符数组的所有子序列
* 注意子序列不是排列组合，因为子序列必须是按照元字符已有的顺序从前到后去组合
* 如：[w, a, c, d]，wa是，wc就不是
* 进一步细化，可以分解为每一个元素选或不选的子过程 -> 迭代
* 递归参数开始可能不容易那么快确定，当你感觉无法实现问题的时候就不断添加，修正
* 不一定一下子就能确定
* */

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Code02_PrintAllSubsequences {

	public static List<String> subs(String s) {
		char[] str = s.toCharArray();
		String path = "";
		List<String> ans = new ArrayList<>();
		process1(str, 0, ans, path);
		return ans;
	}

	// str固定，不变
	// index此时来到的位置, 要  or 不要
	// 如果index来到了str中的终止位置，把沿途路径所形成的答案，放入ans中
	// 之前做出的选择，就是path
	public static void process1(char[] str, int index, List<String> ans, String path) {
		if (index == str.length) {
			ans.add(path);
			return;
		}
		String no = path;
		process1(str, index + 1, ans, no);
		String yes = path + String.valueOf(str[index]);
		process1(str, index + 1, ans, yes);
	}

	public static List<String> subsNoRepeat(String s) {
		char[] str = s.toCharArray();
		String path = "";
		HashSet<String> set = new HashSet<>();
		process2(str, 0, set, path);
		List<String> ans = new ArrayList<>();
		for (String cur : set) {
			ans.add(cur);
		}
		return ans;
	}

	// str  index  set
	public static void process2(char[] str, int index,
			HashSet<String> set, String path) {
		if (index == str.length) {
			set.add(path);
			return;
		}
		String no = path;
		process2(str, index + 1, set, no);
		String yes = path + String.valueOf(str[index]);
		process2(str, index + 1, set, yes);
	}

	public static void main(String[] args) {
		String test = "aacc";
		List<String> ans1 = subs(test);
		List<String> ans2 = subsNoRepeat(test);

		for (String str : ans1) {
			System.out.println(str);
		}
		System.out.println("=================");
		for (String str : ans2) {
			System.out.println(str);
		}
		System.out.println("=================");

	}

}
