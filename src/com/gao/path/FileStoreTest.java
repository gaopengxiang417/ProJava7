package com.gao.path;

import java.io.IOException;
import java.nio.file.*;

/**
 * User: gaopengxiang
 * Date: 12-4-15
 * Time: 上午9:29
 */
public class FileStoreTest {
    public static void main(String[] args) throws IOException {

        Path path = Paths.get("D:\\daily\\BR_BRAND_CRM_gpx_20120214", "brandcrm.properties");

        Iterable<FileStore> fileStores = FileSystems.getDefault().getFileStores();

        for (FileStore fileStore : fileStores) {
            System.out.println("name:"+fileStore);
            System.out.println("total space:"+fileStore.getTotalSpace()/1024*1024);
            System.out.println("free space:"+fileStore.getUnallocatedSpace()/1024*1024);
            System.out.println("isReadOnly:"+fileStore.isReadOnly());
            System.out.println(fileStore.type());

        }

        FileStore fileStore = Files.getFileStore(path);
//        System.out.println(fileStore.supportsFileAttributeView((Class<? extends FileAttributeView>) FileStoreAttributeView.class));
    }
}
