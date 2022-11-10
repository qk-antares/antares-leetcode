package leetcode.algorithm;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Consumer;

/**
 * @author Antares
 * @date 2022/10/14
 */
public class BinarySearch {
    /**
     * 寻找旋转排序数组中的最小值（实质等于找数组中的第一个下降点，如果当前的值小于首位，就证明已经过了下降点，如果找不到，则首位就是下降点）
     */
    class FindMin {
        public int findMin(int[] nums) {
            //首先将首位存储起来
            return binarySearch(nums, 1, nums.length-1, nums[0]);
        }

        public int binarySearch(int[] nums, int begin, int end, int head){
            if(end > nums.length - 1 || begin > nums.length - 1)
                return head;

            int mid = begin + (end - begin) / 2;

            if(nums[mid] < head && nums[mid-1] >= head)
                return nums[mid];

            if(nums[mid] > head){
                return binarySearch(nums, mid + 1, end, head);
            }else {
                return binarySearch(nums, begin, mid - 1, head);
            }
        }
    }

    /**
     * 猜数字大小
     *
     * 你可以通过调用一个预先定义好的接口 int guess(int num) 来获取猜测结果，返回值一共有 3 种可能的情况（-1，1 或 0）：
     *
     * -1：我选出的数字比你猜的数字小 pick < num
     * 1：我选出的数字比你猜的数字大 pick > num
     * 0：我选出的数字和你猜的数字一样。恭喜！你猜对了！pick == num
     */
    public class GuessNumber {
        public int guessNumber(int n) {
            int left = 1, right = n;
            int mid, res;
            while (left < right){
                mid = left + (right - left) / 2;
                res = guess(mid);
                switch (res){
                    case -1 : left = mid + 1;break;
                    case 1 : right = mid - 1;break;
                    case 0 : return mid;
                }
            }

            return -1;
        }

        public int guess(int num){
            return -1;
        }


    }

    /**
     * 搜索旋转排序数组
     */
    class Search {
        public int search(int[] nums, int target) {
            int left = 0, right = nums.length - 1;
            int mid;

            while (left <= right){
                mid = left + (right - left) / 2;
                if(nums[mid] == target)
                    return mid;
                //首先判断中点落到了哪里
                if(nums[mid] > nums[right]){
                    //落在了左递增区间

                    if(target >= nums[left] && target < nums[mid]){
                        //target在左边
                        right = mid - 1;
                    }else {
                        left = mid + 1;
                    }
                }
                else {
                    //落在了右递增区间

                    if(target > nums[mid] && target <= nums[right]){
                        left = mid + 1;
                    }else {
                        right = mid - 1;
                    }
                }
            }

            return -1;
        }
    }

    /**
     * 寻找峰值（等价于寻找第一个下降点）
     */
    class FindPeakElement {
        public int findPeakElement(int[] nums) {
            int left = 0, right = nums.length - 1;
            int mid;
            while (left <= right){
                mid = left + (right - left) / 2;
                if(mid + 1 > nums.length - 1 || nums[mid] > nums[mid + 1]){
                    //mid是一个下降点
                    if(mid - 1 < 0 || nums[mid] > nums[mid-1])
                        return mid;
                    right = mid;
                }
                else {
                    //mid不是下降点，但其右边一定有下降点
                    left = mid + 1;
                }
            }

            return -1;
        }
    }

    /**
     * 寻找旋转排序数组中的最小值
     */
    class FindMin0 {
        public int findMin(int[] nums) {
            int left = 0, right = nums.length - 1;
            int mid;
            while (left <= right){
                mid = left + (right - left) / 2;
                //首先判断mid落在哪里
                if(nums[mid] > nums[right]){
                    //mid落在了左递增

                    left = mid + 1;
                }else {
                    //mid落在了右递增
                    if(mid-1 < 0 || nums[mid] < nums[mid-1])
                        return nums[mid];

                    right = mid - 1;
                }
            }

            return -1;
        }
    }

    /**
     * 在排序数组中查找元素的第一个和最后一个位置（答案解法：分别寻找左右边界，相当于两次二分搜索，需要注意的是这里使用的是二分搜索的模板III）
     * 显著特征是循环条件为l+1<r
     * 当循环终止是，l和r刚好位于边界上
     */
    class SearchRange {
        public int[] searchRange(int[] nums, int target) {
            int[] ans = new int[2];
            ans[0] = binarySearchLeft(nums, target);
            ans[1] = ans[0] == -1 ? -1 : binarySearchRight(nums, target, ans[0]);
            return ans;
        }

