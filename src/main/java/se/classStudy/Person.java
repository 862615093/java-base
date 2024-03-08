package se.classStudy;

public class Person {

    private String name;

    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}

class B {
    public void m(Person p) {
//        p = null;
//        p.setAge(12);
//        p = new Person();
//        p.setAge(11);
        System.out.println("m---->" + p);
    }
}

class A {

    public static void main(String[] args) {
        Person p = new Person();
        p.setName("haha");
        p.setAge(18);
        B b = new B();
        b.m(p);
        System.out.println("main---->" + p);
    }

}


