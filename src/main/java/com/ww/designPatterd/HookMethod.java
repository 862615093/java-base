package com.ww.designPatterd;

/**
 * 钩子方法源于设计模式中模板方法（Template Method）模式，模板方法模式的概念为：
 * 在一个方法中定义一个算法的骨架，而将一些步骤延迟到子类中。模板方法使得子类可以在不改变算法结构的情况下，重新定义算法中的某些步骤。
 * 其主要分为两大类：模版方法和基本方法，而基本方法又分为：抽象方法（Abstract Method），具体方法（Concrete Method），钩子方法（Hook Method）。
 * <p>
 * 四种方法的基本定义（前提：在抽象类中定义）：
 * （1）抽象方法：由抽象类声明，由具体子类实现，并以abstract关键字进行标识。
 * （2）具体方法：由抽象类声明并且实现，子类并不实现或者做覆盖操作。其实质就是普遍适用的方法，不需要子类来实现。
 * （3）钩子方法：由抽象类声明并且实现，子类也可以选择加以扩展。通常抽象类会给出一个空的钩子方法，也就是没有实现的扩展。它和具体方法在代码上没有区别，不过是一种意识的区别；而它和抽象方法有时候也是没有区别的，就是在子类都需要将其实现的时候。而不同的是抽象方法必须实现，而钩子方法可以不实现。也就是说钩子方法为你在实现某一个抽象类的时候提供了可选项，相当于预先提供了一个默认配置。
 * （4）模板方法：定义了一个方法，其中定义了整个逻辑的基本骨架。
 * <p>
 * 优点
 * 封装不变部分，扩展可变部分
 * 提取公共部分代码，便于维护
 * 行为由父类控制，子类实现
 */
public class HookMethod { //https://blog.csdn.net/qq_39132177/article/details/109441230

    /**
     * 抽象类，定义模板方法和基本方法
     */
    static abstract class AbstractClass {
        /**
         * 具体方法,声明并实现，继承此抽象类不需实现此方法
         */
        public void concreteMethod() {
            System.out.println("AbstractClass concreteMethod() 这是一个具体方法");
        }

        /**
         * 抽象方法，abstract关键字标识，只声明，并不实现，继承此抽象类必须实现此方法
         */
        protected abstract void abstractMethod();

        /**
         * 钩子方法，声明并实现（空实现或者定义相关内容皆可），继承此抽象类的子类可扩展实现或者不实现
         */
        public void hookMethod() {
            //可定义一个默认操作，或者为空
            //System.out.print("此钩子方法有个默认操作")
        }

        /**
         * 模板方法，整个算法的骨架 (为了防止恶意的操作，一般模板方法都加上final关键字，不允许被覆写)
         */
        public final void templateMethod() {
            abstractMethod();
            concreteMethod();
            hookMethod();
        }
    }

    static class childClass1 extends AbstractClass {
        @Override
        protected void abstractMethod() {
            System.out.print("子类实现父类抽象类中的抽象方法");
        }

        /**
         * 重构钩子方法
         */
        //public void hookMethod(){
        // System.out.print("子类可以在父类钩子方法实现的基础上进行扩展");
        //}
    }

    static class childClass2 {
        public void bond(AbstractClass abstractClass) {
            abstractClass.templateMethod();
        }
    }


    public static void main(String[] args) {
        childClass2 childClass2 = new childClass2();
        childClass2.bond(new AbstractClass() {
            //匿名内部类实现回调
            @Override
            protected void abstractMethod() {
                System.out.println("子类实现父类抽象类中的抽象方法 abstractMethod()");
            }

            /**
             * 重构钩子方法
             */
            public void hookMethod() {
                System.out.println("子类可以在父类钩子方法hookMethod() 实现的基础上进行扩展");
            }
        });
    }


}
