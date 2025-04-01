package leetcode.questions.T1000.T400.hard;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class BinarySearchT {
    /*
     * 315. 计算右侧小于当前元素的个数
     * 
     * 维护一个sorted数组，在从右往左进行遍历的同时进行插入排序
     * 计算当前位置的答案时，在sorted中进行二分查找
     * 注入插入排序从sorted的尾端开始比较，如果sored[pos] > cur，sorted[pos+1]=sorted[pos]
     */
    public List<Integer> countSmaller(int[] nums) {
        int n = nums.length;
        int[] sorted = new int[n];
        Integer[] ans = new Integer[n];
        int len = 0;
        for (int i = n - 1; i >= 0; i--) {
            // 在sorted中进行二分查找
            int l = 0, r = len - 1;
            while (l <= r) {
                int mid = (l + r) / 2;
                if (sorted[mid] < nums[i]) {
                    l = mid + 1;
                } else {
                    r = mid - 1;
                }
            }
            ans[i] = l;

            // 通过插入排序插入到sorted
            int pos = len - 1;
            while (pos > 0 && sorted[pos] > nums[i]) {
                sorted[pos + 1] = sorted[pos];
                pos -= 1;
            }
            sorted[pos + 1] = nums[i];
            len++;
        }
        return Arrays.asList(ans);
    }

    @Test
    public void test() {
        int[] nums = { 1, 0, 2 };
        System.out.println(countSmaller(nums));
    }
}
