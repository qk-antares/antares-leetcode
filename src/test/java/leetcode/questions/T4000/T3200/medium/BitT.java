package leetcode.questions.T4000.T3200.medium;

public class BitT {
    /*
     * 3191. 使二进制数组全部等于 1 的最少操作次数 I
     * 10反转用^=1即可
     * 从前往后遍历
     */
    public int minOperations(int[] nums) {
        int ans = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                ans++;
                int j;
                for (j = i; j < nums.length && j < i + 3; j++) {
                    nums[j] ^= 1;
                }
                if (j == nums.length) {
                    return -1;
                }
            }
        }
        return ans;
    }

    /*
     * 3192. 使二进制数组全部等于 1 的最少操作次数 II
     * 10反转用^=1即可
     * 
     * 动态规划
     * dp[i] 表示nums[0..i]的最少操作次数
     * dp[0] = 1 if nums[0] == 0 else 0
     * 
     * if dp[i-1]%2==1 && nums[i] == 0 || dp[i-1]%2==0 && nums[i] == 1:
     * dp[i] = dp[i-1]
     * else
     * dp[i] = dp[i-1]+1
     */
    public int minOperations0(int[] nums) {
        int ans = 0;
        if (nums[0] == 0) {
            ans = 1;
        }

        for (int i = 1; i < nums.length; i++) {
            if (ans % 2 == 1 && nums[i] == 1 || ans % 2 == 0 && nums[i] == 0) {
                ans++;
            }
        }
        return ans;
    }

    /*
     * 下面的代码是简写版
     */
    public int minOperations1(int[] nums) {
        int ans = 0;
        for (int i = 0; i < nums.length; i++) {
            if(nums[i] == ans%2){
                ans++;
            }
        }
        return ans;
    }
}
