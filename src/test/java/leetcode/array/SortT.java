package leetcode.array;

/**
 * 关于排序
 */
public class SortT {
    /**
     * 75. 颜色分类 [Medium]
     * 
     * p0和p1记录0和1的插入指针，i本身就是2的插入指针
     */
    public void sortColors(int[] nums) {
        int p0 = 0, p1 = 0;
        for (int i = 0; i < nums.length; i++) {
            int tmp = nums[i];
            nums[i] = 2;
            if (tmp <= 1) {
                nums[p1++] = 1;
            }
            if (tmp == 0) {
                nums[p0++] = 0;
            }
        }
    }
}
