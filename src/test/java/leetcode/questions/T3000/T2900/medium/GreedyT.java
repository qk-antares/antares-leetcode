package leetcode.questions.T3000.T2900.medium;

import java.util.ArrayDeque;
import java.util.Deque;

import org.junit.jupiter.api.Test;

public class GreedyT {
    /*
     * 2829. k-avoiding 数组的最小总和
     * 
     * 优化可以把两个for换成等差数列求和公式
     */
    public int minimumSum(int n, int k) {
        int mid = k / 2;
        int ans = 0;
        for (int i = 1; i <= Math.min(n, mid); i++) {
            ans += i;
        }

        for (int i = k; i < k + n - mid; i++) {
            ans += i;
        }
        return ans;
    }

    /*
     * 2116. 判断一个括号字符串是否有效
     * 
     * 我的一遍遍历解法错误的原因在于，遇到一个未锁的括号时，能把它消耗就消耗掉，这种方法不一定是最优解
     * 
     * 两遍遍历解法：
     * 1. 首先判断s的长度，如果是奇数，返回false
     * 2. 第一遍遍历，假设所有未锁的都变成'('。遇到'(' cnt++，遇到')' cnt--。如果cnt<0，返回false
     * 3. 第二遍遍历，假设所有未锁的都变成')'。遇到')' cnt++，遇到'(' cnt--。如果cnt<0，返回false
     * 4. 遇到冲突返回false
     */
    public boolean canBeValid0(String s, String locked) {
        Deque<Character> charStack = new ArrayDeque<>();
        Deque<Boolean> lockStack = new ArrayDeque<>();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            char lock = locked.charAt(i);
            // 当前位置锁住
            if (lock == '1') {
                if (c == '(') {
                    charStack.push(c);
                    lockStack.push(true);
                } else {
                    if (!lockStack.isEmpty() && !lockStack.peek() || !charStack.isEmpty() && charStack.peek() == '(') {
                        charStack.pop();
                        lockStack.pop();
                    } else {
                        return false;
                    }
                }
            } else {
                if (charStack.isEmpty()) {
                    charStack.push(c);
                    lockStack.push(false);
                } else {
                    charStack.pop();
                    lockStack.pop();
                }
            }
        }
        return charStack.isEmpty();
    }

    public boolean canBeValid(String s, String locked) {
        int len = s.length();
        if (len % 2 == 1) {
            return false;
        }

        int cnt = 0;
        for (int i = 0; i < len; i++) {
            if (s.charAt(i) == '(' || locked.charAt(i) == '0') {
                cnt++;
            } else {
                cnt--;
                if (cnt < 0) {
                    return false;
                }
            }
        }

        cnt = 0;
        for (int i = len - 1; i >= 0; i--) {
            if (s.charAt(i) == ')' || locked.charAt(i) == '0') {
                cnt++;
            } else {
                cnt--;
                if (cnt < 0) {
                    return false;
                }
            }
        }
        return true;
    }

    @Test
    public void test() {
        System.out.println(canBeValid("((()(()()))()((()()))))()((()(()", "10111100100101001110100010001001"));
    }
}
