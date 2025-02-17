package leetcode.questions.T1000.T200.medium;

import org.junit.jupiter.api.Test;

public class StringT {
    /*
     * 165. 比较版本号
     */
    public int compareVersion(String version1, String version2) {
        String[] arr1 = version1.split("\\.");
        String[] arr2 = version2.split("\\.");
        int len = Math.max(arr1.length, arr2.length);
        for (int i = 0; i < len; i++) {
            int num1 = i < arr1.length ? Integer.parseInt(arr1[i]) : 0;
            int num2 = i < arr2.length ? Integer.parseInt(arr2[i]) : 0;
            if (num1 > num2) {
                return 1;
            } else if (num1 < num2) {
                return -1;
            }
        }
        return 0;
    }

    @Test
    public void test() {
        System.out.println(compareVersion("1.01", "1.001"));
    }
}
