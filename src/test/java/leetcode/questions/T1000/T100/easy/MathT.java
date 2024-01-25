package leetcode.questions.T1000.T100.easy;

import org.junit.jupiter.api.Test;

public class MathT {
    /**
     * 9. 回文数
     * 我的解法
     */
    public boolean isPalindrome(int x) {
        if(x < 0){
            return false;
        }
        StringBuilder sb = new StringBuilder();
        while (x > 0){
            sb.append(x % 10);
            x = x / 10;
        }
        int i = 0, j = sb.length()-1;
        while (i <= j){
            if(sb.charAt(i) != sb.charAt(j)){
                return false;
            }
            i++;j--;
        }
        return true;
    }

    /**
     * 67. 二进制求和
     */
    public String addBinary(String a, String b) {
        StringBuilder sb = new StringBuilder();
        int i = a.length() - 1, j = b.length() - 1;

        int add = 0;
        while (i >= 0 && j >= 0) {
            //都是1
            if(((a.charAt(i)-'0') & (b.charAt(j)-'0')) == 1){
                sb.append(add);
                add = 1;
            } else if(((a.charAt(i)-'0') ^ (b.charAt(j)-'0')) == 1){
                //一个1一个0                
                if(add == 1){
                    sb.append(0);
                    add = 1;
                } else {
                    sb.append(1);
                }
            } else {
                //都是0
                sb.append(add);
                add = 0;
            }
            i--;j--;
        }

        while (i >= 0) {
            //都是1
            if((add & (a.charAt(i)-'0')) == 1){
                sb.append(0);
            } else if((add ^ (a.charAt(i)-'0')) == 1){
                sb.append(1);
                add = 0;
            } else {
                sb.append(0);
            }
            i--;
        }

        while (j >= 0) {
            //都是1
            if((add & (b.charAt(j)-'0')) == 1){
                sb.append(0);
            } else if((add ^ (b.charAt(j)-'0')) == 1){
                sb.append(1);
                add = 0;
            } else {
                sb.append(0);
            }
            j--;
        }

        if(add != 0){
            sb.append(1);
        }

        return sb.reverse().toString();

    }

    @Test
    void test(){
        // isPalindrome(10);
        addBinary("1010", "1011");
    }
}
