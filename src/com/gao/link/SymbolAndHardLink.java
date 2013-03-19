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
 * Time: 下午2:02
 */
public class SymbolAndHardLink {
    public static void main(String[] args) {
        Path symbolPath = FileSystems.getDefault().getPath("D:/ss-2");
        Path targetPath = FileSystems.getDefault().getPath("D:\\daily\\BR_BRAND_CRM_gpx_20120214", "brandcrm.properties");

        try {

            BasicFileAttributeView fileAttributeView = Files.getFileAttributeView(targetPath, BasicFileAttributeView.class);
            BasicFileAttributes basicFileAttributes = fileAttributeView.readAttributes();


            System.out.println(Files.isWritable(symbolPath));
            Files.createSymbolicLink(symbolPath,targetPath);



        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
