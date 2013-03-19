package com.gao.fileanddirctory;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * User: gaopengxiang
 * Date: 12-4-16
 * Time: 下午2:51
 */
public class TempDirctoryAndFile {
    public static void main(String[] args) {
        Path path = FileSystems.getDefault().getPath("D:\\daily\\BR_BRAND_CRM_zfb_20111215");

        try {
            Path tempDirectory= Files.createTempDirectory(path, "gaopengxiang");

            System.out.println(tempDirectory.getFileName());

            Path tempDirectory1 = Files.createTempDirectory(null);
            System.out.println(tempDirectory1);

            Path tempDirectory2 = Files.createTempDirectory("gaopengxiang");
            System.out.println(tempDirectory2);

            String property = System.getProperty("java.io.tmpdir");
            System.out.println(property);
        } catch (IOException e) {
            System.out.println("the error is"+e.getMessage());
        }

    }
}
