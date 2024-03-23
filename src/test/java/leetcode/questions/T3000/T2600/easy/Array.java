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
}
