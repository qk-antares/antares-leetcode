package leetcode.questions.T1000.T1000;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class QueueT {
    /*
     * 936. 戳印序列
     * 
     * 假设stamp的长度是n，target的长度是m
     * 
     * 维护m-n+1个Node(这是最多戳印的次数)，每个Node[i]代表target[i...i+n-1]这段序列的信息，包括：
     * 1. Y: 已经配对上的字母index集合
     * 2. N: 未配对上的字母index集合
     * 
     * 如果一个Node所有的位置都匹配上了，则标记为已配对。
     * 如果该Node匹配，就可以将对应的区间修改为?(可以匹配任意)，将其index添加到ansStack，将?的index添加到q
     * 根据?的index处理受影响的其他Node，如果这些Node也变为了全匹配，执行和上面相同的操作
     * 
     * 当队列为空时，看是否所有的位置都变成?了，然后返回结果([]或ansStack.pop())
     */
    public int[] movesToStamp(String stamp, String target) {
        int m = stamp.length(), n = target.length();
        boolean[] Y = new boolean[n];   //标记各个位置是否已经匹配了
        List<Node> list = new ArrayList<>();
        ArrayDeque<Integer> ans = new ArrayDeque<>();   //存储倒序的戳印的位置
        ArrayDeque<Integer> q = new ArrayDeque<>(); //待处理的已经变成?的位置
        for(int i = 0;i <= n-m;i++) {
            Node node = new Node();
            for(int j = 0;j < m;j++) {
                if(target.charAt(i+j) == stamp.charAt(j)) {
                    node.Y.add(i+j);
                } else {
                    node.N.add(i+j);
                }
            }
            list.add(node);
            if(node.N.isEmpty()){
                ans.push(i);

                for(int j = i;j < i+m;j++) {
                    if(!Y[j]) {
                        q.add(j);
                        Y[j] = true;
                    }
                }
            }
        }

        while(!q.isEmpty()) {
            //取出待处理的变成?的位置
            Integer i = q.poll();
            //遍历受影响区间(j代表区间的起点)
            for(int j = Math.max(0, i-m+1); j <= Math.min(n-m, i); j++) {
                Node affectedNode = list.get(j);
                if(affectedNode.N.contains(i)) { //不匹配的位置变成了?
                    affectedNode.N.remove(i);
                    if(affectedNode.N.isEmpty()) {  //这个区间可以匹配了
                        ans.push(j);
                        for(int y: affectedNode.Y) {
                            if(!Y[y]) {
                                q.add(y);
                                Y[y] = true;
                            }
                        }
                    }
                }

            }
        }

        for(boolean y: Y) {
            if(!y) return new int[0];
        }

        int[] ret = new int[ans.size()];
        int k = 0;
        while(!ans.isEmpty()) {
            ret[k++]=ans.pop();
        }

        return ret;
    }

    class Node {
        Set<Integer> Y = new HashSet<>();
        Set<Integer> N = new HashSet<>();
    }
}
