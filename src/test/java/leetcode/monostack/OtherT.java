package leetcode.monostack;

import java.util.ArrayDeque;

import org.junit.jupiter.api.Test;

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

    /*
     * 3542. 将所有元素变为 0 的最少操作次数 [Medium]
     * 
     * 维护一个单调递增栈，栈底到栈顶依次为严格递增的元素
     * 1) 当遇到的元素大于栈顶元素时，入栈
     * 2) 当遇到的元素小于栈顶元素时，这表明对于栈顶，其左右一定存在更小的元素，则栈顶要操作1次：出栈,ans++
     * 3) 当遇到的元素等于栈顶元素时，该元素可以和栈顶一并操作，直接跳过
     * 
     * 栈可以直接在原nums上模拟
     */
    public int minOperations(int[] nums) {
        int heapIdx = -1;
        int ans = 0;
        for (int i = 0; i < nums.length;) {
            if (heapIdx < 0 || nums[i] > nums[heapIdx]) {
                nums[++heapIdx] = nums[i++];
            } else if (nums[i] < nums[heapIdx]) {
                heapIdx--;
                ans++;
            } else {
                i++;
            }
        }
        return ans + heapIdx + (nums[0] > 0 ? 1 : 0);
    }

    @Test
    void test() {
        minOperations(new int[] { 1, 2, 1, 2, 1, 2 });
    }
}
