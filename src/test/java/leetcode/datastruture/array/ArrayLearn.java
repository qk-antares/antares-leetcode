package leetcode.datastruture.array;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @author Antares
 * @date 2022/9/28
 */
public class ArrayLearn {
    /**
     * 寻找数组的中心索引（我的解法：双指针，漏洞太多，不能找到最左边的中心索引，对负数的考虑不到位）答案解法：先计算总和，然后计算左边和，如果其x2+当前值=sum，返回当前索引
     */
    static class PivotIndex {
        public int pivotIndex(int[] nums) {
            int left = 0, right = nums.length - 1;
            int sumLeft = 0, sumRight = 0;
            while (left < right){
                if(sumLeft < sumRight){
                    sumLeft += nums[left++];
                } else if (sumLeft > sumRight) {
                    sumRight += nums[right--];
                } else {
                    sumLeft += nums[left++];
                    sumRight += nums[right--];
                }
            }

            if(left > right)
                return -1;

            if(sumLeft == sumRight){
                return left;
            } else{
                if(sumLeft < sumRight){
                    while (sumLeft < sumRight){
                        try {
                            sumLeft += nums[left++];
                            sumRight -= nums[left];
                        } catch (IndexOutOfBoundsException e) {
                            return -1;
                        }
                    }
                } else{
                    while (sumLeft > sumRight){
                        try {
                            sumRight += nums[left--];
                            sumLeft -= nums[left];
                        } catch (IndexOutOfBoundsException e) {
                            return -1;
                        }
                    }
                }
            }

            if(sumLeft == sumRight)
                return left;
            else
                return -1;
        }

        public int pivotIndex0(int[] nums) {
            int sum = 0;
            for (int num : nums) {
                sum += num;
            }

            int left = 0;
            for(int i = 0;i < nums.length;i++){
                if(left * 2 + nums[i] == sum)
                    return i;
                left += nums[i];
            }

            return -1;
        }
    }

    /**
     * 搜索插入位置
     */
    class SearchInsert {
        public int searchInsert(int[] nums, int target) {
            return binarySearch(nums, 0, nums.length-1, target);
        }

        public int binarySearch(int[] nums, int left, int right, int target){
            if(left > right){
                return left;
            }
            int mid = left + (right - left) / 2;

            if(nums[mid] == target || nums[mid] > target && mid > 0 && nums[mid-1] < target){
                return mid;
            } else if (nums[mid] > target) {
                return binarySearch(nums, left, mid-1, target);
            }
            else {
                return binarySearch(nums, mid+1, right, target);
            }
        }
    }

    /**
     * 合并区间（我的解法：使用优先级队列，执行用时比较长，可以用排序进行优化）
     */
    class Merge {
        public int[][] merge(int[][] intervals) {
            ArrayList<int[]> ans = new ArrayList<>();

            //首先对各个区间，按照开始进行排序（放进优先级队列里）
            PriorityQueue<int[]> priorityQueue = new PriorityQueue<>((o1, o2) -> o1[0] - o2[0]);
            for(int i = 0;i < intervals.length;i++){
                priorityQueue.add(intervals[i]);
            }

            ans.add(priorityQueue.poll());
            while (!priorityQueue.isEmpty()){
                int[] poll = priorityQueue.poll();
                if(poll[0] <= ans.get(ans.size()-1)[1]){
                    ans.set(ans.size()-1, new int[]{ans.get(ans.size()-1)[0], Math.max(ans.get(ans.size()-1)[1], poll[1])});
                }else{
                    ans.add(poll);
                }
            }

            return ans.toArray(new int[ans.size()][2]);
        }

        public int[][] merge0(int[][] intervals) {
            ArrayList<int[]> ans = new ArrayList<>();
            Arrays.sort(intervals, (o1, o2) -> o1[0]-o2[0]);

            ans.add(intervals[0]);
            int index;
            for(int i = 1;i < intervals.length;i++){
                index = ans.size()-1;
                if(intervals[i][0] <= ans.get(index)[1]){
                    ans.set(index, new int[]{ans.get(index)[0], Math.max(intervals[i][1], ans.get(index)[1])});
                }else {
                    ans.add(intervals[i]);
                }
            }

            return ans.toArray(new int[ans.size()][2]);
        }
    }

    /**
     * 旋转矩阵（转置再对称）
     *   [1,2,3],   [7,4,1],
     *   [4,5,6],   [8,5,2],
     *   [7,8,9]    [9,6,3]
     *
     *   两次对称是旋转180°
     *   [3,2,1]
     *   [6,5,4]
     *   [9,8,7]
     *
     *   [9,8,7]
     *   [6,5,4]
     *   [3,2,1]
     *
     *   转置(swap(matrix[i][j], matrix[j][i]) (j>i))
     *   [1,4,7]
     *   [2,5,8]
     *   [3,6,9]
     *   再对称
     *   [7,4,1],
     *   [8,5,2],
     *   [9,6,3]
     */
    class Rotate {
        public void rotate(int[][] matrix) {
            int N = matrix.length;
            //先转置
            for(int i = 0;i < N-1;i++){
                for(int j = i+1; j < N;j++)
                    swap(matrix, i, j, j, i);
            }

            //再对称（左右）
            for(int i = 0;i < N;i++){
                for(int j = 0;j < N/2;j++){
                    swap(matrix, i, j, i, N-1-j);
                }
            }
        }

