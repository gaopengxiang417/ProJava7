package com.second.pathclass;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * User: wangchen.gpx
 * Date: 13-1-5
 * Time: 下午2:31
 */
public class ComparePath {
    public static void main(String[] args) throws IOException {
        //我们可以看到这里的路径比较
        Path pathOne = Paths.get("src\\com\\second\\pathclass");
        Path pathTwo = Paths.get("G:\\idea_workspace\\ProJava7\\src\\com\\second\\pathclass");

        //虽然他们的构造的参数不同，但是他们的实际路径是一样的，但是如果直接调用equals返回的是false
        System.out.println(pathOne.equals(pathTwo));
        System.out.println(pathOne.toAbsolutePath());
        System.out.println(pathTwo.toAbsolutePath());

        //下面用Files的方法来比较两个Path是否一样
        boolean sameFile = Files.isSameFile(pathOne, pathTwo);
        System.out.println(sameFile);

        //调用Files类的这个比较方法的两个path必须存在，如果不存在，就会抛出异常
        Path pathThree = FileSystems.getDefault().getPath("/a/b/c");
        Path pathFour = FileSystems.getDefault().getPath("/a/b/c/d");
        try {
            boolean sameFile1 = Files.isSameFile(pathThree, pathFour);
            System.out.println(sameFile1);
        } catch (IOException e) {
            System.out.println(e);
        }

        //startWith endWith的使用,这两个方法都有重写参数分别是String和path
        String pathString = "/a/b/c/d/e";
        Path path = FileSystems.getDefault().getPath(pathString);
        System.out.println(path.startsWith("/a"));
        System.out.println(path.endsWith("e"));
    }
}
