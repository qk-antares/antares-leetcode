package leetcode.questions.T1000.T100;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

import org.junit.jupiter.api.Test;

public class StackT {
    /**
     * 84. 柱状图中最大的矩形 [Hard]    [Link: 739. 每日温度]
     * 
     * 使用单调栈，对heights进行遍历，分别保存：
     * leftSmaller: heights左侧第一个<heights[i]的index
     * rightSmaller: heights右侧第一个<heights[i]的index
     * 随后对heights进行遍历，每次枚举到的heights[i]作为高，则：
     * ans=Math.max(ans, heights[i] * (rightSmaller[i]-leftSmaller[i]-2))
     * 
     * 上述方法还可以进一步优化：
     * 考虑第一个for循环，它是为了确定rightSmaller的。
     * 当结束里面的while循环时，要么stk为空了，要么height[i] >= height[stk.peek()]
     * 那么此时对于i来说，我们也相当于找到了它左侧的第一个更小元素（包含相等）stk.peek()
     * 我们无需对相等的情况做额外判断。
     */
    public int largestRectangleArea(int[] heights) {
        int n = heights.length;
        int[] leftSmaller = new int[n];
        Arrays.fill(leftSmaller, -1);
        int[] rightSmaller = new int[n];
        Arrays.fill(rightSmaller, n);

        Deque<Integer> stk = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            while (!stk.isEmpty() && heights[i] < heights[stk.peek()]) {
                rightSmaller[stk.pop()] = i;
            }
            stk.push(i);
        }

        stk.clear();
        for (int i = n - 1; i >= 0; i--) {
            while (!stk.isEmpty() && heights[i] < heights[stk.peek()]) {
                leftSmaller[stk.pop()] = i;
            }
            stk.push(i);
        }

        int ans = 0;
        for (int i = 0; i < n; i++) {
            ans = Math.max(ans, heights[i] * (rightSmaller[i] - leftSmaller[i] - 1));
        }
        return ans;
    }

    @Test
    void test() {
        largestRectangleArea(new int[] { 2, 1, 5, 6, 2, 3 });
    }
}
