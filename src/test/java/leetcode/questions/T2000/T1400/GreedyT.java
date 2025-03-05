package leetcode.questions.T2000.T1400;

public class GreedyT {
    /*
     * 1328. 破坏回文串
     * 
     * 当字符串长度为1时不存在解
     * 剩下的情况使用双指针从字符串的两侧向中间比较，
     * 如果字符不为'a'，那么将其替换为'a'
     * 
     * 如果比较到最后一直都是'a'（函数没有return），则将最后一个字符改为'b'
     * 
     * 一个更高效的方法是使用char[]
     */
    public String breakPalindrome0(String palindrome) {
        int l = 0, r = palindrome.length() - 1;
        if(r == 0) return "";

        while (l < r) {
            if (palindrome.charAt(l) != 'a') {
                return palindrome.substring(0, l) + 'a' + palindrome.substring(l + 1);
            }
            l++;
            r--;
        }
        return palindrome.substring(0, palindrome.length() - 1) + 'b';
    }

    public String breakPalindrome(String palindrome) {
        int len = palindrome.length();
        if(len == 1) return "";

        int l = 0, r = len - 1;
        char[] chars = palindrome.toCharArray();

        while (l < r) {
            if (chars[l] != 'a') {
                chars[l] = 'a';
                return new String(chars);
            }
            l++;
            r--;
        }

        chars[len - 1] = 'b';
        return new String(chars);
    }
}
