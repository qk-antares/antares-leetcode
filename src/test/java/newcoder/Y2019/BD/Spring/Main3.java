package newcoder.Y2019.BD.Spring;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 最难的一道，后续再了解了解
 */
public class Main3 {
    static HashMap<Integer, Integer> map;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        map = new HashMap<>();
        int key;
        for (int i = 0; i < 13; i++) {
            key = scanner.nextInt();
            map.put(key, map.getOrDefault(key, 0) + 1);
        }

        //首先看能否组成4个刻子+雀头，（3个刻子+雀头+【2】）（4个刻子+【1】）
        Set<Integer> keySet = map.keySet();
        int i = 0,j = 0;//分别代表刻子和雀头数
        Set<Integer> ans = new HashSet<Integer>();//存储结果
        ArrayList<Integer> tmpAns = new ArrayList<>();
        for (Integer integer : keySet) {
            if(map.get(integer) == 3){
                i++;
            } else if (map.get(integer) == 2) {
                j++;
                tmpAns.add(integer);
            } else if (map.get(integer) == 1) {
                tmpAns.add(integer);
            }
        }
        if(i == 3 && j == 2 || i == 4){
            ans.addAll(tmpAns);
        }

        //再看能够组成4个顺子+刻头
        for (int k = 1; k <= 9; k++) {
            map.put(k, map.getOrDefault(k, 0) + 1);
            Set<Integer> integers = map.keySet();
            for (Integer integer : integers) {
                if(map.get(integer) >= 2 && win(integer, integers)){
                    ans.add(k);
                    break;
                }
            }

            if(map.get(k) == 1){
                map.remove(k);
            } else {
                map.put(k, map.get(k)-1);
            }
        }


        Iterator<Integer> iterator = ans.iterator();
        if (!iterator.hasNext()){
            System.out.println(0);
        }

        while (iterator.hasNext()){
            System.out.print(iterator.next() + " ");
        }

    }

    private static boolean win(Integer target, Set<Integer> keys) {
        //拷贝map
        HashMap<Integer, Integer> tmpMap = new HashMap<>();
        for (Integer key : keys) {
            tmpMap.put(key, map.get(key));
        }

        tmpMap.put(target, map.get(target)-2);

        List<Integer> keyList = keys.stream().collect(Collectors.toList());
        Collections.sort(keyList);

        for (int i = 0; i < keyList.size(); i++) {
            Integer key = keyList.get(i);
            if(tmpMap.get(key) < 0) return false;
            while (tmpMap.get(key) > 0) {
                tmpMap.put(key, tmpMap.get(key)-1);
                try {
                    Integer key1 = keyList.get(i+1);
                    Integer key2 = keyList.get(i+2);
                    tmpMap.put(key1, tmpMap.get(key1)-1);
                    tmpMap.put(key2, tmpMap.get(key2)-1);
                } catch (Exception e) {
                    return false;
                }
            }
        }

        return true;
    }
}
