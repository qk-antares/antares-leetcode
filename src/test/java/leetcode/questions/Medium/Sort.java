package leetcode.questions.Medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Set;

import org.junit.jupiter.api.Test;

/**
 * @author Antares
 * @date 2022/9/26
 */
public class Sort {
    /**
     * 前 K 个高频元素
     */
    static class TopKFrequent{
        /**
         * 我的解法：使用ArrayList + HashMap + 遍历一遍数组，过是过了，但是效率太低，尤其是时间复杂度太高
         */
        public int[] topKFrequent(int[] nums, int k) {
            ArrayList<Integer> count = new ArrayList<>();

            int index = 0;

            HashMap<Integer, Integer> numsVisited = new HashMap<>();
            for(int i = 0;i < nums.length;i++){
                if(!numsVisited.containsKey(nums[i])){
                    nums[index] = nums[i];
                    numsVisited.put(nums[i], index);
                    count.add(1);
                    index++;
                } else{
                  count.set(numsVisited.get(nums[i]), count.get(numsVisited.get(nums[i]))+1);
                }
            }

            for(int i = 0;i < k;i++){
                for(int j = i+1;j < count.size();j++){
                    if(count.get(i) < count.get(j)){
                        int temp = count.get(i);
                        count.set(i, count.get(j));
                        count.set(j, temp);
                        temp = nums[i];
                        nums[i] = nums[j];
                        nums[j] = temp;
                    }
                }
            }

            return Arrays.copyOf(nums, k);

        }

        /**
         * 答案解法：最大堆，使用了PriorityQueue这个数据结构和HashMap的getOrDefault，并且将HashMap中的（k, v）转成了数组来解决
         * getOrDefault(key, returnVal) 方法获取指定 key 对应对 value，如果找不到 key ，则返回设置的默认值。
         * 如何设置HashMap中的val+1？ map.put(key, map.getOrDefault(key, 0)+1)
         *
         * 在优先级队列中，添加的对象根据其优先级。默认情况下，优先级由对象的自然顺序决定。队列构建时提供的比较器可以覆盖默认优先级。
         * 1、PriorityQueue是一个无限制的队列，并且动态增长。默认初始容量11，可以使用相应构造函数中的initialCapacity参数覆盖。
         * 2、不允许NULL对象。
         * 3、添加到PriorityQueue的对象必须具有可比性。
         * 4、默认情况下，优先级队列的对象按自然顺序排序；比较器可用于队列中对象的自定义排序。
         * 5、优先级队列的头部是基于自然排序或基于比较器的排序的最小元素。当我们轮询队列时，它从队列中返回头对象。
         * 6、如果存在多个具有相同优先级的对象，则它可以随机轮询其中任何一个。
         * 7、PriorityQueue 不是线程安全的。PriorityBlockingQueue在并发环境中使用。
         * 8、它为add和poll方法提供了O（log（n））时间。
         */
        public int[] topKFrequent0(int[] nums, int k) {
            //首先统计每个数字的出现次数
            HashMap<Integer, Integer> count = new HashMap<>();
            for (int num : nums) {
                count.put(num, count.getOrDefault(num, 0) + 1);
            }

            PriorityQueue<int[]> priorityQueue = new PriorityQueue<>((a, b) -> b[1] - a[1]);

            Set<Integer> keySet = count.keySet();
            for (Integer integer : keySet) {
                priorityQueue.add(new int[]{integer, count.get(integer)});
            }

            //取堆中前k大元素
            int[] ans = new int[k];
            for(int i = 0;i < k;i++){
                ans[i] = priorityQueue.poll()[0];
            }

            return ans;
        }
    }

    /**
     * 数组中的第K个最大元素（要求时间复杂度必须是O(n)，没做出来）
     *
     */
    class FindKthLargest{
        /**
         * 答案解法一：使用Java的最小堆，效率太低了
         * 遍历数组中的元素，添加到最小堆中
         */
        public int findKthLargest(int[] nums, int k) {
            PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(k+1);
            for (int num : nums) {
                priorityQueue.add(num);
                if(priorityQueue.size() > k)
                    priorityQueue.poll();
            }
            return priorityQueue.peek();
        }

