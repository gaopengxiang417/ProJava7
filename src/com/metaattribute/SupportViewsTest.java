package com.metaattribute;

import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.*;
import java.util.Set;

/**
 * User: wangchen.gpx
 * Date: 13-1-6
 * Time: 上午10:36
 */
public class SupportViewsTest {
    public static void main(String[] args) throws IOException {
        //可以通过下面的方法来查看当前的文件系统多支持的文件view
        //basic作为最基本的view，所有的系统都应该支持，所以这里应该至少返回一个
        Set<String> strings = FileSystems.getDefault().supportedFileAttributeViews();
        for (String string : strings) {
            System.out.println(string);
        }

        //获取所有的文件存储
        Iterable<FileStore> fileStores = FileSystems.getDefault().getFileStores();
        for (FileStore fileStore : fileStores) {
            System.out.println(fileStore.name()+":"+fileStore.supportsFileAttributeView(BasicFileAttributeView.class));
        }

        //查看某个路径是否支持文件视图的类型
        Path path = FileSystems.getDefault().getPath("D:\\java_tools");
        FileStore fileStore = Files.getFileStore(path);
        System.out.println(fileStore.supportsFileAttributeView("basic"));
        System.out.println(fileStore.supportsFileAttributeView(PosixFileAttributeView.class));
        System.out.println(fileStore.supportsFileAttributeView(AclFileAttributeView.class));
        System.out.println(fileStore.supportsFileAttributeView(UserDefinedFileAttributeView.class));
        System.out.println(fileStore.supportsFileAttributeView(DosFileAttributeView.class));

        //获取所有的文件存储的根目录
        Iterable<Path> rootDirectories = FileSystems.getDefault().getRootDirectories();
        for (Path rootDirectory : rootDirectories) {
            System.out.println(rootDirectory);
        }
    }
}
