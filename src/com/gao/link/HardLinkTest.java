package com.gao.link;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * User: gaopengxiang
 * Date: 12-4-15
 * Time: 下午2:26
 */
public class HardLinkTest {
    public static void main(String[] args) throws IOException {

        Path hardPath = FileSystems.getDefault().getPath("D:/ss-2.txt");
        Path targetPath = FileSystems.getDefault().getPath("D:\\daily\\BR_BRAND_CRM_gpx_20120214", "brandcrm.properties");

        try {
            Files.createLink(hardPath, targetPath);
            System.out.println("the hard link already created");
        } catch (IOException e) {
            e.printStackTrace();
        }


        boolean isSymbolLink1 = Files.isSymbolicLink(hardPath);
        Boolean isSymbolLink2 = false;
        try {
            isSymbolLink2 = (Boolean)Files.getAttribute(targetPath, "basic:isSymbolicLink");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(isSymbolLink1);
        System.out.println(isSymbolLink2);

        BasicFileAttributeView fileAttributeView = Files.getFileAttributeView(hardPath, BasicFileAttributeView.class);
        BasicFileAttributes basicFileAttributes = fileAttributeView.readAttributes();
        boolean symbolicLink = basicFileAttributes.isSymbolicLink();
        System.out.println(symbolicLink);


        boolean isSameFile = Files.isSameFile(hardPath, targetPath);
        System.out.println("is same?"+isSameFile);

        try {
            Files.readSymbolicLink(hardPath);
        } catch (IOException e) {
            System.out.println("this link is not a symbolic link,please check it");
        }
    }
}
