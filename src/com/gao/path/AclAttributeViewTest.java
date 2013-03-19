package com.gao.path;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.*;
import java.util.List;

/**
 * User: gaopengxiang
 * Date: 12-4-15
 * Time: 上午8:56
 */
public class AclAttributeViewTest {
    public static void main(String[] args) throws IOException {

        Path path = FileSystems.getDefault().getPath("D:\\daily\\BR_BRAND_CRM_gpx_20120214", "brandcrm.properties");

        AclFileAttributeView fileAttributeView = Files.getFileAttributeView(path, AclFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);

        List<AclEntry> acl = fileAttributeView.getAcl();


        List attribute = (List<AclEntry>)Files.getAttribute(path, "acl:acl", LinkOption.NOFOLLOW_LINKS);

        for (int i = 0; i < attribute.size(); i++) {
            AclEntry entity = (AclEntry) attribute.get(i);
            System.out.println(entity.flags());
            System.out.println(entity.permissions());
            System.out.println(entity.type());
            System.out.println(entity.principal());
        }

        /*AclEntry aclEntry = AclEntry.newBuilder().setType(AclEntryType.ALLOW).setPermissions(AclEntryPermission.DELETE, AclEntryPermission.EXECUTE)
                .setFlags(AclEntryFlag.FILE_INHERIT, AclEntryFlag.NO_PROPAGATE_INHERIT).build();

        acl.add(aclEntry);

        fileAttributeView.setAcl(acl);*/



    }
}
