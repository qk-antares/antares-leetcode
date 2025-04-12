package leetcode.questions.T3000.T2400;

public class GraphT {
    /*
     * 2360. 图中的最长环   [Hard]
     * 
     * 首先在读题的时候要注意到，每个节点至多有1条出边，edges的长度就等于nodes数，edges[i]=0代表nodei没有出边
     * 
     * 在此基础上，用跑步的思想来看这道题。
     * 用一个全局变量curTime来记录当前时间，用visTime[]数组来记录第一次访问某个节点的时间
     * 
     * 从任意一个节点开始，记录本轮的开始时间startTime。
     * 如果当前节点没有访问过(curNode != -1 && visTime[curNode]==0)：
     * 那么就一直访问下一个节点，直到到头了（curNode == -1）或者到了一个已经访问过的节点(visTime[curNode] != 0）
     * 在此过程中，curNode=edges[curNode]，curTime++
     * 
     * 如果属于上述<到了一个已经访问过的节点>的情况，则说明有环，记录最长环的长度：
     * ans = Math.max(ans, curTime - visTime[curNode])
     */
    public int longestCycle(int[] edges) {
        int n = edges.length;
        int ans = -1;
        int curTime = 0;
        int[] visitTime = new int[n];
        for (int i = 0; i < n; i++) {
            int startTime = curTime; // 本轮的开始时间
            int curNode = i; // 本轮的起始节点

            // 当前节点未访问过
            while (curNode != -1 && visitTime[curNode] == 0) {
                visitTime[curNode] = curTime++;
                curNode = edges[curNode];
            }

            // 要么是没有下一个节点了，要么就是遇到访问过的节点了
            // 是在本轮重复访问的
            if (curNode != -1 && visitTime[curNode] >= startTime) {
                ans = Math.max(ans, curTime - visitTime[curNode]);
            }
        }

        return ans;
    }
}
