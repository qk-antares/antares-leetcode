package leetcode.interview150;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;

import org.junit.jupiter.api.Test;

public class ArrayAndString {
    /**
     * 88. 合并两个有序数组
     * 结题：从后面开始！！！
     */
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int i = m-1, j = n-1, cur = m+n-1;
        while (i >= 0 && j >= 0){
            nums1[cur--] = nums1[i] > nums2 [j] ? nums1[i--] : nums2[j--];
        }
        while (i >= 0){
            nums1[cur--] = nums1[i--];
        }
        while (j >= 0){
            nums1[cur--] = nums1[j--];
        }
    }

    /**
     * 27. 移除元素
     */
    public int removeElement(int[] nums, int val) {
        int cur = 0;
        for (int i = 0; i < nums.length; i++) {
            if(nums[i] != val){
               nums[cur++] = nums[i];
            }
        }
        return cur;
    }

    /**
     * 26. 删除有序数组中的重复项
     */
    public int removeDuplicates(int[] nums) {
        int cur = 1;
        for (int i = 1; i < nums.length; i++) {
            if(nums[i] != nums[cur-1]){
                nums[cur++] = nums[i];
            }
        }
        return cur;
    }

    /**
     * 80. 删除有序数组中的重复项 II
     */
    public int removeDuplicates2(int[] nums) {
        int cur = 1, count = 1;
        for (int i = 1; i < nums.length; i++) {
            if(nums[i] != nums[cur-1]){
                count = 1;
                nums[cur++] = nums[i];
            } else {
                if(count == 1){
                    count++;
                    nums[cur++] = nums[i];
                } else {
                    count++;
                }
            }
        }
        return cur;
    }

    /**
     * 169. 多数元素
     * 采用对战的思想，生存下来的那个元素就是大多数
     */
    public int majorityElement(int[] nums) {
        int survivor = nums[0], count = 1;
        for (int i = 1;i < nums.length;i++){
            if(count == 0){
                survivor = nums[i];
                count = 1;
            } else if(nums[i] == survivor){
                count++;
            } else {
                count--;
            }
        }
        return survivor;
    }

    /**
     * 189. 轮转数组
     */
    public void rotate(int[] nums, int k) {
        k%=nums.length;
        reverse(nums, 0, nums.length-1);
        reverse(nums, 0, k-1);
        reverse(nums, k, nums.length-1);
    }
    public void reverse(int[] nums, int l, int r){
        while (l < r){
            int tmp = nums[l];
            nums[l] = nums[r];
            nums[r] = tmp;
            l++;
            r--;
        }
    }

    /**
     * 121. 买卖股票的最佳时机
     */
    public int maxProfit(int[] prices) {
        //计算每个元素右侧的最大值
        int len = prices.length;
        int[] max = new int[len];
        max[len-1] = prices[len-1];
        for (int i = len - 2;i >= 0;i--){
            max[i] = Math.max(prices[i+1], max[i+1]);
        }

        int ans = Integer.MIN_VALUE;
        for (int i = 0;i < len;i++){
            if(max[i] - prices[i] > ans){
                ans = max[i] - prices[i];
            }
        }
        return ans;
    }

    /**
     * 122. 买卖股票的最佳时机 II
     */
    public int maxProfit2(int[] prices) {
        int ans = 0;
        for (int i = 1; i < prices.length; i++) {
            if(prices[i] > prices[i-1]){
                ans += prices[i] - prices[i-1];
            }
        }
        return ans;
    }

    /**
     * 55. 跳跃游戏
     */
    public boolean canJump(int[] nums) {
        int maxDis = 0;
        for (int i = 0; i < nums.length; i++) {
            if(i > maxDis){
                return false;
            }
            maxDis = Math.max(maxDis, i+nums[i]);
        }
        return maxDis >= nums.length-1;
    }

    /**
     * 380. O(1) 时间插入、删除和获取随机元素
     * 使用数组+HashMap
     */
    static class RandomizedSet {
        List<Integer> nums;
        Map<Integer, Integer> indices;
        Random random;

        public RandomizedSet() {
            nums = new ArrayList<>();
            indices = new HashMap<>();
            random = new Random();
        }

        public boolean insert(int val) {
            if(indices.containsKey(val)){
                return false;
            }
            int index = nums.size();
            nums.add(val);
            indices.put(val, index);
            return true;
        }

        public boolean remove(int val) {
            if(!indices.containsKey(val)){
                return false;
            }
            Integer index = indices.get(val);
            //获取数组中的最后一个元素
            int last = nums.get(nums.size()-1);
            nums.set(index, last);
            indices.put(last, index);
            nums.remove(nums.size()-1);
            indices.remove(val);
            return true;
        }

        public int getRandom() {
            int randomIndex = random.nextInt(nums.size());
            return nums.get(randomIndex);
        }
    }

    /**
     * 42. 接雨水
     * 记录每个点的左侧最高的右侧最高
     */
    public int trap(int[] height) {
        int len = height.length;
        int[] left = new int[len];
        left[0] = 0;
        int[] right = new int[len];
        right[len-1] = 0;

        for (int i = 1; i < len; i++) {
            left[i] = Math.max(left[i-1], height[i-1]);
            right[len-1-i] = Math.max(right[len-i], height[len-i]);
        }

        int ans = 0;
        for (int i = 0; i < len; i++) {
            int minMax = Math.min(left[i], right[i]);
            if(minMax > height[i]){
                ans += minMax - height[i];
            }
        }
        return ans;
    }

    /**
     * 13. 罗马数字转整数
     */
    Map<String, Integer> map = new HashMap<>();
    {
        map.put("I", 1);
        map.put("IV", 4);
        map.put("V", 5);
        map.put("IX", 9);
        map.put("X", 10);
        map.put("XL", 40);
        map.put("L", 50);
        map.put("XC", 90);
        map.put("C", 100);
        map.put("CD", 400);
        map.put("D", 500);
        map.put("CM", 900);
        map.put("M", 1000);
    }
    public int romanToInt(String s) {
        int res = 0;
        //先尝试截取两个，如果不在map中，再截取一个
        for(int i = 0;i < s.length();){
            if(i+1 < s.length() && map.containsKey(s.substring(i, i+2))){
                res += map.get(s.substring(i, i+2));
                i += 2;
            }else {
                res += map.get(s.substring(i, i+1));
                i += 1;
            }
        }
        return res;
    }

    /**
     * 14. 最长公共前缀
     */
    public String longestCommonPrefix(String[] strs) {
        StringBuilder sb = new StringBuilder();
        int len = strs[0].length();
        char ch;
        for (int i = 0; i < len; i++) {
            ch = strs[0].charAt(i);
            for (int j = 1; j < strs.length; j++) {
                if(strs[j].length()-1 < i || strs[j].charAt(i) != ch){
                    return sb.toString();
                }
            }
            sb.append(ch);
        }

        return sb.toString();
    }

    /**
     * 151. 反转字符串中的单词
     */
    public String reverseWords(String s) {
        s = s.trim();
        String[] split = s.split(" ");
        StringBuilder ans = new StringBuilder();
        for (int i = split.length-1; i >= 0 ; i--) {
            if(split[i].isEmpty()){
                continue;
            }
            ans.append(split[i]);
            if(i != 0){
                ans.append(' ');
            }
        }
        return ans.toString();
    }

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

    /**
     * 68. 文本左右对齐
     */
    public List<String> fullJustify(String[] words, int maxWidth) {
        int cost;
        List<String> lineWords;
        List<String> ans = new ArrayList<>();
        StringBuilder sb;
        for (int i = 0; i < words.length;) {
            cost = 0;
            lineWords = new ArrayList<>();

            while (i < words.length){
                if(cost + words[i].length() + lineWords.size() < maxWidth){
                    cost += words[i].length();
                    lineWords.add(words[i]);
                    i++;
                } else {
                    break;
                }
            }

            sb = new StringBuilder();
            //最后一行
            if(i == words.length){
                for (int j = 0; j < lineWords.size(); j++) {
                    sb.append(lineWords.get(j));
                    if(j != lineWords.size()-1){
                        sb.append(" ");
                    }
                }
                for (int j = 0; j < maxWidth - cost - lineWords.size() + 1; j++) {
                    sb.append(" ");
                }
            } else if (lineWords.size() == 1) {
                //只有一个单词
                sb.append(lineWords.get(0));
                for (int j = 0; j < maxWidth - cost; j++) {
                    sb.append(" ");
                }
            } else {
                //计算单词之间间距
                int delta = (maxWidth - cost) / (lineWords.size() - 1);
                int left = (maxWidth - cost) % (lineWords.size() - 1);
                for (int j = 0; j < lineWords.size(); j++) {
                    sb.append(lineWords.get(j));
                    if(j < left){
                        for (int k = 0; k < delta+1; k++) {
                            sb.append(" ");
                        }
                    } else if(j != lineWords.size()-1){
                        for (int k = 0; k < delta; k++) {
                            sb.append(" ");
                        }
                    }
                }

            }
            ans.add(sb.toString());
        }

        return ans;
    }

    @Test
    void test(){
//        strStr("mississippi", "issip");
        fullJustify(new String[]{"Science","is","what","we","understand","well","enough","to","explain","to","a","computer.","Art","is","everything","else","we","do"}, 20);
    }
}
