package com.ww.jvm;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * 1. 强引用
 * 只有所有 GC Roots 对象都不通过【强引用】引用该对象，该对象才能被垃圾回收
 * <p>
 * 2. 软引用（SoftReference）
 * 仅有软引用引用该对象时，在垃圾回收后，内存仍不足时会再次出发垃圾回收，回收软引用对象。可以配合引用队列来释放软引用自身
 * <p>
 * 3. 弱引用（WeakReference）
 * 仅有弱引用引用该对象时，在垃圾回收时，无论内存是否充足，都会回收弱引用对象。可以配合引用队列来释放弱引用自身
 * <p>
 * 4. 虚引用（PhantomReference）
 * 必须配合引用队列使用，主要配合 ByteBuffer 使用，被引用对象回收时，会将虚引用入队。由 Reference Handler 线程调用虚引用相关方法释放直接内存
 * <p>
 * 5. 终结器引用（FinalReference）
 * 无需手动编码，但其内部配合引用队列使用，在垃圾回收时，终结器引用入队（被引用对象暂时没有被回收），
 * 再由 Finalizer 线程通过终结器引用找到被引用对象并调用它的 finalize方法，第二次 GC 时才能回收被引用对象
 */
public class Reference4Test {

    private static final int _4MB = 4 * 1024 * 1024;

    /**
     * -Xmx20m -XX:+PrintGCDetails -verbose:gc
     */
    public static void main(String[] args) throws IOException {
//        soft();
        weak();
    }

    //软引用
    public static void soft() throws IOException {
//        List<byte[]> list = new ArrayList<>();
//        for (int i = 0; i < 5; i++) {
//            list.add(new byte[_4MB]);
//        }
//        System.out.println("结束...");
//        System.in.read();

        // list --> SoftReference --> byte[]
        List<SoftReference<byte[]>> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            SoftReference<byte[]> ref = new SoftReference<>(new byte[_4MB]);
            System.out.println(ref.get());
            list.add(ref);
            System.out.println(list.size());
        }
        System.out.println("循环结束：" + list.size());
        for (SoftReference<byte[]> ref : list) {
            System.out.println(ref.get());
        }
    }

    //弱引用
    public static void weak() {
        //  list --> WeakReference --> byte[]
        List<WeakReference<byte[]>> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            WeakReference<byte[]> ref = new WeakReference<>(new byte[_4MB]);
            list.add(ref);
            for (WeakReference<byte[]> w : list) {
                System.out.print(w.get() + " ");
            }
            System.out.println();

        }
        System.out.println("循环结束：" + list.size());
    }


}
