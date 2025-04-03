package leetcode.questions.T1000.T300;

public class DpT {
    /*
     * TODO 300. 最长递增子序列 [Medium]    [Link: 354. 俄罗斯套娃信封问题]
     * 
     * 使用dp[i]表示以nums[i]结尾的最长递增子序列，则
     * dp[i] = 1 + Math.max(dp[j])，其中j<i且nums[j]<nums[i]
     * 最后遍历一遍dp，最大值就是答案
     * dp[0]=1
     * 
     * 上述方法的时间复杂度为O(n^2)
     * 
     * 另一种方法是"新员工替代老员工"的二分法：
     * 1. 初始时，最长递增子序列为：d=[nums[0]]
     * 2. 接着开始从1遍历nums，如果nums[i]>d[d.length-1]，则将nums[i]加入d，d的长度+1
     * 3. 如果nums[i]<=d[d.length-1]，则通过二分法将nums[i]插入d中
     * 4. 遍历完nums后，d的长度即为答案
     */
    public int lengthOfLIS0(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        for (int i = 0; i < n; i++) {
            dp[i] = 1;
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i])
                    dp[i] = Math.max(dp[i], 1 + dp[j]);
            }
        }

        int ans = 1;
        for (int i = 0; i < n; i++) {
            ans = Math.max(ans, dp[i]);
        }
        return ans;
    }

    public int lengthOfLIS(int[] nums) {
        int n = nums.length;
        int[] d = new int[n];
        d[0] = nums[0];
        int len = 1;

        for (int i = 1; i < n; i++) {
            if (nums[i] > d[len - 1]) {
                d[len++] = nums[i];
            } else {
                // 找到第一个>=nums[i]的数进行替换
                int l = 0, r = len - 1;
                while (l <= r) {
                    int mid = (l + r) / 2;
                    if (d[mid] < nums[i]) {
                        l = mid + 1;
                    } else {
                        r = mid - 1;
                    }
                }
                d[l] = nums[i];
            }
        }
        return len;
    }
}
