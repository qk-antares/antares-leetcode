package leetcode.questions.T1000.T300;

public class BitT {
    /*
     * 201. 数字范围按位与  [Medium]
     * 
     * [m,n]的范围与等价于求m和n的最大二进制前缀，后面补零
     * 
     * m: 00001001
     * n: 00001100
     * 
     * 例如对于这样的一个范围，最大的二进制前缀为00001，而由于范围是连续的，后面的部分必然会在与的过程中变成0
     * 
     * 求最大二进制前缀的过程相当于将m和n一直右移，直到m和n相等，然后再往左移动即可
     */
    public int rangeBitwiseAnd(int left, int right) {
        int shift = 0;
        while (left != right) {
            left >>= 1;
            right >>= 1;
            shift++;
        }
        return left << shift;
    }
}
