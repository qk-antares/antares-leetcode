package leetcode.questions.T3000.T2800.easy;

import java.util.HashSet;
import java.util.Set;

public class BitT {
    /*
     * 2716. 最小化字符串长度
     * 
     * 问题的本质是统计字符串中出现的不同字符数
     * 一种简单的方法是使用HashSet
     * 由于字符串中只会出现小写字母，还可以使用bit运算进一步优化
     * 使用一个int的26位来标记出现过的字母
     * 最后使用Integer.bitCount(mask)来统计出现过的字母数
     */
    public int minimizedStringLength(String s) {
        Set<Character> set = new HashSet<>();
        int len = s.length();
        for(int i = 0;i < len;i++){
            set.add(s.charAt(i));
        }
        return set.size();
    }
}
