package leetcode.redo;

import org.junit.jupiter.api.Test;

import java.util.*;

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

    /**
     * 移动零（双指针试一试），答案解法：快慢指针
     */
    class MoveZeroes {
        public void moveZeroes(int[] nums) {
            //j是当前遍历到的元素,i j之间的元素为0
            int i = -1, j = 0;
            //首先找到第一个为0的元素
            while (j < nums.length){
                if(nums[j] == 0){
                    i = j;
                    break;
                }
                j++;
            }
            //如果里面有0，才继续操作
            if(i != -1){
                for(j = i+1;j < nums.length;j++){
                    if(nums[j] != 0){
                        nums[i++] = nums[j];
                        nums[j] = 0;
                    }
                }
            }
        }

        public void moveZeroes0(int[] nums) {
            int i = 0, j = 0;

            while (j < nums.length){
                if(nums[j] != 0){
                    nums[i++] = nums[j];
                }
                j++;
            }

            for(;i < nums.length;i++)
                nums[i] = 0;
        }
    }

    /**
     * 多数元素，我的解法：hashmap，效率太低；先排序再返回，效率比hashmap高；摩尔投票法（效率最高），将数组中的不同数字想象成不同国家的人，进行两两抵消
     */
    class MajorityElement {
        public int majorityElement(int[] nums) {
            HashMap<Integer, Integer> hashMap = new HashMap<>();
            int bound = nums.length / 2;
            for (int num : nums) {
                hashMap.put(num, hashMap.getOrDefault(num, 0) + 1);
                if(hashMap.get(num) > bound) return num;
            }
            return -1;
        }

        public int majorityElement0(int[] nums) {
            Arrays.sort(nums);
            return nums[nums.length / 2];
        }

        public int majorityElement1(int[] nums) {
            int survivor = nums[0];
            int count = 1;
            for(int i = 1;i < nums.length;i++){
                if(count == 0){
                    survivor = nums[i];
                    count = 1;
                } else if(nums[i] == survivor){
                    count++;
                } else{
                    count--;
                }
            }

            return survivor;

        }
    }


    @Test
    public void invoke(){
//        new Generate().generate(5);
//         new MoveZeroes().moveZeroes(new int[]{1,0,3,12,0,0,15,0,0});
        new MajorityElement().majorityElement(new int[]{3,2,3});
    }
}
