import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class TestT {
    Map<String, Integer> map = new HashMap<>();
    List<ArrayList<Integer>> edges = new ArrayList<>();
    int nodeNum = 0;

    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        for(String word: wordList) {
            addEdge(word);
        }
        if(!map.containsKey(endWord)){
            return 0;
        }
        addEdge(beginWord);

        int beginId = map.get(beginWord);
        int endId = map.get(endWord);
        int[] dis = new int[nodeNum];
        Arrays.fill(dis, Integer.MAX_VALUE);
        dis[beginId] = 0;

        Deque<Integer> q = new ArrayDeque<>();
        q.offer(beginId);

        while(!q.isEmpty()){
            int id = q.poll();
            if(id == endId) {
                return dis[id] / 2 + 1;
            }
            for(int neighbor: edges.get(id)) {
                if(dis[neighbor] == Integer.MAX_VALUE) {
                    dis[neighbor] = dis[id] + 1;
                    q.offer(neighbor);
                }
            }
        }
        return 0;
    }

    public void addEdge(String word) {
        addNode(word);
        int id1 = map.get(word);
        char[] chars = word.toCharArray();
        int len = word.length();
        for(int i = 0;i < len;i++){
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

    public void addNode(String word){
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
