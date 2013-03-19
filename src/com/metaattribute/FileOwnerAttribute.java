package com.metaattribute;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.FileOwnerAttributeView;
import java.nio.file.attribute.UserPrincipal;

/**
 * User: wangchen.gpx
 * Date: 13-1-6
 * Time: 下午1:07
 */
public class FileOwnerAttribute {
    public static void main(String[] args) throws IOException {
        //这里获取系统的用户名
        UserPrincipal wangchen = FileSystems.getDefault().getUserPrincipalLookupService().lookupPrincipalByName("wangchen");
        System.out.println(wangchen);

        //获取文件的用户名和组名
        Path path = Paths.get("D:\\a.txt");
        //根据view来获取owner
        FileOwnerAttributeView fileAttributeView = Files.getFileAttributeView(path, FileOwnerAttributeView.class, LinkOption.NOFOLLOW_LINKS);
        System.out.println(fileAttributeView.getOwner());

        //根据Files的方法来获取owner
        UserPrincipal owner = Files.getOwner(path, LinkOption.NOFOLLOW_LINKS);
        System.out.println(owner);

        System.out.println(FileSystems.getDefault().supportedFileAttributeViews());

        //通过getAttribute方法来获取owner
        Object attribute = Files.getAttribute(path, "owner:owner");
        System.out.println(attribute);

        //设置owner的方法，通过Files的setowner方法
        //首先来获取一个指定的user
        UserPrincipal wangchen1 = FileSystems.getDefault().getUserPrincipalLookupService().lookupPrincipalByName("wangchen");
        //然后进行设置
        Files.setOwner(path, wangchen1);

        //通过Files。setattreibute的方法来设置
        Path path1 = Files.setAttribute(path, "owner:owner", wangchen1, LinkOption.NOFOLLOW_LINKS);

        //通过view的方式来进行设置
        FileOwnerAttributeView fileAttributeView1 = Files.getFileAttributeView(path, FileOwnerAttributeView.class, LinkOption.NOFOLLOW_LINKS);
        fileAttributeView1.setOwner(owner);
    }
}
