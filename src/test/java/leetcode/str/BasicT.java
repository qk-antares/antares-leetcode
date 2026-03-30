package leetcode.str;

import org.junit.jupiter.api.Test;

public class BasicT {
    /**
     * 5. 最长回文子串 [Medium]
     * 
     * 中心扩展法，枚举回文串的中心，然后向两侧扩散
     */
    public String longestPalindrome(String s) {
        char[] ss = s.toCharArray();
        int n = ss.length;
        int ans = 0;
        int ansL = -1, ansR = -1;
        for (int i = 0; i < n; i++) {
            int j = i;
            while (j + 1 < n && ss[j + 1] == ss[i])
                j++;

            // [i,j]作为中心
            for (int d = 0; i - d >= 0 && j + d < n; d++) {
                if (ss[i - d] != ss[j + d])
                    break;

                int len = 2 * d + j - i + 1;
                if (len > ans) {
                    ans = len;
                    ansL = i - d;
                    ansR = j + d;
                }
            }
        }

        return s.substring(ansL, ansR + 1);
    }

    /*
     * 165. 比较版本号 [Medium]
     */
    public int compareVersion0(String version1, String version2) {
        String[] s1 = version1.split("\\.");
        String[] s2 = version2.split("\\.");
        int len1 = s1.length, len2 = s2.length;
        int i = 0;
        int n = Math.min(len1, len2);
        for (; i < n; i++) {
            int a = Integer.valueOf(s1[i]);
            int b = Integer.valueOf(s2[i]);
            if (a > b) {
                return 1;
            } else if (a < b) {
                return -1;
            }
        }

        for (int j = i; j < len1; j++) {
            int a = Integer.valueOf(s1[j]);
            if (a != 0)
                return 1;
        }

        for (int j = i; j < len2; j++) {
            int a = Integer.valueOf(s2[j]);
            if (a != 0)
                return -1;
        }
        return 0;
    }

    /*
     * 更简洁的写法
     */
    public int compareVersion(String version1, String version2) {
        String[] s1 = version1.split("\\.");
        String[] s2 = version2.split("\\.");
        int len1 = s1.length, len2 = s2.length;

        int n = Math.max(len1, len2);
        for (int i = 0; i < n; i++) {
            int a = i < len1 ? Integer.valueOf(s1[i]) : 0;
            int b = i < len2 ? Integer.valueOf(s2[i]) : 0;
            if (a > b) {
                return 1;
            } else if (a < b) {
                return -1;
            }
        }

        return 0;
    }

    /*
     * 3461. 判断操作后字符串中的数字是否相等 I [Easy]
     * 
     * 只用注意外层循环的边界条件是ss.length -2
     */
    public boolean hasSameDigits(String s) {
        char[] ss = s.toCharArray();
        int[] nums = new int[ss.length];
        for (int i = 0; i < ss.length; i++) {
            nums[i] = ss[i] - '0';
        }

        for (int i = 0; i < ss.length - 2; i++) {
            for (int j = 0; j < ss.length - 1 - i; j++) {
                nums[j] = (nums[j] + nums[j + 1]) % 10;
            }
        }

        return nums[0] == nums[1];
    }

    @Test
    public void test() {
        // String version1 = "1.2";
        // String version2 = "1.10";
        // System.out.println(compareVersion(version1, version2));

        hasSameDigits("3902");
    }
}
