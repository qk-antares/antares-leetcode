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
            if(!split[i].isEmpty()){
                if(split[i].equals("..")){
                    if(!stack.isEmpty()){
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

        if(len == 0){
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
            list.addLast(new int[]{val, list.isEmpty() ? val : Math.min(val, list.getLast()[1])});
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
     * 一个操作数栈，一个符号栈
     * 遇到操作数：
     * 用flag标记之前的字符是否是数字，如果是，则把栈顶弹出*10+当前数，然后再压栈
     *
     * 遇到符号：
     * 当前符号的优先级低于符号栈栈顶符号，则弹出两个操作数和一个符号进行运算
     */
    public int calculate(String s) {
        new ArrayDeque<>();
        return -1;
    }


    @Test
    void test(){
//        simplifyPath("/a//b////c/d//././/..");
        calculate("-(2 + 3)");
    }
}