        public int binarySearchLeft(int[] nums, int target){
            if(nums.length == 0)
                return -1;

            int left = 0, right = nums.length-1;
            int mid;
            while (left + 1 < right){
                mid = left + (right - left) / 2;
                if(nums[mid] < target)
                    left = mid;
                else
                    right = mid;
            }

            if(nums[left] == target) return left;
            if(nums[right] == target) return right;
            return -1;
        }

        public int binarySearchRight(int[] nums, int target, int start){
            int left = start, right = nums.length-1;
            int mid;
            while (left + 1 < right){
                mid = left + (right - left) / 2;
                if(nums[mid] > target)
                    right = mid;
                else
                    left = mid;
            }

            if(nums[right] == target) return right;
            if(nums[left] == target) return left;
            return -1;
        }
    }

    class FindClosestElements {
        public List<Integer> findClosestElements(int[] arr, int k, int x) {
            //首先使用二分找到最接近x的元素的位置
            int l = 0, r = arr.length - 1;
            int mid = -1;
            //最终l和r停在边界上
            while (l + 1 < r){
                mid = l + (r - l) / 2;
                if(arr[mid] > x)
                    r = mid;
                else
                    l = mid;
            }

            LinkedList<Integer> ans = new LinkedList<>();

            while (k > 0 && l >= 0 && r < arr.length){
                if(Math.abs(x - arr[l]) <= Math.abs(x - arr[r])){
                    ans.addFirst(arr[l]);
                    l--;

                }else {
                    ans.addLast(arr[r]);
                    r++;
                }
                k--;
            }

            while (k > 0 && r < arr.length){
                ans.addLast(arr[r++]);
                k--;
            }

            while (k > 0 && l >= 0){
                ans.addFirst(arr[l--]);
                k--;
            }

            return ans;
        }
    }

    /**
     * Pow(x, n)
     * 2^26
     *
     * 26 -->      2^2
     * 13 --> 2^2, 2^4
     * 6 -->       2^8
     * 3 --> 2^10, 2^16
     * 1 --> 2^26, 2^32
     * 0 break
     */
    class MyPow {
        /**
         * 2.00000
         * -2
         * ---
         * 2^2
         *
         * 2 --> 2^2
         * 1 -->
         *
         */
        public double myPow(double x, int n) {
            double ans = 1.0;
            for(int i = n;i != 0;i /= 2){
                if(i % 2 != 0){
                    ans *= x;
                }
                x *= x;
            }

            return n > 0 ? ans : 1.0 / ans;
        }
    }

    /**
     * 有效的完全平方数
     */
    class IsPerfectSquare {
        public boolean isPerfectSquare(int num) {
            int l = 1, r = num;
            int mid;
            while (l <= r){
                mid = l + (r - l) / 2;
                if((long)mid * (long)mid > num)
                    r = mid - 1;
                else if (mid * mid < num) {
                    l = mid + 1;
                }
                else
                    return true;
            }

            return false;
        }
    }

    /**
     * 寻找比目标字母大的最小字母
     */
    class NextGreatestLetter {
        public char nextGreatestLetter(char[] letters, char target) {
            //使用二分模板III
            int l = 0, r = letters.length - 1;
            int mid;
            while (l + 1 < r){
                mid = l + (r - l) / 2;
                if(letters[mid] > target)
                    r = mid;
                else
                    l = mid;
            }

            if(letters[l] > target)
                return letters[l];
            if(letters[r] > target)
                return letters[r];
            else
                return letters[0];
        }
    }

    /**
     * 寻找旋转排序数组中的最小值 II
     */
    class FindMin1 {
        public int findMin(int[] nums) {
            int l = 0, r = nums.length - 1;
            int mid;

            while (l + 1 < r){
                mid = l + (r - l) / 2;
                //首先判断中点的位置（遇到和左边界相等，先缩小左边界）
                if(nums[mid] < nums[r]){
                    //中点位于右递增序列
                    r = mid;
                } else if (nums[mid] == nums[r]) {
                    r--;
                } else {
                    l = mid;
                }
            }

            return Math.min(nums[0], Math.min(nums[l], nums[r]));
        }
    }

