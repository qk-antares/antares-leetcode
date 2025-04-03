package leetcode.questions.Medium;

import org.junit.jupiter.api.Test;

/**
 * @author Antares
 * @date 2022/9/28
 */
public class DPMedium {
    /**
     * 跳跃游戏，我的解法（构造一个数组表示能否跳到某个单元格，执行用时太长）
     */
    static class CanJump {
        public boolean canJump(int[] nums) {
            boolean[] can = new boolean[nums.length];
            can[0] = true;
            for(int i = 0;i < nums.length;i++){
                if(can[i]){
                    for(int j = 1;j <= nums[i] && i+j < nums.length;j++){
                        can[i+j] = true;
                    }
                }
            }
            return can[nums.length-1];
        }

        /**
         * 答案解法：dp[i]表示经过下标i，最大往后再走多远
         */
        public boolean canJump0(int[] nums) {
            if(nums.length == 1)
                return true;
            if(nums[0] == 0)
                return false;

            int[] dp = new int[nums.length];
            dp[0] = nums[0];
            for(int i = 1;i < nums.length;i++){
                dp[i] = Math.max(dp[i-1]-1, nums[i]);
                if(dp[i] + i + 1 >= nums.length)
                    return true;
                else if (dp[i] == 0) {
                    return false;
                }
            }
            return false;
        }

        /**
         * 状态压缩（效率最高）
         */
        public boolean canJump1(int[] nums) {
            if(nums.length == 1)
                return true;
            if(nums[0] == 0)
                return false;

            int last, now;
            last = nums[0];
            for(int i = 1;i < nums.length;i++){
                now = Math.max(last-1, nums[i]);
                if(now + i + 1 >= nums.length)
                    return true;
                else if (now == 0) {
                    return false;
                }
                last = now;
            }
            return false;
        }
    }

    /**
     * 不同路径
     */
    class UniquePaths {
        public int uniquePaths(int m, int n) {
            int[][] dp = new int[m+2][n+2];
            dp[0][1] = 1;
            for(int i = 1;i <= m;i++){
                for(int j = 1;j <= n;j++){
                    dp[i][j] = dp[i-1][j] + dp[i][j-1];
                }
            }
            return dp[m][n];
        }
    }

    /**
     * 零钱兑换(我的解法：动态规划，效率貌似不是很高)
     * dp[i] = min{dp[i-k]} + 1 (k属于coins)
     * dp[k] = 1    (k属于coins)
     */
    class CoinChange {
        public int coinChange(int[] coins, int amount) {
            int[] dp = new int[amount + 1];

            int min;
            for(int i = 1;i <= amount;i++){
                min = Integer.MAX_VALUE;
                for (int coin : coins) {
                    if(i >= coin && dp[i-coin] != -1 && dp[i-coin] < min)
                        min = dp[i-coin];
                }
                if(min != Integer.MAX_VALUE)
                    dp[i] = min + 1;
                else
                    dp[i] = -1;
            }

            return dp[amount];
        }
    }


    @Test
    public void invoke(){
//        new CanJump().canJump0(new int[]{2,0,0});
    }
}