        public void swap(int[][] matrix, int i0, int j0, int i1, int j1){
            int temp = matrix[i0][j0];
            matrix[i0][j0] = matrix[i1][j1];
            matrix[i1][j1] = temp;
        }
    }

    /**
     * 零矩阵（我的解法：将需要清零的列映射到第一行上，将需要清零的行映射到第一列上，答案解法：多用两个布尔数组，来统计哪些行和列需要清零）
     */
    class SetZeroes {
        public void setZeroes(int[][] matrix) {
            boolean clearColumn = false, clearRow = false;
            for(int i = 0;i < matrix.length;i++){
                for(int j = 0;j < matrix[0].length;j++){
                    if(matrix[i][j] == 0){
                        matrix[0][j] = 0;
                        matrix[i][0] = 0;
                        if(i == 0)
                            clearRow = true;
                        if(j == 0)
                            clearColumn = true;
                    }
                }
            }

            //遍历第一列
            for(int i = 1;i < matrix.length;i++){
                if(matrix[i][0] == 0){
                    for(int j = 1;j < matrix[0].length;j++)
                        matrix[i][j] = 0;
                }
            }
            //遍历第一行
            for(int j = 1;j < matrix[0].length;j++){
                if(matrix[0][j] == 0){
                    for(int i = 1;i < matrix.length;i++)
                        matrix[i][j] = 0;
                }
            }

            if (clearColumn){
                for(int i = 1;i < matrix.length;i++)
                    matrix[i][0] = 0;
            }
            if(clearRow){
                for(int j = 1;j < matrix[0].length;j++)
                    matrix[0][j] = 0;
            }
        }
    }

    /**
     * 对角线遍历
     * 来看一下访问坐标
     * (0,0)
     *
     * (0,1)
     * (1,0)
     *
     * (2,0)
     * (1,1)
     * (0,2)
     *
     * (1,2)
     * (2,1)
     *
     * (2,2)
     *
     * 共有m+n-1个来回，每个来回有以下规律
     * 1.第i个来回，横纵坐标之和为i
     * 2.偶数来回中，横坐标减小，纵坐标增大；奇数来回中，横坐标增大，纵坐标减小
     * 3.1-2-3-...-3-2-1驼峰式，第i个来回总数：i（i <= (m+n-1)/2）
     */
    class FindDiagonalOrder {
        public int[] findDiagonalOrder(int[][] mat) {
            int[] ans = new int[mat.length * mat[0].length];
            int index = 0;
            int m = mat.length, n = mat[0].length;
            int startX = 0, startY = 0;
            boolean flag = false;
            for(int i = 0;i < m+n-1;i++){
                if(i%2 == 0){
                    //偶数来回
                    while (true){
                        ans[index++] = mat[startX][startY];
                        startX--;
                        startY++;

                        if(startX < 0 ^ startY > n-1){
                            //向下移动
                            if(startX < 0){
                                startX++;
                                flag = true;
                            }
                            //向左向下向下
                            if(startY > n-1){
                                startY--;
                                startX += 2;
                                flag = true;
                            }
                        } else if (startX < 0 && startY > n-1) {
                            startY--;
                            startX += 2;
                            flag = true;
                        }


                        if(flag){
                            flag = false;
                            break;
                        }
                    }
                } else {
                    //奇数来回
                    while (true){
                        ans[index++] = mat[startX][startY];
                        startX++;
                        startY--;

                        if(startX > m-1 ^ startY < 0){
                            //向上，向右，向右
                            if(startX > m-1){
                                startX--;
                                startY += 2;
                                flag = true;
                            }
                            //向右移动
                            if(startY < 0){
                                startY++;
                                flag = true;
                            }
                        } else if (startX > m-1 && startY < 0) {
                            startX--;
                            startY += 2;
                            flag = true;
                        }

                        if(flag){
                            flag = false;
                            break;
                        }
                    }
                }
            }
            return ans;
        }
    }

    @Test
    public void invoke(){
//        new PivotIndex().pivotIndex(new int[]{-1, 1, -1});
//        new SearchInsert().searchInsert(new int[]{1,3}, 0);
//        new SetZeroes().setZeroes(new int[][]{{1,1,1},{0,1,2}});
//        new SetZeroes().setZeroes(new int[][]{{1,0,3}});
//        new SetZeroes().setZeroes(new int[][]{{1,1,1},{0,1,2}});
        new FindDiagonalOrder().findDiagonalOrder(new int[][]{{1,2,3},{4,5,6},{7,8,9},{10,11,12}});
//        new FindDiagonalOrder().findDiagonalOrder(new int[][]{{1,2,3},{4,5,6},{7,8,9}});

    }

}
