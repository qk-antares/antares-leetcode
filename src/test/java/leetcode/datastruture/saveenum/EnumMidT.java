package leetcode.datastruture.saveenum;

public class EnumMidT {
    /*
     * 2909. 元素和最小的山形三元组 II [Medium]
     * 
     * 保存每个元素左边和右边的最小元素（不包含当前元素）
     * 
     * 一个小优化，不用提前创建lMin，在后面枚举寻找ans时更新lMin
     */
    public int minimumSu0(int[] nums) {
        int n = nums.length;
        int[] lMin = new int[n];
        int[] rMin = new int[n];

        lMin[0] = Integer.MAX_VALUE;
        rMin[n - 1] = Integer.MAX_VALUE;

        for (int i = 1; i < n; i++) {
            lMin[i] = Math.min(lMin[i - 1], nums[i - 1]);
            rMin[n - 1 - i] = Math.min(rMin[n - i], nums[n - i]);
        }

        int ans = Integer.MAX_VALUE;
        for (int i = 1; i < n - 1; i++) {
            if (lMin[i] < nums[i] && rMin[i] < nums[i])
                ans = Math.min(ans, lMin[i] + nums[i] + rMin[i]);
        }

        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    public int minimumSum(int[] nums) {
        int n = nums.length;
        int[] rMin = new int[n];

        rMin[n - 1] = Integer.MAX_VALUE;

        for (int i = 1; i < n; i++) {
            rMin[n - 1 - i] = Math.min(rMin[n - i], nums[n - i]);
        }

        int ans = Integer.MAX_VALUE;
        int lMin = nums[0];
        for (int i = 1; i < n - 1; i++) {
            if (lMin < nums[i] && rMin[i] < nums[i])
                ans = Math.min(ans, lMin + nums[i] + rMin[i]);
            lMin = Math.min(lMin, nums[i]);
        }

        return ans == Integer.MAX_VALUE ? -1 : ans;
    }
}
