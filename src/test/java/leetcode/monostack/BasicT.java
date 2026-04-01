package leetcode.monostack;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class BasicT {
    /*
     * 739. 每日温度 [Medium] [Link: 84. 柱状图中最大的矩形]
     * 
     * 单调栈
     * 创建一个栈，对temperatures进行1次遍历（存入idx）
     * 如果当前元素比栈顶小，这表示这个元素肯定不是答案，压入栈中
     * 如果当前元素比栈顶大，这代表我们知道了满足条件的temp
     * 依次弹出栈顶元素，直至不满足上面的条件。则这些弹出的元素，它们的答案就是当前这个元素
     * 最后将当前元素压入栈中
     */
    public int[] dailyTemperatures(int[] temperatures) {
        int n = temperatures.length;
        Deque<Integer> stk = new ArrayDeque<>();
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            while (!stk.isEmpty() && temperatures[i] > temperatures[stk.peek()]) {
                int idx = stk.pop();
                ans[idx] = i - idx;
            }
            stk.push(i);
        }

        return ans;
    }

    /**
     * 84. 柱状图中最大的矩形 [Hard] [Link: 739. 每日温度]
     * 
     * 使用单调栈，对heights进行遍历，分别保存：
     * l: heights左侧第一个<heights[i]的index
     * r: heights右侧第一个<heights[i]的index
     * 随后对heights进行遍历，每次枚举到的heights[i]作为高，则：
     * ans=Math.max(ans, heights[i] * (r[i]-l[i]-1))
     * 
     * 上述方法还可以进一步优化：
     * 考虑第一个for循环，它是为了确定l的。
     * 当结束里面的while循环时，要么stk为空了，要么height[i] >= height[stk.peek()]
     * 那么此时对于i来说，我们也相当于找到了它右侧的第一个更小（或相等）元素stk.peek()
     * 我们无需对相等的情况做额外判断
     */
    public int largestRectangleArea(int[] heights) {
        int n = heights.length;
        int[] l = new int[n];
        Arrays.fill(l, -1);
        int[] r = new int[n];
        Arrays.fill(r, n);
        ArrayDeque<Integer> stk = new ArrayDeque<>();
        for (int i = n - 1; i >= 0; i--) {
            while (!stk.isEmpty() && heights[i] < heights[stk.peek()]) {
                l[stk.pop()] = i;
            }
            stk.push(i);
        }

        stk.clear();
        for (int i = 0; i < n; i++) {
            while (!stk.isEmpty() && heights[i] < heights[stk.peek()]) {
                r[stk.pop()] = i;
            }
            stk.push(i);
        }

        int ans = 0;
        for (int i = 0; i < n; i++) {
            ans = Math.max(ans, heights[i] * (r[i] - l[i] - 1));
        }
        return ans;
    }

    public int largestRectangleArea0(int[] heights) {
        int n = heights.length;
        int[] l = new int[n];
        Arrays.fill(l, -1);
        int[] r = new int[n];
        Arrays.fill(r, n);
        ArrayDeque<Integer> stk = new ArrayDeque<>();
        for (int i = n - 1; i >= 0; i--) {
            while (!stk.isEmpty() && heights[i] < heights[stk.peek()]) {
                l[stk.pop()] = i;
            }
            r[i] = stk.isEmpty() ? n : stk.peek();
            stk.push(i);
        }

        int ans = 0;
        for (int i = 0; i < n; i++) {
            ans = Math.max(ans, heights[i] * (r[i] - l[i] - 1));
        }
        return ans;
    }
}