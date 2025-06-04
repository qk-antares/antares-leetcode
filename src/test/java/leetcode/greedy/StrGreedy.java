package leetcode.greedy;

/*
 * 字符串贪心：
 * 字典序最大/最小
 * 回文串贪心
 */
public class StrGreedy {
    /*
     * 3403. 从盒子中找出字典序最大的字符串 I   [Medium]
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
}
