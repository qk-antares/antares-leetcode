package leetcode.questions.T3000.T2700.medium;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HashT {
    /*
     * 2610. 转换二维数组
     * 
     * 使用一个List<Integer, HashSet<Integer>>保存当前行的数字
     * 
     * 标准答案的解法是先遍历一遍nums，保存各个数字的出现次数，然后再创建List<List<Integer>>保存答案
     */
    public List<List<Integer>> findMatrix0(int[] nums) {
        List<HashSet<Integer>> ans = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            int len = ans.size();
            boolean flag = true;
            for (int j = 0; j < len; j++) {
                if (!ans.get(j).contains(nums[i])) {
                    ans.get(j).add(nums[i]);
                    flag = false;
                    break;
                }
            }
            if (flag) {
                HashSet<Integer> set = new HashSet<>();
                set.add(nums[i]);
                ans.add(set);
            }
        }

        return ans.stream().map(set -> new ArrayList<>(set)).collect(Collectors.toList());
    }

    public List<List<Integer>> findMatrix(int[] nums) {
        HashMap<Integer, Integer> cntMap = new HashMap<Integer, Integer>();
        int max = 0;
        for (int num : nums) {
            int cnt = cntMap.getOrDefault(num, 0) + 1;
            max = Math.max(max, cnt);
            cntMap.put(num, cnt);
        }
        List<List<Integer>> ans = new ArrayList<>();
        for (int i = 1; i <= max; i++) {
            ans.add(new ArrayList<>());
        }

        for (Map.Entry<Integer, Integer> entry : cntMap.entrySet()) {
            int cnt = entry.getValue();
            int num = entry.getKey();
            for (int i = 0; i < cnt; i++) {
                ans.get(i).add(num);
            }
        }

        return ans;
    }
}
