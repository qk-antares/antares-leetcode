package leetcode.math.numbertheory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
 * 
 */
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
     * 1015. 可被 K 整除的最小整数 [Medium]
     *
     * 用x记录当前的余数，初始x=1%k
     * 用seen记录出现过的余数(显然seen.size()<k)
     * 每次更新x=(x*10+1)%k，直到x=0或者x在seen中出现过
     * 
     * 如果x=0，说明找到了，返回seen.size()+1
     * 如果x>0，说明进入了循环，返回-1
     */
    public int smallestRepunitDivByK(int k) {
        Set<Integer> seen = new HashSet<Integer>();
        // x代表余数
        int x = 1 % k;
        while (x > 0 && seen.add(x)) {
            x = (x * 10 + 1) % k;
        }
        return x > 0 ? -1 : seen.size() + 1;
    }

    /*
     * ========================== 分割线 ==========================
     */

    /*
     * 1018. 可被 5 整除的二进制前缀 [Easy]
     * 
     * 诸位构造出当前二进制对应的数字，然后对5取模即可
     */
    public List<Boolean> prefixesDivBy5(int[] nums) {
        List<Boolean> ans = new ArrayList<>();
        int cur = 0;
        for (int i = 0; i < nums.length; i++) {
            cur = ((cur << 1) + nums[i]) % 5;
            if (cur != 0)
                ans.add(false);
            else
                ans.add(true);
        }
        return ans;
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
