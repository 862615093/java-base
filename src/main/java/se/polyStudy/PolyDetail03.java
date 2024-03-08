package se.polyStudy;

public class PolyDetail03 {
    public static void main(String[] args) {
//        instanceOf 比较操作符，用于判断对象的运行类型是否为 XX 类型或 XX 类型的子类型

        BBB BBB = new BBB();
        System.out.println(BBB instanceof BBB);// true
        System.out.println(BBB instanceof AA);// true

        //aa 编译类型 AA, 运行类型是 BBB
        //BBB 是 AA 子类
        AA aa = new BBB();
        System.out.println(aa instanceof AA);//true
        System.out.println(aa instanceof BBB);//true
        Object obj = new Object();
        System.out.println(obj instanceof AA);//false
        String str = "hello";
//        System.out.println(str instanceof AA);
        System.out.println(str instanceof Object);//true
    }
}

class AA {
} //父类

class BBB extends AA {
}//子类