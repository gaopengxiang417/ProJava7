package com.gao.path;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.*;
import java.util.List;

/**
 * User: gaopengxiang
 * Date: 12-4-15
 * Time: 上午9:07
 */
public class AclUpdateTest {
    public static void main(String[] args) throws IOException {
        //constructe the path
        /**
         * there is two way to do this,
         * 1.Paths.get() method<br/>
         * 2.FIles.getDefault().get()
         */
        Path path = Paths.get("D:\\daily\\BR_BRAND_CRM_gpx_20120214", "brandcrm.properties");

        /**
         * get the fileView
         */
        AclFileAttributeView fileAttributeView = Files.getFileAttributeView(path, AclFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);

        /**
         * find the default userPrincipal
         */
        UserPrincipal userPrincipal = path.getFileSystem().getUserPrincipalLookupService().lookupPrincipalByName("gaopengxiang");

        /**
         * get the aclEntry
         */
        AclEntry aclEntry = AclEntry.newBuilder().setFlags(AclEntryFlag.DIRECTORY_INHERIT)
                .setPermissions(AclEntryPermission.DELETE, AclEntryPermission.DELETE_CHILD, AclEntryPermission.READ_ACL)
                .setType(AclEntryType.ALLOW)
                .setPrincipal(userPrincipal).build();


        List<AclEntry> aclEntryList = fileAttributeView.getAcl();

        aclEntryList.add(aclEntry);

        fileAttributeView.setAcl(aclEntryList);

//        Files.setAttribute(path, "acl:acl", aclEntryList, LinkOption.NOFOLLOW_LINKS);


    }
}
