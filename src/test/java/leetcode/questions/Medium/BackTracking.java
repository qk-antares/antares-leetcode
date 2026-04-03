package leetcode.questions.Medium;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * @author Antares
 * @date 2022/9/23
 */
public class BackTracking {
    /**
     * 全排列（我的解法）
     */
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        Deque<Integer> path = new ArrayDeque<>();
        boolean[] visited = new boolean[nums.length];

        backtrack1(nums, visited, path, ans);
        return ans;
    }

    public void backtrack1(int[] nums, boolean[] visited, Deque<Integer> path, List<List<Integer>> ans){
        if(path.size() == nums.length){
            ArrayList<Integer> res = new ArrayList<>(nums.length);
            for (Integer integer : path) {
                res.add(integer);
            }
            ans.add(res);
            return;
        }

        //进行选择
        for(int i = 0;i < nums.length;i++){
            if(!visited[i]){
                path.addLast(nums[i]);
                visited[i] = true;
                backtrack1(nums, visited, path, ans);
                path.removeLast();
                visited[i] = false;
            }
        }
    }

    /**
     * 答案解法：（还是那个思路，但是代码进行了优化，不使用双端队列和visited数组，而是直接用List来存储路径）
     */
    public List<List<Integer>> permute0(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        backtrack2(nums, path, ans);
        return ans;
    }

    public void backtrack2(int[] nums, List<Integer> path, List<List<Integer>> ans){
        if(path.size() == nums.length){
            ArrayList<Integer> res = new ArrayList<>(path);
            ans.add(res);
            return;
        }

        //进行选择
        for(int i = 0;i < nums.length;i++){
            if(path.contains(nums[i]))
                continue;
            path.add(nums[i]);
            backtrack2(nums, path, ans);
            path.remove(path.size()-1);
        }
    }


    @Test
    public void invoke(){
//        permute(new int[]{1,2,3});
    }
}
