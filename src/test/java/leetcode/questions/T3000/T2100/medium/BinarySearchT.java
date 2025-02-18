package leetcode.questions.T3000.T2100.medium;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;

public class BinarySearchT {
    /*
     * 2080. 区间内查询数字的频率
     * 
     * 首先建立一个哈希表map：
     * map的key是数组中的值,value是该数字出现的【下标数组】。O(n)
     * 
     * 在查询时:
     * 首先取出对应的下标数组，然后利用二分查找得到该值出现的次数
     */
    class RangeFreqQuery {

        HashMap<Integer, List<Integer>> map = new HashMap<>();

        public RangeFreqQuery(int[] arr) {
            for (int i = 0; i < arr.length; i++) {
                map.putIfAbsent(arr[i], new ArrayList<>());
                map.get(arr[i]).add(i);
            }
        }
        
        public int query(int left, int right, int value) {
            List<Integer> list = map.get(value);
            if (list == null || left > list.get(list.size()-1) || right < list.get(0)) {
                return 0;
            }

            // 需要找到第一个大于等于left的元素index
            int leftIndex = lowerBound(list, left);
            // 需要找到第一个大于right的元素index
            int rightIndex = upperBound(list, right);

            return rightIndex - leftIndex;
        }


        public int lowerBound(List<Integer> list, int val) {
            int l = 0, r = list.size()-1;
            while (l <= r) {
                int mid = l + (r-l) / 2;
                if(list.get(mid) < val) {
                    l = mid+1;
                } else {
                    r = mid-1;
                }
            }
            return l;
        }

        public int upperBound(List<Integer> list, int val) {
            int l = 0, r = list.size()-1;
            while (l <= r) {
                int mid = l + (r-l) / 2;
                if(list.get(mid) <= val) {
                    l = mid+1;
                } else {
                    r = mid-1;
                }
            }
            return l;
        }
    }

    @Test
    public void test(){
        RangeFreqQuery rangeFreqQuery = new RangeFreqQuery(new int[]{12,33,4,56,22,2,34,33,22,12,34,56});
        rangeFreqQuery.query(0,11,33);
    }
    
}
