package leetcode.str;

import java.util.ArrayList;
import java.util.List;

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

    /*
     * 3036. 匹配模式数组的子数组数目 II [Hard]
     * 
     * 首先构造nums的相邻元素差delta
     * 满足题设条件的一个子数组，其实就是长度为m一段的匹配delta
     */
    public int countMatchingSubarrays(int[] nums, int[] pattern) {
        int n = nums.length;
        int m = pattern.length;

        // 构造nums的相邻元素差
        int[] delta = new int[n];
        for (int i = 1; i < n; i++) {
            int d = nums[i] - nums[i - 1];
            if (d > 0)
                delta[i] = 1;
            else if (d < 0)
                delta[i] = -1;
            else
                delta[i] = 0;
        }

        int[] pmt = buildPmt(pattern);
        int j = 0; // 当前匹配长度

        int ans = 0;

        for (int i = 1; i < n; i++) {
            while (j > 0 && pattern[j] != delta[i])
                j = pmt[j - 1];
            if (pattern[j] == delta[i])
                j++;
            if (j == m) {
                ans++;
                // 完成一次匹配。假设失配，重设匹配长度j
                j = pmt[j - 1];
            }
        }

        return ans;
    }

    int[] buildPmt(int[] p) {
        int n = p.length;
        int[] pmt = new int[n];
        int j = 0; // 当前匹配长度

        for (int i = 1; i < n; i++) {
            while (j > 0 && p[j] != p[i])
                j = pmt[j - 1];
            if (p[j] == p[i])
                j++;
            pmt[i] = j;
        }

        return pmt;
    }

    /*
     * 1764. 通过连接另一个数组的子数组得到一个数组 [Medium]
     * 
     * 相当于使用groups中的数组，逐一地与nums执行匹配
     * 贪心地执行匹配，尽量消耗nums前面的元素
     * 用一个cur来标记当前消耗到的nums的元素index
     */
    public boolean canChoose(int[][] groups, int[] nums) {
        int n = nums.length;
        int cur = 0;

        for (int[] p : groups) {
            int[] pmt = buildPmt(p);
            int m = p.length;
            int j = 0;
            boolean match = false;

            for (int i = cur; i < n; i++) {
                while (j > 0 && p[j] != nums[i])
                    j = pmt[j - 1];
                if (p[j] == nums[i])
                    j++;
                if (j == m) {
                    match = true;
                    cur = i + 1;
                    break;
                }
            }

            if (!match)
                return false;
        }

        return true;
    }

    /*
     * 1668. 最大重复子字符串 [Easy]
     * 
     * 第一种错解是使用k来标记当前匹配的次数，当失配一次后k=0，当完成一次匹配后j=0
     * 错误的本质原因在于j=0，以aaabaaaabaaabaaaabaaaabaaaabaaaaba和aaaba为例：
     * [aaaba][aaaba][aab..]
     * 可以看到在匹配第3个aaaba时导致失配，但由于每次完成匹配后j都会被重置为0，导致此时实际上无法回退：
     * aaabaaaab[aaaab]...
     * 
     * 解法二：
     * 找到word在sequence中所有出现的位置，用一个List来保存
     * "深度优先搜索"List
     * 
     * 解法三：
     * 使用"二分答案"，由于word最多出现n/m次，可以使用二分来查找最大k
     */
    public int maxRepeating0(String sequence, String word) {
        int k = 0;
        int ans = 0;

        int n = sequence.length();
        int m = word.length();
        int[] pmt = buildPmt(word);
        int j = 0;

        for (int i = 0; i < n; i++) {
            while (j > 0 && word.charAt(j) != sequence.charAt(i))
                j = pmt[j - 1];
            if (word.charAt(j) == sequence.charAt(i))
                j++;
            else
                k = 0;
            if (j == m) {
                k++;
                ans = Math.max(ans, k);
                j = 0;
            }
        }

        return ans;
    }

    public int maxRepeating1(String sequence, String word) {

        int n = sequence.length();
        int m = word.length();
        int[] pmt = buildPmt(word);
        int j = 0;

        boolean[] idx = new boolean[n];

        for (int i = 0; i < n; i++) {
            while (j > 0 && word.charAt(j) != sequence.charAt(i))
                j = pmt[j - 1];
            if (word.charAt(j) == sequence.charAt(i))
                j++;
            if (j == m) {
                j = pmt[j - 1];
                idx[i] = true;
            }
        }

        int ans = 0;
        boolean[] vis = new boolean[n];

        for (int i = 0; i < n; i++) {
            if (!idx[i] || vis[i])
                continue;
            vis[i] = true;
            int k = 1;
            while (i + k * m < n && idx[i + k * m]) {
                vis[i + k * m] = true;
                k++;
            }
            ans = Math.max(ans, k);
        }

        return ans;
    }

    public int maxRepeating(String sequence, String word) {
        int n = sequence.length();
        int m = word.length();
        int l = 0, r = n / m;
        while (l <= r) {
            int mid = (l + r) / 2;
            if (sequence.contains(word.repeat(mid))) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return r;
    }

    /*
     * 459. 重复的子字符串 [Easy]
     */
    public boolean repeatedSubstringPattern(String s) {
        int n = s.length();
        String ss = (s + s).substring(1, 2 * n - 1);
        int m = ss.length();
        int j = 0;
        int[] pmt = buildPmt(s);
        for (int i = 0; i < m; i++) {
            while (j > 0 && s.charAt(j) != ss.charAt(i))
                j = pmt[j - 1];
            if (s.charAt(j) == ss.charAt(i))
                j++;
            if (j == n)
                return true;
        }
        return false;
    }

    /*
     * 3008. 找出数组中的美丽下标 II [Hard]
     * 
     * KMP+二分
     * 题设的意思是，s中包含a和b这两个子串，且a和b的距离在k以内
     * 可以首先找出b的所有位置pos
     * 然后开始用kmp在s中找a，假设找到了下标i是符合要求的
     * 需要通过二分找到第一个>i的位置pos[r]，pos[r-1]是第一个<=i的位置
     * 判断这两个位置能否满足要求
     * 
     * 由于两个pos数组是有序的，还可使用双指针，效率更高。这里双指针的写法很精髓
     * 
     * 或者另一个思路
     * 首先利用kmp在s中找a，假设找到了下标i(i-len1+1)
     * 那么j的位置是[i-k,i+k]
     * 可以在s[i-k,i+k+b.length()-1]的子串内找b
     * 思路二超时的原因？我猜是需要频繁地调用s.substring，这个方法的效率比较低
     */
    public List<Integer> beautifulIndices(String s, String a, String b, int k) {
        char[] arr = s.toCharArray();
        char[] p1 = a.toCharArray();
        char[] p2 = b.toCharArray();
        int[] pmt1 = buildPmt(p1);
        int[] pmt2 = buildPmt(p2);

        List<Integer> idx1 = findIndex(arr, p1, pmt1);
        List<Integer> idx2 = findIndex(arr, p2, pmt2);

        List<Integer> ans = new ArrayList<>();

        int j = 0;
        int bd = idx2.size();
        for (int idx : idx1) {
            while (j < bd && idx2.get(j) < idx - k)
                j++;
            if (j < bd && idx2.get(j) <= idx + k)
                ans.add(idx);
        }

        return ans;
    }

    // 找出p在s中所有出现的下标
    List<Integer> findIndex(char[] s, char[] p, int[] pmt) {
        int n = s.length;
        int m = p.length;
        List<Integer> ans = new ArrayList<>();
        int j = 0;

        for (int i = 0; i < n; i++) {
            while (j > 0 && p[j] != s[i])
                j = pmt[j - 1];
            if (p[j] == s[i])
                j++;
            if (j == m) {
                ans.add(i - j + 1);
                j = pmt[j - 1];
            }
        }
        return ans;
    }

    int[] buildPmt(char[] p) {
        int n = p.length;
        int[] pmt = new int[n];
        int j = 0;

        for (int i = 1; i < n; i++) {
            while (j > 0 && p[j] != p[i])
                j = pmt[j - 1];
            if (p[j] == p[i])
                j++;
            pmt[i] = j;
        }

        return pmt;
    }

    @Test
    public void testGetNext() {
        // maxRepeating("aaabaaaabaaabaaaabaaaabaaaabaaaaba", "aaaba");

        // String p = "ababc";
        // int[] pmt = buildPmt(p);
        // for (int i = 0; i < pmt.length; i++) {
        // System.out.print(pmt[i] + " ");
        // }
        // Expected output: -1 0 0 1 2
    }
}
