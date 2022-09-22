package leetcode.questions.Medium;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.BiFunction;

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
     * 矩阵置零（我的解法：效率还行，使用了两个临时数组）
     */
    public void setZeroes(int[][] matrix) {
        //记录需要被置零的那些行
        ArrayList<Integer> rows = new ArrayList<>();
        //记录那些需要被置零的列
        ArrayList<Integer> columns = new ArrayList<>();
        for(int i = 0;i < matrix.length;i++){
            for(int j = 0;j < matrix[i].length;j++){
                if(matrix[i][j] == 0){
                    rows.add(i);
                    columns.add(j);
                }
            }
        }

        //将对应的行置为零
        for (Integer row : rows) {
            for (int i = 0;i < matrix[row].length;i++) {
                matrix[row][i] = 0;
            }
        }

        //将对应的列置为零
        for (Integer column : columns) {
            for (int i = 0;i < matrix[column].length;i++) {
                matrix[i][column] = 0;
            }
        }
    }

    /**
     * 答案解法（直接将目标矩阵的第一行和第一列作为临时数组，同时增加两个变量来判断第一行和第一列是否有0）
     */
    public void setZeroes0(int[][] matrix) {
        //判断第一行是否有0
        boolean row = false;
        //判断第一列是否有0
        boolean column = false;

        for(int i = 0;i < matrix[0].length;i++){
            if(matrix[0][i] == 0){
                row = true;
                break;
            }
        }

        for(int i = 0;i < matrix.length;i++){
            if(matrix[i][0] == 0){
                column = true;
                break;
            }
        }

        for(int i = 1;i < matrix.length;i++){
            for(int j = 1;j < matrix[0].length;j++){
                if(matrix[i][j] == 0){
                    matrix[0][j] = 0;
                    matrix[i][0] = 0;
                }
            }
        }

        //将对应的行置为零
        for(int i = 1;i < matrix.length;i++){
            if(matrix[i][0] == 0){
                for(int j = 1;j < matrix[i].length;j++)
                    matrix[i][j] = 0;
            }
        }

        //将对应的列置为零
        for(int i = 1;i < matrix[0].length;i++){
            if(matrix[0][i] == 0){
                for(int j = 1;j < matrix.length;j++)
                    matrix[j][i] = 0;
            }
        }

        //判断第一行和第一列的情况
        if(row){
            for(int i = 0;i < matrix[0].length;i++)
                matrix[0][i] = 0;
        }
        if(column){
            for(int i = 0;i < matrix.length;i++)
                matrix[i][0] = 0;
        }
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
     * 无重复字符的最长子串（我的解法：滑动窗口，效果还可以）
     */
    public int lengthOfLongestSubstring(String s) {
        //这是窗口的边界
        int i = -1,j = 0;
        int ans = 0;
        HashSet<Character> chars = new HashSet<>();
        for(;i < s.length()-1;i++){
            //一定不可能是答案
            if(s.length() - i - 1 <= ans)
                continue;
            for(;j < s.length();j++){
                if(chars.contains(s.charAt(j)))
                    break;
                else {
                    chars.add(s.charAt(j));
                    if(j - i > ans)
                        ans = j - i;
                }
            }
            chars.remove(s.charAt(i+1));
        }

        return ans;
    }

    /**
     * 答案解法，使用数组标记所有字符（效率少有提升）
     */
    public int lengthOfLongestSubstring0(String s) {
        boolean[] visited = new boolean[256];

        int ans = 0,startIndex = 0;
        for(int i = 0;i < s.length();i++){
            char c = s.charAt(i);

            //如果该字符已经访问过
            while (visited[c]){
                visited[s.charAt(startIndex++)] = false;
            }

            //标记为已经访问过
            visited[c] = true;
            ans = Math.max(ans, i - startIndex + 1);
        }
        return ans;
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

    /**
     * 递增的三元子序列（我的解法：动态规划，思路是对的，但是超时）
     * dp[i]表示nums[i]结尾的最长递增子序列
     * dp[0] = 1
     * dp[i] = max(dp[j])+1 (nums[j]<nums[i])
     */
    public boolean increasingTriplet(int[] nums) {
        int[] dp = new int[nums.length];
        dp[0] = 1;
        int maxDp;
        for(int i = 1;i < nums.length;i++){
            maxDp = 0;
            for(int j = 0;j < i;j++){
                if(nums[j] < nums[i] && dp[j] > maxDp){
                    maxDp = dp[j];
                }
            }
            dp[i] = maxDp + 1;
            if(dp[i] >= 3)
                return true;
        }
        return false;
    }

    /**
     * 答案解法（两个临时变量分别保存当前遇到的第一小值和第二小值）
     */
    public boolean increasingTriplet0(int[] nums) {
        int min0 = Integer.MAX_VALUE,min1 = Integer.MAX_VALUE;
        for(int i = 0;i < nums.length;i++){
            if(nums[i] < min0)
                min0 = nums[i];
            else if(nums[i] > min0 && nums[i] < min1)
                min1 = nums[i];
            else if (nums[i] > min1)
                return true;
        }
        return false;
    }

    @Test
    public void invoke(){
//        setZeroes(new int[][]{{1,1,1},{1,0,1},{1,1,1}});
//        groupAnagrams(new String[]{"eat","tea","tan","ate","nat","bat"});
//        longestPalindrome("babad");
        longestPalindrome2("fsajlkasjabcddcbalj");
    }

}
