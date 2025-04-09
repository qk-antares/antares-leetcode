package leetcode.questions.T1000.T100;

import java.util.Arrays;

public class GreedyT {
    /*
     * 55. 跳跃游戏
     * 
     * 只用遍历一遍nums，记录可以跳跃的最远位置即可，像这种在用一个vis数组的，效率很低
     */
    public boolean canJump0(int[] nums) {
        int n = nums.length;
        boolean[] vis = new boolean[n];
        vis[0] = true;
        for (int i = 0; i < n; i++) {
            if (!vis[i]) {
                return false;
            }
            if (i + nums[i] >= n - 1)
                return true;
            for (int j = i + 1; j <= i + nums[i]; j++) {
                vis[j] = true;
            }
        }
        return false;
    }

    public boolean canJump(int[] nums) {
        int n = nums.length;
        int maxDis = 0;
        for (int i = 0; i < n; i++) {
            if (i > maxDis)
                return false;
            maxDis = Math.max(maxDis, i + nums[i]);
            if (maxDis >= n - 1)
                return true;
        }
        return false;
    }

    /*
     * 45. 跳跃游戏 II
     * 
     * 方法一是使用动态规划：
     * dp[i]表示到达i处的最少跳跃次数
     * dp[i] = 1 + min(dp[j])其中j<i&&j+num[j]>=i
     * 
     * 上面方法的时间复杂度是O(n^2)
     * 实际上可以通过一次遍历来寻找答案
     * 使用maxCur来记录当前step到达的最远距离（maxCur以内全部可以通过step到达）
     * maxNext记录下一个step能到达的最远距离
     * 当遍历到的i==maxCur时，step++，maxCur=maxNext（注意循环的边界是n-1）
     */
    public int jump0(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;

        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (j + nums[j] >= i) {
                    dp[i] = Math.min(dp[i], dp[j] + 1);
                }
            }
        }

        return dp[n - 1];
    }

    public int jump(int[] nums) {
        int n = nums.length;
        int ans = 0;
        int maxCur = 0;
        int maxNext = 0;
        for (int i = 0; i < n - 1; i++) {
            maxNext = Math.max(maxNext, i + nums[i]);
            if (i == maxCur) {
                maxCur = maxNext;
                ans++;
            }
        }

        return ans;
    }
}
