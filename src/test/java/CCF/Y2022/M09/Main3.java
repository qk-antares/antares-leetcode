package CCF.Y2022.M09;

import java.util.*;
import java.util.stream.Collectors;

public class Main3 {
    static class Msg{
        int day;
        int user;
        int loc;
        public Msg(int day, int user, int loc){
            this.day = day;
            this.user = user;
            this.loc = loc;
        }
    }

    static class Loc{
        int loc;
        int start;
        int end;
        public Loc(int loc, int start, int end){
            this.loc = loc;
            this.start = start;
            this.end = end;
        }
    }

    public static ArrayList<ArrayList<Msg>> msgList;
    public static HashMap<Integer, Loc> locMap = new HashMap<>();
    public static final String SPACE = " ";

    public static void main(String[] args) {
        //首先专心接收输入
        Scanner scanner = new Scanner(System.in);
        int dayNum = scanner.nextInt();//天数
        msgList = new ArrayList<>(dayNum);

        int locNum, loc, msgNum;
        ArrayList<List<Integer>> ans = new ArrayList<>();
        for (int i = 0; i < dayNum; i++) {
            locNum = scanner.nextInt();
            msgNum = scanner.nextInt();

            //接收所有地区信息
            for (int j = 0; j < locNum; j++) {
                loc = scanner.nextInt();
                if (locMap.containsKey(loc)) {
                    updateLoc(locMap.get(loc), i);
                } else {
                    locMap.put(loc, new Loc(loc, i, i + 6));
                }
            }

            //接收所有漫游消息（应该一天一天处理）
            ArrayList<Msg> dayMsg = new ArrayList<>();
            for (int j = 0; j < msgNum; j++) {
                Msg msg = new Msg(scanner.nextInt(), scanner.nextInt(), scanner.nextInt());
                dayMsg.add(msg);
            }
            msgList.add(dayMsg);

            HashSet<Integer> userSet = new HashSet<>();
            //对于第i天，要再往前遍历6天，看7天内的消息
            for (int j = i; j >= i-6 && j >= 0 ; j--) {
                dayMsg = msgList.get(j);
                for (Msg msg : dayMsg) {
                    //消息所指向的地区是危险地区，而且消息的时间在危险范围内，消息是7天内的
                    Loc dest = locMap.get(msg.loc);
                    if(dest != null && i <= dest.end && i - msg.day < 7 && msg.day >= dest.start && msg.day <= dest.end){
                        userSet.add(msg.user);
                    }
                }
            }
            List<Integer> tmp = userSet.stream().collect(Collectors.toList());
            Collections.sort(tmp);
            ans.add(tmp);
        }


        //至此地区信息和漫游信息接收完毕
        for (int i = 0; i < dayNum; i++) {
            System.out.print(i);
            System.out.print(SPACE);
            for (Integer user : ans.get(i)) {
                System.out.print(user);
                System.out.print(SPACE);
            }
            System.out.println();
        }
    }

    private static void updateLoc(Loc loc, int i) {
        if (i > loc.end + 1) {
            loc.start = i;
        }
        loc.end = i + 6;
    }
}