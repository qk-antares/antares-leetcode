package leetcode.greedy;

/*
 * 脑筋急转弯，基本上是根据输入直接输出，需要找规律
 */
public class BrainTeaserT {
    /*
     * 2311. 小于等于 K 的最长二进制子序列 [Medium]
     * 
     * 变长滑动窗口，求最长
     * 读错题了，这里是子序列，不一定连续
     * 
     * 贪心，假设k有m位
     * 枚举s的后缀（也从k位开始枚举）
     * 如果说s长为m的后缀小于k，则该后缀之前的所有0的个数+m就是答案
     * 如果说s长为m的后缀大于k，则长为m-1的后缀一定<k，则后缀之前的所有0的个数+m-1就是答案
     */
    public int longestSubsequence(String s, int k) {
        int tmp = k;
        int suffix = 0;
        char[] arr = s.toCharArray();
        int i = 0;
        int n = arr.length;
        while (i < n && tmp != 0) {
            suffix += (arr[n - 1 - i] - '0') << i;
            tmp >>= 1;
            i++;
        }

        int cnt = 0;
        for (int j = 0; j < n - i; j++) {
            if (arr[j] == '0')
                cnt++;
        }

        if (suffix <= k)
            return cnt + i;
        else
            return cnt + i - 1;
    }

    /*
     * 3227. 字符串元音游戏 [Medium]
     * 
     * 当s中元音字母个数为奇数时，小红可以直接获胜
     * 当s中元音字母个数为正偶数时：
     * 小红先移除奇数个，则一定还剩下奇数个；
     * 小明移除偶数个，奇偶性不变；
     * 然后小红再移除整个字符串（奇数个），回到了情况1
     * 
     * 综上所述，只要元音字母>0，都是小红获胜
     */
    public boolean doesAliceWin(String s) {
        for (char c : s.toCharArray()) {
            if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
                return true;
            }
        }
        return false;
    }
}
