package leetcode.questions.T1000.T100;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class MathT {
    /**
     * 9. 回文数 [Easy]
     */
    public boolean isPalindrome(int x) {
        if (x < 0) {
            return false;
        }
        StringBuilder sb = new StringBuilder();
        while (x > 0) {
            sb.append(x % 10);
            x = x / 10;
        }
        int i = 0, j = sb.length() - 1;
        while (i <= j) {
            if (sb.charAt(i) != sb.charAt(j)) {
                return false;
            }
            i++;
            j--;
        }
        return true;
    }

    /**
     * 67. 二进制求和 [Easy]
     */
    public String addBinary(String a, String b) {
        StringBuilder sb = new StringBuilder();
        int i = a.length() - 1, j = b.length() - 1;

        int add = 0;
        while (i >= 0 && j >= 0) {
            // 都是1
            if (((a.charAt(i) - '0') & (b.charAt(j) - '0')) == 1) {
                sb.append(add);
                add = 1;
            } else if (((a.charAt(i) - '0') ^ (b.charAt(j) - '0')) == 1) {
                // 一个1一个0
                if (add == 1) {
                    sb.append(0);
                    add = 1;
                } else {
                    sb.append(1);
                }
            } else {
                // 都是0
                sb.append(add);
                add = 0;
            }
            i--;
            j--;
        }

        while (i >= 0) {
            // 都是1
            if ((add & (a.charAt(i) - '0')) == 1) {
                sb.append(0);
            } else if ((add ^ (a.charAt(i) - '0')) == 1) {
                sb.append(1);
                add = 0;
            } else {
                sb.append(0);
            }
            i--;
        }

        while (j >= 0) {
            // 都是1
            if ((add & (b.charAt(j) - '0')) == 1) {
                sb.append(0);
            } else if ((add ^ (b.charAt(j) - '0')) == 1) {
                sb.append(1);
                add = 0;
            } else {
                sb.append(0);
            }
            j--;
        }

        if (add != 0) {
            sb.append(1);
        }

        return sb.reverse().toString();
    }

    /*
     * ========================== 分割线 ==========================
     */

    /**
     * 29. 两数相除 [Medium]
     */
    public int divide(int dividend, int divisor) {
        if (divisor == -1 && dividend == Integer.MIN_VALUE)
            return Integer.MAX_VALUE;

        int sign = 1;
        if (dividend > 0 && divisor < 0 || dividend < 0 && divisor > 0) {
            sign = -1;
        }

        dividend = dividend > 0 ? -dividend : dividend;
        divisor = divisor > 0 ? -divisor : divisor;

        return sign == 1 ? div(dividend, divisor) : -div(dividend, divisor);
    }

    public int div(int a, int b) {
        if (a > b)
            return 0;
        int count = 1;
        int tb = b;
        while ((tb << 1) > a && (tb << 1) < 0) {
            tb <<= 1;
            count <<= 1;
        }
        return count + div(a - tb, b);
    }

    /**
     * 31. 下一个排列 [Medium]
     * 思路：
     * 要把后面的大数与前面的小数交换
     * 要尽可能在低位进行交换
     * 参与交换的大数要尽可能小
     * 交换后要将大数后面的序列重排列成升序
     */
    public void nextPermutation(int[] nums) {
        // 首先从后往前遍历，找到第一个升序
        int len = nums.length;
        int end = len - 1;
        while (end - 1 >= 0 && nums[end - 1] > nums[end])
            end--;
        if (end == 0)
            reverse(nums, 0, len - 1);
        else {
            int minIndex = end - 1;
            int maxIndex = len - 1;
            while (nums[maxIndex] <= nums[minIndex]) {
                maxIndex--;
            }

            int tmp = nums[maxIndex];
            nums[maxIndex] = nums[minIndex];
            nums[minIndex] = tmp;

            Arrays.sort(nums, minIndex + 1, len);
        }
    }

    public void reverse(int[] nums, int start, int end) {
        while (start < end) {
            int tmp = nums[start];
            nums[start] = nums[end];
            nums[end] = tmp;
            start++;
            end--;
        }
    }

    /**
     * 43. 字符串相乘 [Medium]
     */
    public String multiply(String num1, String num2) {
        if (num1.equals("0") || num2.equals("0")) {
            return "0";
        }

        int len = num2.length();
        StringBuilder s0 = multiplyA(num1, num2.charAt(len - 1));
        for (int i = len - 2; i >= 0; i--) {
            StringBuilder s1 = multiplyA(num1, num2.charAt(i));
            for (int j = 0; j < len - i - 1; j++) {
                s1.append(0);
            }
            s0 = sum(s0, s1);
        }

        return s0.toString();
    }

    public StringBuilder multiplyA(String num1, char a) {
        int len = num1.length();
        StringBuilder ans = new StringBuilder();

        int add = 0, mul;
        for (int i = len - 1; i >= 0; i--) {
            mul = (num1.charAt(i) - '0') * (a - '0') + add;
            ans.append((mul) % 10);
            add = mul / 10;
        }

        if (add != 0) {
            ans.append(add);
        }

        return ans.reverse();
    }

    public StringBuilder sum(StringBuilder num1, StringBuilder num2) {
        StringBuilder ans = new StringBuilder();

        int i = num1.length() - 1, j = num2.length() - 1;
        int add = 0, mul;
        while (i >= 0 && j >= 0) {
            mul = (num1.charAt(i) - '0') + (num2.charAt(j) - '0') + add;
            ans.append((mul) % 10);
            add = mul / 10;
            i--;
            j--;
        }
        int sum;
        while (i >= 0) {
            sum = add + num1.charAt(i) - '0';
            ans.append(sum % 10);
            add = sum / 10;
            i--;
        }
        while (j >= 0) {
            sum = add + num2.charAt(j) - '0';
            ans.append(sum % 10);
            add = sum / 10;
            j--;
        }

        if (add != 0) {
            ans.append(add);
        }

        return ans.reverse();
    }

    /*
     * ========================== 分割线 ==========================
     */

    /*
     * 60. 排列序列 [Hard]
     */
    boolean[] visited;
    int[] factorial;

    public String getPermutation(int n, int k) {
        visited = new boolean[n];
        factorial = new int[10];
        factorial[0] = 1;
        for (int i = 1; i <= n; i++) {
            factorial[i] = factorial[i - 1] * i;
        }

        StringBuilder sb = new StringBuilder();
        k--;
        for (int i = 0; i < n; i++) {
            int order = k / factorial[n - i - 1] + 1;
            k %= factorial[n - i - 1];

            int cur = 0;
            while (order > 0) {
                if (!visited[cur]) {
                    order--;
                }
                cur++;
            }
            visited[cur - 1] = true;
            sb.append(cur);
        }

        return sb.toString();
    }

    @Test
    void test() {
        // isPalindrome(10);
        addBinary("1010", "1011");
    }
}
