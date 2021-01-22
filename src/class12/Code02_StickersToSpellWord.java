package class12;

import java.util.Arrays;
import java.util.HashMap;

/*
* 给定一个字符串str，给定一个字符串类型的数组arr。
* arr里的每一个字符串，代表一张贴纸，你可以把单个字符剪开使用，目的是拼出str来。
* 返回需要至少多少张贴纸可以完成这个任务。例子: str= "babac", arr = {"ba" ,"c","abcd"}
* 至少需要两张贴纸"ba"和"abcd"，因为使用这两张贴纸，把每一个字符单独剪开，含有2个a、2个b、1个c。是可以拼出str的。所以返回2。
*
* 思路：从选第一张贴纸开始（有多少种贴纸就有多少种选择），然后把自己拿到的有用的贴纸放在一遍，然后再去拼凑剩下的部分...如此往复
*
* 可变参数能少则少
* */

public class Code02_StickersToSpellWord {

	public static int minStickers1(String[] stickers, String target) {
		
		int n = stickers.length;
		
		int[][] map = new int[n][26];// stickers -> [26] [26] [26]
		for (int i = 0; i < n; i++) {
			char[] str = stickers[i].toCharArray();
			for (char c : str) {
				map[i][c - 'a']++;
			}
		}
		HashMap<String, Integer> dp = new HashMap<>();
		dp.put("", 0);
		return process1(dp, map, target);
	}

	// dp 傻缓存，如果t已经算过了，直接返回dp中的值
	// t 剩余的目标
	// 0..N每一个字符串所含字符的词频统计
	// 返回值是-1，map 中的贴纸  怎么都无法rest
	public static int process1(
			HashMap<String, Integer> dp,
			int[][] map, 
			String rest) {
		if (dp.containsKey(rest)) {
			return dp.get(rest);
		}
		// 以下就是正式的递归调用过程
		int ans = Integer.MAX_VALUE; // ans -> 搞定rest，使用的最少的贴纸数量 
		int n = map.length; // N种贴纸
		int[] tmap = new int[26]; // tmap 去替代 rest
		char[] target = rest.toCharArray();
		for (char c : target) {
			tmap[c - 'a']++;
		}
		for (int i = 0; i < n; i++) {
			// 枚举当前第一张贴纸是谁？
			if (map[i][target[0] - 'a'] == 0) {
				continue;
			}
			StringBuilder sb = new StringBuilder();
			// i 贴纸， j 枚举a~z字符
			for (int j = 0; j < 26; j++) { // 
				if (tmap[j] > 0) { // j这个字符是target需要的
					for (int k = 0; k < Math.max(0, tmap[j] - map[i][j]); k++) {
						sb.append((char) ('a' + j));
					}
				}
			}
			// sb ->  i
			String s = sb.toString();
			int tmp = process1(dp, map, s);
			if (tmp != -1) {
				ans = Math.min(ans, 1 + tmp);
			}
		}
		// ans 系统最大  rest
		dp.put(rest, ans == Integer.MAX_VALUE ? -1 : ans);
		return dp.get(rest);
	}

	public static int minStickers2(String[] stickers, String target) {
		int n = stickers.length;
		int[][] map = new int[n][26];
		for (int i = 0; i < n; i++) {
			char[] str = stickers[i].toCharArray();
			for (char c : str) {
				map[i][c - 'a']++;
			}
		}
		char[] str = target.toCharArray();
		int[] tmap = new int[26];
		for (char c : str) {
			tmap[c - 'a']++;
		}
		HashMap<String, Integer> dp = new HashMap<>();
		int ans = process2(map, 0, tmap, dp);
		return ans;
	}

	public static int process2(int[][] map, int i, int[] tmap, HashMap<String, Integer> dp) {
		StringBuilder keyBuilder = new StringBuilder();
		keyBuilder.append(i + "_");
		for (int asc = 0; asc < 26; asc++) {
			if (tmap[asc] != 0) {
				keyBuilder.append((char) (asc + 'a') + "_" + tmap[asc] + "_");
			}
		}
		String key = keyBuilder.toString();
		if (dp.containsKey(key)) {
			return dp.get(key);
		}
		boolean finish = true;
		for (int asc = 0; asc < 26; asc++) {
			if (tmap[asc] != 0) {
				finish = false;
				break;
			}
		}
		if (finish) {
			dp.put(key, 0);
			return 0;
		}
		if (i == map.length) {
			dp.put(key, -1);
			return -1;
		}
		int maxZhang = 0;
		for (int asc = 0; asc < 26; asc++) {
			if (map[i][asc] != 0 && tmap[asc] != 0) {
				maxZhang = Math.max(maxZhang, (tmap[asc] / map[i][asc]) + (tmap[asc] % map[i][asc] == 0 ? 0 : 1));
			}
		}
		int[] backup = Arrays.copyOf(tmap, tmap.length);
		int min = Integer.MAX_VALUE;
		int next = process2(map, i + 1, tmap, dp);
		tmap = Arrays.copyOf(backup, backup.length);
		if (next != -1) {
			min = next;
		}
		for (int zhang = 1; zhang <= maxZhang; zhang++) {
			for (int asc = 0; asc < 26; asc++) {
				tmap[asc] = Math.max(0, tmap[asc] - (map[i][asc] * zhang));
			}
			next = process2(map, i + 1, tmap, dp);
			tmap = Arrays.copyOf(backup, backup.length);
			if (next != -1) {
				min = Math.min(min, zhang + next);
			}
		}
		int ans = min == Integer.MAX_VALUE ? -1 : min;
		dp.put(key, ans);
		return ans;
	}
	
	public static void main(String[] args) {
		String[] arr = {"aaaa","bbaa","ccddd"};
		String str = "abcccccdddddbbbaaaaa";
		System.out.println(minStickers1(arr, str));
		System.out.println(minStickers2(arr, str));
		
		
	}

}
