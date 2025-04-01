package leetcode.questions.T1000.T1000.medium;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class HashT {
    /*
     * 981. 基于时间的键值存储
     * 下面的代码虽然能够通过测试，但并不是这道题想考察的点。
     * 
     * 由于题目保证set操作中的时间戳timestamp是严格递增的，一个更好的解法是结合二分查找
     * 具体来说，timeMap应该是Map<String, List<Pair<Integer, String>>>
     * 其中Pair{timestamp, value}，List中的Pair按照timestamp从小到大排序
     * 
     * 当来一个key和timestamp的查询时，从List中二分查找
     */
    class TimeMap0 {
        Map<String, TreeMap<Integer, String>> timeMap;

        public TimeMap0() {
            timeMap = new HashMap<>();
        }

        public void set(String key, String value, int timestamp) {
            TreeMap<Integer, String> t = timeMap.get(key);
            if (t != null) {
                t.put(timestamp, value);
            } else {
                t = new TreeMap<Integer, String>();
                t.put(timestamp, value);
                timeMap.put(key, t);
            }
        }

        public String get(String key, int timestamp) {
            TreeMap<Integer, String> t = timeMap.get(key);
            if (t == null)
                return "";
            // <=timestamp的最大key
            Integer maxPrev = t.floorKey(timestamp);
            if (maxPrev == null)
                return "";
            return t.get(maxPrev);
        }
    }

    // 好吧，实测性能也没有很大的区别
    class TimeMap {
        class Pair {
            int timestamp;
            String value;

            public Pair(int timestamp, String value) {
                this.timestamp = timestamp;
                this.value = value;
            }
        }

        Map<String, List<Pair>> timeMap;

        public TimeMap() {
            this.timeMap = new HashMap<>();
        }

        public void set(String key, String value, int timestamp) {
            timeMap.computeIfAbsent(key, (k) -> new ArrayList<Pair>()).add(new Pair(timestamp, value));
        }

        public String get(String key, int timestamp) {
            List<Pair> list = timeMap.get(key);
            if (list == null)
                return "";
            // 开始二分
            int l = 0, r = list.size() - 1;
            while (l <= r) {
                int mid = (l + r) / 2;
                if (list.get(mid).timestamp > timestamp) {
                    r = mid - 1;
                } else {
                    l = mid + 1;
                }
            }

            if (r == -1)
                return "";
            return list.get(r).value;

        }
    }
}
