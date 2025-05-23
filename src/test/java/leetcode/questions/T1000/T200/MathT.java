package leetcode.questions.T1000.T200;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

public class MathT {
    /**
     * 149. 直线上最多的点数    [Medium]
     * 
     * 假设有n个点，则用两层循环
     * for (int i = 0; i < n; i++) {
     * HashMap保存某个斜率的直线数
     * for (int j = i + 1; j < n; j++) {
     * // 两个点形成直线的斜率
     * int slope;
     * }
     * 遍历HashMap来记录当前的答案
     * }
     * 需要注意的是斜率的保存：
     * 1、可以用一个二元组（dx，dy）来保存斜率
     * 2、需要统一正负，例如dy应该始终是正的
     * 3、对于dx=0或dy=0的情况，需要单独处理（设置dy=1或dx=1）
     * 4、应该将（dx，dy）化成最简，也即除以最大公约数
     * 5、假设化简后的二元组是（mx，my），可以用一个数字来保存斜率
     * slope=mx+(2*10^4+1)*my，这是因为mx和my一定在[-10^4,10^4]的范围内
     * 
     * 还需要注意的是最大公约数的求法（欧几里得算法）
     * 欧几里得算法基于以下原理：两个整数的最大公约数等于其中较小的数和两数相除余数的最大公约数。
     */
    public int maxPoints(int[][] points) {
        int n = points.length;
        if (n <= 2)
            return n;

        int ans = 0;

        for (int i = 0; i < n; i++) {
            HashMap<Integer, Integer> map = new HashMap<>();
            for (int j = i + 1; j < n; j++) {
                int dx = points[i][0] - points[j][0];
                int dy = points[i][1] - points[j][1];
                if (dx == 0) {
                    dy = 1;
                } else if (dy == 0) {
                    dx = 1;
                } else {
                    if (dy < 0) {
                        dx = -dx;
                        dy = -dy;
                    }
                    int gcd = gcd(Math.abs(dx), Math.abs(dy));
                    dx /= gcd;
                    dy /= gcd;
                }

                map.put(dx * 20001 + dy, map.getOrDefault(dx * 20001 + dy, 0) + 1);
            }

            for (Integer value : map.values()) {
                ans = Math.max(ans, value + 1);
            }
        }

        return ans;
    }

    public int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    /*
     * ========================== 分割线 ==========================
     */

    /*
     * 166. 分数到小数 [Hard]
     */
    public String fractionToDecimal(int numerator, int denominator) {
        // 转换成long防止溢出
        long numeratorL = numerator;
        long denominatorL = denominator;

        HashMap<Long, Integer> map = new HashMap<>();
        StringBuilder ans = new StringBuilder();
        // 做负号处理
        if (numeratorL * denominatorL < 0) {
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
