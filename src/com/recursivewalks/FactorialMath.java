package com.recursivewalks;

/**
 * User: wangchen.gpx
 * Date: 13-1-8
 * Time: 上午8:54
 */
public class FactorialMath {
    public static void main(String[] args) {
        System.out.println(factorial(4));
    }

    public static int factorial(int n) {
        if (n <= 1) {
            return 1;
        }
        return n * factorial(n - 1);
    }
}
