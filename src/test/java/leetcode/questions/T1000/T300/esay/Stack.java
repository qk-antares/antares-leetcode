package leetcode.questions.T1000.T300.esay;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Stack
 */
public class Stack {
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
}