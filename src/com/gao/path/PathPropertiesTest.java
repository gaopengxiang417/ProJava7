package com.gao.path;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.*;

/**
 * User: gaopengxiang
 * Date: 12-4-14
 * Time: 上午9:21
 */
public class PathPropertiesTest {
    public static void main(String[] args) {
        Path path = Paths.get("D:\\daily\\BR_BRAND_PRM_gpxi_20120209", "brandprm.properties");

        Path fileName = path.getFileName();

        System.out.println("this file name is :"+fileName);

        Path root = path.getRoot();
        System.out.println(root);
        System.out.println(path.getNameCount());
        for(int i = 0,j = path.getNameCount();i < j;i++){
            System.out.println("elelment "+i+" is :"+path.getName(i));
        }
        System.out.println(path.getName(0));
        System.out.println(path.getParent());

        Path subpath = path.subpath(0, 2);
        System.out.println(subpath);


        String str = path.toString();
        System.out.println(str);


        URI uri = path.toUri();
        System.out.println(uri);

        Path absoltePath = path.toAbsolutePath();
        System.out.println("before absolute:"+path);
        System.out.println("after absolute:"+absoltePath);

        Path path1 = Paths.get("/test/te.txt");
        System.out.println("after absolute:"+path1.toAbsolutePath());


        try {
            Path realPath = path.toRealPath(LinkOption.NOFOLLOW_LINKS);
            System.out.println(realPath);
        } catch (IOException e) {
            System.out.println("this path is not exist");
        }

        try {
            Path realPath1 = path1.toRealPath(LinkOption.NOFOLLOW_LINKS);
            System.out.println(realPath1);
        } catch (IOException e) {
            System.out.println("this path1 is not really exist");
        }


        File file = path.toFile();
        System.out.println(file);


        Path resolvePath = path.resolve("replace.txt");
        System.out.println(resolvePath);

        /**
         * if the paremeter is a absolute path ,then is will return this parameter
         * if the parameter is a relative path,then it will treat this object is a base
         * dirctory,and parameter is a file,so it simply add the parameter after thie path
         * with a separater between them
         */
        Path resolvePath2 = path.resolve("C:/replace.txt");
        System.out.println("resolve : "+resolvePath2);


        Path siblingPath = path.resolveSibling("replace.txt");

        System.out.println("resolveSibling : "+siblingPath);

        Path siblingPath2 = path.resolveSibling("C:/replace.txt");
        System.out.println(siblingPath2);


        Path path2 = Paths.get("first.txt");
        Path path3 = Paths.get("secibd.txt");

        Path relativize = path2.relativize(path3);
        System.out.println(relativize);
        System.out.println(path3.relativize(path2));


        Path path4 = Paths.get("/firstD/firstfile.txt");
        Path path5 = Paths.get("/seondD/secondFile.txt");

        Path relativize1 = path4.relativize(path5);
        Path relativize2 = path5.relativize(path4);

        System.out.println(relativize1);
        System.out.println(relativize2);


        Path path6 = FileSystems.getDefault().getPath("C:/first/first/txt");
        Path path7 = FileSystems.getDefault().getPath("second/second.txt");

        /*Path relativize3 = path6.relativize(path7);
        Path relativize4 = path7.relativize(path6);

        System.out.println(relativize3);
        System.out.println(relativize4);*/

        Path path8 = Paths.get("/first/first.txt");
        Path path9 = Paths.get("/first/first.txt");

        if (path8.equals(path9)) {
            System.out.println("this two paths are equal");
        }else{
            System.out.println("they are different");
        }


        boolean sameFile = false;
        try {
            sameFile = Files.isSameFile(path8, path9);
            System.out.println(sameFile);
        } catch (IOException e) {
            System.out.println("exception is:"+sameFile+" "+e.getMessage());
        }

        int isEqual = path8.compareTo(path9);
        System.out.println(isEqual);

        boolean start = path8.startsWith("/first");
        System.out.println(start);
        boolean end = path8.endsWith("first.txt");
        System.out.println(end);


        for (Path name : path) {
            System.out.println(name);
        }
        Path normalize = path.normalize();
        System.out.println(normalize);

        Path path10 = FileSystems.getDefault().getPath("C:/mychild/../hiChild/thirdDirctory/./finalFiles/my.properties");
        System.out.println(path10);
        System.out.println(path10.normalize());
    }
}
