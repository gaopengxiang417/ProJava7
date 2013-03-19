package com.gao.fileanddirctory;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * User: gaopengxiang
 * Date: 12-4-16
 * Time: 下午6:33
 */
public class ShutdownHookTest {
    public static void main(String[] args) throws IOException {
        /*Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                System.out.println("shutdown hook acticitied/");


                System.out.println("shutdown hook completed");
            }
        });*/

        Path path = FileSystems.getDefault().getPath("D:\\daily\\BR_BRAND_CRM_zfb_20111215");

        /*try {
            final Path tempDirectory = Files.createTempDirectory(path, "gaopengxiang");

            Runtime.getRuntime().addShutdownHook(new Thread(){
                @Override
                public void run() {
                    try(DirectoryStream<Path> directoryStream = Files.newDirectoryStream(tempDirectory)){
                        for (Path paths : directoryStream) {
                            Files.delete(paths);
                        }
                        Files.delete(tempDirectory);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    System.out.println("shutdown hook compeleted");
                }
            });
            Thread.sleep(10000);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        Path tempDirectory = Files.createTempDirectory(path, "gaopengxiang");
        File file = tempDirectory.toFile();
        file.deleteOnExit();

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
