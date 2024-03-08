package se.abstractStudy;

public class BB extends Template {

    @Override
    public void job() {//这里也去，重写了 Template 的 job 方法
        long num = 0;
        for (long i = 1; i <= 80000; i++) {
            num *= i;
        }
    }
}