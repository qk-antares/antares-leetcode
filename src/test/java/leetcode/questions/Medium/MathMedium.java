package leetcode.questions.Medium;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;

/**
 * @author Antares
 * @date 2022/9/29
 */
public class MathMedium {
    /**
     * 快乐数（答案解法：用Set记录计算过的sum，如果出现重复就证明进入死循环，不是isHappy）
     */
    class IsHappy {
        public boolean isHappy(int n) {
            HashSet<Integer> integers = new HashSet<>();
            int sum = 0;
            int temp = n;
            while (sum != 1){
                sum = 0;
                while (temp > 9){
                    int a = temp % 10;  //当前的个位
                    temp = temp / 10;
                    sum += a * a;
                }
                sum += temp * temp;

                if(!integers.add(sum))
                    return false;

                temp = sum;

            }
            return true;
        }
    }

    /**
     * 阶乘后的零（实际考察的就是数学，将阶乘的所有因数分解质因数，可以发现只有2*5可以得到0，而2的数量明显多于5，因此只需要计算质因数5的数量即可）
     * 哪些数可以分解出5呢？5，10，15，20，25（可以分解出两个5）
     */
    class TrailingZeroes {
        public int trailingZeroes(int n) {
            int ans = 0;
            while (n >= 5){
                ans += n / 5;
                n /= 5;
            }
            return ans;
        }
    }


    /**
     * Excel表列序号(可以看成是27进制)
     * AA 27
     * AZ 52
     * BA 53
     */
    class TitleToNumber {
        public int titleToNumber(String columnTitle) {
            int ans = 0;
            for(int i = columnTitle.length() - 1;i > -1 ;i--){
                ans += (columnTitle.charAt(i) - 'A' + 1) * Math.pow(26, columnTitle.length() - i - 1);
            }
            return ans;
        }
    }

    /**
     * Pow(x, n) （n可以是负数）
     */
    class MyPow {
        public double myPow(double x, int n) {
            if(n == 0)
                return 1;

            if(Math.abs(x-1.0) < Double.MIN_VALUE)
                return x;

            if(Math.abs(x+1.0) < Double.MIN_VALUE)
                return Math.abs(n) % 2 == 1 ? -1 : 1;

            if(n < 0) {
                x = 1.0/x;
            }

            //存放计算过的pow(x, n)
            HashMap<Integer, Double> computed = new HashMap<>();
            computed.put(1, x);

            int i = 1;
            long abs_n = Math.abs((long)n);
            double ans = x;
            while (i <= abs_n){
                ans *= ans;
                i += i;
                computed.put(i, ans);
                if(ans < Double.MIN_VALUE)
                    return ans;
            }
            int temp = i;
            //此时i超了
            while (i != abs_n){
                while (i - temp < abs_n){
                    temp /= 2;
                }
                ans /= computed.get(temp);
                i -= temp;
            }

            return ans;
        }

        /**
         * 答案解法
         */
        public double myPow0(double x, int n) {
            double res = 1.0;
            for (int i = n; i != 0; i /= 2) {
                if (i % 2 != 0) {
                    res *= x;
                }
                x *= x;
            }
            return n < 0 ? 1 / res : res;
        }
    }

    /**
     * x的平方根（的整数部分，解法：二分搜索，将long的mid强转为int比将int的mid强转为long节省很多时间）
     */
    class MySqrt {
        public int mySqrt(int x) {
            int left = 0,right = x;
            long mid;
            long temp;
            while (left <= right){
                mid = left + (right - left) / 2;
                temp = mid * mid;
                if(temp > x){
                    right = (int)mid-1;
                }
                else if(temp < x){
                    temp += 2 * mid + 1;
                    if(temp > x)
                        return (int)mid;
                    else
                        left = (int)mid + 1;
                }else{
                    return (int)mid;
                }
            }
            return -1;
        }
    }


    @Test
    public void invoke(){
        //new IsHappy().isHappy(19);
//        new MyPow().myPow(2.00000, -2147483648);
//        new MySqrt().mySqrt(2147483647);
        new TrailingZeroes().trailingZeroes(59);
    }

}
