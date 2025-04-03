package leetcode.questions.T1000.T400;

import java.util.ArrayDeque;
import java.util.Deque;

public class StackT {
    /*
     * 394. 字符串解码 [Medium]
     * 
     * 有两个栈，numStk和strStk，一边遍历一边将答案保存至ans中
     * 
     */
    public String decodeString(String s) {
        Deque<Integer> numStk = new ArrayDeque<>();
        Deque<StringBuilder> strStk = new ArrayDeque<>();
        StringBuilder curStr = new StringBuilder();
        int curNum = 0;

        for (char ch : s.toCharArray()) {
            if (Character.isDigit(ch)) {
                curNum = curNum * 10 + ch - '0';
            } else if (ch == '[') {
                numStk.addLast(curNum);
                curNum = 0;
                strStk.addLast(curStr);
                curStr = new StringBuilder();
            } else if (ch == ']') {
                StringBuilder preStr = strStk.removeLast();
                Integer num = numStk.removeLast();
                while (num-- > 0) {
                    preStr.append(curStr);
                }
                curStr = preStr;
            } else {
                curStr.append(ch);
            }
        }

        return curStr.toString();
    }
}
