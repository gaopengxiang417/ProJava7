package com.symbolandhardlink;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.*;
import java.util.Set;

/**
 * User: wangchen.gpx
 * Date: 13-1-6
 * Time: 下午4:49
 */
public class CreateSymbolLink {
    public static void main(String[] args) throws IOException {
        /*Path sourcePath = FileSystems.getDefault().getPath("D:/a.txt");
        Path symbolPath = FileSystems.getDefault().getPath("D:/symbol");

        //创建默认属性的符号连接
        try {
            Path symbolicLink = Files.createSymbolicLink(symbolPath, sourcePath);
        } catch (IOException e) {
            System.out.println(e);
        }

        //创建带有属性的符号连接
        Path path = FileSystems.getDefault().getPath("D:/b.txt");
        Path path1 = FileSystems.getDefault().getPath("D:/symbol1");

        PosixFileAttributes posixFileAttributes = Files.readAttributes(path, PosixFileAttributes.class);
        FileAttribute<Set<PosixFilePermission>> setFileAttribute = PosixFilePermissions.asFileAttribute(posixFileAttributes.permissions());

        Path symbolicLink = Files.createSymbolicLink(path1, path, setFileAttribute);


        //创建软连接并且修改他的属性
        Path path2 = FileSystems.getDefault().getPath("D:/b.txt");
        Path path3 = FileSystems.getDefault().getPath("D:sss");

        Files.createSymbolicLink(path3, path2);

        FileTime lastAccessTime = (FileTime) Files.getAttribute(path2, "basic:lastAccessTime");
        FileTime lastModifiedTime = (FileTime) Files.getAttribute(path2, "basic:lastModifiedTime");

        Files.setAttribute(path3, "basic:lastAccessTime", lastAccessTime);
        Files.setAttribute(path3, "basic:lastModifiedTime", lastModifiedTime);*/

        //注意，这里只能在同一个文件系统里面，不能从D建立到E的硬连接
        Path path = FileSystems.getDefault().getPath("D:/a.txt");
        Path path1 = FileSystems.getDefault().getPath("D:/s");
        Files.createLink(path1,path);
    }
}
