package leetcode.questions.Easy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * @author Antares
 * @date 2022/9/8
 */
public class OtherEasy {
    /**
     * 位1的个数（涉及到位运算）解法一：将n向右依次位移，然后和1做与运算
     */
    public int hammingWeight(int n) {
        int count = 0;
        for(int i = 0;i < 32;i++){
            if(((n >>> i) & 1) == 1)
                count++;
        }
        return count;
    }

    /**
     * 颠倒二进制位(我的解法：)
     */
    public int reverseBits(int n) {
        int res = 0;
        for(int i = 0;i < 32;i++){
            res = res | (n & 1);
            if(i < 31)
                res <<= 1;
            n >>= 1;
        }
        return res;
    }

    /**
     * 杨辉三角（我的解法）
     */
    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> res = new ArrayList<>(numRows);

        List<Integer> firstRow = new ArrayList<>(1);
        firstRow.add(1);
        res.add(firstRow);

        for(int i = 1;i < numRows;i++){
            List<Integer> row = new ArrayList<>(i+1);
            row.add(1);
            List<Integer> lastRow = res.get(i-1);
            for(int j = 1;j < i - 1;j++){
                row.add(lastRow.get(j-1) + lastRow.get(j));
            }
            row.add(1);
            res.add(row);
        }

        return res;
    }

    /**
     * 缺失数字(我的解法：排序+二分，效率比较低)
     * [0,1,3]
     */
    public int missingNumber(int[] nums) {
        Arrays.sort(nums);
        int start = 0;
        int end = nums.length-1;
        int mid;

        while (start < end){
            mid = (start + end)/2;
            if(mid == nums[mid])
                start = mid + 1;
            else
                end = mid;
        }

        if(start != nums[start])
            return start;
        else
            return start + 1;
    }

    /**
     * 解法一：位运算法
     * [0, n]共n+1个数，缺失了1个
     * 我们在增加n+1个数，使每个数出现2次，只有一个数出现了1次
     * 如此，所有数异或运算之后便得到了那个只出现1次的数
     */
    public int missingNumber0(int[] nums) {
        int res = 0;
        for(int i = 0;i < nums.length;i++){
            res = res ^ nums[i] ^ (i+1);
        }
        return res;
    }

    /**
     * 解法二：求和法(效率最高)
     * 求和[0,n]，再减去数组中的数
     */
    public int missingNumber1(int[] nums) {
        int res = nums.length * (nums.length + 1)/2;

        for(int i = 0;i < nums.length;i++)
            res -= nums[i];

        return res;
    }



    @Test
    public void invoke(){
//        reverseBits();
//        generate(3);
//        isValid("()[]{}");
        missingNumber1(new int[]{9,6,4,2,3,5,7,0,1});
    }

}
