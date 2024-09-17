package leetcode.questions.T1000.T100.hard;

import java.util.ArrayDeque;

import org.junit.jupiter.api.Test;

public class StackT {
    /**
     * 84. 柱状图中最大的矩形
     * 用两个单调栈分别记录每个柱子左侧最短和右侧最短
     */
    public int largestRectangleArea(int[] heights) {
        int n = heights.length;
        int[] leftLow = new int[n];
        int[] rightLow = new int[n];

        ArrayDeque<Integer> stk = new ArrayDeque<Integer>();

        for (int i = 0; i < n; i++) {
            while (!stk.isEmpty() && heights[stk.peek()] >= heights[i]) {
                stk.pop();
            }

            if(stk.isEmpty()) {
                leftLow[i] = -1;
            } else {
                leftLow[i] = stk.peek();
            }
            stk.push(i);
        }

        stk.clear();

        for (int i = n - 1; i >= 0; i--) {
            while (!stk.isEmpty() && heights[stk.peek()] >= heights[i]) {
                stk.pop();
            }
            if(stk.isEmpty()) {
                rightLow[i] = n;
            } else {
                rightLow[i] = stk.peek();
            }
            stk.push(i);
        }

        int max = 0;
        for (int i = 0; i < n; i++) {
            max = Math.max(max, heights[i] * (rightLow[i] - leftLow[i] - 1));
        }
        return max;
    }

    @Test
    void test() {
        largestRectangleArea(new int[]{2,1,5,6,2,3});
    }
}
