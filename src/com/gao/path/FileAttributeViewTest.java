package com.gao.path;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.FileTime;
import java.util.concurrent.TimeUnit;

/**
 * User: gaopengxiang
 * Date: 12-4-14
 * Time: 下午1:35
 */
public class FileAttributeViewTest {
    public static void main(String[] args) throws IOException {
        /*Set<String> supportedViews = FileSystems.getDefault().supportedFileAttributeViews();

        for (String view : supportedViews) {
            System.out.println(view);
        }

        Iterable<FileStore> fileStores = FileSystems.getDefault().getFileStores();
        for (FileStore fileStore : fileStores) {
            System.out.println(fileStore.toString());
            System.out.println(fileStore.getTotalSpace()+":"+fileStore.getUnallocatedSpace()+":"+fileStore.getUsableSpace());
            System.out.println(fileStore.isReadOnly()+":"+fileStore.type());
            System.out.println(fileStore.supportsFileAttributeView(BasicFileAttributeView.class));
        }*/


        Path path = Paths.get("D:\\daily\\BR_BRAND_CRM_zfb_20111215", "brandcrm.properties");

       /* FileStore fileStore = Files.getFileStore(path);

        boolean isSupported = fileStore.supportsFileAttributeView("basic");
        System.out.println(isSupported);

        BasicFileAttributes basicFileAttributes = Files.readAttributes(path, BasicFileAttributes.class, LinkOption.NOFOLLOW_LINKS);
        System.out.println("isSymbolicLink:"+basicFileAttributes.isSymbolicLink());
        System.out.println(basicFileAttributes.isOther());
        System.out.println(basicFileAttributes.isRegularFile());
        System.out.println(basicFileAttributes.lastAccessTime());
        System.out.println(basicFileAttributes.lastModifiedTime());
        System.out.println(basicFileAttributes.creationTime());
        System.out.println(basicFileAttributes.fileKey());
        System.out.println(basicFileAttributes.isDirectory());
        System.out.println(basicFileAttributes.size());


        FileTime timeAttribute = (FileTime)Files.getAttribute(path, "basic:lastModifiedTime", LinkOption.NOFOLLOW_LINKS);
        System.out.println(timeAttribute);*/

        BasicFileAttributeView fileAttributeView = Files.getFileAttributeView(path, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
        FileTime fromTime = FileTime.from(System.nanoTime(), TimeUnit.NANOSECONDS);
        fileAttributeView.setTimes(fromTime,null,null);

        FileTime lastModifiedTime = Files.getLastModifiedTime(path, LinkOption.NOFOLLOW_LINKS);
        System.out.println(lastModifiedTime);

        FileTime nowTime = FileTime.from(System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        Path path2 = Files.setLastModifiedTime(path,nowTime);
        FileTime lastModifiedTime1 = Files.getLastModifiedTime(path2, LinkOption.NOFOLLOW_LINKS);
        System.out.println(lastModifiedTime1);

        Files.setAttribute(path,"basic:lastModifiedTime",nowTime,LinkOption.NOFOLLOW_LINKS);
        Files.setAttribute(path,"basic:creationTime",nowTime,LinkOption.NOFOLLOW_LINKS);
        Files.setAttribute(path, "basic:lastAccessTime", nowTime, LinkOption.NOFOLLOW_LINKS);

        System.out.println(Files.getAttribute(path,"basic:lastModifiedTime",LinkOption.NOFOLLOW_LINKS));
        System.out.println(Files.getAttribute(path,"basic:creationTime",LinkOption.NOFOLLOW_LINKS));
        System.out.println(Files.getAttribute(path,"basic:lastAccessTime",LinkOption.NOFOLLOW_LINKS));

    }
}
