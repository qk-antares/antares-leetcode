package leetcode.questions.Medium;

import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;

import org.junit.jupiter.api.Test;

/**
 * @author Antares
 * @date 2022/10/3
 */
public class OtherMedium {
    /**
     * 逆波兰表达式求值（我的解法：操作数栈）
     */
    class EvalRPN {
        public int evalRPN(String[] tokens) {
            Stack<Integer> integers = new Stack<>();

            int num0, num1;
            for(int i = 0;i < tokens.length;i++){
                //是操作数
                if(Character.isDigit(tokens[i].charAt(0)) || tokens[i].length() > 1){
                    integers.push(Integer.valueOf(tokens[i]));
                //是运算符
                }else {
                    num1 = integers.pop();
                    num0 = integers.pop();
                    switch (tokens[i].charAt(0)){
                        case '+' : integers.push(num0 + num1);break;
                        case '-' : integers.push(num0 - num1);break;
                        case '*' : integers.push(num0 * num1);break;
                        case '/' : integers.push(num0 / num1);break;
                    }
                }
            }
            return integers.pop();
        }
    }

    /**
     * 任务调度器（数学问题，可以推出答案为(count[maxSize] - 1) * (n + 1) + maxCount）
     *
     * AAABBBCC 1
     * ACBABCB
     *
     */
    class LeastInterval {
        public int leastInterval(char[] tasks, int n) {
            //统计每种类型任务的数量
            HashMap<Character, Integer> count = new HashMap<>();
            for (char task : tasks) {
                count.put(task, count.getOrDefault(task, 0) + 1);
            }

            PriorityQueue<int[]> priorityQueue = new PriorityQueue<>((o1, o2) -> o2[1]-o1[1]);
            Set<Character> key = count.keySet();
            for (Character character : key) {
                priorityQueue.add(new int[]{character, count.get(character)});
            }

            //(count[maxSize] - 1) * (n + 1)
            int pre = (priorityQueue.peek()[1] - 1) * (n + 1);

            //maxCount
            int maxCount = 1;
            int max = priorityQueue.poll()[1];
            while (!priorityQueue.isEmpty()){
                if(priorityQueue.poll()[1] == max)
                    maxCount++;
                else
                    break;
            }

            return pre+maxCount > tasks.length ? pre+maxCount : tasks.length;
        }
    }

    /**
     * 两整数之和（位运算）
     */
    class GetSum {
        public int getSum(int a, int b) {
            if(b == 0)
                return a;
            // a ^ b 表示不产生进位, a & b是产生进位
            return getSum(a^b, (a&b) << 1);
        }
    }

    @Test
    public void invoke(){
    }
}
