package leetcode.questions.T1000.T100.medium;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;

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

    /**
     * 77. 组合
     */
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> ans = new ArrayList<>();
        boolean[] visited = new boolean[n];

        Deque<Integer> path = new ArrayDeque<>();
        backtrackCombine(ans, visited, path, n, k, 0);
        return ans;
    }

    public void backtrackCombine(List<List<Integer>> ans, boolean[] visited, Deque<Integer> path, int n, int k, int start){
        if(path.size() == k){
            ans.add(new ArrayList<>(path));
            return;
        }

        for (int i = start; i < n; i++) {
            path.addLast(i+1);
            visited[i] = true;
            backtrackCombine(ans, visited, path, n, k, i+1);
            visited[i] = false;
            path.removeLast();
        }
    }

    /**
     * 93. 复原 IP 地址
     */
    public List<String> restoreIpAddresses(String s) {
        ArrayList<String> ans = new ArrayList<String>();
        int[] segments = new int[4];

        if(s.length() > 12){
            return ans;
        }
        
        backtrackIp(s, ans, segments, 0, 0);
        return ans;
    }

    public void backtrackIp(String s, List<String> ans, int[] segments, int segId, int startIndex){
        //遍历完
        if(segId == 4){
            if(startIndex == s.length()){
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < segments.length; i++) {
                    sb.append(segments[i]);
                    if(i != 3){
                        sb.append('.');
                    }
                }
                ans.add(sb.toString());
            }
            return;
        }

        //遍历完了，但没凑够4段
        if(startIndex == s.length()){
            return;
        }
        
        //前导0
        if(s.charAt(startIndex) == '0'){
            segments[segId] = 0;
            backtrackIp(s, ans, segments, segId+1, startIndex+1);
            return;
        }

        //遍历所有可能
        int cur = 0;
        for (int i = 0; i < 3 && startIndex+i < s.length(); i++) {
            cur = cur * 10 + (s.charAt(startIndex+i) - '0');
            if(cur <= 255) {
                segments[segId] = cur;
                backtrackIp(s, ans, segments, segId+1, startIndex+i+1);
            } else {
                continue;
            }
        }
    }

    /**
     * 90. 子集 II
     */
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        ArrayList<Integer> curPath = new ArrayList<Integer>();
        Arrays.sort(nums);

        dfs(ans, curPath, false, nums, 0);
        return ans;
    }

    public void dfs(List<List<Integer>> ans, ArrayList<Integer> curPath, boolean choosePre, int[] nums, int curIndex){
        if(curIndex == nums.length) {
            ans.add(new ArrayList<>(curPath));
            return;
        }
        
        //不选择的这层dfs必须位于前面
        dfs(ans, curPath, false, nums, curIndex+1);

        if(curIndex > 0 && !choosePre && nums[curIndex]==nums[curIndex-1]){
            return;
        }

        curPath.add(nums[curIndex]);
        dfs(ans, curPath, true, nums, curIndex+1);
        curPath.remove(curPath.size()-1);
    }

    @Test
    void test(){
        // permuteUnique(new int[]{1,1,2});
        // restoreIpAddresses("0000");
        subsetsWithDup(new int[]{1,2,2});
    }
}
