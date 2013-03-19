package com.metaattribute;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.util.Set;

/**
 * User: wangchen.gpx
 * Date: 13-1-6
 * Time: 下午1:23
 */
public class PosixAttributeTest {
    public static void main(String[] args) throws IOException {
        //首先读取所有的posix的属性,一般这种类型的文件系统有unix，linux
        Path path = FileSystems.getDefault().getPath("D:/a.txt");
        PosixFileAttributes posixFileAttributes = Files.readAttributes(path, PosixFileAttributes.class, LinkOption.NOFOLLOW_LINKS);
        System.out.println(posixFileAttributes.owner());
        System.out.println(posixFileAttributes.group());
        System.out.println(posixFileAttributes.permissions());

        //设置一个文件系统的permissions
        FileAttribute<Set<PosixFilePermission>> setFileAttribute = PosixFilePermissions.asFileAttribute(posixFileAttributes.permissions());
        Files.setPosixFilePermissions(path, posixFileAttributes.permissions());

        //构建一个permission的方式
        Set<PosixFilePermission> posixFilePermissions = PosixFilePermissions.fromString("rwx------");
        Files.setAttribute(path, "posix:permissions", posixFilePermissions, LinkOption.NOFOLLOW_LINKS);

        //获取文件系统的group
        GroupPrincipal wangchen = FileSystems.getDefault().getUserPrincipalLookupService().lookupPrincipalByGroupName("wangchen");
        System.out.println(wangchen);

        Path path4 = Paths.get("D:/a.txt");
        PosixFileAttributeView fileAttributeView = Files.getFileAttributeView(path4, PosixFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
        System.out.println(fileAttributeView.readAttributes().group());


    }
}
