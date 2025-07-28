package leetcode.linklisttree.backtrack;

/*
 * 子集型回溯
 */
public class SubsetT {
    /*
     * 2044. 统计按位或能得到最大值的子集数目 [Medium]
     * 
     * 假设dp[i]表示nums的前i位，按位或最大值的不同非空子集数[val, cnt]
     * dp[i+1].val=dp[i].val || nums[i]
     * dp[i+1].val != dp[i].val: 这证明nums[i]是必选的，dp[i+1].cnt=dp[i].cnt
     * == : nums[i]可选可不选，dp[i+1].cnt=dp[i].cnt*2
     * 
     * 以上是错误的思路
     * 
     * 最大值肯定是nums中所有元素或的结果
     * 可以用dfs枚举所有子集，dfs结束是枚举长度等于nums.length
     * 从另一个角度来看，只要cur==max也可以结束dfs，
     * 因为此时后面的元素无论如何选或不选，结果都不会超过max
     * 即ans += (1 << (nums.length - idx))
     */
    int maxOrCnt = 0;

    public int countMaxOrSubsets(int[] nums) {
        int max = 0;
        for(int num : nums) max |= num;

        dfs(0, 0, nums, max);
        return maxOrCnt;
    }

    void dfs0(int idx, int cur, int[] nums, int max) {
        if(idx == nums.length) {
            if(cur == max) 
                maxOrCnt++;
            return;
        }

        dfs(idx+1, cur|nums[idx], nums, max);
        dfs(idx+1, cur, nums, max);
    }

    void dfs(int idx, int cur, int[] nums, int max) {
        if (cur == max) {
            maxOrCnt += (1 << (nums.length - idx));
            return;
        }
        if (idx == nums.length)
            return;

        dfs(idx + 1, cur | nums[idx], nums, max);
        dfs(idx + 1, cur, nums, max);
    }
}
