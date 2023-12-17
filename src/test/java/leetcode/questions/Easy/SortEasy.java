package leetcode.questions.Easy;

import java.util.Arrays;

/**
 * @author Antares
 * @date 2022/9/5
 */
public class SortEasy {
    /**
     * 合并两个有序数组(我的解法：作弊，直接合并后排序，效率极高)
     */
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        for(int i = 0;i < n;i++){
            nums1[m+i] = nums2[i];
        }
        Arrays.sort(nums1);
    }

    /**
     * 答案解法：从大往小归并
     */
    public void merge0(int[] nums1, int m, int[] nums2, int n) {
        int i = m-1;
        int j = n-1;
        for(int k = m+n-1;k > -1;k--){
            if(i < 0 || j < 0){
                if(i < 0)
                    while (j > -1)
                        nums1[j] = nums2[j--];
                return;
            }
            if(nums1[i] >= nums2[j]){
                nums1[k] = nums1[i--];
            }else{
                nums1[k] = nums2[j--];
            }
        }
    }

    /**
     * 从大往小归并的一个更简洁的写法如下
     */
    public void merge1(int[] nums1, int m, int[] nums2, int n) {
        int i = m - 1;
        int j = n - 1;
        int end = m + n - 1;
        while (j >= 0) {
            nums1[end--] = (i >= 0 && nums1[i] > nums2[j]) ? nums1[i--] : nums2[j--];
        }
    }

    /**
     * 解法二：参照归并排序，最初就想到了，但是感觉效率很低，需要一个辅助数组temp，而且最后还要讲temp的值赋值给nums2
     * 事实证明这个算法的效率还可以
     */
    public void merge2(int[] nums1, int m, int[] nums2, int n) {
        int[] temp = new int[m+n];
        int i = 0,j = 0;
        while (i < m && j < n){
            if(nums1[i] <= nums2[j])
                temp[i+j] = nums1[i++];
            else
                temp[i+j] = nums2[j++];
        }

        while (i < m)
            temp[i+j] = nums1[i++];
        while (j < n)
            temp[i+j] = nums2[j++];

        for(i=0;i < m+n;i++){
            nums1[i] = temp[i];
        }
    }

    /**
     * 第一个错误的版本（我的解法：二分法递归版，思路是对的，但是超时）
     */
    public int firstBadVersion(int n) {
        return (int)firstBadVersionHelper(1, n);
    }

    public long firstBadVersionHelper(long start, long end){
        if(start == end)
            return start;
        long mid = (start + end)/2;
        if(isBadVersion((int)mid))
            return firstBadVersionHelper(start, mid);
        else
            return firstBadVersionHelper(mid + 1, end);
    }

    /**
     * 解法二（无递归版）
     */
    public int firstBadVersion0(int n) {
        long start = 0;
        long mid;
        while (true){
            if(start == n)
                return (int)start;
            mid = (start + n)/2;
            if(isBadVersion((int)mid)){
                n = (int)mid - 1;
            }else {
                start = (int)mid + 1;
            }
        }
    }


    public boolean isBadVersion(int n){
        if (n >= 1702766710)
            return true;
        return false;
    }

}
