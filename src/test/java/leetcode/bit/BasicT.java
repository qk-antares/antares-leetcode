package leetcode.bit;

public class BasicT {
    /*
     * 3370. 仅含置位位的最小整数 [Easy]
     * 
     * Integer.numberOfLeadingZeros(n) 返回 n 的二进制表示中前导零的个数
     */
    public int smallestNumber0(int n) {
        boolean flag = false;
        for(int i = 31; i >= 0; i--) {
            int andVal = 1 << i; 
            if(flag && (andVal & n) == 0) {
                n |= andVal;
            } else if(!flag && (andVal & n) > 0) {
                flag = true;
            }
        }
        return n;
    }

    public int smallestNumber(int n) {
        n = 32 - Integer.numberOfLeadingZeros(n);
        return (1 << n) - 1;
    }
}