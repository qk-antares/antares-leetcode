package leetcode.questions.T1000.T800;

import java.util.ArrayDeque;
import java.util.Deque;

public class StackT {
    /*
     * 739. 每日温度 [Medium]   [Link: 84. 柱状图中最大的矩形]
     * 
     * 单调栈
     * 创建一个栈，对temperatures进行1次遍历（存入idx）
     * 如果当前元素比栈顶小，这表示这个元素肯定不是答案，压入栈中
     * 如果当前元素比栈顶大，这代表我们知道了满足条件的temp
     * 依次弹出栈顶元素，纸质不满足上面的条件。则这些弹出的元素，它们的答案就是当前这个元素
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
}
