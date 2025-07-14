package leetcode.questions.Medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * @author Antares
 * @date 2022/9/8
 */
public class ArrayAndString {
    /**
     * 三数之和(我的解法：穷举法，不行，思路都是错误的，因为这一题中要输出的数字而不是下标，且数字的顺序不重要，这两点我都没有考虑到)
     */
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        for(int i = 0;i < nums.length - 2;i++){
            for(int j = 1;j < nums.length - 1;j++){
                for(int k = 2;k < nums.length;k++){
                    if(nums[i] + nums[j] + nums[k] == 0){
                        ArrayList<Integer> single = new ArrayList<>();
                        single.add(i);
                        single.add(j);
                        single.add(k);
                        res.add(single);
                    }
                }
            }
        }
        return res;
    }

    /**
     * 解法一：排序，固定一个值，然后使用双指针(关键在于重复答案的过滤)
     */
    public List<List<Integer>> threeSum0(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();

        Arrays.sort(nums);

        //外面这层for循环确定的是被固定的值
        for(int i = 0;i < nums.length;i++){
            //过滤掉重复的
            if(i > 0 && nums[i] == nums[i-1])
                continue;
            if(nums[i] > 0)
                break;

            int p = i + 1, q = nums.length - 1;

            while (p < q){
                if(nums[p] + nums[q] == -nums[i]){
                    ArrayList<Integer> ans = new ArrayList<>(3);
                    Collections.addAll(ans, nums[i], nums[p], nums[q]);
                    res.add(ans);
                    //过滤掉重复的
                    while (p < q && nums[p] == nums[p+1]) p++;
                    while (p < q && nums[q] == nums[q-1]) q--;

                    p++;
                    q--;
                } else if (nums[p] + nums[q] < -nums[i]) {
                    p++;
                } else if (nums[p] + nums[q] > -nums[i]) {
                    q--;
                }
            }
        }

        return res;
    }

    /**
     * 字母异位词分组（我的解法：字符串排序，可以，但是效率比较低，注意chars.toString()和String.valueOf(chars)是不同的）
     */
    public List<List<String>> groupAnagrams(String[] strs) {
        if(strs == null || strs.length == 0)
            return new ArrayList<>();

        Map<String, List<String>> ans = new HashMap<>();

        char[] chars;
        for(int i = 0;i < strs.length;i++){
            chars = strs[i].toCharArray();
            Arrays.sort(chars);
            if(ans.containsKey(String.valueOf(chars))){
                ans.get(String.valueOf(chars)).add(strs[i]);
            }else{
                ans.put(String.valueOf(chars), new ArrayList<String>(Arrays.asList(strs[i])));
            }
        }

        return new ArrayList<>(ans.values());
    }


    /**
     * 最长回文子串（我的解法：动态规划，方向是对的，但细节错了）
     * dp[i][j]表示str[i...j]这段字符内的最长回文子串
     * 初始状态：dp[i][j] = 1 (i==j)
     * dp[i][j] = dp[i+1][j-1]+2    (str[i]==str[j])
     * dp[i][j] = max(dp[i+1][j], dp[i][j-1])   (str[i]!=str[j])
     */
    public String longestPalindrome(String s) {
        int len = s.length();
        int[][] dp = new int[len][len];
        //初始状态
        for(int i = 0;i < len;i++)
            dp[i][i] = 1;

        int start = 0, end = len-1;

        //从右下到左上斜着构造dp
        for(int i = 1;i < len;i++){
            for(int j = len - 1;j >= i;j--){
                if(s.charAt(j-i) == s.charAt(j)){
                    dp[j-i][j] = dp[j-i+1][j-1] + 2;
                    start = j-i;
                    end = j;
                }
                else
                    dp[j-i][j] = Math.max(dp[j-i+1][j], dp[j-i][j-1]);
            }
        }

        return s.substring(start, end+1);
    }

    /**
     * 答案解法（动态规划，但是dp存储的是s[i...j]是否为回文串）
     */
    public String longestPalindrome0(String s) {
        int len = s.length();
        boolean[][] dp = new boolean[len][len];
        //初始状态
        for(int i = 0;i < len;i++)
            dp[i][i] = true;

        for(int i = 1;i < len;i++)
            dp[i][i-1] = true;

        //从右下到左上斜着构造dp
        for(int i = 1;i < len;i++){
            for(int j = len - 1;j >= i;j--){
                if(s.charAt(j-i) == s.charAt(j) && dp[j-i+1][j-1])
                    dp[j-i][j] = true;
                else
                    dp[j-i][j] = false;
            }
        }

        //找出最大差值
        int maxLen = Integer.MIN_VALUE;
        int start = -1, end = -1;
        for(int i = 0;i < len;i++){
            for(int j = i;j < len;j++){
                if(dp[i][j] && j-i+1 > maxLen){
                    maxLen = j-i+1;
                    start = i;
                    end = j;
                }
            }
        }
        return s.substring(start, end+1);
    }

    /**
     * 答案解法：动态规划，但不是斜着构造dp的，而是以行为单位，（效率依然不高）
     */
    public String longestPalindrome1(String s) {
        int len = s.length();
        boolean[][] dp = new boolean[len][len];
        int start = 0,maxLen = 1;

        for(int i = 1;i < len;i++){
            for(int j = 0;j <= i;j++){
                if(s.charAt(i) != s.charAt(j))
                    dp[j][i] = false;
                else {
                    if(i == j)
                        dp[j][i] = true;
                    else if (i - j <= 2)
                        dp[j][i] = true;
                    else
                        dp[j][i] = dp[j+1][i-1];

                    if(i - j + 1 > maxLen && dp[j][i]){
                        start = j;
                        maxLen = i - j + 1;
                    }
                }
            }
        }

        return s.substring(start, start+maxLen);
    }

    /**
     * 答案解法二：中心扩散法（效率最高）
     */
    public String longestPalindrome2(String s) {
        int len = s.length();

        int start = 0,maxLen = 1;
        for(int i = 0;i < len;i++){
            int j = i;
            while (j < len && s.charAt(j) == s.charAt(i))
                j++;

            for(int step = 0;i-step > -1 && j-1+step < len && s.charAt(i-step) == s.charAt(j-1+step);step++){
                if(2 * step + j - i > maxLen){
                    start = i - step;
                    maxLen = 2 * step + j - i;
                }
            }

            i = j-1;
        }
        return s.substring(start, start + maxLen);
    }

    @Test
    public void invoke(){
//        groupAnagrams(new String[]{"eat","tea","tan","ate","nat","bat"});
//        longestPalindrome("babad");
        longestPalindrome2("fsajlkasjabcddcbalj");
    }

}
