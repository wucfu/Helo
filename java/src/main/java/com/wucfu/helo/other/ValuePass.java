package com.wucfu.helo.other;

/**
 * @author wucfu
 * @description 证明java只有值传递
 * @date 2020-08-12
 */
public class ValuePass {

    public static void main(String[] args) {
        User user = new ValuePass().new User("wcf");

        System.out.println("调用change前:" + user);
        changeUser(user);
        System.out.println("调用change后:" + user);
    }

    static void changeUser(User user){
        user = new ValuePass().new User("jack");
        System.out.println("change方法中的user:" + user);
    }

    class User{
        private String name;

        public User(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }
}
