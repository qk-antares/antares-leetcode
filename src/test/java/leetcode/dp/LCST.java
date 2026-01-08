package leetcode.dp;

import java.util.Arrays;

/**
 * 最长公共子序列
 */
public class LCST {
    /**
     * 1458. 两个子序列的最大点积 [Hard]
     * 
     * 选的数字个数不限
     * dp[i][j]表示nums1[0...i]与nums2[0...j]的答案
     * dp[0][0]=nums1[0]*nums2[0]
     * 选i和j：dp[i][j] = Math.max(0, dp[i-1][j-1]) + nums[i]*nums[j]
     * 不选i：dp[i][j] = dp[i-1][j]
     * 不选j：dp[i][j] = dp[i][j-1]
     * 
     * 依赖左、上、左上三个元素的状态，可将dp压缩成一维，再用两个临时变量保存左上的状态
     */
    public int maxDotProduct0(int[] nums1, int[] nums2) {
        int m = nums1.length, n = nums2.length;
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 0; i <= m; i++) {
            dp[i][0] = Integer.MIN_VALUE;
        }

        for (int i = 0; i <= n; i++) {
            dp[0][i] = Integer.MIN_VALUE;
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                dp[i + 1][j + 1] = Math.max(
                        Math.max(0, dp[i][j]) + nums1[i] * nums2[j],
                        Math.max(dp[i][j + 1], dp[i + 1][j]));
            }
        }

        return dp[m][n];
    }

    public int maxDotProduct(int[] nums1, int[] nums2) {
        int m = nums1.length, n = nums2.length;
        int[] dp = new int[n + 1];
        Arrays.fill(dp, Integer.MIN_VALUE);

        for (int i = 0; i < m; i++) {
            int pre = dp[0];
            for (int j = 0; j < n; j++) {
                int tmp = dp[j + 1];
                dp[j + 1] = Math.max(
                        Math.max(0, pre) + nums1[i] * nums2[j],
                        Math.max(dp[j + 1], dp[j]));
                pre = tmp;
            }
        }

        return dp[n];
    }
}
