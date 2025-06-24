package leetcode.str;

import java.util.ArrayList;
import java.util.Arrays;
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
     * 28. 找出字符串中第一个匹配项的下标 [Easy] <Star>
     * 
     * 转成char[]之后再进行匹配的效率稍高
     */
    public int strStr0(String haystack, String needle) {
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

    int strStr(String s, String p) {
        char[] sArr = s.toCharArray();
        char[] pArr = p.toCharArray();
        int n = sArr.length;
        int m = pArr.length;

        int[] pmt = buildPmt(pArr);
        int j = 0;
        for (int i = 0; i < n; i++) {
            while (j > 0 && pArr[j] != sArr[i])
                j = pmt[j - 1];
            if (pArr[j] == sArr[i])
                j++;
            if (j == m)
                return i - m + 1;
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

    /*
     * 214. 最短回文串 [Hard]
     * 
     * 最朴素的方法是将s反转revS，然后枚举revS的后缀
     * 假设s.startsWith(revS[i..])，那么将revS[0..i-1]添加到s的前面即可
     * 
     * 上述方法由于要枚举i，所以时间复杂度会比较高
     * 
     * 观察上述过程，本质上是在寻找s的【最长回文前缀】
     * 另一种方法是直接使用KMP，将s和revS拼接成一个新的字符串
     * ss = s + '#' + revS
     * pmt数组的最后一位就是最长回文前缀的长度
     */
    public String shortestPalindrome0(String s) {
        int n = s.length();
        String reverseS = new StringBuilder(s).reverse().toString();
        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < n; i++) {
            if (s.startsWith(reverseS.substring(i))) {
                ans.append(reverseS.substring(0, i)).append(s);
                break;
            }
        }
        return ans.toString();
    }

    public String shortestPalindrome(String s) {
        String revS = new StringBuilder(s).reverse().toString();
        String ss = new StringBuilder().append(s).append('#').append(revS).toString();
        int[] pmt = buildPmt(ss);
        return revS.substring(0, s.length() - pmt[pmt.length - 1]) + s;

    }

    /*
     * 3529. 统计水平子串和垂直子串重叠格子的数目 [Medium]
     * 
     * 用两个boolean[][]矩阵分别做标记
     * 这种方法的瓶颈就在于将boolean[][]置为true，如果pattern很长，则每次置为true的时间开销很大
     * 可以与差分结合
     * 具体来说，使用两个int[m*n+1]矩阵做标记
     * 假设(i,j)的位置匹配上了，那么[i*n+j-p.length+1..i*n+j]这段置为true，等价于左边界+1，(右边界+1)-1
     */
    public int countCells(char[][] grid, String pattern) {
        int m = grid.length, n = grid[0].length;
        char[] p = pattern.toCharArray();
        int[] pmt = buildPmt(p);

        int[] d1 = new int[m * n + 1];
        int[] d2 = new int[m * n + 1];

        int len = 0; // 匹配长度
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                while (len > 0 && p[len] != grid[i][j])
                    len = pmt[len - 1];
                if (p[len] == grid[i][j])
                    len++;

                if (len == pmt.length) {
                    len = pmt[len - 1];

                    d1[i * n + j - p.length + 1]++;
                    d1[i * n + j + 1]--;
                }
            }
        }

        len = 0;
        for (int j = 0; j < n; j++) {
            for (int i = 0; i < m; i++) {
                while (len > 0 && p[len] != grid[i][j])
                    len = pmt[len - 1];
                if (p[len] == grid[i][j])
                    len++;

                if (len == pmt.length) {
                    len = pmt[len - 1];

                    d2[j * m + i - p.length + 1]++;
                    d2[j * m + i + 1]--;
                }
            }
        }

        for (int i = 1; i < m * n; i++) {
            d1[i] += d1[i - 1];
            d2[i] += d2[i - 1];
        }

        int ans = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (d1[i * n + j] > 0 && d2[j * m + i] > 0)
                    ans++;
            }
        }

        return ans;
    }

    /*
     * 686. 重复叠加字符串匹配 [Medium]
     * 
     * 这里的次数是有上下界的，画个图很容易看出来
     * 而且min+1=max
     * 也就是答案只有两种可能
     * 实测发现char[] KMP > String KMP >> Java contains/indexOf
     * 所以在做算法题时最好还是使用自己实现的KMP算法
     */
    public int repeatedStringMatch(String a, String b) {
        int m = a.length(), n = b.length();
        int min = (int) Math.ceil((double) n / m);
        int max = 1 + (int) Math.ceil((n - 1.0) / m);
        String aa = a.repeat(max);
        int idx = strStr(aa, b);
        if (idx == -1)
            return -1;
        return idx + b.length() > a.length() * min ? max : min;
    }

    /*
     * 3455. 最短匹配子字符串 [Hard]
     * 
     * 首先去掉首尾的*
     * 将p分割成了（至多）3个子串subP[]（题设说了*号恰好有两个）
     * 则s中必须包含所有的这些子串
     * 记录这些子串出现的位置List<List<Integer>> posList，这是一些有序的列表
     * 对这些列表进行遍历
     * 
     * 上述解法需要处理p中的*，还需要对p分割后不同数量的情况进行分类讨论
     * 
     * 另一种简单的方法是使用split("\\*", -1)保留分割出来的子串，这样分割后一定是3个子串
     * 然后对每个子串使用KMP找出所有出现的位置
     * 对于空串，我们认为s中的任何位置都可以匹配（包括s.length）
     * 然后枚举中间这个串的位置，并维护左右最近的子串位置
     */
    public int shortestMatchingSubstring0(String s, String p) {
        int pl = 0, pr = p.length() - 1;
        int m = p.length();
        while (pl <= pr && p.charAt(pl) == '*')
            pl++;
        while (pr >= pl && p.charAt(pr) == '*')
            pr--;
        p = p.substring(pl, pr + 1);
        m = p.length();

        List<List<Integer>> posList = new ArrayList<>();
        List<String> subP = new ArrayList<>();
        for (String subStr : p.split("\\*")) {
            if (subStr.length() > 0) {
                subP.add(subStr);
                posList.add(strStrAll(s, subStr));
            }
        }

        int len = posList.size();
        if (len == 0)
            return 0;
        if (len == 1) {
            int size0 = posList.get(0).size();
            if (size0 == 0)
                return -1;
            else
                return m;
        }
        if (len == 2) {
            int size0 = posList.get(0).size(), size1 = posList.get(1).size();
            if (size0 == 0 || size1 == 0)
                return -1;
            int ans = Integer.MAX_VALUE;
            for (int i = 0; i < size0; i++) {
                int l = posList.get(0).get(i);
                int r = binarySearch(posList.get(1), l + subP.get(0).length() - 1);
                if (r < 0 || r >= size1)
                    break;
                ans = Math.min(ans, posList.get(1).get(r) + subP.get(1).length() - l);
            }
            return ans == Integer.MAX_VALUE ? -1 : ans;
        }
        if (len == 3) {
            int size0 = posList.get(0).size(), size1 = posList.get(1).size(), size2 = posList.get(2).size();
            if (size0 == 0 || size1 == 0 || size2 == 0)
                return -1;
            int ans = Integer.MAX_VALUE;
            for (int i = 0; i < size0; i++) {
                int l = posList.get(0).get(i);
                int r = binarySearch(posList.get(1), l + subP.get(0).length() - 1);
                if (r < 0 || r >= size1)
                    break;
                int rr = binarySearch(posList.get(2), posList.get(1).get(r) + subP.get(1).length() - 1);
                if (rr < 0 || rr >= size2)
                    break;
                ans = Math.min(ans, posList.get(2).get(rr) + subP.get(2).length() - l);
            }
            return ans == Integer.MAX_VALUE ? -1 : ans;
        }
        return -1;
    }

    // 二分查找第一个>target的元素下标
    int binarySearch(List<Integer> list, int target) {
        int l = 0, r = list.size() - 1;
        while (l <= r) {
            int mid = (l + r) / 2;
            if (list.get(mid) <= target) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return l;
    }

    List<Integer> strStrAll(String s, String p) {
        char[] sArr = s.toCharArray();
        char[] pArr = p.toCharArray();
        int n = sArr.length;
        int m = pArr.length;

        List<Integer> ans = new ArrayList<>();

        int[] pmt = buildPmt(pArr);
        int j = 0;
        for (int i = 0; i < n; i++) {
            while (j > 0 && pArr[j] != sArr[i])
                j = pmt[j - 1];
            if (pArr[j] == sArr[i])
                j++;
            if (j == m) {
                ans.add(i - m + 1);
                j = pmt[j - 1];
            }
        }
        return ans;
    }

    public int shortestMatchingSubstring(String s, String p) {
        char[] sArr = s.toCharArray();
        // 保留空串
        String[] subP = p.split("\\*", -1);

        List<Integer> pos0 = strStrAll(sArr, subP[0].toCharArray());
        List<Integer> pos1 = strStrAll(sArr, subP[1].toCharArray());
        List<Integer> pos2 = strStrAll(sArr, subP[2].toCharArray());

        int ans = Integer.MAX_VALUE;
        int l = 0, r = 0;
        // 枚举中间，维护左右最近的元素
        for (int mid : pos1) {
            // 找右边最近
            while (r < pos2.size() && pos2.get(r) < mid + subP[1].length())
                r++;
            // 右侧不存在满足条件的点
            if (r >= pos2.size())
                break;
            // 找左侧最近
            while (l < pos0.size() && pos0.get(l) <= mid - subP[0].length())
                l++;
            // l-1是左边最近的子串
            if (l > 0)
                ans = Math.min(ans, pos2.get(r) + subP[2].length() - pos0.get(l - 1));
        }

        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    List<Integer> strStrAll(char[] s, char[] p) {
        int n = s.length;
        int m = p.length;

        if (m == 0) {
            // 所有位置都可以匹配s
            List<Integer> ans = new ArrayList<>(n + 1);
            for (int i = 0; i <= n; i++) {
                ans.add(i);
            }
            return ans;
        }

        List<Integer> ans = new ArrayList<>();

        int[] pmt = buildPmt(p);
        int j = 0;
        for (int i = 0; i < n; i++) {
            while (j > 0 && p[j] != s[i])
                j = pmt[j - 1];
            if (p[j] == s[i])
                j++;
            if (j == m) {
                ans.add(i - m + 1);
                j = pmt[j - 1];
            }
        }
        return ans;
    }

    /*
     * 1397. 找到所有好字符串 [Hard]
     * 
     * 数位dp
     * dfs(i,status,s1Limit,s2Limit,char[] s1, char[] s2, int[][] memo, char[] evil,
     * int[] pmt, int n)
     * i表示当前填写的位，一共有n位
     * status表示已经连续多少位和evil相同
     * s1Limit表示是否受到下界限制
     * s2Limit表示是否受到上界限制
     * s1是下界
     * s2是上界
     * memo是记忆数组
     * 返回当前的状态下，有多少个结果
     */
    public int findGoodStrings(int n, String s1, String s2, String evil) {
        char[] arr = evil.toCharArray();
        int[] pmt = buildPmt(arr);
        int[][] memo = new int[n][pmt.length];
        for (int[] row : memo)
            Arrays.fill(row, -1);
        return dfs(0, 0, true, true, s1.toCharArray(), s2.toCharArray(), memo, arr, pmt, n);
    }

    int dfs(int i, int status, boolean s1Limit, boolean s2Limit, char[] s1, char[] s2, int[][] memo, char[] evil,
            int[] pmt, int n) {
        if (status == pmt.length)
            return 0;
        if (i == n)
            return 1;
        if (!s1Limit && !s2Limit && memo[i][status] != -1)
            return memo[i][status];

        int ans = 0; // 共有ans种填法
        int low = s1Limit ? s1[i] - 'a' : 0;
        int high = s2Limit ? s2[i] - 'a' : 25;
        for (int j = low; j <= high; j++) {
            // 这相当于失配
            if (j != evil[status] - 'a') {
                int newStatus = status;
                while (newStatus > 0 && j != evil[newStatus])
                    newStatus = pmt[newStatus - 1];
                if (evil[newStatus] == j)
                    newStatus++;
                ans = (ans
                        + dfs(i + 1, newStatus, s1Limit && j == low, s2Limit && j == high, s1, s2, memo, evil, pmt, n))
                        % 1_000_000_007;
            } else {
                ans = (ans
                        + dfs(i + 1, status + 1, s1Limit && j == low, s2Limit && j == high, s1, s2, memo, evil, pmt, n))
                        % 1_000_000_007;
            }
        }

        if (!s1Limit && !s2Limit)
            memo[i][status] = ans;
        return ans;
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

        // shortestMatchingSubstring("cvtrmfmvuhzncqffl", "fl**");

        findGoodStrings(4, "aaa", "zzz", "ab");
    }
}
