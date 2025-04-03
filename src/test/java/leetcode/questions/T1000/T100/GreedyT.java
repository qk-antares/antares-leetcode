package leetcode.questions.T1000.T100;

public class GreedyT {
    /*
     * 55. 跳跃游戏
     * 
     * 只用遍历一遍nums，记录可以跳跃的最远位置即可，想这种在用一个vis数组的，效率很低
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
}
