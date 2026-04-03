package leetcode.binarysearch;

/**
 * 二分其他题：
 * 旋转有序数组
 */
public class OtherT {
    /**
     * 153. 寻找旋转排序数组中的最小值 [Medium]
     * 
     * 最小值就是下降点
     * 取mid，[l,mid]和[mid,r]至少有一个是有序的，另一个一定包含下降点
     * 如果nums[mid] > nums[r]，说明下降点一定在(mid,r]，l=mid+1
     * 如果nums[mid] <= nums[r]，说明下降点一定在[l,mid]，但不能排除mid，r=mid
     * 
     * 循环条件是while(l<r)，使用r=mid可以放心不会死循环
     */
    public int findMin(int[] nums) {
        int l = 0, r = nums.length - 1;
        while (l < r) {
            int mid = (l + r) / 2;
            // 左半区是有序的，或者只剩一个元素
            if (nums[mid] > nums[r]) {
                l = mid + 1;
            } else {
                r = mid;
            }
        }
        return nums[l];
    }

    /**
     * 154. 寻找旋转排序数组中的最小值 II [Hard]
     */
    public int findMinII(int[] nums) {
        int l = 0, r = nums.length - 1;
        while (l < r) {
            int mid = (l + r) / 2;
            if (nums[mid] > nums[r]) {
                l = mid + 1;
            } else if (nums[mid] < nums[r]) {
                r = mid;
            } else {
                r--;
            }
        }
        return nums[l];
    }

    /**
     * 33. 搜索旋转排序数组 [Medium]
     * 
     * 只在有序的半区内搜索，不断缩圈
     */
    public int search(int[] nums, int target) {
        int l = 0, r = nums.length - 1;
        while (l <= r) {
            int mid = (l + r) / 2;
            if (nums[mid] == target)
                return mid;

            // [l, mid] 是有序的
            if (nums[mid] > nums[r]) {
                // target在这个有序区间内
                if (target >= nums[l] && target < nums[mid]) {
                    r = mid - 1;
                } else {
                    l = mid + 1;
                }
            } else {
                if (target > nums[mid] && target <= nums[r]) {
                    l = mid + 1;
                } else {
                    r = mid - 1;
                }
            }
        }

        return -1;
    }

    /**
     * 4. 寻找两个正序数组的中位数 [Hard] <Star>
     * 
     * 同时对两个数组进行二分，找到第k小的元素
     * 用idx1和idx2分别指向nums1和nums2当前被排除的元素的下一个元素
     * 每次通过比较nums1[idx1+k/2-1]和nums2[idx2+k/2-1]的大小来排除掉k/2个元素(注意不能越界)，直至：
     * 1. nums1或nums2的所有元素都被排除掉了，直接返回另一个数组中第k小的元素
     * 2. k=1了，直接返回nums1[idx1]和nums2[idx2]中的较小值
     */
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int len1 = nums1.length, len2 = nums2.length;
        int tot = len1 + len2;
        if (tot % 2 == 1) {
            int mid = tot / 2;
            return getKthElement(nums1, nums2, mid + 1);
        } else {
            int mid1 = tot / 2 - 1, mid2 = tot / 2;
            return (getKthElement(nums1, nums2, mid1 + 1) + getKthElement(nums1, nums2, mid2 + 1)) / 2.0;
        }
    }

    // 找到nums1和nums2中第k小的元素（k从1开始）
    public int getKthElement(int[] nums1, int[] nums2, int k) {
        int len1 = nums1.length, len2 = nums2.length;
        // nums1和nums2的当前指针
        int idx1 = 0, idx2 = 0;

        while (true) {
            // 边界条件
            // nums1的所有元素都被排除了
            if (idx1 == len1) {
                return nums2[idx2 + k - 1];
            }
            // nums2的所有元素都被排除了
            if (idx2 == len2) {
                return nums1[idx1 + k - 1];
            }
            if (k == 1) {
                return Math.min(nums1[idx1], nums2[idx2]);
            }

            int half = k / 2;
            int newIdx1 = Math.min(idx1 + half, len1) - 1;
            int newIdx2 = Math.min(idx2 + half, len2) - 1;
            int pivot1 = nums1[newIdx1], pivot2 = nums2[newIdx2];
            if (pivot1 <= pivot2) {
                // 排除nums1[idx1, newIdx1]这段
                k -= (newIdx1 - idx1 + 1);
                idx1 = newIdx1 + 1;
            } else {
                k -= (newIdx2 - idx2 + 1);
                idx2 = newIdx2 + 1;
            }
        }
    }

    /**
     * 74. 搜索二维矩阵 [Medium]
     * 
     * O(m+n),O(logm+logn)
     */
    public boolean searchMatrix(int[][] matrix, int target) {
        int m = matrix.length, n = matrix[0].length;
        int l = 0, r = m * n - 1;
        while (l <= r) {
            int mid = (l + r) / 2;
            int x = mid / n, y = mid % n;
            if (matrix[x][y] > target) {
                r = mid - 1;
            } else if (matrix[x][y] < target) {
                l = mid + 1;
            } else {
                return true;
            }
        }
        return false;
    }
}
