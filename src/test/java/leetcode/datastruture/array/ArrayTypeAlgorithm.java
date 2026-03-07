package leetcode.datastruture.array;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class ArrayTypeAlgorithm {
    /**
     * 删除排序数组中的重复项 II
     */
    class RemoveDuplicates {
        public int removeDuplicates(int[] nums) {
            // i是当前应当插入的位置，j是当前遍历到的位置，count是计数器
            int i = 1, j = 1, count = 1;

            for (; j < nums.length; j++) {
                if (nums[j] == nums[j - 1]) {
                    if (count < 2) {
                        nums[i++] = nums[j];
                    }
                    count++;
                } else {
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
            chars.addAll(Arrays.asList('a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U'));

            int left = 0, right = s.length() - 1;
            while (left < right) {
                while (left < len && !chars.contains(s.charAt(left)))
                    left++;
                while (right >= 0 && !chars.contains(s.charAt(right)))
                    right--;
                if (left >= right)
                    return ans.toString();
                ans.setCharAt(left, s.charAt(right));
                ans.setCharAt(right, s.charAt(left));
                left++;
                right--;
            }

            return ans.toString();
        }
    }

    /**
     * 42. 接雨水 [Hard]
     * 
     * 前后缀分解：
     * 记录每个元素左侧的最大值lMax和右侧的最大值rMax
     * 
     * 相向双指针：
     * 不使用数组提前计算，而是用两个指针分别从两端向中间遍历，记录当前的lMax和rMax
     * 假设lMax < rMax，那么对于l指针所在位置，能接的雨水量取决于lMax，因为rMax比lMax更高，不会成为限制因素
     * 同理，假设rMax < lMax，那么对于r指针所在位置，能接的雨水量取决于rMax
     * 
     * 单调栈：
     * 单调栈的方法是一行一行接雨水
     * 想象输入是一个数据流，如果是递减的，就塞进栈中
     * 当遇到一个比栈顶大的，弹出栈顶idx，则此时的栈顶是idx左侧的高点
     * ans += (Math.min(h[l],h[r])-h[idx]) * (i-l-1)
     */
    public int trap0(int[] height) {
        int n = height.length;
        int[] rMax = new int[n];
        for (int i = n - 2; i >= 0; i--) {
            rMax[i] = Math.max(rMax[i + 1], height[i + 1]);
        }

        int ans = 0;
        int lMax = 0;
        for (int i = 0; i < n; i++) {
            ans += Math.max(0, Math.min(lMax, rMax[i]) - height[i]);
            lMax = Math.max(lMax, height[i]);
        }
        return ans;
    }

    public int trap1(int[] height) {
        int l = 0, r = height.length - 1;
        int lMax = 0, rMax = 0;
        int ans = 0;
        while (l <= r) {
            lMax = Math.max(lMax, height[l]);
            rMax = Math.max(rMax, height[r]);
            if (lMax < rMax) {
                ans += lMax - height[l++];
            } else {
                ans += rMax - height[r--];
            }
        }
        return ans;
    }

    public int trap2(int[] height) {
        ArrayDeque<Integer> stk = new ArrayDeque<>();
        int ans = 0;
        for (int i = 0; i < height.length; i++) {
            while (!stk.isEmpty() && height[stk.peek()] <= height[i]) {
                int idx = stk.pop();
                if (stk.isEmpty())
                    break;
                int l = stk.peek();
                ans += (Math.min(height[l], height[i]) - height[idx]) * (i - l - 1);
            }
            stk.push(i);
        }
        return ans;
    }

    @Test
    void invoke() {
        // new RemoveDuplicates().removeDuplicates(new int[]{1,1,1,1,2,2,3,3,4});
        // new ReverseVowels().reverseVowels("hello");
        // new MaxArea().maxArea0(new int[]{1,8,6,2,5,4,8,3,7});
        // new MaxArea().maxArea0(new int[]{1,1});
    }
}
