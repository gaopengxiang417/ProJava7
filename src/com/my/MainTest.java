package com.my;

/**
 * User: wangchen.gpx
 * Date: 13-1-8
 * Time: 下午1:59
 */
public class MainTest {
    public static void main(String[] args) {
       /* int i = 0 ;
        i = i++;
        System.out.println(i);

        int j = 1;
        j = (j++) + (++j);
        System.out.println(j);*/

        int i = 1;
        for (int j = 0; j < 100; j++) {
//            i = i++;
//            i = ++i;
            i += i++;//int j = i +1;  i = j +i;
        }
        System.out.println(i);
    }
}
