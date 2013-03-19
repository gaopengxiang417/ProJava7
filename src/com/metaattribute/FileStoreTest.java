package com.metaattribute;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.AclFileAttributeView;
import java.nio.file.attribute.PosixFileAttributeView;

/**
 * User: wangchen.gpx
 * Date: 13-1-6
 * Time: 下午2:59
 */
public class FileStoreTest {
    public static void main(String[] args) throws IOException {
        Iterable<FileStore> fileStores = FileSystems.getDefault().getFileStores();
        int count = 0;
        for (FileStore fileStore : fileStores) {
            count++;
            System.out.println("--------"+fileStore.name()+"-------" + fileStore.type());
            System.out.println("total size:"+(fileStore.getTotalSpace() / (1024*1024*1024)));
            System.out.println("used size:"+fileStore.getUsableSpace() / (1024*1024*1024));
            System.out.println("unallocate size" + fileStore.getUnallocatedSpace() / (1024*1024*1024));
            System.out.println("isreadonly"+fileStore.isReadOnly());
            System.out.println(fileStore.supportsFileAttributeView("basic"));
            System.out.println(fileStore.supportsFileAttributeView(PosixFileAttributeView.class));
            System.out.println(fileStore.supportsFileAttributeView(AclFileAttributeView.class));

//            System.out.println(fileStore.getAttribute("basic:lastModifiedTime"));
        }
        System.out.println(count);

        System.out.println("------------------华丽的分割线---------------");

        Path path = Paths.get("D:/a.txt");
        FileStore fileStore = Files.getFileStore(path);
        System.out.println(fileStore.name());
        System.out.println(fileStore.type());
        System.out.println("total size : " + fileStore.getTotalSpace() / (1024*1024*1024));
        System.out.println("usered size:" + (fileStore.getTotalSpace() - fileStore.getUsableSpace())/(1024*1024*1024));
        System.out.println("aviable size:"+(fileStore.getUsableSpace() / (1024*1024*1024)));
        System.out.println(fileStore.isReadOnly());
    }
}
