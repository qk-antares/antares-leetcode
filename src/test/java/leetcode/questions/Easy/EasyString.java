package leetcode.questions.Easy;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

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
            return new Long(res).intValue();
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

    @Test
    public void invoke(){
//        reverse(-123);
//        firstUniqChar0("loveleetcode");
        isAnagram("anagram","nagaram");
    }
}
