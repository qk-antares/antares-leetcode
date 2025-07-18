package leetcode.algorithm;

import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Set;

import org.junit.jupiter.api.Test;

/**
 * @author Antares
 * @date 2022/10/14
 */
public class DoublePointer {
    /**
     * 翻转字符串里的单词，使用split函数+StringBuilder
     */
    class ReverseWords {
        public String reverseWords(String s) {
            String[] strings = s.trim().split(" ");

            StringBuilder sb = new StringBuilder();

            for(int i = strings.length-1;i >= 0;i--){
                if (!strings[i].equals("")){
                    sb.append(strings[i]).append(" ");
                }
            }

            sb.deleteCharAt(sb.length()-1);

            return sb.toString();
        }
    }

    /**
     * 数组拆分 I（实际是排序+偶数下标元素之和，但是效率太低，一种解法使用数组统计出现次数，也可以用优先级队列）
     */
    class ArrayPairSum {
        public int arrayPairSum(int[] nums) {
            int[] count = new int[20001];

            for (int num : nums) {
                count[num + 10000]++;
            }

            int ans = 0,borrow = 0;
            for(int i = 0;i < 20001;i++){
                if(count[i] != 0){
                    // count[i]是总共出现了多少个， -borrow是i-1有没有借走一个
                    // +1是为了保证，count[i] - borrow是奇数的情况下，多算一个
                    ans += (count[i] - borrow + 1) / 2 * (i - 10000);

                    // 更新borrow，如果count[i] - borrow是奇数，就得从i+1借一个
                    // +2是java语言防止-1 % 2 == -1
                    borrow = (count[i] - borrow + 2) % 2;
                }
            }
            return ans;
        }

        /**
         * 使用优先级队列进一步优化(内存消耗小，执行用时反而增长，因为其实质还是进行了排序)
         */
        public int arrayPairSum0(int[] nums) {
            HashMap<Integer, Integer> count = new HashMap<>();
            for (int num : nums) {
                count.put(num, count.getOrDefault(num, 0) + 1);
            }

            PriorityQueue<int[]> priorityQueue = new PriorityQueue<>((o1, o2) -> o1[0] - o2[0]);
            Set<Integer> keySet = count.keySet();
            for (Integer integer : keySet) {
                priorityQueue.add(new int[]{integer, count.get(integer)});
            }

            int ans = 0,borrow = 0;
            while (!priorityQueue.isEmpty()){
                int[] poll = priorityQueue.poll();
                ans += (poll[1] - borrow + 1) / 2 * poll[0];

                borrow = (poll[1] - borrow) % 2;
            }
            return ans;
        }
    }

    /**
     * 移除元素
     */
    class RemoveElement {
        public int removeElement(int[] nums, int val) {
            int target = 0;
            for(int i = 0;i < nums.length;i++){
                if(nums[i] == val){
                    continue;
                }
                nums[target++] = nums[i];
            }

            return target;
        }
    }

    /**
     * 最大连续1的个数
     * 如果当前位是0，跳过
     * 如果是1，加上前面那一位
     * 用一个max变量
     */
    class FindMaxConsecutiveOnes {
        public int findMaxConsecutiveOnes(int[] nums) {
            int max = nums[0];
            for(int i = 1;i < nums.length;i++){
                if(nums[i] == 0)
                    continue;

                nums[i] += nums[i-1];
                if(nums[i] > max)
                    max = nums[i];
            }

            return max;
        }
    }

    @Test
    public void invoke(){
//        new ReverseWords().reverseWords("a good   example");
//        new StrStr().buildNext("AATGPACY");
//        new StrStr().strStr("babba", "bbb");
//        new ArrayPairSum().arrayPairSum(new int[]{6,2,6,5,1,2});
    }
}
