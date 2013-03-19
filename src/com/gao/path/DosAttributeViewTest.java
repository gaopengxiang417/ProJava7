package com.gao.path;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;

/**
 * User: gaopengxiang
 * Date: 12-4-14
 * Time: 下午2:20
 */
public class DosAttributeViewTest {
    public static void main(String[] args) throws IOException {
        Path path = FileSystems.getDefault().getPath("D:\\daily\\BR_BRAND_CRM_zfb_20111215", "brandcrm.properties");

        /*DosFileAttributes dosFileAttributes = Files.readAttributes(path, DosFileAttributes.class, LinkOption.NOFOLLOW_LINKS);

        System.out.println(dosFileAttributes.isArchive());
        System.out.println(dosFileAttributes.isHidden());
        System.out.println(dosFileAttributes.isReadOnly());
        System.out.println(dosFileAttributes.isSystem());*/


        /*DosFileAttributeView fileAttributeView = Files.getFileAttributeView(path, DosFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);

        DosFileAttributes dosFileAttributes3 = fileAttributeView.readAttributes();

        System.out.println(dosFileAttributes3.isArchive());
        System.out.println(dosFileAttributes3.isHidden());
        System.out.println(dosFileAttributes3.isReadOnly());
        System.out.println(dosFileAttributes3.isSystem());*/

        Path path1 = Files.setAttribute(path, "dos:hidden", true, LinkOption.NOFOLLOW_LINKS);

        Object attribute = Files.getAttribute(path, "dos:hidden", LinkOption.NOFOLLOW_LINKS);
        System.out.println(attribute);

        Files.setAttribute(path, "dos:hidden", false, LinkOption.NOFOLLOW_LINKS);
    }
}
