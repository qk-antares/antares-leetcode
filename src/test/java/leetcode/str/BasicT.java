package leetcode.str;

import org.junit.jupiter.api.Test;

public class BasicT {
    /*
     * 165. 比较版本号
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
        for(int i = 0; i < n; i++) {
            int a = i < len1 ? Integer.valueOf(s1[i]) : 0;
            int b = i < len2 ? Integer.valueOf(s2[i]) : 0;
            if(a > b) {
                return 1;
            } else if(a < b) {
                return -1;
            }
        }
        
        return 0;
    }

    @Test
    public void test() {
        String version1 = "1.2";
        String version2 = "1.10";
        System.out.println(compareVersion(version1, version2));
    }
}
