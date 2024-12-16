package designPatterd.composite;

/**
 * 决策树中使用的用户信息
 */
public class User {

    private Gender gender;
    private int age;
    private Object more;

    public User(Gender gender, int age) {
        this.gender = gender;
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Object getMore() {
        return more;
    }

    public void setMore(Object more) {
        this.more = more;
    }
}