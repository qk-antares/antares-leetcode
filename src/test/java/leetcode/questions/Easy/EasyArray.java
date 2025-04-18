package leetcode.questions.Easy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

/**
 * @author Antares
 * @date 2022/8/28
 */
public class EasyArray {
    /**
     * 删除排序数组中的重复项
     * 我的解法
     * 总体思路是对的，但是细节方面有待优化
     * 例如，我的方法是如果nums[i]和nums[j]相等，就让j指针后移
     * 这样做的缺陷是必须要判断j不能越界，就多增加了一重判断
     */
    public int removeDuplicates(int[] nums) {
        if(nums.length < 2){
            return nums.length;
        }
        int i = 0;
        int j = 1;
        while(j < nums.length){
            while(j < nums.length && nums[i] == nums[j]){
                j++;
            }
            if(j < nums.length){
                nums[++i] = nums[j++];
            }
        }
        return i+1;
    }

    /**
     * 答案解法
     */
    public int removeDuplicatesAnswer(int[] A) {
        //边界条件判断
        if (A == null || A.length == 0)
            return 0;
        int left = 0;
        for (int right = 1; right < A.length; right++)
            //如果左指针和右指针指向的值一样，说明有重复的，
            //这个时候，左指针不动，右指针继续往右移。如果他俩
            //指向的值不一样就把右指针指向的值往前挪
            if (A[left] != A[right])
                A[++left] = A[right];
        return ++left;
    }

    /**
     * 买卖股票的最佳时机 II
     * 我的解法
     */
    public int maxProfit(int[] prices) {
        int maxProfit = 0;
        for(int i = 0;i < prices.length-1;i++){
            if(prices[i+1] > prices[i]){
                maxProfit += prices[i+1] - prices[i];
            }
        }
        return maxProfit;
    }

    /**
     * 旋转数组
     * 我的解法1，数组反转法
     */
    public void rotate(int[] nums, int k) {
        if(k == 0 || nums.length == 1)
            return;
        reverse(nums,0,nums.length-1);
        reverse(nums,0,k-1);
        reverse(nums,k,nums.length-1);
    }
    public void reverse(int[] nums,int start,int end){
        int temp;
        for(int i = 0;i <= (end-start)/2;i++){
            temp = nums[start+i];
            nums[start+i] = nums[end-i];
            nums[end-i] = temp;
        }
    }

    /**
     * 我的解法2：临时数组法
     */
    public void rotate0(int[] nums, int k) {
        int[] temp = Arrays.copyOf(nums, nums.length);
        for(int i = 0;i < nums.length;i++){
            nums[(i+k)%nums.length] = temp[i];
        }
    }

    /**
     * 存在重复元素
     * 我的解法1：排序法（内存消耗和执行用时都不理想）
     */
    public boolean containsDuplicate(int[] nums) {
        Arrays.sort(nums);
        for(int i = 0, j = 1;j < nums.length;i++, j++){
            if(nums[i] == nums[j])
                return true;
        }
        return false;
    }

    /**
     * 我的解法2：使用set
     */
    public boolean containsDuplicate0(int[] nums) {
        Set<Integer> integers = new HashSet<>();
        for (int num : nums) {
            integers.add(num);
        }
        return !(nums.length == integers.size());
    }

    /**
     * 只出现一次的数字
     * 我的解法：排序（内存消耗小但执行用时不理想）
     */
    public int singleNumber(int[] nums) {
        Arrays.sort(nums);
        for(int i = 0,j = 1;i < nums.length;i+=2,j+=2){
            if(j == nums.length)
                return nums[i];
            if(nums[i] != nums[j])
                return nums[i];
        }
        return 0;
    }

    /**
     * 答案解法：异或运算
     * 异或运算，相异为真，相同为假，所以 a^a = 0 ;0^a = a
     * 因为异或运算 满足交换律 a^b^a = a^a^b = b 所以数组经过异或运算，单独的值就剩下了
     */
    public int singleNumber0(int[] nums) {
        int res = 0;
        for (int num : nums) {
            res = res ^ num;
        }
        return res;
    }

    /**
     * 两个数组的交集 II
     * 我的解法：排序法（和答案的一个解法相同）
     */
    public int[] intersect(int[] nums1, int[] nums2) {
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        ArrayList<Integer> integers = new ArrayList<>();
        for(int i=0,j=0;i < nums1.length && j < nums2.length;){
            if(nums1[i] == nums2[j]){
                integers.add(nums1[i]);
                i++;
                j++;
            } else if (nums1[i] > nums2[j]) {
                j++;
            }else{
                i++;
            }
        }
        int[] res = new int[integers.size()];
        for(int i = 0;i < res.length;i++)
            res[i] = integers.get(i);
        return res;
    }

    /**
     * 答案解法：使用map(效率比较低)
     * 遍历nums1中的所有元素，把它存放到map中，其中key就是nums1中的元素，value就是这个元素在数组nums1中出现的次数。
     * 遍历nums2中的所有元素，查看map中是否包含nums2的元素，如果包含，就把当前值加入到集合list中，然后对应的value要减1。
     */
    public int[] intersect0(int[] nums1, int[] nums2) {
        HashMap<Integer, Integer> nums1Count = new HashMap<>();
        for (int i : nums1) {
            if(nums1Count.get(i) == null)
                nums1Count.put(i, 1);
            else
                nums1Count.put(i, nums1Count.get(i)+1);
        }

        ArrayList<Integer> integers = new ArrayList<>();
        for (int i : nums2) {
            if(nums1Count.get(i) != null && nums1Count.get(i) != 0){
                integers.add(i);
                nums1Count.put(i, nums1Count.get(i)-1);
            }
        }

        int[] res = new int[integers.size()];
        for(int i = 0;i < res.length;i++)
            res[i] = integers.get(i);
        return res;
    }

