package leetcode.interview150;

import java.util.ArrayDeque;
import java.util.LinkedList;

import org.junit.jupiter.api.Test;

public class Stack {
    /**
     * 71. 简化路径
     */
    public String simplifyPath(String path) {
        ArrayDeque<String> stack = new ArrayDeque<>();
        String[] split = path.split("/");
        for (int i = 0; i < split.length; i++) {
            if (!split[i].isEmpty()) {
                if (split[i].equals("..")) {
                    if (!stack.isEmpty()) {
                        stack.removeLast();
                    }
                } else if (split[i].equals(".")) {

                } else {
                    stack.addLast(split[i]);
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        int len = stack.size();
        for (int i = 0; i < len; i++) {
            sb.append("/").append(stack.pollFirst());
        }

        if (len == 0) {
            return "/";
        }
        return sb.toString();
    }

    /**
     * 155. 最小栈
     */
    class MinStack {

        LinkedList<int[]> list;

        public MinStack() {
            list = new LinkedList<>();
        }

        public void push(int val) {
            list.addLast(new int[] { val, list.isEmpty() ? val : Math.min(val, list.getLast()[1]) });
        }

        public void pop() {
            list.removeLast();
        }

        public int top() {
            return list.getLast()[0];
        }

        public int getMin() {
            return list.getLast()[1];
        }
    }

    /**
     * 224. 基本计算器
     * 只有+-()
     * 每个数字的+-和其前面的单个+-号和前面的()决定
     * 维护ops栈，该栈代表括号造成的影响，初始为+1
     */
    public int calculate(String s) {
        ArrayDeque<Integer> ops = new ArrayDeque<Integer>();
        ops.addLast(1);

        int ans = 0;

        int sign = 1;
        int len = s.length();
        int i = 0;
        while (i < len) {
            if(s.charAt(i) == ' '){
                
            } else if(s.charAt(i) == '+') {
                sign = ops.peekLast();
            } else if(s.charAt(i) == '-') {
                sign = -ops.peekLast();
            } else if(s.charAt(i) == '(') {
                ops.addLast(sign);
            } else if(s.charAt(i) == ')'){
                ops.removeLast();
            } else {
                int num = 0;
                while (i < len && Character.isDigit(s.charAt(i))) {
                    num = 10 * num + s.charAt(i) - '0';
                    i++;
                }
                ans += sign * num;
                i--;
            }
            i++;
        }

        return ans;
    }

    @Test
    void test() {
        // simplifyPath("/a//b////c/d//././/..");
        calculate("(1+(4-5+2)-(3-1))+(6+8)");
    }
}
