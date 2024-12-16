package se.collectionStudy;

import java.util.Comparator;
import java.util.TreeSet;

public class TreeSetStudy {
    public static void main(String[] args) {
        treeSetComparator();
    }

    public static void treeSetComparator() {
        // TreeSet treeSet = new TreeSet();
        TreeSet treeSet = new TreeSet(new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                //下面 调用 String 的 compareTo 方法进行字符串大小比较
                //如果老韩要求加入的元素，按照长度大小排序
                //return ((String) o2).compareTo((String) o1);
                return ((String) o1).length() - ((String) o2).length();
            }
        });
        //添加数据.
//        调用 treeSet.add("tom"), 在底层会执行到
//        if (cpr != null) {//cpr 就是我们的匿名内部类(对象)
//            do {
//                parent = t;
//                //动态绑定到我们的匿名内部类(对象)compare
//                cmp = cpr.compare(key, t.key);
//                if (cmp < 0)
//                    t = t.left;
//                else if (cmp > 0)
//                    t = t.right;
//                else //如果相等，即返回 0,这个 Key 就没有加入
//                    return t.setValue(value);
//            } while (t != null);
//        }
        treeSet.add("tom");//3
        treeSet.add("sp");
        treeSet.add("a");
        treeSet.add("abc");//3
        treeSet.add("jack");
        System.out.println("treeSet=" + treeSet);
    }
}
