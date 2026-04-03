package leetcode.algorithm;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;

import org.junit.jupiter.api.Test;

/**
 * @author Antares
 * @date 2022/10/14
 */
public class BinarySearch {
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
