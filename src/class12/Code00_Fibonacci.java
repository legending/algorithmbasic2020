package class12;

/*
* Fibonacci（斐波拉契数列）：F(n)=F(n-1)+F(n-2),n>2
* 暴力递归为什么暴力？是因为有大量的重复过程
* 所以很自然的想到把重复计算的结果缓存下来，这就是动态规划，也叫记忆化搜索
*
* 暴力递归转动态规划的过程
* 1. 把立马可以判断返回的判断逻辑，cache容器声明放在dp主方法里，最后在返回时调用cache递归方法
* 2. cache递归方法里放base case跟缓存数据的过程：分存dp里存在和不存在分别讨论
* */

public class Code00_Fibonacci {

    public static void main(String[] args) {
        System.out.println(fibonacci(10));
        System.out.println(fibonacciDp(10));
    }

    public static int fibonacci(int n) {
        if (n == 1 || n == 2) return 1;
        return fibonacci(n-1) + fibonacci(n-2);
    }

    public static int fibonacciDp(int n) {
        if (n < 1) return 0;
        int [] dp = new int[n+1];
        return fibonacciCache(n, dp);
    }
    public static int fibonacciCache (int n, int [] dp) {
        if (n == 1 || n == 2) {
            dp[n] = 1;
            return dp[n];
        }
        if (dp[n] != 0 ) return dp[n];
        return fibonacciCache(n-1, dp) + fibonacciCache(n-2, dp);
    }

}
