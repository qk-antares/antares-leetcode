package leetcode.math.numbertheory;

public class OtherT {
    /*
     * 326. 3 的幂 [Easy]
     * 
     * 在Integer的范围内，所有3的幂一定是最大的3^x的因子
     */
    public boolean isPowerOfThree0(int n) {
        if (n < 0)
            return false;
        while (n > 1) {
            if (n % 3 != 0)
                return false;
            n /= 3;
        }
        return n == 1;
    }

    public boolean isPowerOfThree(int n) {
        return n > 0 && 1162261467 % n == 0;
    }

    /*
     * 1780. 判断一个数字是否可以表示成三的幂的和
     * 
     * 假设n=3^x1+3^x2..好了
     * 首先n%3：如果=0或者=1，n/3；否则返回false
     * 重复上述循环，直至n=0
     */
    public boolean checkPowersOfThree(int n) {
        while (n > 0) {
            int mod = n % 3;
            if (mod == 0 || mod == 1) {
                n /= 3;
            } else {
                return false;
            }
        }
        return true;
    }
}
