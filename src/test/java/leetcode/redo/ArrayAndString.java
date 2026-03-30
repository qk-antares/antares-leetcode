package leetcode.redo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * @author Antares
 * @date 2022/10/14
 */
public class ArrayAndString {
    /**
     * 杨辉三角
     */
    class Generate {
        public List<List<Integer>> generate(int numRows) {
            List<List<Integer>> ans = new ArrayList<>(numRows);

            if(numRows == 0)
                return ans;

            ans.add(new ArrayList<>(Arrays.asList(1)));

            for(int i = 1;i < numRows;i++){
                List<Integer> lastRow = ans.get(i - 1);
                ArrayList<Integer> row = new ArrayList<>(i+1);
                row.add(1);
                for(int j = 1;j < i;j++)
                    row.add(lastRow.get(j-1) + lastRow.get(j));
                row.add(1);
                ans.add(row);
            }

            return ans;
        }
    }

    /**
     * 删除排序数组中的重复项（双指针）
     */
    class RemoveDuplicates {
        public int removeDuplicates(int[] nums) {
            //i是被对比元素，j是当前遍历到的元素
            int i = 0;
            for(int j = 1;j < nums.length;j++){
                if(nums[i] != nums[j]){
                    nums[++i] = nums[j];
                }
            }
            return i+1;
        }
    }

    @Test
    public void invoke(){
//        new Generate().generate(5);
    }
}
