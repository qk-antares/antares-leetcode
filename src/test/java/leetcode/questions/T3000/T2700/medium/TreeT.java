package leetcode.questions.T3000.T2700.medium;

import org.junit.jupiter.api.Test;

public class TreeT {
    /**
     * 2673. 使二叉树所有路径值相等的最小代价
     * 0
     * 1   2
     * 3 4 5 6
     * 自底向上+贪心
     * 对于满二叉树，n=2^h-1，叶子节点从[n/2, n-1]
     * 对于节点i，其父节点为(i-1)/2
     */
    public int minIncrements(int n, int[] cost) {
        int ans = 0;
        for (int i = n-1; i > 0; i-=2) {
            ans += Math.abs(cost[i]-cost[i-1]);
            cost[(i-1)/2] += Math.max(cost[i], cost[i-1]);
        }
        return ans;
    }

    @Test
    void test(){
        minIncrements(7, new int[]{1,5,2,2,3,3,1});
    }

}