        /**
         * 答案解法二：使用类似快排的思路（效率大有提升）
         */
        public int findKthLargest0(int[] nums, int k) {
            int index = 0;
            int left = 0, right = nums.length-1;
            while (true){
                index = partition(nums, left, right);
                if(index < k-1){
                    left = index+1;
                } else if (index > k-1) {
                    right = index-1;
                } else {
                    return nums[index];
                }
            }
        }

        //将第一个元素作为pivot，返回其index
        public int partition(int nums[], int left, int right){
            int key = nums[left];
            while (left < right){
                while (left < right && nums[right] < key) right--;
                if(left < right)
                    nums[left++] = nums[right];
                while (left < right && nums[left] > key) left++;
                if(left < right)
                    nums[right--] = nums[left];
            }
            nums[left] = key;
            return left;
        }

        /**
         * 答案解法三：手动实现堆排序（执行用时不如快排，内存消耗好于快排）
         */
        public int findKthLargest1(int[] nums, int k) {
            //构建大顶堆
            heapify(nums, nums.length);
            for(int i = 0;i < nums.length && k != 1;i++,k--){
                swap(nums,0, nums.length-1-i);
                heap(nums, nums.length-1-i, 0);
            }

            //返回当前的堆顶
            return nums[0];
        }

        /**
         * 建堆，将无序的数组建成大根堆
         */
        public void heapify(int[] nums, int n){
            //最后一个父节点的位置
            int last = (n-2)/2;
            for(int i = last;i >= 0;i--){
                heap(nums, n, i);
            }
        }

        /**
         * 堆化（大顶堆）
         * @param nums：是数组
         * @param n：n是数组中的元素个数
         * @param i：要做堆化的那个结点
         */
        public void heap(int[] nums, int n, int i){
            //左右子节点
            int left = 2*i+1, right = 2*i+2;
            //默认根节点是最大值的索引
            int max = i;
            if(left < n && nums[left] > nums[max]){
                max = left;
            }
            if(right < n && nums[right] > nums[max]){
                max = right;
            }
            //发生了交换，对被交换的那颗子树再进行堆化
            if(max != i){
                swap(nums, max, i);
                heap(nums, n, max);
            }
        }

        public void swap(int[] nums, int i, int j){
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }

    }

    /**
     * 寻找峰值（要求时间复杂度必须是O(log n)，没想出来，知道一定是需要使用二分的）
     */
    static class FindPeakElement{
        /**
         * 该问题等价于寻找下降点
         */
        public int findPeakElement(int[] nums) {
            return binarySearch(nums, 0, nums.length-1);
        }

        public int binarySearch(int[] nums, int begin, int end){
            int mid = begin + (end - begin)/2;
            if(begin < end){
                //左边一定有下降点（可能是mid）
                if(nums[mid] > nums[mid+1]){
                    return binarySearch(nums, begin, mid);
                }
                //不能确定左边是否有下降点，但是右边一定有下降点（可能是mid+1）
                else{
                    return binarySearch(nums, mid+1, end);
                }
            }
            return mid;
        }

    }

    @Test
    public void invoke(){
//        new FindKthLargest().findKthLargest0(new int[]{3,2,3,1,2,4,5,5,6}, 4);
//        new SearchRange().searchRange(new int[]{1},1);
        /**
         * [5,1,3]
         * 5
         */
        //new Search().binarySearchDeclinePoint(new int[]{5,1,3}, 0, 2);
        //System.out.println(new Search().binarySearchDeclinePoint(new int[]{5,1,3}, 0, 2));

        /**
         * [3,1]
         * 1
         */
//        new Search().search0(new int[]{3,1}, 1);
//        new SearchMatrix().searchMatrix(new int[][]{{-5}}, -5);
        //new SearchMatrix().searchMatrix(new int[][]{{1,4,7,11,15},{2,5,8,12,19},{3,6,9,16,22},{10,13,14,17,24},{18,21,23,26,30}}, 20);
    }
}
