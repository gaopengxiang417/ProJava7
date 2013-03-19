package com.filesDirectory;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * User: wangchen.gpx
 * Date: 13-1-7
 * Time: 下午3:19
 */
public class DeleteTempDirectoryOne {
    public static void main(String[] args) throws IOException, InterruptedException {
        //首先创建要存放临时目录的路径
        Path path = FileSystems.getDefault().getPath("D:/");
        //新建临时目录
        final Path tempDirectory = Files.createTempDirectory(path, "wangchen");
        //添加一个hook程序用来对在jvm退出的时候删除临时目录
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("system begin delete temp directory");
                try(DirectoryStream<Path> directoryStream = Files.newDirectoryStream(tempDirectory)) {
                    for (Path path1 : directoryStream) {
                        Files.delete(path1);
                    }
                    System.out.println("complete delete sub directory");
                    Files.delete(tempDirectory);
                    System.out.println("compete delete temp directory");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }));

        Thread.sleep(1000);

        //deleteOnExit
        Path tempDirectory1 = Files.createTempDirectory(path, "linux-china");
        File file = tempDirectory1.toFile();
        Thread.sleep(1000);
        file.deleteOnExit();
    }
}
