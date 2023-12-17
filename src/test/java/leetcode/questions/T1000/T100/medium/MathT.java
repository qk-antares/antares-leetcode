package leetcode.questions.T1000.T100.medium;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class MathT {
    /**
     * 两数相除
     */
    public int divide(int dividend, int divisor) {
        if(divisor == -1 && dividend == Integer.MIN_VALUE) return Integer.MAX_VALUE;

        int sign = 1;
        if(dividend > 0 && divisor < 0 || dividend < 0 && divisor > 0) {
            sign = -1;
        }

        dividend = dividend > 0 ? -dividend : dividend;
        divisor = divisor > 0 ? -divisor : divisor;

        return sign == 1 ? div(dividend, divisor) : -div(dividend, divisor);
    }

    public int div(int a, int b){
        if(a > b) return 0;
        int count = 1;
        int tb = b;
        while ((tb << 1) > a && (tb << 1) < 0) {
            tb <<= 1;
            count <<= 1;
        }
        return count + div(a-tb, b);
    }

    /**
     * 31. 下一个排列
     * 思路：
     * ①要把后面的大数与前面的小数交换
     * ②要尽可能在低位进行交换
     * ③参与交换的大数要尽可能小
     * ④交换后要将大数后面的序列重排列成升序
     */
    public void nextPermutation(int[] nums) {
        //首先从后往前遍历，找到第一个升序
        int len = nums.length;
        int end = len - 1;
        while (end-1 >= 0 && nums[end-1] > nums[end]) end--;
        if(end == 0) reverse(nums, 0, len-1);
        else {
            int minIndex = end-1;
            int maxIndex = len-1;
            while (nums[maxIndex] <= nums[minIndex] ){
                maxIndex--;
            }

            int tmp = nums[maxIndex];
            nums[maxIndex] = nums[minIndex];
            nums[minIndex] = tmp;

            Arrays.sort(nums, minIndex+1, len);
        }
    }

    public void reverse(int[] nums, int start, int end){
        while (start < end){
            int tmp = nums[start];
            nums[start] = nums[end];
            nums[end] = tmp;
            start++;end--;
        }
    }

    /**
     * 43. 字符串相乘
     */
    public String multiply(String num1, String num2) {
        if(num1.equals("0") || num2.equals("0")){
            return "0";
        }

        int len = num2.length();
        StringBuilder s0 = multiplyA(num1, num2.charAt(len-1));
        for (int i = len-2;i >= 0;i--){
            StringBuilder s1 = multiplyA(num1, num2.charAt(i));
            for (int j = 0; j < len-i-1; j++) {
                s1.append(0);
            }
            s0 = sum(s0, s1);
        }

        return s0.toString();
    }

    public StringBuilder multiplyA(String num1, char a){
        int len = num1.length();
        StringBuilder ans = new StringBuilder();

        int add = 0, mul;
        for (int i = len-1; i >= 0; i--) {
            mul = (num1.charAt(i) - '0') * (a - '0') + add;
            ans.append((mul) % 10);
            add = mul / 10;
        }

        if(add != 0){
            ans.append(add);
        }

        return ans.reverse();
    }

    public StringBuilder sum(StringBuilder num1, StringBuilder num2){
        StringBuilder ans = new StringBuilder();

        int i = num1.length()-1, j = num2.length()-1;
        int add = 0, mul;
        while (i >= 0 && j >= 0){
            mul = (num1.charAt(i) - '0') + (num2.charAt(j) - '0') + add;
            ans.append((mul) % 10);
            add = mul / 10;
            i--;j--;
        }
        int sum;
        while (i >= 0){
            sum = add + num1.charAt(i) - '0';
            ans.append(sum%10);
            add = sum/10;
            i--;
        }
        while (j >= 0){
            sum = add + num2.charAt(j) - '0';
            ans.append(sum%10);
            add = sum/10;
            j--;
        }

        if(add != 0){
            ans.append(add);
        }

        return ans.reverse();
    }

    /**
     *
     */

    @Test
    void test(){
        multiply("123456789", "987654321");
    }
}
