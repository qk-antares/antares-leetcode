package leetcode.questions.Easy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

/**
 * @author Antares
 * @date 2022/9/1
 */
public class EasyString {
    /**
     * 反转字符串
     * 我的解法（双指针）
     */
    public void reverseString(char[] s) {
        char temp;
        for(int i = 0,j = s.length-1;i < j;i++,j--){
            temp = s[i];
            s[i] = s[j];
            s[j] = temp;
        }
    }

    /**
     * 整数反转
     * 我的解法（非常之麻烦）
     */
    public int reverse(int x) {
        String s = String.valueOf(x);
        long res = 0;
        if(x>=0){
            int len = s.length();
            for(int i = 0;i < len;i++){
                res += (s.charAt(i)-'0') * Math.pow(10, i);
            }
        }
        else{
            s = s.substring(1);
            int len = s.length();
            for(int i = 0;i < len;i++){
                res += (s.charAt(i)-'0') * Math.pow(10, i);
            }
            res *= -1;
        }
        if(res >= -2147483648 && res <= 2147483647)
            return (int)res;
        else
            return 0;
    }

    /**
     * 答案解法
     */
    public int reverse0(int x) {
        long res = 0;
        while (x != 0){
            res = res * 10 + x % 10;
            x /= 10;
        }
        return (int)res == res ? (int)res : 0;
    }

    /**
     * 字符串中的第一个唯一字符
     * 我的解法（暴力解法，时间复杂度太高）
     */
    public int firstUniqChar(String s) {
        int len = s.length();
        for(int i = 0;i < len;i++){
            char c = s.charAt(i);
            int j;
            for(j = 0;j < len;j++){
                if(i == j)
                    continue;
                if(c == s.charAt(j))
                    break;
            }
            if(j == len)
                return i;
        }
        return -1;
    }

    /**
     * 我的解法二，使用Set进行优化(表现还不错)
     */
    public int firstUniqChar0(String s) {
        int len = s.length();
        HashSet<Character> characters = new HashSet<>();
        for(int i = 0;i < len;i++){
            if(characters.contains(s.charAt(i)))
                continue;
            char c = s.charAt(i);
            characters.add(c);
            int j;
            for(j = i+1;j < len;j++){
                if(c == s.charAt(j))
                    break;
            }
            if(j == len)
                return i;
        }
        return -1;
    }

    /**
     * 使用java api(效率低)
     */
    public int firstUniqChar1(String s) {
        for (int i = 0; i < s.length(); i++)
            if (s.indexOf(s.charAt(i)) == s.lastIndexOf(s.charAt(i)))
                return i;
        return -1;
    }

    /**
     * 有效的字母异位词
     * 我的方法，hashmap(效率极低)
     */
    public boolean isAnagram(String s, String t) {
        int len = s.length();
        if(len != t.length())
            return false;
        HashMap<Character, Integer> hashMap = new HashMap<>();
        for(int i = 0;i < len;i++){
            if(hashMap.get(s.charAt(i)) != null){
                hashMap.put(s.charAt(i), hashMap.get(s.charAt(i)) + 1);
            }else{
                hashMap.put(s.charAt(i), 1);
            }
        }

        for(int i = 0;i < len;i++){
            if(hashMap.get(t.charAt(i)) == null)
                return false;
            hashMap.put(t.charAt(i), hashMap.get(t.charAt(i)) - 1);
            if(hashMap.get(t.charAt(i)) == 0)
                hashMap.remove(t.charAt(i));
        }

        if(hashMap.size() == 0)
            return true;
        return false;
    }

    /**
     * 答案解法（先排序再比较，执行用时和内存均有较大提升，但时间依然不理想）
     */
    public boolean isAnagram0(String s, String t) {
        int len = s.length();
        if(len != t.length())
            return false;
        char[] sChars = s.toCharArray();
        char[] tChars = t.toCharArray();
        Arrays.sort(sChars);
        Arrays.sort(tChars);
        for(int i = 0;i < s.length();i++){
            if(sChars[i] != tChars[i])
                return false;
        }
        return true;
        //判断数组相等可以直接使用下面这个函数，使用该函数后执行用时大大提升
//        return Arrays.equals(sChars, tChars);
    }

    /**
     * 验证回文串
     * 我的解法：转数组，硬解(表现差强人意)
     */
    public boolean isPalindrome(String s) {
        ArrayList<Character> characters = new ArrayList<>();
        int len = s.length();
        for(int i = 0;i < len;i++){
            if(Character.isDigit(s.charAt(i)) || Character.isLetter(s.charAt(i)))
                characters.add(Character.toLowerCase(s.charAt(i)));
        }

        int size = characters.size();
        for(int i = 0,j = size-1;i < j;i++,j--){
            if(characters.get(i) != characters.get(j))
                return false;
        }
        return true;
    }

    /**
     * 答案解法（省去了转数组的过程，时间大大提升，但空间还有提升空间）
     */
    public boolean isPalindrome0(String s) {
        for(int i = 0,j = s.length()-1;i < j;){
            if(!Character.isLetter(s.charAt(i)) && !Character.isDigit(s.charAt(i))){
                i++;
                continue;
            }
            if(!Character.isLetter(s.charAt(j)) && !Character.isDigit(s.charAt(j))){
                j--;
                continue;
            }
            if(Character.toLowerCase(s.charAt(i)) != Character.toLowerCase(s.charAt(j))){
                return false;
            }
            i++;
            j--;
        }
        return true;
    }

