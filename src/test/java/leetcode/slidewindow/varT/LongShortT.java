package leetcode.slidewindow.varT;

/*
 * [变长滑动窗口] / [求最长/最短]
 */
public class LongShortT {
    /*
     * 3. 无重复字符的最长子串 [Medium]
     * 
     * 用l和r分别标记窗口的左右端点。
     * r向右扩展，直至不满足条件
     * l向右扩展，直至满足条件
     * 这样一个迭代的过程
     * 虽然在代码上我们会看到两个while循环，但算法整体的时间复杂度是O(n)
     */
    public int lengthOfLongestSubstring(String s) {
        int ans = 0;
        int l = 0, r = 0;
        int[] cnt = new int[128];
        char[] arr = s.toCharArray();
        int n = arr.length;
        while (r < n) {
            int add = arr[r];
            cnt[add]++;
            r++;

            while (cnt[add] > 1) {
                cnt[arr[l]]--;
                l++;
            }
            ans = Math.max(ans, r - l);
        }
        return ans;
    }

    /*
     * 3090. 每个字符最多出现两次的最长子字符串 [Easy]
     * 
     * 完全同上
     */
    public int maximumLengthSubstring(String s) {
        char[] arr = s.toCharArray();
        int n = arr.length;
        int l = 0, r = 0;
        int ans = 0;
        int[] cnt = new int[26];

        while (r < n) {
            int add = arr[r] - 'a';
            cnt[add]++;
            r++;

            while (cnt[add] > 2) {
                cnt[arr[l] - 'a']--;
                l++;
            }

            ans = Math.max(ans, r - l);
        }

        return ans;
    }
}
