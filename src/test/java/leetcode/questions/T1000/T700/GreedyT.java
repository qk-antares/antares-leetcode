package leetcode.questions.T1000.T700;

import java.util.List;

public class GreedyT {
    /*
     * 624. 数组列表中的最大距离    [Medium]
     * 
     * 原始解法（O(n^2)超时）
     * 两两比较数组的最小值和最大值，最终返回结果
     * 
     * 单次扫描（O(n)）
     * 无需两两比较数组
     * 在遍历数组时，记录已遍历过的数组的最大值max和最小值min
     * 则假设再遍历到一个数组arr，则最大间距可能是
     * ans = Math.max(ans, Math.abs(max - arr.getFirst()));
     * ans = Math.max(ans, Math.abs(min - arr.getLast()));
     * 
     * 之后更新max和min
     */
    public int maxDistance0(List<List<Integer>> arrays) {
        int ans = Integer.MIN_VALUE;
        int len = arrays.size();
        for (int i = 0; i < len; i++) {
            int a = arrays.get(i).get(0);
            for (int j = 0; j < len; j++) {
                if(i == j) continue;
                List<Integer> arr = arrays.get(j);
                ans = Math.max(ans, Math.abs(a - arr.get(arr.size()-1)));
            }
        }
        return ans;
    }
    
    public int maxDistance(List<List<Integer>> arrays) {
        int ans = 0, min = arrays.get(0).getFirst(), max = arrays.get(0).getLast();
        int len = arrays.size();
        for (int i = 1; i < len; i++) {
            List<Integer> arr = arrays.get(i);
            int l = arr.getFirst(), r = arr.getLast();
            ans = Math.max(ans, Math.abs(max - l));
            ans = Math.max(ans, Math.abs(min - r));
            min = Math.min(min, l);
            max = Math.max(max, r);
        }
        return ans;
    }
}
