package com.ww.streamStudy.lambda;

@FunctionalInterface
public interface CustomizeFunctionalInterface<T> {

    double get(T t);
}
