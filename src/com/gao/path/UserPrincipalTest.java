package com.gao.path;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileOwnerAttributeView;
import java.nio.file.attribute.UserPrincipal;

/**
 * User: gaopengxiang
 * Date: 12-4-14
 * Time: 下午3:43
 */
public class UserPrincipalTest {
    public static void main(String[] args) throws IOException {

        Path path = Paths.get("D:\\daily\\BR_BRAND_CRM_zfb_20111215", "brandcrm.properties");

        UserPrincipal gaopengxiang = path.getFileSystem().getUserPrincipalLookupService().lookupPrincipalByName("gaopengxiang");

        System.out.println(gaopengxiang.getName());


        Files.setOwner(path, gaopengxiang);


        FileOwnerAttributeView fileAttributeView = Files.getFileAttributeView(path, FileOwnerAttributeView.class, LinkOption.NOFOLLOW_LINKS);
        UserPrincipal owner = fileAttributeView.getOwner();

        Files.setOwner(path,owner);

        fileAttributeView.setOwner(owner);

        Files.setAttribute(path, "owner:owner", owner, LinkOption.NOFOLLOW_LINKS);

    }
}
