package com.metaattribute;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.DosFileAttributeView;
import java.nio.file.attribute.DosFileAttributes;
import java.util.Map;

/**
 * User: wangchen.gpx
 * Date: 13-1-6
 * Time: 上午11:26
 */
public class DosFileAttributeTeset {
    public static void main(String[] args) throws IOException {
        Iterable<FileStore> fileStores = FileSystems.getDefault().getFileStores();
        for (FileStore syso : fileStores) {
            System.out.print(syso.supportsFileAttributeView(DosFileAttributeView.class));
       }

        //获取dos文件属性的一种方法，直接获取所有的属性
        Path path =Paths.get("D:\\a.txt");
        DosFileAttributes dosFileAttributes = Files.readAttributes(path, DosFileAttributes.class, LinkOption.NOFOLLOW_LINKS);
        System.out.println(dosFileAttributes.isArchive());
        System.out.println(dosFileAttributes.isReadOnly());
        System.out.println(dosFileAttributes.isHidden());
        System.out.println(dosFileAttributes.isSystem());

        //获取dos文件属性的第二种方法,获取指定的属性
        Map<String,Object> stringObjectMap = Files.readAttributes(path, "dos:hidden,archive,readonly,system", LinkOption.NOFOLLOW_LINKS);
        System.out.println(stringObjectMap.keySet());
        System.out.println(stringObjectMap.values());

        //获取dos文件属性的第三种方法,通过view的方式来获取
        DosFileAttributeView fileAttributeView = Files.getFileAttributeView(path, DosFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
        DosFileAttributes dosFileAttributes1 = fileAttributeView.readAttributes();
        System.out.println(dosFileAttributes1.isArchive());
        System.out.println(dosFileAttributes1.isSystem());
        System.out.println(dosFileAttributes1.isHidden());
        System.out.println(dosFileAttributes1.isReadOnly());

        //修改dos文件属性的第一种方法，通过Files类来进行修改
        Path path1 = Files.setAttribute(path, "dos:hidden", true, LinkOption.NOFOLLOW_LINKS);
        System.out.println(Files.getAttribute(path1,"dos:hidden",LinkOption.NOFOLLOW_LINKS));

        //修改dos文件属性的第二种方法，通过view的方式来修改
        DosFileAttributeView fileAttributeView1 = Files.getFileAttributeView(path, DosFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
        fileAttributeView1.setHidden(false);
        System.out.println(fileAttributeView1.readAttributes().isHidden());

        //查看一个文件是否是系统文件
        DosFileAttributeView fileAttributeView2 = Files.getFileAttributeView(Paths.get("C:\\Windows\\Boot\\Resources\\bootres.dll"), DosFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
        System.out.println(fileAttributeView2.readAttributes().isSystem());
    }
}
