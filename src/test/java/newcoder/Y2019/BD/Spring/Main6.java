package newcoder.Y2019.BD.Spring;

import java.util.Scanner;

/**
 * 找零问题（dp）
 * dp[1] = 1
 * dp[4] = 1
 * dp[16] = 1
 * dp[64] = 1
 * dp[x] = min{dp[x-1]+1, dp[x-4]+1, dp[x-16]+1, dp[x-64]+1}
 * dp[0] = 0
 * dp[负数] = MAX
 */
public class Main6 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        int count = 1024 - N;
        int[] dp = new int[count+1];
        int a,b,c,d;
        for (int i = 1; i <= count; i++) {
            a = dp[i-1] + 1;
            b = i-4 < 0 ? Integer.MAX_VALUE : dp[i-4]+1;
            c = i-16 < 0 ? Integer.MAX_VALUE : dp[i-16]+1;
            d = i-64 < 0 ? Integer.MAX_VALUE : dp[i-64]+1;
            dp[i] = Math.min(a, Math.min(b, Math.min(c, d)));
        }
        System.out.println(dp[count]);
    }
}
