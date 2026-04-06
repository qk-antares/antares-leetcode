package leetcode.array;

/**
 * 数组
 * 智力题
 */
public class BrainTeaserT {
    /**
     * 41. 缺失的第一个正数 [Hard]
     * 
     * i是一个座位，它上面应该坐i+1
     * 每次看一个座位，把该座位的元素放到正确的位置上（通过交换实现）
     * 直至该座位的元素超出范围或者该座位的元素符合要求
     * 最后从前往后
     * 
     */
    public int firstMissingPositive(int[] nums) {
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            // i座位上坐的nums[i]在范围内，但没放到正确位置
            while (nums[i] > 0 && nums[i] <= n && nums[nums[i] - 1] != nums[i]) {
                int tmp = nums[i];
                nums[i] = nums[tmp - 1];
                nums[tmp - 1] = tmp;
            }
        }

        for (int i = 0; i < n; i++) {
            if (nums[i] != i + 1)
                return i + 1;
        }

        return n + 1;
    }

}
