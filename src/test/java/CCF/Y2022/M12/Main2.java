package CCF.Y2022.M12;

import java.util.*;

/**
 * 链表
 */
public class Main2 {
    static class Node{
        Node prev;//指向其依赖的任务
        int startTime;
        int endTime;
        int maxStart = Integer.MAX_VALUE;

        public Node(int startTime, int endTime){
            this.startTime = startTime;
            this.endTime = endTime;
            this.prev = null;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int dayCount = scanner.nextInt();
        int taskCount = scanner.nextInt();
        int[][] tasks = new int[taskCount][2];
        for(int i = 0;i < taskCount;i++){
            tasks[i][0] = scanner.nextInt();
        }
        for(int i = 0;i < taskCount;i++){
            tasks[i][1] = scanner.nextInt();
        }

        int endTime;
        boolean flag = true;//能不能在规定时间内完成
        HashMap<Integer, Node> nodeMap = new HashMap<>();
        //找到所有的结束节点
        HashSet<Node> endNodes = new HashSet<>();
        for (int i = 0; i < taskCount; i++) {
            //该任务不需要其他任务
            if(tasks[i][0] == 0){
                Node node = new Node(1, tasks[i][1]);
                nodeMap.put(i+1, node);
                endNodes.add(node);
            } else {
                //找到这个节点依赖的任务
                Node prev = nodeMap.get(tasks[i][0]);
                endNodes.remove(prev);
                endTime = prev.endTime + tasks[i][1];
                if(endTime > dayCount) flag = false;
                Node node = new Node(prev.endTime + 1, endTime);
                nodeMap.put(i+1, node);
                node.prev = prev;
                endNodes.add(node);
            }
        }

        for(int i = 1;i <= taskCount;i++){
            System.out.print(nodeMap.get(i).startTime + " ");
        }
        if(flag){
            //从每一个结束结点往前
            Iterator<Node> iterator = endNodes.iterator();
            while (iterator.hasNext()){
                setMaxStart(iterator.next(), dayCount);
            }
            System.out.println();
            for(int i = 1;i <= taskCount;i++){
                System.out.print(nodeMap.get(i).maxStart + " ");
            }
        }
    }

    public static void setMaxStart(Node node, int dayCount){
        int maxStart = node.startTime + (dayCount - node.endTime);
        node.maxStart = Math.min(node.maxStart, maxStart);
        if(node.prev != null){
            setMaxStart(node.prev, node.maxStart-1);
        }
    }


    /** 70分的解答，问题在于没有考虑到一个结点可能有多个子节点
    static class Node{
        Node next;
        int startTime;
        int endTime;
        int maxStart;

        public Node(int startTime, int endTime){
            this.startTime = startTime;
            this.endTime = endTime;
            this.next = null;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int dayCount = scanner.nextInt();
        int taskCount = scanner.nextInt();
        int[][] tasks = new int[taskCount][2];
        for(int i = 0;i < taskCount;i++){
            tasks[i][0] = scanner.nextInt();
        }
        for(int i = 0;i < taskCount;i++){
            tasks[i][1] = scanner.nextInt();
        }

        int endTime;
        boolean flag = true;//能不能在规定时间内完成
        HashMap<Integer, Node> nodeMap = new HashMap<>();
        for (int i = 0; i < taskCount; i++) {
            //该任务不需要其他任务
            if(tasks[i][0] == 0){
                Node node = new Node(1, tasks[i][1]);
                nodeMap.put(i+1, node);
            } else {
                //找到这个节点的父亲
                Node prev = nodeMap.get(tasks[i][0]);
                endTime = prev.endTime + tasks[i][1];
                if(endTime > dayCount) flag = false;
                Node node = new Node(prev.endTime + 1, endTime);
                nodeMap.put(i+1, node);
                prev.next = node;
            }
        }

        for(int i = 1;i <= taskCount;i++){
            System.out.print(nodeMap.get(i).startTime + " ");
        }
        if(flag){
            System.out.println();
            for(int i = 1;i <= taskCount;i++){
                System.out.print(getMaxStartTime(nodeMap.get(i), dayCount) + " ");
            }
        }
    }

    public static int getMaxStartTime(Node node, int dayCount){
        if(node.next == null){
            node.maxStart = node.startTime + (dayCount - node.endTime);
        } else{
            node.maxStart = node.startTime + (getMaxStartTime(node.next, dayCount) - node.next.startTime);
        }
        return node.maxStart;
    }
     **/
}
