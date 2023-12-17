package CSP.Y2022.M06;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Main3 {
    public static List<Role> roles;
    public static List<Relation> relations;
    public static HashMap<String, Relation> userRelation = new HashMap<>();
    public static HashMap<String, Relation> groupRelation = new HashMap<>();
    public static HashMap<String, Role> roleMap = new HashMap<>();


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();//角色数量
        int m = scanner.nextInt();//关联数量
        int q = scanner.nextInt();//操作数量

        roles = new ArrayList<>(n);
        relations = new ArrayList<>(m);
        scanner.nextLine();
        //统计所有的角色
        for (int i = 0; i < n; i++) {
            roles.add(new Role(scanner.nextLine().split(" ")));
        }

        //统计所有角色关联
        for (int i = 0; i < m; i++) {
            relations.add(new Relation(scanner.nextLine().split(" ")));
        }

        ArrayList<Integer> ans = new ArrayList<>(q);
        for (int i = 0; i < q; i++) {
            Action action = new Action(scanner.nextLine().split(" "));
            ans.add(checkAction(action));
        }

        for (int i = 0; i < q; i++) {
            System.out.println(ans.get(i));
        }

        scanner.close();

    }

    private static Integer checkAction(Action action) {
        //首先获取该用户所属的角色关联
        String username = action.user.name;
        List<String> groups = action.user.groups;
        Relation relation = userRelation.get(username);
        if(relation != null){
            Role role = roleMap.get(relation.roleName);
            if(role != null && role.isActionOk(action.operation, action.resType, action.resName)){
                return 1;
            }
        }
        for (String group : groups) {
            relation = groupRelation.get(group);
            if(relation != null){
                Role role = roleMap.get(relation.roleName);
                if(role != null && role.isActionOk(action.operation, action.resType, action.resName)){
                    return 1;
                }
            }
        }
        return 0;
    }

    static class User{
        String name;
        List<String> groups;
    }

    static class Action{
        User user;
        String operation;
        String resType;
        String resName;

        public Action(String[] split) {
            user = new User();
            user.name = split[0];
            int len = Integer.valueOf(split[1]);
            user.groups = new ArrayList<>(len);
            for (int i = 0; i < len; i++) {
                user.groups.add(split[2+i]);
            }
            operation = split[2+len];
            resType = split[3+len];
            resName = split[4+len];
        }
    }

    static class Role{
        String name;
        List<String> operations;
        List<String> resType;
        List<String> resName;

        public Role(String[] split) {
            int len1,len2,len3;
            this.name = split[0];
            len1 = Integer.valueOf(split[1]);
            operations = new ArrayList<>(len1);
            for (int i = 0; i < len1; i++) {
                operations.add(split[2+i]);
            }
            len2 = Integer.valueOf(split[2+len1]);
            resType = new ArrayList<>(len2);
            for (int i = 0, start = 3+len1; i < len2; i++) {
                resType.add(split[start+i]);
            }
            len3 = Integer.valueOf(split[3+len1+len2]);
            resName = new ArrayList<>(len3);
            for (int i = 0, start = 4+len1+len2; i < len3; i++) {
                resName.add(split[start+i]);
            }
            roleMap.put(name, this);
        }

        public boolean isActionOk(String operation, String resType, String resName){
            if((this.operations.contains("*") || this.operations.contains(operation)) &&
                    (this.resType.contains("*") || this.resType.contains(resType)) &&
                    (this.resName.size() == 0 || this.resName.contains(resName))){
                return true;
            }
            return false;
        }
    }

    static class Relation{
        String roleName;

        public Relation(String[] split) {
            this.roleName = split[0];
            int len = Integer.valueOf(split[1]);
            for (int i = 0; i < len; i++) {
                switch (split[2+2*i]){
                    case "u" : userRelation.put(split[3+2*i], this);break;
                    case "g" : groupRelation.put(split[3+2*i], this);break;
                }
            }
        }
    }
}
