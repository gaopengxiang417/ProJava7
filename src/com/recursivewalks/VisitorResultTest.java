package com.recursivewalks;

import java.nio.file.FileVisitResult;

/**
 * User: wangchen.gpx
 * Date: 13-1-8
 * Time: 上午9:06
 */
public class VisitorResultTest {
    public static void main(String[] args) {
        FileVisitResult[] values = FileVisitResult.values();
        for (FileVisitResult value : values) {
            System.out.println(value);
        }
    }
}
