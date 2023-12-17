package leetcode.interview150;

import org.junit.jupiter.api.Test;

import java.util.*;

public class Range {
    /**
     * 228. 汇总区间
     */
    public List<String> summaryRanges(int[] nums) {
        ArrayList<String> ans = new ArrayList<>();
        if(nums.length == 0) return ans;
        int pre = nums[0];
        for (int i = 1, len = 1; i < nums.length; i++) {
            if(nums[i] - pre != len){
                if(nums[i-1] == pre){
                    ans.add(String.valueOf(pre));
                } else {
                    ans.add(pre+"->"+nums[i-1]);
                }
                pre = nums[i];
                len = 1;
            } else {
                len++;
            }
        }

        if(pre == nums[nums.length-1]){
            ans.add(String.valueOf(pre));
        } else {
            ans.add(pre+"->"+nums[nums.length-1]);
        }

        return ans;
    }

    /**
     * 57. 插入区间
     */
    public int[][] insert(int[][] intervals, int[] newInterval) {
        ArrayList<int[]> list = new ArrayList<>();

        boolean flag = false;
        for (int i = 0; i < intervals.length; i++) {
            //这个区间在newInterval的左侧
            if(intervals[i][1] < newInterval[0]){
                list.add(intervals[i]);
            } else if(intervals[i][0] > newInterval[1]){
                if(!flag){
                    list.add(newInterval);
                    flag = true;
                }
                list.add(intervals[i]);
            } else {
                newInterval[0] = Math.min(newInterval[0], intervals[i][0]);
                newInterval[1] = Math.max(newInterval[1], intervals[i][1]);
            }
        }

        if(!flag){
            list.add(newInterval);
        }

        int[][] ans = new int[list.size()][];
        for (int i = 0; i < list.size(); i++) {
            ans[i] = list.get(i);
        }

        return ans;
    }

    /**
     * 452. 用最少数量的箭引爆气球
     * 用一个hash表保存交集的数量
     */
    public int findMinArrowShots(int[][] points) {
        Arrays.sort(points, (o1, o2) -> {
            if(o1[1] > o2[1]){
                return 1;
            } else if (o1[1] < o2[1]) {
                return -1;
            }else{
                return 0;
            }
        });

        int max = points[0][1];
        int ans = 1;
        for (int i = 1; i < points.length; i++) {
            if(points[i][0] > max){
                ans++;
                max = points[i][1];
            }
        }
        return ans;
    }

    @Test
    void test(){
//        summaryRanges(new int[]{0,1,2,4,5,7});
//        insert(new int[][]{new int[]{1,3}, new int[]{6,9}}, new int[]{2, 5});
//        findMinArrowShots(new int[][]{new int[]{10,16},new int[]{2,8},new int[]{1,6},new int[]{7,12}});
//        findMinArrowShots(new int[][]{new int[]{-2147483646,-2147483645},new int[]{2147483646,2147483647}});
    }
}
