package leetcode.questions.T1000.T100;

public class BinarySearchT {
    /*
     * 69. x 的平方根 [Easy]
     */
    public int mySqrt(int x) {
        long l = 1, r = x;
        while (l <= r) {
            long mid = (l + r) / 2;
            long square = mid * mid;
            if (square == x)
                return (int) mid;
            else if (square > x)
                r = mid - 1;
            else
                l = mid + 1;
        }
        return (int) r;
    }
}
