package leetcode.datastruture.array;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

public class ArrayTypeAlgorithm {
    /**
     * 删除排序数组中的重复项 II
     */
    class RemoveDuplicates {
        public int removeDuplicates(int[] nums) {
            //i是当前应当插入的位置，j是当前遍历到的位置，count是计数器
            int i = 1, j = 1, count = 1;

            for(;j < nums.length;j++){
                if(nums[j] == nums[j-1]){
                    if(count < 2){
                        nums[i++] = nums[j];
                    }
                    count++;
                }else {
                    count = 1;
                    nums[i++] = nums[j];
                }
            }

            return i;
        }
    }

    /**
     * 反转字符串中的元音字母(双指针)
     */
    class ReverseVowels {
        public String reverseVowels(String s) {
            int len = s.length();
            StringBuilder ans = new StringBuilder(s);

            HashSet<Character> chars = new HashSet<>();
            chars.addAll(Arrays.asList('a', 'e', 'i', 'o','u', 'A', 'E', 'I', 'O', 'U'));

            int left = 0, right = s.length() - 1;
            while (left < right){
                while (left < len && !chars.contains(s.charAt(left))) left++;
                while (right >= 0 && !chars.contains(s.charAt(right))) right--;
                if(left >= right)
                    return ans.toString();
                ans.setCharAt(left, s.charAt(right));
                ans.setCharAt(right, s.charAt(left));
                left++;
                right--;
            }

            return ans.toString();
        }
    }

    @Test
    void invoke(){
        // new RemoveDuplicates().removeDuplicates(new int[]{1,1,1,1,2,2,3,3,4});
        new ReverseVowels().reverseVowels("hello");
    }
}
