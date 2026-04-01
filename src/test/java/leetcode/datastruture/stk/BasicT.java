package leetcode.datastruture.stk;

import java.util.ArrayDeque;

/**
 * 栈
 * 基础
 */
public class BasicT {
    /**
     * 155. 最小栈 [Medium]
     * 
     * 简言之就是栈中的节点同时保存当前节点的值和最小值
     */
    class MinStack {
        ArrayDeque<int[]> stk = new ArrayDeque<>();

        public MinStack() {

        }

        public void push(int val) {
            stk.push(new int[] { val, stk.isEmpty() ? val : Math.min(val, stk.peek()[1]) });
        }

        public void pop() {
            stk.pop();
        }

        public int top() {
            return stk.peek()[0];
        }

        public int getMin() {
            return stk.peek()[1];
        }
    }

}
