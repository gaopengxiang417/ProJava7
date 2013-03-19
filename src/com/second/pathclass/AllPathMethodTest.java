package com.second.pathclass;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * User: wangchen.gpx
 * Date: 13-1-5
 * Time: 下午2:51
 */
public class AllPathMethodTest {
    public static void main(String[] args) throws IOException {
        FileSystem fileSystem = FileSystems.getDefault();
        Path path = fileSystem.getPath("G:\\idea_workspace\\ProJava7\\src\\com\\second\\pathclass\\DefineClassTest.java");

        //filesystem
        FileSystem fileSystem1 = path.getFileSystem();
        System.out.println(fileSystem == fileSystem1);
        System.out.println(fileSystem);

        //判断是否是绝对路径
        boolean absolute = path.isAbsolute();
        System.out.println(absolute);
        Path path1 = Paths.get("a/b/c/");
        System.out.println(path1.isAbsolute());

        //getRoot，如果是绝对路径返回他的根路径，否则返回为null
        //windows下根路径返回为C：等，linux下为/
        Path root = path.getRoot();
        System.out.println(root);
        System.out.println(path1.getRoot());

        //返回离根路径最远的节点
        Path fileName = path.getFileName();
        System.out.println(fileName);
        System.out.println(path1.getFileName());

        //获取路径的父路径，如果没有父路径那么返回为null
        Path parent = path.getParent();
        System.out.println(parent);
        System.out.println(path1.getParent());
        Path a = FileSystems.getDefault().getPath("a");
        System.out.println(a.getParent());

        //获取该路径上的节点的个数，不算根节点。即/和C：等
        System.out.println(path.getNameCount());
        System.out.println(path1.getNameCount());

        //获取路径上的指定index的节点
        System.out.println(path.getName(3));
        for (int i = 0; i < path.getNameCount(); i++) {
            System.out.print(path.getName(i) + " ");
        }
        System.out.println();

        //获取路径的子路径，这里的参数是指第几个节点，从0开始到getNameCount - 1
        Path subpath = path.subpath(0, 4);
        System.out.println(subpath);

        //如果一个含有根路径，另一个不含有根路径，返回直接返回false
        Path startPath = FileSystems.getDefault().getPath("/a/b/c/d");
        Path d = FileSystems.getDefault().getPath("a");
        boolean b = startPath.startsWith(d);
        System.out.println(b);

        //如果两个都是相对路径,那么应该可以进行比较
        Path path2 = FileSystems.getDefault().getPath("a/b/c/d");
        Path path3 = FileSystems.getDefault().getPath("a/b");
        System.out.println(path2.startsWith(path3));

        //如果两个都含有根路径，那么可以进行比较
        Path path4 = FileSystems.getDefault().getPath("/a/b/c/d");
        Path path5 = FileSystems.getDefault().getPath("/a/b");
        System.out.println(path4.startsWith(path5));

        //endWith 当他们都不含有根节点的时候
        Path path6 = FileSystems.getDefault().getPath("a/b/c/d");
        Path path7 = FileSystems.getDefault().getPath("c/d");
        System.out.println(path6.endsWith(path6));

        //如果他们都含有根节点的时候,这样实际上他们的构造参数必须一样
        Path path8 = FileSystems.getDefault().getPath("/a/b/c/d");
        Path path9 = FileSystems.getDefault().getPath("/a/b/c/d");
        System.out.println(path8.endsWith(path9));

        //如果原路径是相对，要end的路径是绝对路径，那么就只就返回false
        Path path10 = FileSystems.getDefault().getPath("a/b/c");
        Path path11 = FileSystems.getDefault().getPath("/b/c");
        System.out.println(path10.endsWith(path11));

        //normanize方法,其实就是消除冗余的的. 和..
        String pathString = "/home/a/../b/./../c/d/./f";
        Path path12 = Paths.get(pathString);
        Path normalize = path12.normalize();
        System.out.println(normalize);

        //reslove,如果两个路径都是绝对路径的时候,那么返回的就是给定的路径
        Path path13 = Paths.get("/a/b/c/d");
        Path path14 = Paths.get("/d/f/tr/f");
        System.out.println(path13.resolve(path14));

        //如果给定的路径不是绝对路径，那么会将给定路径附加到原路径上
        Path path15 = Paths.get("a/b/c/d");
        Path path16 = Paths.get("t/f");
        System.out.println(path15.resolve(path16));

        //resolvesbing，将给定路径附加到指定路径的父路径上去
        System.out.println(path13.resolveSibling(path14));
        System.out.println(path15.resolveSibling(path16));

        //relativize方法，该方法是resove方法的反向
        Path path17 = Paths.get("/a/b/c/d");
        Path path18 = Paths.get("/a/b/c");
        System.out.println(path17.relativize(path18));

        Path path19 = Paths.get("/a/b/c/d");
        Path path20 = Paths.get("/a/b");
        System.out.println(path19.relativize(path20));

        //toabsoultepath 返回该路径的绝对路径，如果该路径本身已经是绝对路径，返回直接返回
        //否则会加上前面的当前路径构成绝对路径
        //注意，这个方法不会检查该文件是否实际存在，只是在抽象上的拼接
        Path path21 = FileSystems.getDefault().getPath("a/b/c/d");
        System.out.println(path21.toAbsolutePath());

        //torealpath
        //因为该方法会检查该文件是否真实的存在，如果不存在就会抛出异常
        try {
            System.out.println(path21.toRealPath());
        } catch (IOException e) {
            System.out.println(e);
        }
        //构造存在的文件
        System.out.println(path.toRealPath());

        //iterator 方法的调用,所以这里要注意Path类实现了itertaor接口，所以他可以使用增强的for循环
        for (Path paths : path) {
            System.out.print(paths + " ");
        }
    }
}
