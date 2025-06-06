package leetcode.greedy;

import java.util.ArrayDeque;

/*
 * 字符串贪心：
 * 字典序最大/最小
 * 回文串贪心
 */
public class StrGreedy {
    /*
     * 3403. 从盒子中找出字典序最大的字符串 I [Medium]
     * 
     * 枚举子串的起点。子串的最大长度是n-numFriends+1
     */
    public String answerString(String word, int numFriends) {
        if (numFriends == 1)
            return word;

        int n = word.length();
        String ans = "";
        for (int i = 0; i < n; i++) {
            String subStr = word.substring(i, Math.min(n, i + n - numFriends + 1));
            if (subStr.compareTo(ans) > 0)
                ans = subStr;
        }

        return ans;
    }

    /*
     * 2434. 使用机器人打印字典序最小的字符串 [Medium]  <Star>
     * 
     * 从后往前遍历s，用一个字符保存当前遍历到的最小字符
     * 用一个char[]数组来标记当前位置往后的最小字符
     * 接下来从前往后遍历s，并用一个栈来保存遍历结果
     * 如果后面没有更小字符，直接出栈，否则入栈
     */
    public String robotWithString(String s) {
        char[] arr = s.toCharArray();
        int n = arr.length;
        char min = (char) 128;
        char[] flags = new char[n];
        for (int i = n - 1; i >= 0; i--) {
            flags[i] = min;
            min = (char) Math.min(min, arr[i]);
        }

        char[] ans = new char[n];
        int idx = 0;
        ArrayDeque<Character> stk = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            stk.push(arr[i]);
            while (!stk.isEmpty() && flags[i] >= stk.peek())
                ans[idx++] = stk.pop();
        }

        return new String(ans);
    }
}
