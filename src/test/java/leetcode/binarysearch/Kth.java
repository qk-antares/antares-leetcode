package leetcode.binarysearch;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

public class Kth {
    /*
     * 378. 有序矩阵中第 K 小的元素 [Medium]
     * 
     * 二分答案，ans的值域是[matrix[0][0], matrix[r][r]]
     */
    public int kthSmallest(int[][] matrix, int k) {
        int n = matrix.length;
        int l = matrix[0][0], r = matrix[n - 1][n - 1];
        while (l <= r) {
            int mid = l + (r - l) / 2;
            // <=mid的数量>=k，在临界情况下，即<=mid的数量=k-1
            if (check(matrix, mid, k)) {
                r = mid - 1;
            } else { // <=mid的数量<k，在临界情况下，即<=mid的数量=k
                l = mid + 1;
            }
        }
        // r恰好不满足>=数量约束，而r+1恰好满足该约束
        return l;
    }

    // 计算<=target的元素个数是否>=k
    boolean check(int[][] matrix, int target, int k) {
        int n = matrix.length;
        int r = n - 1;
        int cnt = 0;
        // for循环中添加cnt<k的约束，能够更早结束循环
        for (int i = 0; i < n && cnt < k; i++) {
            // 搜索这一行<=target的最大位置
            int l = 0;
            while (l <= r) {
                int mid = (l + r) / 2;
                if (matrix[i][mid] > target) {
                    r = mid - 1;
                } else {
                    l = mid + 1;
                }
            }
            cnt += (r + 1);
        }
        return cnt >= k;
    }

    /*
     * 2040. 两个有序数组的第 K 小乘积 [Hard]
     * 
     * 不能创建matrix[][]，会超内存限制，只能现算...
     */
    public long kthSmallestProduct(int[] nums1, int[] nums2, long k) {
        int m = nums1.length, n = nums2.length;

        // 二分找到nums1和nums2中第一个>=0的位置
        int l = 0, r = m - 1;
        while (l <= r) {
            int mid = (l + r) / 2;
            if (nums1[mid] < 0) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        int x = l;

        l = 0;
        r = n - 1;
        while (l <= r) {
            int mid = (l + r) / 2;
            if (nums2[mid] < 0) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        int y = l;

        List<Long> cornors = Arrays.asList((long) nums1[0] * nums2[n - 1], (long) nums1[m - 1] * nums2[0],
                (long) nums1[0] * nums2[0], (long) nums1[m - 1] * nums2[n - 1]);

        long min = Collections.min(cornors);
        long max = Collections.max(cornors);
        while (min <= max) {
            long mid = (min + max) / 2;
            // >=k k-1
            if (check(nums1, nums2, mid, k, x, y)) {
                max = mid - 1;
            } else { // <k k
                min = mid + 1;
            }
        }

        return min;
    }

    // 统计矩阵<=target的元素数量，是否>=k
    boolean check(int[] nums1, int[] nums2, long target, long k, int x, int y) {
        int m = nums1.length, n = nums2.length;
        long cnt = 0;
        int l, r;

        if (target < 0) {
            // 只在右上和左下搜索

            // 右上区域
            l = y;
            for (int i = 0; i < x && l < n; i++) {
                r = n - 1;
                while (l <= r) {
                    int mid = (l + r) / 2;
                    if ((long) nums1[i] * nums2[mid] > target) {
                        l = mid + 1;
                    } else {
                        r = mid - 1;
                    }
                }
                cnt += (n - l);
            }

            // 左下区域
            r = y - 1;
            for (int i = m - 1; i >= x && r >= 0; i--) {
                l = 0;
                while (l <= r) {
                    int mid = (l + r) / 2;
                    if ((long) nums1[i] * nums2[mid] > target) {
                        r = mid - 1;
                    } else {
                        l = mid + 1;
                    }
                }
                cnt += (r + 1);
            }
        } else {
            // 只在左上和右下搜索
            cnt += (long) x * (n - y) + (long) (m - x) * y;

            // 左上区域
            l = 0;
            for (int i = x - 1; i >= 0 && l < y; i--) {
                r = y - 1;
                while (l <= r) {
                    int mid = (l + r) / 2;
                    if ((long) nums1[i] * nums2[mid] > target) {
                        l = mid + 1;
                    } else {
                        r = mid - 1;
                    }
                }
                cnt += (y - l);
            }

            // 右下区域
            r = n - 1;
            for (int i = x; i < m && r >= y; i++) {
                l = y;
                while (l <= r) {
                    int mid = (l + r) / 2;
                    if ((long) nums1[i] * nums2[mid] > target) {
                        r = mid - 1;
                    } else {
                        l = mid + 1;
                    }
                }
                cnt += (r - y + 1);
            }
        }

        return cnt >= k;
    }

    //下面这个check函数效率更高，不知道什么原因
    boolean check0(int[] a, int[] b, long mx, long k, int i0, int j0) {
        int n = a.length;
        int m = b.length;
        long cnt = 0;

        if (mx < 0) {
            // 右上区域
            int i = 0;
            int j = j0;
            while (i < i0 && j < m) { // 不判断 cnt < k 更快
                if ((long) a[i] * b[j] > mx) {
                    j++;
                } else {
                    cnt += m - j;
                    i++;
                }
            }

            // 左下区域
            i = i0;
            j = 0;
            while (i < n && j < j0) {
                if ((long) a[i] * b[j] > mx) {
                    i++;
                } else {
                    cnt += n - i;
                    j++;
                }
            }
        } else {
            // 右上区域和左下区域的所有数都 <= 0 <= mx
            cnt = (long) i0 * (m - j0) + (long) (n - i0) * j0;

            // 左上区域
            int i = 0;
            int j = j0 - 1;
            while (i < i0 && j >= 0) {
                if ((long) a[i] * b[j] > mx) {
                    i++;
                } else {
                    cnt += i0 - i;
                    j--;
                }
            }

            // 右下区域
            i = i0;
            j = m - 1;
            while (i < n && j >= j0) {
                if ((long) a[i] * b[j] > mx) {
                    j--;
                } else {
                    cnt += j - j0 + 1;
                    i++;
                }
            }
        }

        return cnt >= k;
    }

    @Test
    public void test() {
        int[] nums1 = { -2, -1, 0, 1, 2 };
        int[] nums2 = { -3, -1, 2, 4, 5 };
        System.out.println(kthSmallestProduct(nums1, nums2, 3)); // Output: -8
    }
}
