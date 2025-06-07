package leetcode.str;

import org.junit.jupiter.api.Test;

public class KMP {
    int[] buildPmt(String p) {
        int n = p.length();
        int[] pmt = new int[n];
        int j = 0; // 已匹配长度

        for (int i = 1; i < n; i++) {
            while (j > 0 && p.charAt(i) != p.charAt(j)) {
                j = pmt[j - 1];
            }
            if (p.charAt(i) == p.charAt(j)) {
                j++;
            }
            pmt[i] = j;
        }
        return pmt;
    }

    int[] buildNext(String p) {
        int n = p.length();
        int[] next = new int[n + 1];
        next[0] = -1;
        int j = 0; // 已匹配长度

        for (int i = 1; i < n; i++) {
            while (j > 0 && p.charAt(i) != p.charAt(j)) {
                j = next[j];
            }
            if (p.charAt(i) == p.charAt(j)) {
                j++;
            }
            next[i + 1] = j;
        }
        return next;
    }

    /*
     * 28. 找出字符串中第一个匹配项的下标 [Easy] <Star>
     */
    public int strStr(String haystack, String needle) {
        int m = haystack.length(), n = needle.length();
        int[] pmt = buildPmt(needle);
        int j = 0; // 匹配长度
        for (int i = 0; i < m; i++) {
            while (j > 0 && haystack.charAt(i) != needle.charAt(j))
                j = pmt[j - 1];
            if (haystack.charAt(i) == needle.charAt(j))
                j++;
            if (j == n)
                return i - j + 1;
        }
        return -1;
    }

    /*
     * 796. 旋转字符串 [Easy]
     */
    public boolean rotateString(String s, String goal) {
        if (s.length() != goal.length())
            return false;

        String ss = goal + goal;
        int m = ss.length(), n = s.length();

        int j = 0;
        int[] pmt = buildPmt(s);

        for (int i = 0; i < m; i++) {
            while (j > 0 && ss.charAt(i) != s.charAt(j))
                j = pmt[j - 1];
            if (ss.charAt(i) == s.charAt(j))
                j++;
            if (j == n)
                return true;
        }

        return false;
    }

    /*
     * 1392. 最长快乐前缀 [Hard]
     * 
     * 其实就是构造pmt，然后根据pmt的最后一位返回
     */
    public String longestPrefix(String s) {
        int n = s.length();
        int[] pmt = buildPmt(s);
        return s.substring(n - pmt[n - 1], n);
    }

    @Test
    public void testGetNext() {
        String p = "ababc";
        int[] pmt = buildPmt(p);
        for (int i = 0; i < pmt.length; i++) {
            System.out.print(pmt[i] + " ");
        }
        // Expected output: -1 0 0 1 2
    }
}
