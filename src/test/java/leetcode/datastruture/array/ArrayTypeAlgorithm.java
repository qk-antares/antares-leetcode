package leetcode.datastruture.array;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class ArrayTypeAlgorithm {
    /**
     * 删除排序数组中的重复项 II
     */
    class RemoveDuplicates {
        public int removeDuplicates(int[] nums) {
            //i是当前应当插入的位置，j是当前遍历到的位置，count是计数器
            int i = 1, j = 1, count = 1;

            for(;j < nums.length;j++){
                if(nums[j] == nums[j-1]){
                    if(count < 2){
                        nums[i++] = nums[j];
                    }
                    count++;
                }else {
                    count = 1;
                    nums[i++] = nums[j];
                }
            }

            return i;
        }
    }

    /**
     * 反转字符串中的元音字母(双指针)
     */
    class ReverseVowels {
        public String reverseVowels(String s) {
            int len = s.length();
            StringBuilder ans = new StringBuilder(s);

            HashSet<Character> chars = new HashSet<>();
            chars.addAll(Arrays.asList('a', 'e', 'i', 'o','u', 'A', 'E', 'I', 'O', 'U'));

            int left = 0, right = s.length() - 1;
            while (left < right){
                while (left < len && !chars.contains(s.charAt(left))) left++;
                while (right >= 0 && !chars.contains(s.charAt(right))) right--;
                if(left >= right)
                    return ans.toString();
                ans.setCharAt(left, s.charAt(right));
                ans.setCharAt(right, s.charAt(left));
                left++;
                right--;
            }

            return ans.toString();
        }
    }

    /**
     * 盛最多水的容器，动态规划？
     * dp[i][j]表示[i,j]中长条所能构成的盛水最多的容器
     * dp[i][j] = max(dp[i+1][j], dp[i][j-1], (j-i)*min(height[i],height[k]))
     * 需要斜着构造dp
     */
    class MaxArea {
        /**
         * dp[][]可解，但是元素特别多时内存会超出限制
         */
        public int maxArea(int[] height) {
            int len = height.length;
            int[][] dp = new int[len][len];
            //初始化dp
            // for(int i = 0;i < len - 1;i++){
            //     dp[i][i+1] = Math.min(height[i+1], height[i]);
            // }

            //[0,1] [1,2] ...
            //[0,2],[1,3]
            for(int delta = 1;delta < len - 1;delta++){
                for(int j = 0;j < len - delta;j++){
                    dp[j][j+delta] = Math.max(Math.max(dp[j+1][j+delta], dp[j][j+delta-1]), delta * Math.min(height[j], height[j+delta]));
                }
            }

            return dp[0][len-1];
        }

        /**
         * dp压缩（不行，数据量大了会超出时间限制）
         */
        public int maxArea0(int[] height) {
            int len = height.length;
            int[] dp = new int[len];

            //[0,1] [1,2] ...
            //[0,2],[1,3]
            for(int delta = 1;delta < len;delta++){
                for(int j = len - delta - 1;j >= 0;j--){
                    dp[j+delta] = Math.max(Math.max(dp[j+delta], dp[j+delta-1]), delta * Math.min(height[j], height[j+delta]));
                }
            }

            return dp[len-1];
        }

        /**
         * 答案解法：简单双指针，这题的精妙之处在于永远只移动较短的板，移动长板无论长板变长还是变短，最终的volume都一定减小
         */
        public int maxArea1(int[] height) {
            int i = 0, j = height.length - 1;
            int ans = Integer.MIN_VALUE;

            while (i < j){
                ans = Math.max(ans, (j-i)*Math.min(height[i], height[j]));
                if(height[i] < height[j]){
                    i++;
                }else {
                    j--;
                }
            }

            return ans;
        }
    }

    @Test
    void invoke(){
        // new RemoveDuplicates().removeDuplicates(new int[]{1,1,1,1,2,2,3,3,4});
        // new ReverseVowels().reverseVowels("hello");
        // new MaxArea().maxArea0(new int[]{1,8,6,2,5,4,8,3,7});
        // new MaxArea().maxArea0(new int[]{1,1});
    }
}
