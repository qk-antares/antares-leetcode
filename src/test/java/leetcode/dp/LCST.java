package leetcode.dp;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

/**
 * 最长公共子序列
 */
public class LCST {
    /**
     * 1143. 最长公共子序列 [Medium]
     * 
     * dp[i][j]表示text1[-1,i)与text2[-1,j)的最长公共子序列
     * 如果text1[i]=text2[j] dp[i+1][j+1] = 1+dp[i][j]
     * dp[i+1][j+1] = Math.max(dp[i][j], Math.max(dp[i+1][j], dp[i][j+1]))
     */
    public int longestCommonSubsequence(String text1, String text2) {
        char[] s1 = text1.toCharArray();
        char[] s2 = text2.toCharArray();
        int m = s1.length, n = s2.length;
        int[][] dp = new int[m + 1][n + 1];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (s1[i] == s2[j])
                    dp[i + 1][j + 1] = 1 + dp[i][j];
                else
                    dp[i + 1][j + 1] = Math.max(dp[i][j], Math.max(dp[i + 1][j], dp[i][j + 1]));
            }
        }

        return dp[m][n];
    }

    /**
     * 72. 编辑距离 [Medium]
     * 
     * dp[i][j]表示word1[-1,i)与word2[-1,j)之间的编辑距离
     * 当word1[i]==word2[j]: dp[i+1][j+1] = dp[i][j]
     * 否则: dp[i+1][j+1] = 1 + Math.min(dp[i][j], Math.min(dp[i+1][j], dp[i][j+1]))
     */
    public int minDistance(String word1, String word2) {
        char[] s1 = word1.toCharArray();
        char[] s2 = word2.toCharArray();
        int m = s1.length, n = s2.length;
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            dp[i][0] = i;
        }
        for (int j = 1; j <= n; j++) {
            dp[0][j] = j;
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (s1[i] == s1[j])
                    dp[i + 1][j + 1] = dp[i][j];
                else
                    dp[i + 1][j + 1] = 1 + Math.min(dp[i][j], Math.min(dp[i + 1][j], dp[i][j + 1]));
            }
        }

        return dp[m][n];
    }

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

    /**
     * 712. 两个字符串的最小ASCII删除和 [Medium]
     * 
     * dp[i][j]表示s1[0..i)与s2[0..j)相等的最小删除和
     * dp[0][0] == 0
     * dp[i+1][j+1] = dp[i][j] if s1[i]=s2[j]
     * dp[i+1][j+1] = dp[i+1][j]+s2[j] / dp[i][j+1]+s1[j] if s1[i]!=s2[j]
     * 依赖左/上/左上三个位置的状态
     * 可进行空间压缩
     */
    public int minimumDeleteSum(String s1, String s2) {
        char[] ss1 = s1.toCharArray();
        char[] ss2 = s2.toCharArray();
        int m = ss1.length, n = ss2.length;
        int[] dp = new int[n + 1];
        // 初始化
        for (int j = 0; j < n; j++) {
            dp[j + 1] = dp[j] + ss2[j];
        }
        for (int i = 0; i < m; i++) {
            // 左上
            int pre = dp[0];
            // 初始化dp[0]
            dp[0] += ss1[i];
            for (int j = 0; j < n; j++) {
                // 预存"上"，将在下一轮变成"左上"
                int tmp = dp[j + 1];
                if (ss1[i] == ss2[j])
                    dp[j + 1] = pre;
                else
                    dp[j + 1] = Math.min(dp[j] + ss2[j], tmp + ss1[i]);
                pre = tmp;
            }
        }
        return dp[n];
    }

    public int minimumDeleteSum0(String s1, String s2) {
        char[] ss1 = s1.toCharArray();
        char[] ss2 = s2.toCharArray();
        int m = ss1.length, n = ss2.length;
        int[][] dp = new int[m + 1][n + 1];
        dp[0][0] = 0;
        for (int i = 0; i < m; i++) {
            dp[i + 1][0] = dp[i][0] + ss1[i];
        }
        for (int j = 0; j < n; j++) {
            dp[0][j + 1] = dp[0][j] + ss2[j];
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (ss1[i] == ss2[j])
                    dp[i + 1][j + 1] = dp[i][j];
                else
                    dp[i + 1][j + 1] = Math.min(dp[i + 1][j] + ss2[j], dp[i][j + 1] + ss1[i]);
            }
        }
        return dp[m][n];
    }

    @Test
    public void invoke() {
        minDistance("horse", "ros");
    }
}
