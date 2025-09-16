package leetcode.datastruture.stk;

import java.util.ArrayList;
import java.util.List;

/*
 * 临项消除
 */
public class NbEliminate {
    /*
     * 2197. 替换数组中的非互质数 [Hard]
     */
    public List<Integer> replaceNonCoprimes(int[] nums) {
        List<Integer> ans = new ArrayList<>();
        for (int num : nums) {
            while (!ans.isEmpty() && gcd(num, ans.getLast()) != 1) {
                num = lcm(num, ans.removeLast());
            }
            ans.add(num);
        }
        return ans;
    }

    int gcd(int a, int b) {
        while (a != 0) {
            int tmp = a;
            a = b % a;
            b = tmp;
        }
        return b;
    }

    int lcm(int a, int b) {
        return a / gcd(a, b) * b;
    }
}
