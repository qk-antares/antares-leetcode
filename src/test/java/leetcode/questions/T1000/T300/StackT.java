package leetcode.questions.T1000.T300;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Stack
 */
public class StackT {
    /**
     * 225. 用队列实现栈    [Easy]
     */
    class MyStack {
        Queue<Integer> q;

        public MyStack() {
            q = new LinkedList<>();
        }

        public void push(int x) {
            q.offer(x);
            int size = q.size();
            for (int i = 0; i < size - 1; i++) {
                q.offer(q.poll());
            }
        }

        public int pop() {
            return q.poll();
        }

        public int top() {
            return q.peek();
        }

        public boolean empty() {
            return q.isEmpty();
        }
    }

    /**
     * 232. 用栈实现队列    [Easy]
     */
    class MyQueue {
        Deque<Integer> stk1;
        Deque<Integer> stk2;

        public MyQueue() {
            stk1 = new ArrayDeque<>();
            stk2 = new ArrayDeque<>();
        }

        public void push(int x) {
            while (!stk1.isEmpty()) {
                stk2.push(stk1.pop());
            }
            stk1.push(x);
            while (!stk2.isEmpty()) {
                stk1.push(stk2.pop());
            }
        }

        public int pop() {
            return stk1.pop();
        }

        public int peek() {
            return stk1.peek();
        }

        public boolean empty() {
            return stk1.isEmpty();
        }
    }

}