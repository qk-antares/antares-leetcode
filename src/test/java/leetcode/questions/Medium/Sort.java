package leetcode.questions.Medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import org.junit.jupiter.api.Test;

/**
 * @author Antares
 * @date 2022/9/26
 */
public class Sort {
    /**
     * 颜色分类(就是一个简单的排序问题，由于要求原地排序，不能使用辅助数组，所以不能用归并排序)
     */
    static class SortColors{
        /**
         * 我的解法
         */
        public void sortColors(int[] nums) {
            quickSort(nums, 0, nums.length-1);
        }

        public void quickSort(int[] nums, int begin, int end){
            if(begin >= end)
                return;

            int key = nums[begin];

            int i = begin, j = end;

            while (i < j){
                //从右到左找小于key的数
                while (i < j && key <= nums[j]){
                    j--;
                }
                //把小于key的这个数填进坑里
                if(i < j){
                    nums[i] = nums[j];
                }
                //从左到右找大于key的数
                while (i < j && key >= nums[i]){
                    i++;
                }
                if(i < j){
                    nums[j] = nums[i];
                }
            }

            nums[i] = key;
            quickSort(nums, begin, i-1);
            quickSort(nums, i+1, end);
        }

        /**
         * 答案解法一：三指针
         */
        public void sortColors0(int[] nums) {
            int left = 0,right = nums.length-1, index = 0;

            while (index <= right){
                if(nums[index] == 0){
                    //注意这里index++
                    swap(nums, left++, index++);
                } else if (nums[index] == 1) {
                    index++;
                } else if (nums[index] == 2) {
                    //这里index不变，这是因为，后面交换来的数没有被index指针访问过，不能确定它是否为0，如果为0，还要将其前置到数组首部
                    swap(nums, right--, index);
                }
            }
        }

        public void swap(int[] nums, int i, int j){
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }

        /**
         * 答案解法二（快排，但是更规整，标准的快排写法）
         */
        public void sortColors1(int[] nums) {
            quickSort1(nums,0,nums.length-1);
        }

        public void quickSort1(int[] nums,int left,int right){
            if(left >= right) return;
            int i = partition(nums,left,right);
            quickSort(nums,left,i-1);
            quickSort(nums,i+1,right);
        }

        public int partition(int[] nums,int left,int right){
            int i = left,j = right;
            while(i < j){
                while(i < j && nums[j] >= nums[left]) j--;
                while(i < j && nums[i] <= nums[left]) i++;
                swap(nums,i,j);
            }
            swap(nums,i,left);
            return i;
        }
    }

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

    /**
     * 在排序数组中查找元素的第一个和最后一个位置（我的解法：二分，分别寻找左右边界）
     */
    static class SearchRange{
        /**
         * 我的解法（执行用时短，但内存消耗较高）
         */
        public int[] searchRange(int[] nums, int target) {
            int left = binarySearchLeft(nums, 0, nums.length-1, target);
            if(left == -1)
                return new int[]{-1, -1};
            else {
                return new int[]{left, binarySearchRight(nums, 0, nums.length-1, target)};
            }
        }

        //寻找左边界
        public int binarySearchLeft(int[] nums, int begin, int end, int target){
            if(begin > end){
                return -1;
            }
            int mid = begin + (end - begin)/2;
            //target在左边
            if(nums[mid] > target){
                return binarySearchLeft(nums, begin, mid-1, target);
            } else if (nums[mid] < target){
                return binarySearchLeft(nums, mid+1, end, target);
            } else {
                if(mid-1 < 0 || nums[mid-1] != target)
                    return mid;
                else
                    return binarySearchLeft(nums, begin, mid-1, target);
            }
        }

        //寻找右边界
        public int binarySearchRight(int[] nums, int begin, int end, int target){
            if(begin > end){
                return -1;
            }
            int mid = begin + (end - begin)/2;
            //target在左边
            if(nums[mid] > target){
                return binarySearchRight(nums, begin, mid-1, target);
            } else if (nums[mid] < target){
                return binarySearchRight(nums, mid+1, end, target);
            } else {
                if(mid + 1 > nums.length - 1 || nums[mid+1] != target)
                    return mid;
                else
                    return binarySearchRight(nums, mid+1, end, target);
            }
        }

        /**
         * 我的解法二（对上述方法进行再改进，只二分一次，内存消耗小有提升）
         */
        public int[] searchRange0(int[] nums, int target) {
            int mid = binarySearch(nums, 0, nums.length-1, target);
            if(mid == -1)
                return new int[]{-1, -1};
            else {
                int left = binarySearchLeft(nums, 0, mid-1, target);
                int right = binarySearchRight(nums, mid+1, nums.length-1, target);
                return new int[]{left != -1 ? left : mid, right != -1 ? right : mid };
            }
        }

        //普通的二分
        public int binarySearch(int[] nums, int begin, int end, int target){
            if(begin > end)
                return -1;

            int mid = begin + (end - begin)/2;
            if(nums[mid] > target){
                return binarySearch(nums, begin, mid-1, target);
            } else if (nums[mid] < target) {
                return binarySearch(nums, mid+1, end, target);
            } else {
                return mid;
            }
        }
    }

    /**
     * 合并区间(我的解法，先对intervals按照区间左端点进行排序，再逐个遍历。执行用时太高了！)，精华之点在于先依据左端点进行排序，不进行这步就得不到正确答案
     */
    static class Merge {
        public int[][] merge(int[][] intervals) {
            Arrays.sort(intervals, (o1, o2) -> o1[0] - o2[0]);
            List<int[]> ans = new ArrayList<>();

            for(int i = 0;i < intervals.length;i++){
                int index = ans.size()-1;
                if(index < 0 || intervals[i][0] > ans.get(index)[1]){
                    ans.add(intervals[i]);
                } else {
                    ans.set(index, new int[]{ans.get(index)[0], Math.max(intervals[i][1], ans.get(index)[1])});
                }
            }

            return ans.toArray(new int[ans.size()][2]);
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
