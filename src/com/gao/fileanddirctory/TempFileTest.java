package com.gao.fileanddirctory;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * User: gaopengxiang
 * Date: 12-4-16
 * Time: 下午6:56
 */
public class TempFileTest {
    public static void main(String[] args) {
        Path path = FileSystems.getDefault().getPath("D:\\daily\\BR_BRAND_CRM_zfb_20111215");

        String prefix = "gaopengxiang";
        String suffix = ".doc";

        try {
            final Path tempFile = Files.createTempFile(null, null);
            System.out.println(tempFile);

            Path tempFile1 = Files.createTempFile(prefix, suffix);
            System.out.println(tempFile1);

            final Path tempFile2 = Files.createTempFile(path, prefix, suffix);
            System.out.println(tempFile2);


            Runtime.getRuntime().addShutdownHook(new Thread(){
                @Override
                public void run() {
                    System.out.println("delete temp file.......");
                    try {
                        Files.delete(tempFile);
                        Files.deleteIfExists(tempFile2);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    System.out.println("complete delete temp file");
                }
            });

            File file = tempFile1.toFile();
            file.deleteOnExit();


            Thread.sleep(10000);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