    /**
     * 字符串转换整数 (atoi)
     * 我的解法1(超出内存限制)
     */
    public int myAtoi(String s) {
        ArrayList<Character> characters = new ArrayList<>();
        int i = 0;
        int factor = 1;
        if(Character.isDigit(s.charAt(i))){

        } else if (s.charAt(i) == '+') {
            i++;
        } else if (s.charAt(i) == '-') {
            factor = -1;
            i++;
        }else {
            return 0;
        }

        while (Character.isDigit(s.charAt(i))){
            characters.add(s.charAt(i));
        }

        int size = characters.size();
        long res = 0;
        for(i = 0;i < size;i++){
            res += (characters.get(i) * Math.pow(10, size-1-i));
        }

        if((int)res == res)
            return (int)res*factor;
        else
            if(factor == 1)
                return 2147483647;
            else
                return -2147483648;
    }

    /**
     * 我的解法2(卡在了判断溢出上，因为题中给出的数据甚至超出了long的范围)
     */
    public int myAtoi0(String s) {
        s = s.trim();
        if(s.length() == 0)
            return 0;

        int i = 0;
        int factor = 1;
        if(Character.isDigit(s.charAt(i))){

        } else if (s.charAt(i) == '+') {
            i++;
        } else if (s.charAt(i) == '-') {
            factor = -1;
            i++;
        }else {
            return 0;
        }
        long res = 0;
        while (i < s.length() && Character.isDigit(s.charAt(i))){
            res = res * 10 + s.charAt(i) - '0';
            i++;
        }


        if((int)res == res)
            return (int)res*factor;
        else
            if(factor == 1)
                return 2147483647;
            else
                return -2147483648;
    }

    /**
     * 答案解法，用巧妙方法解决溢出
     */
    public int myAtoi1(String s) {
        s = s.trim();
        if(s.length() == 0)
            return 0;

        int i = 0;
        int factor = 1;
        if(Character.isDigit(s.charAt(i))){

        } else if (s.charAt(i) == '+') {
            i++;
        } else if (s.charAt(i) == '-') {
            factor = -1;
            i++;
        }else {
            return 0;
        }
        int res = 0;
        long temp;
        while (i < s.length() && Character.isDigit(s.charAt(i))){
            temp = res;
            res = res * 10 + s.charAt(i) - '0';
            if(res / 10 != temp){
                if(factor == 1)
                    return Integer.MAX_VALUE;
                return Integer.MIN_VALUE;
            }
            i++;
        }

        return res * factor;
    }

    /**
     * 实现 strStr()
     * 我的解法(使用双指针，效率已较高)
     */
    public int strStr(String haystack, String needle) {
        int res = 0;
        int len1 = haystack.length(),len2 = needle.length();
        for(int i = 0,j = 0;i < len1;i++){
            if(haystack.charAt(i) != needle.charAt(j))
                continue;
            res = i;
            i++;
            j++;
            for(;j < len2;i++,j++){
                if(i == len1 || needle.charAt(j) != haystack.charAt(i)){
                    j = 0;
                    i = res;
                    break;
                }
            }
            if(j == len2)
                return res;
        }
        return -1;
    }

    /**
     * 答案解法（KMP算法，不太理解）
     * 1.求next数组
     * 什么是next数组？
     * 从上一个字符开始，和最前面的最长匹配长度，例如：ABCABA的next数组为[-1,0,0,0,1,2];WABCABA的next数组为[-1,0,0,0,0,0,0]
     */
    public void getNext(String p, int[] next){
        int len = p.length();
        //i指向当前读取位置，j为被匹配位置
        int i = 0,j = -1;
        next[0] = -1;
        while (i < len - 1){
            if(j == -1 || p.charAt(i) == p.charAt(j)){
                i++;
                j++;
                next[i] = j;
            }else{
                j = next[j];
            }
        }
    }

    /**
     * 外观数组
     * 我的解法(递归调用，效率较低，答案也是这种解法，将String换成StringBuffer就有较大提升)
     */
    public String countAndSay(int n) {
        if(n == 1)
            return "1";
        String s = countAndSay(n - 1);
        StringBuffer res = new StringBuffer();
        char c;
        int count;
        int i = 0, len = s.length();
        while(i < len){
            c = s.charAt(i++);
            count = 1;
            while (i < len && c == s.charAt(i)){
                count++;
                i++;
            }
            res.append(count).append(c);
        }
        return res.toString();
    }

    /**
     * 最长公共前缀
     * 我的解法(表现还不错)
     */
    public String longestCommonPrefix(String[] strs) {
        StringBuffer res = new StringBuffer();
        int size = strs.length;
        for(int i = 0;i < strs[0].length();i++){
            for(int j = 1;j < size;j++){
                try {
                    if(strs[j].charAt(i) != strs[0].charAt(i)){
                        return res.toString();
                    }
                }catch (IndexOutOfBoundsException e){
                    if(size == 1)
                        return strs[0];
                    return res.toString();
                }
            }
            res.append(strs[0].charAt(i));
        }
        return res.toString();
    }

    /**
     * 答案解法（不断截取法）
     */
    public String longestCommonPrefix0(String[] strs) {
        //默认第一个字符串是他们的公共前缀，然后不断剪短这个字符串
        String pre = strs[0];
        int i = 1;
        while (i < strs.length) {
            //不断的截取
            while (strs[i].indexOf(pre) != 0)
                pre = pre.substring(0, pre.length() - 1);
            i++;
        }
        return pre;
    }

    @Test
    public void invoke(){
//        reverse(-123);
//        firstUniqChar0("loveleetcode");
//        isAnagram("anagram","nagaram");
//        isPalindrome("A man, a plan, a canal: Panama");
//        myAtoi1("-91283472332");
//        strStr("mississippi","issip");
//        countAndSay(5);
    }
}
