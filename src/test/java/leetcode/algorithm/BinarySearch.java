package leetcode.algorithm;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import org.junit.jupiter.api.Test;

/**
 * @author Antares
 * @date 2022/10/14
 */
public class BinarySearch {
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
     * 找出第 k 小的距离对，我的解法（暴力求解，可以求解，但是内存超出限制）
     */
    class SmallestDistancePair {
        public int smallestDistancePair(int[] nums, int k) {
            PriorityQueue<Integer> priorityQueue = new PriorityQueue<>((o1, o2) -> o2 - o1);

            int temp;
            for(int i = 0;i < nums.length;i++){
                for(int j = i + 1;j < nums.length;j++){
                    temp = Math.abs(nums[j] - nums[i]);
                    if(priorityQueue.size() <= k){
                        priorityQueue.add(temp);
                    }else{
                        if(temp < priorityQueue.peek()){
                            priorityQueue.poll();
                            priorityQueue.offer(temp);
                        }
                    }
                }
            }

            return priorityQueue.poll();
        }
        public int smallestDistancePair0(int[] nums, int k) {
            Arrays.sort(nums);
            //左右边界
            int left = 0, right = nums[nums.length - 1] - nums[0];
            int mid;
            int count;
            while (left < right){
                mid = left + (right - left) / 2;
                count = countSmallerThanMid(nums, mid);

                //小于等于mid的数量大于k，这证明我们要缩小右边界
                if(count < k){
                    left = mid + 1;
                }else{
                    right = mid;
                }
            }

            return left;
        }

        //计算小于等于mid的数对个数
        int countSmallerThanMid(int[] nums, int mid){
            int count = 0;
            for(int left = 0, right = 0;right < nums.length;right++){
                while (nums[right] - nums[left] > mid){
                    left++;
                }
                //一旦出现小于等于，后面的都是小于等于
                count += right - left;
            }

            return count;
        }

    }

    /**
     * 分割数组的最大值（这一题和算法课上的多窗口排队不一样，因为分割出的数组必须是连续的）
     */
    class SplitArray {
        /**
         * 审题不清
         */
        public int splitArray(int[] nums, int k) {
            Arrays.sort(nums);
            int[] ans = new int[k];

            int targetIndex;
            int temp;
            for(int i = nums.length - 1;i >= 0;i--){
                targetIndex = 0;
                ans[0] += nums[i];
                while (targetIndex + 1 < k && ans[0] > ans[targetIndex + 1]){
                    targetIndex++;
                }
                temp = ans[0];
                ans[0] = ans[targetIndex];
                ans[targetIndex] = temp;
            }

            return ans[k-1];
        }

        public int splitArray0(int[] nums, int k) {
            //left,right分别是左边界和右边界
            int left = Integer.MIN_VALUE,right = 0;

            for (int num : nums) {
                if(num > left){
                    left = num;
                }
                right += num;
            }

            int mid;
            int count;
            while (left < right){
                mid = left + (right - left) / 2;
                //以mid为标准可以分出多少个数组
                count = countSplits(nums, mid);

                //还没达到k，这证明mid太大了，移动右边界
                if(count <= k){
                    right = mid;
                }
                //超过k，这证明mid太小了，且不可能是mid
                else{
                    left = mid + 1;
                }
            }

            return left;

        }

        int countSplits(int[] nums, int mid){
            //ans的初始值为1，这是因为分出的最后一个数组没计入
            int ans = 1;
            int curSum = 0;
            for (int num : nums) {
                //超出mid了，则curSum中的数字应该被划分成一个整体
                if(curSum + num > mid){
                    ans++;
                    curSum = 0;
                }
                curSum += num;
            }

            return ans;
        }
    }


    @Test
    public void invoke(){
        // new FindMin().findMin(new int[]{2,1});
        // new SearchRange().searchRange(new int[]{}, 0);
        // new NextGreatestLetter().nextGreatestLetter(new char[]{'c','f','j'}, 'a');
        // new FindMin1().findMin(new int[]{3,3,1,3});
        // new FindMedianSortedArrays().findMedianSortedArrays(new int[]{}, new int[]{2});
        // PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(new Comparator<Integer>() {
        //     @Override
        //     public int compare(Integer o1, Integer o2) {
        //         return o2 - o1;
        //     }
        // });
        // priorityQueue.offer(1);
        // priorityQueue.offer(2);
        //
        // System.out.println(priorityQueue.poll());

        // new SmallestDistancePair().smallestDistancePair0(new int[]{1,3,1}, 1);
        new SplitArray().splitArray(new int[]{7,2,5,10,8},2);

    }
}
