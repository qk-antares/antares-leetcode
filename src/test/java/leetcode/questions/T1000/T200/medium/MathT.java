package leetcode.questions.T1000.T200.medium;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

public class MathT {
    /*
     * 166. 分数到小数
     */
    public String fractionToDecimal(int numerator, int denominator) {
        //转换成long防止溢出
        long numeratorL = numerator;
        long denominatorL = denominator;

        HashMap<Long, Integer> map = new HashMap<>();
        StringBuilder ans = new StringBuilder();
        //做负号处理
        if(numeratorL * denominatorL < 0) {
            ans.append('-');
        }
        numeratorL = Math.abs(numeratorL);
        denominatorL = Math.abs(denominatorL);

        long a = numeratorL / denominatorL;
        long b = numeratorL % denominatorL;
        ans.append(a);
        if (b == 0)
            return ans.toString();
        ans.append('.');

        StringBuilder tmp = new StringBuilder();
        int index = 0;
        while (b != 0) {
            // 处理循环节的位置
            if (map.containsKey(b)) {
                return ans.append(tmp.substring(0, map.get(b)))
                        .append('(').append(tmp.substring(map.get(b))).append(')').toString();
            }
            map.put(b, index);
            index++;
            b *= 10;
            a = b / denominatorL;
            b = b % denominatorL;
            tmp.append(a);
        }

        return ans.append(tmp).toString();
    }

    @Test
    public void test() {
        System.out.println(fractionToDecimal(-1, -2147483648));
    }
}
