package leetcode.questions.T1000.T100.hard;

import org.junit.jupiter.api.Test;

public class StringT {
    /**
     * 65. 有效数字
     */
    public boolean isNumber(String s) {
        int len = s.length();
        int eIndex = Math.max(s.indexOf("e"), s.indexOf("E"));
        
        if(eIndex == 0 || eIndex == len-1){
            return false;
        }
        
        if(eIndex == -1){
            return isFloat(s) || isInteger(s);
        }

        String pre = s.substring(0, eIndex);
        String end = s.substring(eIndex+1, len);
        return (isFloat(pre) || isInteger(pre)) && isInteger(end);
    }

    public boolean isInteger(String s){
        if(s.charAt(0) == '+' || s.charAt(0) == '-'){
            s = s.substring(1);
        }
        return isAllDigital(s);
    }

    public boolean isFloat(String s){
        if(s.charAt(0) == '+' || s.charAt(0) == '-'){
            s = s.substring(1);
        }

        int len = s.length();
        int dotIndex = s.indexOf(".");
        if(dotIndex == -1){
            return false;
        } else if(dotIndex == 0){
            //后面的全部是数字
            return isAllDigital(s.substring(1));
        } else if(dotIndex == len-1){
            //前面的全部是数字
            return isAllDigital(s.substring(0, dotIndex));
        } else {
            //两边是数字
            return isAllDigital(s.substring(0, dotIndex)) && isAllDigital(s.substring(dotIndex+1));
        }
    }

    public boolean isAllDigital(String s){
        int len = s.length();
        if(len == 0) return false;
        for (int i = 0; i < len; i++) {
            if(!Character.isDigit(s.charAt(i))){
                return false;
            }
        }
        return true;
    }

    @Test
    void test(){
        isNumber("2e0");
        // System.out.println("0.1".split("[.]").length);
    }
}
