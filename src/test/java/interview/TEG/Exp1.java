package interview.TEG;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Exp1 {
    static Map<String, Integer> count(String str) {
        Map<String, Integer> map = new HashMap<>();
        str = str.toLowerCase();
        String[] words = str.split("[^a-z]+");

        for(String word : words) {
            map.merge(word, 1, Integer::sum);
        }
        return map;
    }

    static Map<String, Integer> count1(String str) {
        Map<String, Integer> map = new HashMap<>();
        str = str.toLowerCase();

        StringBuilder sb = new StringBuilder();
        for(char c : str.toCharArray()) {
            if(c >= 'a' && c <= 'z') {
                sb.append(c);
            } else {
                if(sb.length() > 0) {
                    String word = sb.toString();
                    map.merge(word, 1, Integer::sum);
                    sb.setLength(0);
                }
            }
        }
        return map;

    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String str = in.nextLine();
        Map<String, Integer> result = count1(str);
        System.out.println(result);
    }
}
