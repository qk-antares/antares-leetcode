package leetcode.datastruture.string;

import org.junit.jupiter.api.Test;

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
     * 反转字符串中的单词 III（使用Java函数库，解法二手写）
     */
    class ReverseWords {
        public String reverseWords(String s) {
            StringBuilder sb = new StringBuilder();

            String[] strings = s.split(" ");
            for (String string : strings) {
                char[] chars = string.toCharArray();
                for(int i = chars.length-1;i >= 0;i--)
                    sb.append(chars[i]);

                sb.append(' ');
            }

            sb.delete(sb.length()-1, sb.length());

            return sb.toString();
        }


        /**
         * 手写，原地修改(效率并没有明显提高)
         */
        public String reverseWords0(String s) {
            char[] charArray = s.toCharArray();

            int startIndex = 0, endIndex = 0;
            int tempStart, tempEnd;
            char temp;
            while (endIndex < s.length()){
                while (endIndex < s.length() && s.charAt(endIndex) != ' '){
                    endIndex++;
                }
                //对startIndex和endIndex-1区间内的字符进行翻转
                tempStart = startIndex;
                tempEnd = endIndex-1;
                while (tempStart < tempEnd){
                    //这里提升为函数效率会下降
                    temp = charArray[tempStart];
                    charArray[tempStart] = charArray[tempEnd];
                    charArray[tempEnd] = temp;
                    tempStart++;
                    tempEnd--;
                }
                endIndex++;
                startIndex = endIndex;
            }

            return new String(charArray);
        }

        public void swap(char[] chars, int i, int j){
            //将temp提成为类的属性效率反而变慢
            char temp = chars[i];
            chars[i] = chars[j];
            chars[j] = temp;
        }
    }


    @Test
    public void invoke(){
//        new LongestCommonPrefix().longestCommonPrefix(new String[]{"flower","flow","flight"});
//        new LongestPalindrome().longestPalindrome0("cbbd");
        new ReverseWords().reverseWords0("Let's take LeetCode contest");
    }

}
