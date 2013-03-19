package com.metaattribute;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.*;
import java.util.List;

/**
 * User: wangchen.gpx
 * Date: 13-1-6
 * Time: 下午1:53
 */
public class ACLAttributeTest {
    public static void main(String[] args) throws IOException {
        Path path = FileSystems.getDefault().getPath("D:/a.txt");

        //读取acl的属性，这里是通过view的方式来进行读取
        AclFileAttributeView fileAttributeView = Files.getFileAttributeView(path, AclFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
        System.out.println(fileAttributeView.getOwner());
        System.out.println(fileAttributeView.name());
        System.out.println(fileAttributeView.getAcl());

        System.out.println("---------------华丽的分割线---------------------");
        //通过直接读取属性的方式来获取属性
        List<AclEntry> attribute = (List<AclEntry>) Files.getAttribute(path, "acl:acl", LinkOption.NOFOLLOW_LINKS);
        for (AclEntry aclEntry : attribute) {
            System.out.println(aclEntry.principal());
            System.out.println(aclEntry.permissions());
            System.out.println(aclEntry.type());
            System.out.println(aclEntry.flags());
        }

        System.out.println("--------------华丽的分割线-------------------------");

        //修改acl的权限等
        //首先读取view的方式来进行修改
        AclFileAttributeView fileAttributeView1 = Files.getFileAttributeView(path, AclFileAttributeView.class);
       //获取userprinpal
        UserPrincipal wangchen = FileSystems.getDefault().getUserPrincipalLookupService().lookupPrincipalByName("wangchen");
        //构建aclentry
        AclEntry build = AclEntry.newBuilder().setType(AclEntryType.ALLOW).setPrincipal(wangchen)
                .setPermissions(AclEntryPermission.APPEND_DATA, AclEntryPermission.DELETE)
                .setFlags(AclEntryFlag.DIRECTORY_INHERIT).build();

        //获取现在有的entry
        System.out.println("-----------------------before----------------");
        List<AclEntry> acl = fileAttributeView1.getAcl();
        for (AclEntry aclEntry : acl) {
            System.out.println(aclEntry.principal());
            System.out.println(aclEntry.permissions());
            System.out.println(aclEntry.type());
            System.out.println(aclEntry.flags());
        }
        //添加acl
        acl.add(build);

        //设置回去
        fileAttributeView1.setAcl(acl);

        System.out.println("---------------after--------------");

        //获取修改以后的
        List<AclEntry> acl1 = fileAttributeView1.getAcl();
        for (AclEntry aclEntry : acl1) {
            System.out.println(aclEntry.principal());
            System.out.println(aclEntry.permissions());
            System.out.println(aclEntry.type());
            System.out.println(aclEntry.flags());
        }
    }
}
