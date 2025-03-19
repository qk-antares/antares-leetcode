package leetcode.questions.T3000.T2300.easy;

import org.junit.jupiter.api.Test;

public class SlideWindowT {
    /*
     * 2269. 找到一个数字的 K 美丽值
     * 
     * 标准的解法是将num转为string
     */
    public int divisorSubstrings0(int num, int k) {
        int tmp = num;
        int cur = 0;
        int pow = 1;
        while (k > 0) {
            cur += (tmp % 10) * pow;
            tmp /= 10;
            k--;
            pow *= 10;
        }

        pow /= 10;
        int ans = 0;
        while (tmp > 0) {
            if(cur != 0 && num % cur == 0) {
                ans++;
            }
            cur = (tmp % 10) * pow + cur / 10;
            tmp /= 10;
        }

        if(cur != 0 && num % cur == 0) {
            ans++;
        }
        cur = (tmp % 10) * pow + cur / 10;
        tmp /= 10;

        return ans;
    }

    public int divisorSubstrings(int num, int k) {
        String str = String.valueOf(num);
        int ans = 0;
        while (tmp > 0) {
            if(cur != 0 && num % cur == 0) {
                ans++;
            }
            cur = (tmp % 10) * pow + cur / 10;
            tmp /= 10;
        }

        if(cur != 0 && num % cur == 0) {
            ans++;
        }
        cur = (tmp % 10) * pow + cur / 10;
        tmp /= 10;

        return ans;
    }

    @Test
    public void test() {
        System.out.println(divisorSubstrings(430043, 2));
    }
}
