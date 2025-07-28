package leetcode.monostack;

import java.util.ArrayDeque;

/*
 * 单调栈的非常规题
 */
public class OtherT {
    /*
     * 962. 最大宽度坡 [Medium] [Link: 1124. 表现良好的最长时间段]
     * 
     * 从左到右遍历nums，用单调栈stk记录nums中单调递减的元素idx，只有它们能够作为坡的左端点
     * 接下来从右到左枚举nums作为坡的右端点
     * while(nums[i] >= nums[stk.peek()]) ans = Math.max(ans, i-stk.peek())
     */
    public int maxWidthRamp(int[] nums) {
        ArrayDeque<Integer> stk = new ArrayDeque<>();
        stk.push(0);
        int n = nums.length;
        for (int i = 1; i < n; i++) {
            if (nums[i] < nums[stk.peek()])
                stk.push(i);
        }

        int ans = 0;
        for (int i = n - 1; i >= 0; i--) {
            while (!stk.isEmpty() && nums[i] >= nums[stk.peek()])
                ans = Math.max(ans, i - stk.pop());
        }

        return ans;
    }
}
