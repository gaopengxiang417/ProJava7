package com.gao.fileanddirctory;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * User: gaopengxiang
 * Date: 12-4-16
 * Time: 下午7:33
 */
public class DeleteFileTest {
    public static void main(String[] args) {
        Path path = FileSystems.getDefault().getPath("D:\\daily\\BR_BRAND_CRM_zfb_20111215", "gaopengxiang8418206352516037661.doc");

        Path path1 = FileSystems.getDefault().getPath("D:\\daily\\BR_BRAND_CRM_zfb_20111215", "gao");
        try {
            Files.delete(path);

            boolean isSuccess = Files.deleteIfExists(path);
            assert isSuccess == false;

            Files.delete(path1);
        } catch (IOException e) {
            System.out.println("delete file fail...,the reason is "+e.getMessage());
        }
    }
}
