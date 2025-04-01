package leetcode.questions.T1000.T100;

public class BinarySearchT {
    /*
     * 35. 搜索插入位置 [Easy]
     */
    public int searchInsert(int[] nums, int target) {
        int l = 0, r = nums.length - 1;
        while (l <= r) {
            int mid = (l + r) / 2;
            if (nums[mid] < target) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return l;
    }

    /*
     * 69. x 的平方根 [Easy]
     */
    public int mySqrt(int x) {
        long l = 1, r = x;
        while (l <= r) {
            long mid = (l + r) / 2;
            long square = mid * mid;
            if (square == x)
                return (int) mid;
            else if (square > x)
                r = mid - 1;
            else
                l = mid + 1;
        }
        return (int) r;
    }

    /*
     * ========================== 分割线 ==========================
     */

    /*
     * TODO 33. 搜索旋转排序数组 [Medium]
     * 
     * 旋转过后，mid左右两侧一定有一个半区是有序的，根据这个有序半区的范围以及target值，决定下一步二分的范围
     */
    public int search(int[] nums, int target) {
        int n = nums.length, l = 0, r = n - 1;
        while (l <= r) {
            int mid = (l + r) / 2;
            if (nums[mid] == target)
                return mid;

            // 首先判断升序的是哪个半区
            // 升序在左区间
            if (nums[l] <= nums[mid]) {
                // 在左半区进行搜索
                if (target >= nums[l] && target < nums[mid]) {
                    r = mid - 1;
                } else {
                    l = mid + 1;
                }
            } else {
                // 在右半区进行搜索
                if (target > nums[mid] && target <= nums[r]) {
                    l = mid + 1;
                } else {
                    r = mid - 1;
                }
            }
        }
        return -1;
    }

    /*
     * 34. 在排序数组中查找元素的第一个和最后一个位置 [Medium]
     * 
     * 首先按照二分查找，找到第一个位置start，然后以[start, ..]为范围再次进行二分查找。
     * 两次二分查找的边界条件显然是不同的
     */
    public int[] searchRange(int[] nums, int target) {
        int l = 0, r = nums.length - 1;
        while (l <= r) {
            int mid = (l + r) / 2;
            if (nums[mid] < target) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        if (l == nums.length || nums[l] != target) {
            return new int[] { -1, -1 };
        }
        int[] ans = new int[2];
        ans[0] = l;
        r = nums.length - 1;
        while (l <= r) {
            int mid = (l + r) / 2;
            if (nums[mid] > target) {
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        ans[1] = r;
        return ans;
    }

    /*
     * 74. 搜索二维矩阵
     * 
     * 假设是m行n列的矩阵，则进行m次二分，但是每次二分，右边界都缩小
     */
    public boolean searchMatrix(int[][] matrix, int target) {
        int m = matrix.length, n = matrix[0].length;
        int end = n - 1;
        for (int i = 0; i < m; i++) {
            int l = 0, r = end;
            // 要找到小于target的最大index
            while (l <= r) {
                int mid = (l + r) / 2;
                if (matrix[i][mid] == target)
                    return true;
                else if (matrix[i][mid] < target) {
                    l = mid + 1;
                } else {
                    r = mid - 1;
                }
            }
            end = r;
        }
        return false;
    }
}