    /**
     * 两个数组的交集（双指针）
     */
    class Intersection {
        public int[] intersection(int[] nums1, int[] nums2) {
            Arrays.sort(nums1);
            Arrays.sort(nums2);

            int i = 0, j = 0;
            HashSet<Integer> ans = new HashSet<>();
            while (i < nums1.length && j < nums2.length){
                if(nums1[i] == nums2[j]){
                    ans.add(nums1[i++]);
                    j++;
                } else if (nums1[i] > nums2[j]) {
                    j++;
                }else{
                    i++;
                }
            }

            // int[] ints = new int[ans.size()];
            // i = 0;
            // for (Integer an : ans) {
            //     ints[i++] = an;
            // }
            //
            // return ints;

            //使用迭代器效率更高，为什么？
            Iterator<Integer> iterator = ans.iterator();
            int[] ints = new int[ans.size()];
            i = 0;
            while (iterator.hasNext()){
                ints[i++] = iterator.next();
            }

            return ints;
        }
    }

    /**
     * 寻找重复数（我的解法，求和再减）
     */
    class FindDuplicate {
        public int findDuplicate(int[] nums) {
            HashSet<Integer> integers = new HashSet<>();
            for (int num : nums) {
                if(!integers.add(num))
                    return num;
            }

            return -1;
        }
    }

    /**
     * 寻找两个正序数组的中位数
     */
    class FindMedianSortedArrays {
        /**
         * 双指针法：时间复杂度O(m+n)
         */
        public double findMedianSortedArrays(int[] nums1, int[] nums2) {
            int m = nums1.length;
            int n = nums2.length;

            int[] ints = new int[(m + n)/2 + 1];
            int i = 0, j = 0, k = 0;
            while (i < m && j < n && k < ints.length){
                if(nums1[i] < nums2[j]){
                    ints[k++] = nums1[i++];
                }else {
                    ints[k++] = nums2[j++];
                }
            }

            while (i < m && k < ints.length){
                ints[k++] = nums1[i++];
            }
            while (j < n && k < ints.length){
                ints[k++] = nums2[j++];
            }

            if((m + n) % 2 == 0){
                return (ints[k-1] + ints[k-2]) / 2.0;
            }else {
                return ints[k-1];
            }
        }

        /**
         * 二分法（后续研究下）
         */
        public double findMedianSortedArrays0(int[] nums1, int[] nums2) {
            //找长度短的数组的分界线更快
            if (nums1.length>nums2.length){
                int [] temp=nums1;
                nums1=nums2;
                nums2=temp;
            }
            int m=nums1.length;
            int n=nums2.length;
            //奇数个就让左边多一个，左边的最大值就是中位数
            int totalLeft=(m+n+1)/2;
            int left=0;
            int right=m;
            while(left<right){
                int i=left+((right-left+1)>>1);
                int j=totalLeft-i;
                //第一个数组分界线左边的那个数一定要小于等于第二个数组分界线右边的数
                if (nums1[i-1]>nums2[j]){
                    right=i-1;
                }
                else{
                    left=i;
                }
            }
            int i=left;
            int j=totalLeft-i;
            int nums1LeftMax=i==0?Integer.MIN_VALUE:nums1[i-1];
            int nums1RightMin=i==m?Integer.MAX_VALUE:nums1[i];
            int nums2LeftMax=j==0?Integer.MIN_VALUE:nums2[j-1];
            int nums2RightMin=j==n?Integer.MAX_VALUE:nums2[j];
            if ((m+n)%2==0){
                return (Math.max(nums1LeftMax,nums2LeftMax)+Math.min(nums1RightMin,nums2RightMin))*1.0/2;
            }
            else {
                return Math.max(nums1LeftMax,nums2LeftMax);
            }
        }
    }

    /**
     * 找出第 k 小的距离对
     */
    class SmallestDistancePair {
        public int smallestDistancePair(int[] nums, int k) {
            Arrays.sort(nums);
            for(int i = 1;i < nums.length;i++){
                nums[i-1] = Math.abs(nums[i] - nums[i-1]);
            }
            nums[nums.length-1] = Integer.MAX_VALUE;

            Arrays.sort(nums);

            return nums[k-1];
        }
    }


    @Test
    public void invoke(){
        // new FindMin().findMin(new int[]{2,1});
        // new SearchRange().searchRange(new int[]{}, 0);
        // new NextGreatestLetter().nextGreatestLetter(new char[]{'c','f','j'}, 'a');
        // new FindMin1().findMin(new int[]{3,3,1,3});
        new FindMedianSortedArrays().findMedianSortedArrays(new int[]{}, new int[]{2});
    }
}
