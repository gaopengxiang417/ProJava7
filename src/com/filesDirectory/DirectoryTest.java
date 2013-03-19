package com.filesDirectory;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.concurrent.TimeUnit;

/**
 * User: wangchen.gpx
 * Date: 13-1-7
 * Time: 上午11:05
 */
public class DirectoryTest {
    public static void main(String[] args) throws IOException {
        //列出一个文件系统的几个根路径,直接调用java7中的接口
        Iterable<Path> rootDirectories = FileSystems.getDefault().getRootDirectories();
        for (Path rootDirectory : rootDirectories) {
            System.out.print(rootDirectory +" ");
        }
        //调用Java。ile中的接口
        File[] files = File.listRoots();
        for (File file : files) {
            System.out.print(file);
        }

        //创建目录的用法，首先要看得是createDirectory方法，他只会创建一个目录
        //也就是说只有在最后的目录的上级目录存在的时候才会创建，如果上级的父目录不存在，
        //那么他会抛出异常
        //如果要创建的目录存在那么也会抛出异常
        Path directory = FileSystems.getDefault().getPath("D:/direc/");
        Path directory1 = Files.createDirectory(directory);
        System.out.println(Files.isSameFile(directory , directory1));
        System.out.println(directory1);

        //级联创建目录的方法，只要是不存在的目录就会创建
        //注意，如果要创建的目录已经存在，那么他不会抛出异常
        Path path = FileSystems.getDefault().getPath("D:/dir/dir/dir/");
        Path directories = Files.createDirectories(path);
        System.out.println(directories == path);
        System.out.println(directories);

        //列出目录下的内容，这里第一个是没有用任何的过滤器进行过滤，实际上就是将指定目录下的所有东西都显示出来
        //注意，这里只会列出指定目录下的一级目录，这些目录下的东西不会列出来
        Path root = FileSystems.getDefault().getPath("D:/");
        DirectoryStream<Path> paths = Files.newDirectoryStream(root);
        for (Path path1 : paths) {
            System.out.println(path1);
        }

        System.out.println("-------------华丽的分割线-----------");
        //列出指定目录下额内容，这里使用的时glob的过滤器进行过滤
        //同理，他只会显示一级目录
        DirectoryStream<Path> paths1 = Files.newDirectoryStream(root, "**");
        for (Path path1 : paths1) {
            System.out.println(path1);
        }

        System.out.println("----------华丽的分割线-----------");
        Path path1 = FileSystems.getDefault().getPath("G:\\idea_workspace\\ProJava7\\src\\com\\metaattribute");
        DirectoryStream<Path> paths2 = Files.newDirectoryStream(path1, "{*.java,*.txt}");
        for (Path path2 : paths2) {
            System.out.println(path2);
        }
        //

        System.out.println("---------华丽的分割线-----------");
        //过滤器的方式来过滤文件
        //过滤目录，只显示文件
        Path path2 = FileSystems.getDefault().getPath("G:\\idea_workspace\\ProJava7\\src\\com\\filesDirectory");
        DirectoryStream<Path> paths3 = Files.newDirectoryStream(path2, new DirectoryStream.Filter<Path>() {
            @Override
            public boolean accept(Path entry) throws IOException {
                return !Files.isDirectory(entry);
            }
        });
        for (Path path3 : paths3) {
            System.out.println(path3);
        }

        System.out.println("----------华丽的分割线-----------");

        //只过滤创建时间是今天的文件
        DirectoryStream<Path> paths4 = Files.newDirectoryStream(path2, new DirectoryStream.Filter<Path>() {
            @Override
            public boolean accept(Path entry) throws IOException {
                //首先获取当前的天
                long currentDays = FileTime.fromMillis(System.currentTimeMillis()).to(TimeUnit.DAYS);
                //获取文件的创建时间
                BasicFileAttributes basicFileAttributes = Files.readAttributes(entry, BasicFileAttributes.class);
                FileTime fileTime = basicFileAttributes.lastModifiedTime();
                long fileDays = fileTime.to(TimeUnit.DAYS);
                return currentDays == fileDays;
            }
        });
        for (Path path3 : paths4) {
            System.out.println(path3);
        }

        System.out.println("------------华丽的分割线-----------");
        Path path3 = FileSystems.getDefault().getPath("D:/");
        DirectoryStream<Path> paths5 = Files.newDirectoryStream(path3, new DirectoryStream.Filter<Path>() {
            @Override
            public boolean accept(Path entry) throws IOException {
                return Files.size(entry) > 200;
            }
        });
        for (Path path4 : paths5) {
            System.out.println(path4);
        }
    }
}
