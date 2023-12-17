package leetcode.questions.Easy;

import java.util.Arrays;
import java.util.Random;
import java.util.Stack;

/**
 * @author Antares
 * @date 2022/9/6
 */
public class DesignEasy {
    /**
     * 打乱数组（我的解法）
     */
    class Solution {
        private int[] nums;

        public Solution(int[] nums) {
            this.nums = Arrays.copyOf(nums, nums.length);
        }

        public int[] reset() {
            return nums;
        }

        public int[] shuffle() {
            int[] res = Arrays.copyOf(this.nums, this.nums.length);
            int j, temp;
            for (int i = 0; i < nums.length; i++) {
                j = new Random().nextInt(this.nums.length);
                temp = res[i];
                res[i] = res[j];
                res[j] = temp;
            }
            return res;
        }
    }

    /**
     * 设计MinStack（我的解法：效率太低）
     */
    class MinStack {
        private Stack<Integer> myStack;

        public MinStack() {
            this.myStack = new Stack<Integer>();
        }

        public void push(int val) {
            this.myStack.push(val);
        }

        public void pop() {
            this.myStack.pop();
        }

        public int top() {
            return this.myStack.peek();
        }

        public int getMin() {
            if(this.myStack.isEmpty())
                return 0;
            Stack<Integer> tempStack = new Stack<>();
            int min = this.myStack.pop();
            tempStack.push(min);
            int temp;
            while (!this.myStack.isEmpty()){
                temp = this.myStack.pop();
                tempStack.push(temp);
                if(temp < min)
                    min = temp;
            }
            while (!tempStack.isEmpty())
                this.myStack.push(tempStack.pop());
            return min;
        }
    }

    /**
     * 答案解法，使用链表，且每个结点多了一个min属性，效率很高！
     */
    class MinStack0{
        class ListNode{
            public int val;
            public int min;
            public ListNode next;

            public ListNode(int val, int min, ListNode next){
                this.val = val;
                this.min = min;
                this.next = next;
            }
        }

        // 链表的表头，也相当于栈的栈顶
        private ListNode head;

        public MinStack0() {
            head = null;
        }

        public void push(int val) {
            int min;
            if(head != null && val > head.min)
                min = head.min;
            else
                min = val;

            head = new ListNode(val, min, head);
        }

        public void pop() {
            if(head != null)
                head = head.next;
        }

        public int top() {
            if(head != null)
                return head.val;

            return 0;
        }

        public int getMin() {
            return head.min;
        }
    }

}
