package leetcode.questions.T4000.T3300.easy;

public class BitT {
    /*
     * 3226. 使两个整数相等的位更改次数
     * <14,13> 1110 1101 最后的0位无法变成1
     * 看相异的位置
     * 1110^1101=0011，这些相异的位置必须全部在前面那个数上1110&0011==0011，满足证明是可以的，然后相异结果的位数就是操作数
     */
    public int minChanges(int n, int k) {
        int tmp = n ^ k;
        return (n & tmp) == tmp ? Integer.bitCount(tmp) : -1;
    }

}
