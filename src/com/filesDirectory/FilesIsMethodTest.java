package com.filesDirectory;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.DosFileAttributeView;
import java.nio.file.attribute.DosFileAttributes;

/**
 * Uer: wangchen.gpx
 * Date: 13-1-7
 * Time: 上午9:58
 */
public class FilesIsMethodTest {
    public static void main(String[] args) throws IOException {
        //first construct a path
        Path path = Paths.get("D:\\project");
        Path path1 = Paths.get("D:/a.txt");
        //检查文件的一些is方法
        System.out.println("isDirectory : "+Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS));
        System.out.println("isexecuteable:"+Files.isExecutable(path));
        System.out.println("ishidden:"+Files.isHidden(path));
        System.out.println("isreadable:"+Files.isReadable(path));
        System.out.println("isregular:"+Files.isRegularFile(path));
        System.out.println("isSymbollink:"+Files.isSymbolicLink(path));
        System.out.println("iswriteable:"+Files.isWritable(path));
        System.out.println(Files.isSameFile(path,path1));

        //检测文件或者目录是否存在
        Path existPath = FileSystems.getDefault().getPath("D:\\project");
        System.out.println("exist : "+Files.exists(existPath,LinkOption.NOFOLLOW_LINKS));
        System.out.println("notexist : " + Files.notExists(existPath,LinkOption.NOFOLLOW_LINKS));
        //注意exist和notexist不是互反的关系，例如：当exist返回的是false的时候该文件可能不存在或者我们无法访问该文件
        //notexist返回的为false的时候，该文件可能不存在或者无法访问
        //所以只有当他们都返回为false的时候，那么说明他们是无法访问该文件的
        //所以以后用的时候要注意，我们应该这样使用
        /*if (!Files.exists(path) && !Files.notExists(path)) {
            //说明该路径无法访问不一定不存在
        }*/

        System.out.println("----------------华丽的分割线------------");

         //检查文件或者目录的是否可读，可写，可执行,和普通的文件
        //这个作用比较大，一般是在进行实际操作之前的时候进行这些校验
        //防止后面抛出异常和不可预知的错误
        //这些判断都首先会判断真实的的目录或者文件是否存在
        Path existPathIs = FileSystems.getDefault().getPath("D:/a.txt");
        System.out.println("isreadable : "+Files.isReadable(existPathIs));
        System.out.println("isWritable："+Files.isWritable(existPathIs));
        System.out.println("isexecutable: "+Files.isExecutable(existPathIs));
        System.out.println("isregularfile : "+Files.isRegularFile(existPathIs));


        //文件不存在的时候
        Path notExistPath = FileSystems.getDefault().getPath("D:/ss");
        System.out.println("isreadable : "+Files.isReadable(notExistPath));
        System.out.println("isWritable："+Files.isWritable(notExistPath));
        System.out.println("isexecutable: "+Files.isExecutable(notExistPath));
        System.out.println("isregularfile : "+Files.isRegularFile(notExistPath));
        if (Files.isReadable(notExistPath) && Files.isWritable(notExistPath) &&
                Files.isExecutable(notExistPath) && Files.isRegularFile(notExistPath)) {
            System.out.println("this path a accessable path!!");
        }else{
            System.out.println("this path is a invaild path");
        }

        System.out.println("--------------华丽的分割线---------------");

        //判断两个path是否指向的同一个文件
        Path path2 = FileSystems.getDefault().getPath("G:\\idea_workspace\\ProJava7\\src\\com\\filesDirectory\\FilesIsMethodTest.java");
        Path path3 = FileSystems.getDefault().getPath("src\\com\\filesDirectory\\FilesIsMethodTest.java");

        Path path4 = FileSystems.getDefault().getPath("G:/hardlink");
        //创建硬连接只能是针对文件，不能对目录进行硬连接的创建
        Path link = Files.createLink(path4, path2);

        System.out.println(Files.isSameFile(path4,link));
        System.out.println(Files.isSameFile(path2, path3));
        System.out.println(Files.isSameFile(path2,link));

        //判断文件是否为隐藏文件
        Path path5 = FileSystems.getDefault().getPath("D:/a.txt");
        System.out.println(Files.isHidden(path5));
        System.out.println(Files.readAttributes(path5, DosFileAttributes.class).isHidden());

        //设置为hidden
        DosFileAttributeView fileAttributeView = Files.getFileAttributeView(path5, DosFileAttributeView.class);
        fileAttributeView.setHidden(true);

        System.out.println(Files.isHidden(path5));

        fileAttributeView.setHidden(false);
    }
}
