package leetcode.greedy;

import java.util.ArrayDeque;

/*
 * 字符串贪心：
 * 字典序最大/最小
 * 回文串贪心
 */
public class StrGreedy {
    /*
     * 2566. 替换一个数字后的最大差值 [Easy]
     * 
     * 技巧在于直接使用s.replace()方法来替换字符，该方法返回一个新的字符串，而不会修改原字符串。
     */
    public int minMaxDifference0(int num) {
        char[] max = String.valueOf(num).toCharArray();
        char[] min = String.valueOf(num).toCharArray();

        int n = max.length;

        int target = min[0];
        for (int i = 0; i < n; i++) {
            if (min[i] == target)
                min[i] = '0';
        }

        int i = 0;
        for (; i < n; i++) {
            if (max[i] != '9') {
                target = max[i];
                break;
            }
        }

        for (; i < n; i++) {
            if (max[i] == target)
                max[i] = '9';
        }

        return Integer.valueOf(new String(max)) - Integer.valueOf(new String(min));
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

    /*
     * 1717. 删除子字符串的最大得分 [Medium] <Star>
     * 
     * 用dp[i]表示s的子串s[0..i]所能获得的最大收益
     * 如果s[i]='a' && s[i-1]=='b'，这证明我们得到了一个ba子串，dp[i]=Math.max(dp[i-1], y+dp[i-2])
     * 类似的，当得到ab子串时，dp[i]=Math.max(dp[i-1],x+dp[i-2])
     * 其余情况dp[i]=dp[i-1]
     * 
     * 上述思路只考虑删除一遍，实际上我们删除一些子串后，原串的字符顺序会发生变化，可能产生新的ab或ba
     * 贪心策略
     * 不是ab的字符将s分割成了若干个子串
     * 考虑其中的一个子串
     * cnt1是c1的数量，cnt2是c2的数量
     * 由于c1c2的价值更大，遍历的过程中：
     * 如果遇到c1: cnt1++
     * 如果遇到c2: c1>0: cnt1--，ans+=x; c1 <= 0 : cnt2++
     * 剩下的组装c2c1，ans+=Math.min(cnt1, cnt2)*y
     */
    public int maximumGain0(String s, int x, int y) {
        char[] ss = s.toCharArray();
        int n = ss.length;
        int[] dp = new int[n + 1];
        for (int i = 1; i < n; i++) {
            if (ss[i] == 'a' && ss[i - 1] == 'b') {
                dp[i + 1] = Math.max(dp[i], y + dp[i - 1]);
            } else if (ss[i] == 'b' && ss[i - 1] == 'a') {
                dp[i + 1] = Math.max(dp[i], x + dp[i - 1]);
            } else {
                dp[i + 1] = dp[i];
            }
        }
        return dp[n];
    }

    public int maximumGain(String s, int x, int y) {
        if (x > y)
            return maximumGain(s, 'a', x, 'b', y);
        return maximumGain(s, 'b', y, 'a', x);
    }

    // c1开头的价值更大，是x
    public int maximumGain(String s, int c1, int x, int c2, int y) {
        char[] ss = s.toCharArray();
        int cnt1 = 0, cnt2 = 0;
        int ans = 0;
        for (char c : ss) {
            if (c != c1 && c != c2) {
                ans += Math.min(cnt1, cnt2) * y;
                cnt1 = 0;
                cnt2 = 0;
            } else if (c == c1) {
                cnt1++;
            } else {
                if (cnt1 > 0) {
                    cnt1--;
                    ans += x;
                } else {
                    cnt2++;
                }
            }
        }
        ans += Math.min(cnt1, cnt2) * y;

        return ans;
    }

}
