package leetcode.greedy;

import java.util.ArrayDeque;

/*
 * 字符串贪心：
 * 字典序最大/最小
 * 回文串贪心
 */
public class StrGreedy {
    /*
     * 2566. 替换一个数字后的最大差值   [Easy]
     * 
     * 技巧在于直接使用s.replace()方法来替换字符，该方法返回一个新的字符串，而不会修改原字符串。
     */
    public int minMaxDifference0(int num) {
        char[] max = String.valueOf(num).toCharArray();
        char[] min = String.valueOf(num).toCharArray();
        
        int n = max.length;

        int target = min[0];
        for(int i = 0; i < n; i++) {
            if(min[i] == target) min[i] = '0';
        }

        int i = 0;
        for(; i < n; i++){
            if(max[i] != '9') {
                target = max[i];
                break;
            }
        }

        for(; i < n; i++) {
            if(max[i] == target) max[i] = '9';
        }

        return Integer.valueOf(new String(max))-Integer.valueOf(new String(min));
    }

    public int minMaxDifference(int num) {
        String s = String.valueOf(num);
        int mx = num;
        for (char c : s.toCharArray()) {
            if (c != '9') { // 第一个不等于 9 的字符
                mx = Integer.parseInt(s.replace(c, '9')); // 替换成 9
                break;
            }
        }
        // 第一个不等于 0 的字符，替换成 0
        int mn = Integer.parseInt(s.replace(s.charAt(0), '0'));
        return mx - mn;
    }

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
     * 2434. 使用机器人打印字典序最小的字符串 [Medium] <Star>
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
