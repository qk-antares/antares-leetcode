package leetcode.questions.T1000.T200;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class BFST {
    /**
     * 127. 单词接龙    [Hard]
     * 
     * 在beginWord+wordList上建立图，每个单词是一个节点。
     * 一个直观的方法是，如果两个单词只差一个字母，就在这两个节点之间添加双向边，但是这样需要n*n次比较才能完成建图
     * 
     * 因此，可以优化建图方案。具体来说，每个单词，例如abc，都与3个虚拟节点连接（*bc，a*b，ab*）
     * 使用邻接表来存储图
     * 
     * 建完图后，进行广度优先遍历
     */

    Map<String, Integer> map = new HashMap<>();
    List<ArrayList<Integer>> edges = new ArrayList<>();
    int nodeNum = 0;

    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        int len = wordList.size();
        for (int i = 0; i < len; i++) {
            addEdge(wordList.get(i));
        }
        if(!map.containsKey(endWord)) {
            return 0;
        }
        addEdge(beginWord);

        int[] dis = new int[nodeNum];
        int beginId = map.get(beginWord);
        int endId = map.get(endWord);
        Arrays.fill(dis, Integer.MAX_VALUE);
        dis[beginId] = 0;

        Deque<Integer> queue = new ArrayDeque<>();
        queue.add(beginId);

        while(!queue.isEmpty()) {
            int id = queue.poll();
            if(id == endId) {
                return dis[id]/2 + 1;
            }

            for(int i = 0;i < edges.get(id).size();i++) {
                int neighbor = edges.get(id).get(i);
                if(dis[neighbor] == Integer.MAX_VALUE) {
                    dis[neighbor] = dis[id] + 1;
                    queue.add(neighbor);
                }
            }
        }
        return 0;
    }

    public void addEdge(String word) {
        addNode(word);
        char[] chars = word.toCharArray();
        int id1 = map.get(word);

        for(int i = 0;i < chars.length;i++) {
            char tmp = chars[i];
            chars[i] = '*';
            String newWord = new String(chars);
            addNode(newWord);
            int id2 = map.get(newWord);
            edges.get(id1).add(id2);
            edges.get(id2).add(id1);
            chars[i] = tmp;
        }
    }

    public void addNode(String word) {
        if(!map.containsKey(word)) {
            map.put(word, nodeNum++);
            edges.add(new ArrayList<>());
        }
    }

    @Test
    public void test() {
        String beginWord = "hit";
        String endWord = "cog";
        List<String> wordList = Arrays.asList("hot","dot","dog","lot","log","cog");
        System.out.println(ladderLength(beginWord, endWord, wordList));
    }

}
