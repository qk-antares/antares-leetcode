package leetcode.datastruture.string;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @author Antares
 * @date 2022/10/8
 */
public class StringLearn {
    /**
     * 最长公共前缀（我的解法：首先找到最短字符串，答案解法：使用startWith函数）
     */
    class LongestCommonPrefix {
        public String longestCommonPrefix(String[] strs) {
            int minLenIndex = 0;
            for(int i = 1;i < strs.length;i++){
                if(strs[minLenIndex].length() > strs[i].length())
                    minLenIndex = i;
            }

            StringBuilder stringBuilder = new StringBuilder();
            int index = 0;
            while (index < strs[minLenIndex].length()){
                for (String str : strs) {
                    if(str.charAt(index) != strs[minLenIndex].charAt(index))
                        return stringBuilder.toString();
                }
                stringBuilder.append(strs[minLenIndex].charAt(index++));
            }
            return stringBuilder.toString();
        }

        /**
         * 我的解法二：效率较高
         */
        public String longestCommonPrefix0(String[] strs) {
            String prefix = strs[0];
            for (String str : strs) {
                if(str.startsWith(prefix)){
                    continue;
                }
                for(int i = prefix.length() - 1;i >= 0;i--){
                    prefix = strs[0].substring(0, i);
                    if(str.startsWith(prefix))
                        break;
                }
            }
            return prefix;
        }
    }

    /**
     * 最长回文子串：解法一动态规划(效率不是很高)，解法二中心扩散法（效率最高）
     * dp[i][j]：代表s[i..j]是否为回文串
     * dp[i][j] = true (dp[i+1][j-1]&&s[i]==s[j])
     * 初始条件
     * dp[i][i] = true
     * dp[i][i+1]
     */
    class LongestPalindrome {
        public String longestPalindrome(String s) {
            boolean[][] dp = new boolean[s.length()][s.length()];
            int maxLen = 1;
            int start = 0, end = 0;

            for(int i = 0;i < s.length();i++){
                dp[i][i] = true;
            }

            for(int i = 0;i < s.length()-1;i++){
                if(s.charAt(i) == s.charAt(i+1)){
                    maxLen = 2;
                    dp[i][i+1] = true;
                    start = i;
                    end = i+1;
                }
            }

            //斜着构造dp
            //dp[0][2] = dp[1][1] && s[0] == s[2]
            //dp[1][3] = dp[2][2] && s[1] == s[3]
            for(int step = 2;step < s.length();step++){
                for(int i = 0;i < s.length()-step;i++){
                    if(dp[i+1][i+step-1] && s.charAt(i) == s.charAt(i+step)){
                        dp[i][i+step] = true;
                        if(step+1 > maxLen){
                            maxLen = step+1;
                            start = i;
                            end = i+step;
                        }
                    }
                }
            }

            return s.substring(start, end+1);
        }

        public String longestPalindrome0(String s) {
            int len = s.length();
            int start = 0, maxLen = 1;
            int j;

            for(int i = 0;i < len;i++){
                j = i;
                while (j+1 < len && s.charAt(j+1) == s.charAt(i))
                    j++;

                for(int step = 0;i-step > -1 && j+step < len && s.charAt(i-step)==s.charAt(j+step);step++){
                    if(j - i + 1 + 2 * step > maxLen){
                        start = i-step;
                        maxLen = j - i + 1 + 2 * step;
                    }
                }

                i = j;
            }
            return s.substring(start, start + maxLen);
        }
    }

    /**
     * 翻转字符串里的单词，使用split函数+StringBuilder
     */
    class ReverseWords {
        public String reverseWords(String s) {
            String[] strings = s.trim().split(" ");

            StringBuilder sb = new StringBuilder();

            for(int i = strings.length-1;i >= 0;i--){
                if (!strings[i].equals("")){
                    sb.append(strings[i]).append(" ");
                }
            }

            sb.deleteCharAt(sb.length()-1);

            return sb.toString();
        }
    }

    /**
     * 实现 strStr()
     */
    class StrStr {
        public int strStr(String haystack, String needle) {
            return -1;
        }

        //首先构造next
        public int[] buildNext(String P) { // 构造模式串 P 的 next 表
            int m = P.length(), j = 0; // “主”串指针
            int[] N = new int[m]; // next 表
            int t = N[0] = -1; // 模式串指针
            while (j < m - 1)
                if ( 0 > t || P.charAt(j) == P.charAt(t)){ // 匹配
                    j++; t++;
                    N[j] = t; // 此句可改进为 N[j] = (P[j] != P[t] ? t : N[t]);
                }else // 失配
                    t = N[t];

            return N;
        }
    }


    @Test
    public void invoke(){
//        new LongestCommonPrefix().longestCommonPrefix(new String[]{"flower","flow","flight"});
//        new LongestPalindrome().longestPalindrome0("cbbd");
//        new ReverseWords().reverseWords("a good   example");
        new StrStr().buildNext("AATGPACY");
    }

}
