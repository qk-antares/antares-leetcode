package leetcode.questions.T1000.T900;

public class BinarySearchT {
    /*
     * 852. 山脉数组的峰顶索引 [Medium]
     * 
     * 类似于可重复旋转数组的最大值求解
     */
    public int peakIndexInMountainArray(int[] arr) {
        int n = arr.length, l = 0, r = n - 1;
        while (l <= r) {
            int mid = (l + r) / 2;
            // 判断mid位于上升区间还是下降区间
            if (mid == 0 || arr[mid] > arr[mid - 1]) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return r;
    }
}
