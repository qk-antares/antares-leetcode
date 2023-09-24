package leetcode.interview150;

import org.junit.jupiter.api.Test;

import java.util.*;

public class ArrayLearn {
    /**
     * 45. 跳跃游戏 II
     * 使用bfs，检索到答案就返回
     */
    public int jump(int[] nums) {
        if(nums.length == 1){
            return 0;
        }

        Queue<Integer> queue = new ArrayDeque<Integer>();
        boolean[] vis = new boolean[nums.length];
        //初始在0的位置
        queue.offer(0);
        vis[0] = true;
        int ans = 0;
        while (!queue.isEmpty()){
            int size = queue.size();
            ans++;
            for(int i = 0;i < size;i++){
                Integer poll = queue.poll();
                if(poll + nums[poll] >= nums.length-1){
                    return ans;
                } else {
                    for(int j = poll+1;j <= poll+nums[poll];j++){
                        if(!vis[j]){
                            vis[j] = true;
                            queue.offer(j);
                        }
                    }
                }
            }
        }
        return -1;
    }

    /**
     * 45. 跳跃游戏 II
     * 答案解法：一直记录每次跳跃的范围
     */
    public int jump0(int[] nums) {
        if(nums.length == 1) return 0;

        int low = 0, high = 0, ans = 0;
        //还未到达
        while (high < nums.length-1){
            int tmpHigh = high;
            for(int i = low;i <= high;i++){
                tmpHigh = Math.max(tmpHigh, i+nums[i]);
            }
            high = tmpHigh;
            ans++;
        }
        return ans;
    }

    /**
     * 274. H 指数
     */
    public int hIndex(int[] citations) {
        Arrays.sort(citations);
        //倒着来
        for(int i = citations.length-1;i >= 0;i--){
            if(citations[i] < citations.length-i){
                return citations.length-i-1;
            }
        }
        return Math.min(citations.length, citations[0]);
    }

    /**
     * 134. 加油站
     */
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int cur, pos;
        int i = 0, n = gas.length;
        while (i < n){
            cur = 0;
            int j = 0;
            while (j < n){
                pos = (i+j)%gas.length;
                cur += gas[pos] - cost[pos];
                if(cur < 0){
                    break;
                }
                j++;
            }
            if(cur >= 0){
                return i;
            }
            i=i+j+1;
        }

        return -1;
    }

    /**
     * 135. 分发糖果
     */
    public int candy(int[] ratings) {
        int n = ratings.length;

        int[] left = new int[ratings.length];
        //左规则
        for(int i = 0;i < n;i++){
            if(i > 0 && ratings[i] > ratings[i-1]){
                left[i] = left[i-1]+1;
            } else {
                left[i] = 1;
            }
        }

        int right = 0, ret = 0;
        for (int i = n - 1; i >= 0; i--) {
            if (i < n - 1 && ratings[i] > ratings[i + 1]) {
                right++;
            } else {
                right = 1;
            }
            ret += Math.max(left[i], right);
        }

        return ret;
    }

    /**
     * 12. 整数转罗马数字
     */
    int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
    String[] symbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
    public String intToRoman(int num) {
        StringBuilder ans = new StringBuilder();
        for (int i = 0;i < values.length;i++){
            while (num - values[i] >= 0){
                ans.append(symbols[i]);
                num-=values[i];
            }
            if(num == 0){
                return ans.toString();
            }
        }
        return ans.toString();
    }

    /**
     * 58. 最后一个单词的长度
     */
    public int lengthOfLastWord(String s) {
        s = s.trim();
        int ans = 0;
        int n = s.length();
        for (int i = n-1; i >= 0; i--) {
            if(s.charAt(i) == ' '){
                return ans;
            } else {
                ans++;
            }
        }
        return -1;
    }

    /**
     * N 字形变换
     * 1,2,3,2,1,2,3
     */
    public String convert(String s, int numRows) {
        if(numRows < 2) return s;
        ArrayList<StringBuilder> list = new ArrayList<>(numRows);
        for (int i = 0; i < numRows; i++) {
            list.add(new StringBuilder());
        }
        int curRow = 0;
        boolean flag = true;
        for (int i = 0; i < s.length(); i++) {
            if(flag){
                list.get(curRow++).append(s.charAt(i));
            } else {
                list.get(curRow--).append(s.charAt(i));
            }

            if(curRow == numRows){
                flag = !flag;
                curRow-=2;
            }
            if(curRow == -1){
                flag = !flag;
                curRow += 2;
            }
        }

        StringBuilder ans = new StringBuilder();
        for (StringBuilder stringBuilder : list) {
            ans.append(stringBuilder);
        }
        return ans.toString();
    }

    @Test
    void test(){
        convert("PAYPALISHIRING", 3);
    }
}
