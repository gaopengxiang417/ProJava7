package com.gao.path;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;

/**
 * User: gaopengxiang
 * Date: 12-4-14
 * Time: 下午3:55
 */
public class POSIXAttributeTest {
    public static void main(String[] args) throws IOException {
        Path path = FileSystems.getDefault().getPath("D:\\daily\\BR_BRAND_CRM_zfb_20111215", "brandcrm.properties");

        /*PosixFileAttributeView fileAttributeView = Files.getFileAttributeView(path, PosixFileAttributeView.class);

        PosixFileAttributes posixFileAttributes = fileAttributeView.readAttributes();

        System.out.println(posixFileAttributes.owner().getName());
        System.out.println(posixFileAttributes.group().getName());
        System.out.println(posixFileAttributes.permissions());

        PosixFileAttributes posixFileAttributes1 = Files.readAttributes(path, PosixFileAttributes.class, LinkOption.NOFOLLOW_LINKS);*/


        Set<PosixFilePermission> posixFilePermissions = PosixFilePermissions.fromString("rw-r--r--");


        Files.setPosixFilePermissions(path,posixFilePermissions);


        Set<PosixFilePermission> posixFilePermissions1 = Files.getPosixFilePermissions(path, LinkOption.NOFOLLOW_LINKS);
        System.out.println(posixFilePermissions1);
    }
}
