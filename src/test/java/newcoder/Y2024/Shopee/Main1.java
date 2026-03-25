package newcoder.Y2024.Shopee;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

public class Main1 {
    public String char_and_num_return(String text_source) {
        String[] words = text_source.split(" ");
        List<Integer> nums = new ArrayList<>();
        List<String> strs = new ArrayList<>();
        for (String word : words) {
            if (Character.isDigit(word.charAt(0))) {
                nums.add(Integer.valueOf(word));
            } else {
                strs.add(word);
            }
        }

        Collections.sort(nums);
        StringBuilder ans = new StringBuilder();
        for (String str : strs) {
            ans.append(str).append(" ");
        }
        for (Integer num : nums) {
            ans.append(num).append(" ");
        }

        ans.setLength(ans.length() - 1);
        return ans.toString();
    }

    @Test
    void test() {
        String text_source = "";
        System.out.println(char_and_num_return(text_source));
    }
}
