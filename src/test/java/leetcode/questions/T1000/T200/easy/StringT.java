package leetcode.questions.T1000.T200.easy;

import org.junit.jupiter.api.Test;

public class StringT {
    /*
     * 168. Excel 表列名称
     */
    public String convertToTitle(int columnNumber) {
        StringBuilder ans = new StringBuilder();
        while (columnNumber != 0) {
            columnNumber -= 1;
            ans.append((char)('A' + columnNumber % 26 ));
            columnNumber /= 26;
        }

        return ans.reverse().toString();
    }

    @Test
    public void test(){
        convertToTitle(701);
    }
}
