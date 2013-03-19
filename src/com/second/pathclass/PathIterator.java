package com.second.pathclass;

import java.nio.file.FileSystems;
import java.nio.file.Path;

/**
 * User: wangchen.gpx
 * Date: 13-1-5
 * Time: 下午2:44
 */
public class PathIterator {
    public static void main(String[] args) {
        //Path的各个路径的节点可以进行循环查看，这里有两中方法，注意这两种方法都不包含跟节点,
        //在windows下根结点是指：C：等，linux下是指：/
        //第一种就是直接调用它的内部方法来获取指定的节点
        String pathString = "/a/b/c/d/e/f";
        Path path = FileSystems.getDefault().getPath(pathString);
        for (int i = 0; i < path.getNameCount(); i++) {
            System.out.print(path.getName(i) + " ");
        }
        //iterator方法
        for (Path str : path) {
            System.out.print(str +" ");
        }
    }
}
