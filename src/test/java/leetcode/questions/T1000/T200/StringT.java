package leetcode.questions.T1000.T200;

import java.util.Arrays;
import java.util.Comparator;

import org.junit.jupiter.api.Test;

public class StringT {
    /*
     * 168. Excel 表列名称  [Easy]
     */
    public String convertToTitle(int columnNumber) {
        StringBuilder ans = new StringBuilder();
        while (columnNumber != 0) {
            columnNumber -= 1;
            ans.append((char)('A' + columnNumber % 26 ));
            columnNumber /= 26;
        }

        return ans.reverse().toString();
    }

    /*
     * ========================== 分割线 ==========================
     */

    /*
     * 165. 比较版本号  [Medium]
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

    /*
     * 179. 最大数  [Medium]
     */
    public String largestNumber(int[] nums) {
        String[] arr = new String[nums.length];
        for (int i = 0; i < nums.length; i++) {
            arr[i] = String.valueOf(nums[i]);
        }

        Arrays.sort(arr, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return (o2+o1).compareTo(o1+o2);
            }
        });

        if(arr[0].equals("0")){
            return "0";
        }

        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            ans.append(arr[i]);
        }
        return ans.toString();
    }

    @Test
    public void test(){
        convertToTitle(701);
    }
}
