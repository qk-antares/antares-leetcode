package leetcode.questions.Easy;

import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * @author Antares
 * @date 2022/9/6
 */
public class MathEasy {
    /**
     * Fizz Buzz(我的解法)
     */
    public List<String> fizzBuzz(int n) {
        List<String> res = new ArrayList<>(n);
        for(int i = 1; i <= n;i++){
            if(i % 3 == 0 && i % 5 == 0)
                res.add("FizzBuzz");
            else if (i % 3 == 0)
                res.add("Fizz");
            else if (i % 5 == 0)
                res.add("Buzz");
            else
                res.add(i+"");
        }
        return res;
    }

    /**
     * 计数质数(我的解法：超时，使用常规解法无法再规定时间内解出)
     */
    public int countPrimes(int n) {
        if(n < 3)
            return 0;
        if(n == 3)
            return 1;

        int count = 1;
        for(int i = 3;i < n;i++){
            if(isPrime(i))
                count++;
        }
        return count;
    }

    public boolean isPrime(int n){
        for(int i = 2;i < n/Math.sqrt(2);i++){
            if(n % i == 0)
                return false;
        }
        return true;
    }

    /**
     * 答案解法（埃氏筛）
     * 原理：从2开始，将每个素数的各个倍数，标记成合数
     */
    public int countPrimes0(int n) {
        boolean[] notPrime = new boolean[n];
        int count = 0;
        for(int i = 2;i < n;i++){
            if(notPrime[i])
                continue;
            count++;
            for(int j = 2 * i;j < n;j += i)
                notPrime[j] = true;
        }
        return count;
    }

    /**
     * 3的幂(我的解法，使用循环)
     */
    public boolean isPowerOfThree(int n) {
        if(n <= 0)
            return false;

        while (n != 1){
            if(n % 3 != 0)
                return false;
            n /= 3;
        }
        return true;
    }

    /**
     * 答案解法
     * int范围内最大的3的幂是1162261467，如果n是3的幂，则一定可以被它整除
     */
    public boolean isPowerOfThree0(int n) {
        return (n > 0 && 1162261467 % n == 0);
    }

    /**
     * 罗马数字转整数
     */
    public int romanToInt(String s) {
        int res = 0;
        for(int i = 0;i < s.length();i++){
            char c = s.charAt(i);
            if(c == 'I'){
                if(i != s.length()-1){
                    if (s.charAt(i+1) == 'V'){
                        i++;
                        res += 4;
                    } else if (s.charAt(i+1) == 'X') {
                        i++;
                        res += 9;
                    }else
                        res++;
                }else
                    res++;
            } else if (c == 'V') {
                res += 5;
            } else if (c == 'X'){
                if(i != s.length()-1){
                    if (s.charAt(i+1) == 'L'){
                        i++;
                        res += 40;
                    } else if (s.charAt(i+1) == 'C') {
                        i++;
                        res += 90;
                    }else
                        res += 10;
                }else
                    res += 10;
            } else if (c == 'L') {
                res += 50;
            } else if (c == 'C') {
                if(i != s.length()-1){
                    if (s.charAt(i+1) == 'D'){
                        i++;
                        res += 400;
                    } else if (s.charAt(i+1) == 'M') {
                        i++;
                        res += 900;
                    }else
                        res += 100;

                }else
                    res += 100;
            } else if (c == 'D') {
                res += 500;
            } else if (c == 'M') {
                res += 1000;
            }
        }

        return res;
    }

    /**
     * 答案解法：使用map(代码少，效率不高)
     */
    public int romanToInt0(String s) {
        Map<String, Integer> map = new HashMap<>();
        //所有可能的都列出来
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

    @Test
    public void invoke(){
//        countPrimes(10);
        romanToInt("MCMXCIV");
    }

}
