package com.mindcontrol.springbootdemo;

public class Test {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            final int finalI = i;
            System.out.println(finalI);
        }
    }
}
