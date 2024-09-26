package leetcode.questions.T3000.T2600.easy;

import java.util.ArrayList;
import java.util.HashSet;

public class Array {
    /**
     * 2549. 统计桌面上的不同数字
     */
    public int distinctIntegers(int n) {
        HashSet<Integer> set = new HashSet<Integer>();
        set.add(n);
        for (int i = 0; i < 1000000000; i++) {
            ArrayList<Integer> tmp = new ArrayList<Integer>();
            for (Integer e : set) {
                for (int j = 2; j < n; j++) {
                    if(!set.contains(j) && e % j == 1){
                        tmp.add(j);
                    }
                }
            }
            set.addAll(tmp);

            if(set.size() >= n-1){
                break;
            }
        }
        return set.size();
    }

    /**
     * 2535. 数组元素和与数字和的绝对差
     */
    public int differenceOfSum(int[] nums) {
        int sum1 = 0, sum2 = 0;
        for (int i = 0; i < nums.length; i++) {
            sum1 += nums[i];
            while (nums[i] > 0) {
                sum2 += nums[i] % 10;
                nums[i] /= 10;
            }
        }
        return Math.abs(sum1 - sum2);
    }
}
