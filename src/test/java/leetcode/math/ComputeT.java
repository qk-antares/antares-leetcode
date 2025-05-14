package leetcode.math;

/*
 * 数学计算
 */
public class ComputeT {
    /*
     * 50. Pow(x, n)    [Medium]    <Star>
     * 
     * 快速幂
     * 考虑我们计算 x^8的过程：
     * x -> x^2 -> x^4 -> x^8
     * 再考虑我们是如何计算x^15的：
     * x * x^2 * x^4 * x^8
     * 
     * 这里的规律在于，我们不是逐次地乘以x，而是将x的幂次分解成2的幂次之和
     * 这样可以在logn的时间内计算出答案
     * 
     * ans具体是否乘以某个x的幂次，取决于n的二进制表示
     */
    public double myPow(double x, int n) {
        if(n == 0) return 1.0;
        long N = n;
        if(N < 0) {
            N = -N;
            x = 1.0 / x;
        }

        double ans = 1.0;
        while(N != 0) {
            if((N & 1) == 1) ans *= x;
            x *= x;
            N >>= 1;
        }

        return ans;
    }

}
