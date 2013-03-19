package com.second.pathclass;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * User: wangchen.gpx
 * Date: 13-1-5
 * Time: 上午10:09
 */
public class PathInformation {
    public static void main(String[] args) {
        Path path = FileSystems.getDefault().getPath("G:\\idea_workspace\\gaotest1\\src\\com\\gao", "FirstTest.java");
        Path pathTwo = Paths.get("G:\\idea_workspace\\gaotest1\\src\\com\\gao");

        //获取文件名称，不管该路径指定的是目录还是文件，其实返回的时离根目录最远的那个路径
        System.out.println(path.getFileName());
        System.out.println(pathTwo.getFileName());

        //获取文件的根路径，其实也就是获取的根目录
        System.out.println(path.getRoot());
        System.out.println(pathTwo.getRoot());

        //获取父路径
        System.out.println(path.getParent());
        System.out.println(pathTwo.getParent());

        //获取路径的深度，这里根路径不算入，也就是说如果只有根路径，返回的将会是0
        int nameCount = path.getNameCount();
        System.out.println(nameCount);
        for (int i = 0 ; i < nameCount; i++) {
            System.out.println(path.getName(i));
        }

        //获取子路径
        Path subpath = path.subpath(2, 4);
        System.out.println(subpath);
    }
}
