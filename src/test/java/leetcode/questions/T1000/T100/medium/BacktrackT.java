package leetcode.questions.T1000.T100.medium;

import org.junit.jupiter.api.Test;

import java.util.*;

public class BacktrackT {
    /**
     * 全排列 II
     */
    public List<List<Integer>> permuteUnique(int[] nums) {
        ArrayDeque<Integer> path = new ArrayDeque<>();
        List<List<Integer>> ans = new ArrayList<>();
        int len = 0;
        HashMap<Integer, Integer> cntMap = new HashMap<>();

        len = nums.length;
        for (int num : nums) {
            cntMap.put(num, cntMap.getOrDefault(num, 0)+1);
        }
        backtrack(path, ans, len, cntMap);
        return ans;
    }

    public void backtrack(ArrayDeque<Integer> path, List<List<Integer>> ans, int len, HashMap<Integer, Integer> cntMap){
        if(path.size() == len){
            ans.add(new ArrayList<>(path));
            return;
        }

        for (Integer i : cntMap.keySet()) {
            Integer cnt = cntMap.get(i);
            if(cnt != 0){
                path.addLast(i);
                cntMap.put(i, cnt-1);

                backtrack(path, ans, len, cntMap);

                path.removeLast();
                cntMap.put(i, cnt);
            }
        }
    }

    public List<List<Integer>> permuteUnique0(int[] nums) {
        int len = nums.length;
        boolean[] vis = new boolean[len];
        ArrayDeque<Integer> path = new ArrayDeque<>();
        List<List<Integer>> ans = new ArrayList<>();
        int cur = 0;

        Arrays.sort(nums);
        backtrack0(nums, vis, path, ans, cur);

        return ans;
    }

    public void backtrack0(int[] nums, boolean[] vis, ArrayDeque<Integer> path, List<List<Integer>> ans, int cur){
        if(cur == nums.length) {
            ans.add(new ArrayList<>(path));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            //节点已经在path里了 || 【同一层】不重复考虑同一元素
            if(vis[i] || (i != 0 && nums[i] == nums[i-1] && !vis[i-1])){
                continue;
            }

            path.addLast(nums[i]);
            vis[i] = true;
            backtrack0(nums, vis, path, ans, cur+1);
            path.removeLast();
            vis[i] = false;
        }
    }



    @Test
    void test(){
        permuteUnique(new int[]{1,1,2});
    }
}
