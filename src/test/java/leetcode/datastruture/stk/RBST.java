package leetcode.datastruture.stk;

import java.util.ArrayDeque;

/**
 * 合法括号字符串
 */
public class RBST {
    /**
     * 20. 有效的括号 [Easy]
     */
    public boolean isValid(String s) {
        char[] ss = s.toCharArray();
        ArrayDeque<Character> stk = new ArrayDeque<>();
        for (char ch : ss) {
            if (ch == '(' || ch == '[' || ch == '{') {
                stk.push(ch);
            } else if (ch == ')') {
                if (!stk.isEmpty() && stk.peek() == '(')
                    stk.pop();
                else
                    return false;
            } else if (ch == ']') {
                if (!stk.isEmpty() && stk.peek() == '[')
                    stk.pop();
                else
                    return false;
            } else if (ch == '}') {
                if (!stk.isEmpty() && stk.peek() == '{')
                    stk.pop();
                else
                    return false;
            }
        }
        return stk.isEmpty();
    }
}