    /**
     * 加一
     * 我的解法
     */
    public int[] plusOne(int[] digits) {
        if(digits.length == 1 && digits[0] == 9){
            return new int[]{1,0};
        }
        digits[digits.length-1] += 1;
        if(digits[digits.length-1] != 10)
            return digits;
        else{
            digits[digits.length-1] = 0;
            int[] front = plusOne(Arrays.copyOf(digits, digits.length-1));
            if(front.length == digits.length - 1){
                for(int i = 0;i < front.length;i++)
                    digits[i] = front[i];
                return digits;
            }
            else if(front.length == digits.length){
                int[] res = new int[digits.length + 1];
                res[0] = 1;
                return res;
            }
        }
        return null;
    }

    /**
     * 答案解法：消除了递归调用，更加简洁
     */
    public int[] plusOne0(int[] digits) {
        int length = digits.length;
        for (int i = length - 1; i >= 0; i--) {
            if (digits[i] != 9) {
                //如果数组当前元素不等于9，直接加1
                //然后直接返回
                digits[i]++;
                return digits;
            } else {
                //如果数组当前元素等于9，那么加1之后
                //肯定会变为0，我们先让他变为0
                digits[i] = 0;
            }
        }
        //除非数组中的元素都是9，否则不会走到这一步，
        //如果数组的元素都是9，我们只需要把数组的长度
        //增加1，并且把数组的第一个元素置为1即可
        int temp[] = new int[length + 1];
        temp[0] = 1;
        return temp;
    }

    /**
     * 移动零
     * 我的解法：双指针法(答案解法)
     */
    public void moveZeroes(int[] nums) {
        int j = 0;
        for(int i=0;i < nums.length;i++){
            if(nums[i] != 0)
                nums[j++] = nums[i];
        }
        for(;j < nums.length;j++){
            nums[j] = 0;
        }
    }

    /**
     * 有效的数独
     * 我的解法(勉强通过，多看看答案)
     */
    public boolean isValidSudoku(char[][] board) {
        HashSet<Character> chars;
        int count;
        //首先判断行
        for(int i = 0;i < 9;i++){
            count = 0;
            chars = new HashSet<>();
            for(int j = 0;j < 9;j++){
                if(board[i][j] >= '0' && board[i][j] <= '9'){
                    count++;
                    chars.add(board[i][j]);
                }
            }
            if(count != chars.size())
                return false;
        }
        //再判断列
        for(int i = 0;i < 9;i++){
            count = 0;
            chars = new HashSet<>();
            for(int j = 0;j < 9;j++){
                if(board[j][i] >= '0' && board[j][i] <= '9'){
                    count++;
                    chars.add(board[j][i]);
                }
            }
            if(count != chars.size())
                return false;
        }
        //再判断九宫格
        for(int i = 0;i < 9;i+=3){
            for(int j = 0;j < 9;j+=3){
                count = 0;
                chars = new HashSet<>();
                for(int k = 0;k < 9;k++){
                    if(board[i+k/3][j+k%3] >= '0' && board[i+k/3][j+k%3] <= '9'){
                        count++;
                        chars.add(board[i+k/3][j+k%3]);
                    }
                }
                if(count != chars.size())
                    return false;
            }

        }
        return true;
    }

    /**
     * 旋转图像
     * 我的解法：旋转等于转置再对称
     */
    public void rotate(int[][] matrix) {
        //先对矩阵进行转置
        int temp;
        for(int i = 0;i < matrix.length;i++){
            for(int j = 0;j < matrix.length;j++){
                if(i > j) {
                    temp = matrix[i][j];
                    matrix[i][j] = matrix[j][i];
                    matrix[j][i] = temp;
                }
            }
        }
        //之后对矩阵对称操作
        for(int i = 0;i < matrix.length;i++){
            for(int j = 0;j < matrix.length/2;j++){
                temp = matrix[i][j];
                matrix[i][j] = matrix[i][matrix.length-j-1];
                matrix[i][matrix.length-j-1] = temp;
            }
        }

    }

    /**
     * 答案的解法也很巧妙，可以看看
     */

    @Test
    public void invoke(){
//        int i = removeDuplicates(new int[]{1, 1});
//        int i = removeDuplicatesAnswer(new int[]{0,0,1,1,1,2,2,3,3,4});
//        int[] res = plusOne(new int[]{1,9,9});
//        char[][] board = new char[][]{{'5','3','.','.','7','.','.','.','.'}
//                                    ,{'6','.','.','1','9','5','.','.','.'}
//                                    ,{'.','9','8','.','.','.','.','6','.'}
//                                    ,{'8','.','.','.','6','.','.','.','3'}
//                                    ,{'4','.','.','8','.','3','.','.','1'}
//                                    ,{'7','.','.','.','2','.','.','.','6'}
//                                    ,{'.','6','.','.','.','.','2','8','.'}
//                                    ,{'.','.','.','4','1','9','.','.','5'}
//                                    ,{'.','.','.','.','8','.','.','7','9'}};
//        isValidSudoku(board);
        int[][] pic = {{1,2,3},{4,5,6},{7,8,9}};
        rotate(pic);
    }

}
