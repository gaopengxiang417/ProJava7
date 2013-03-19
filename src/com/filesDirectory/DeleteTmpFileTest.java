package com.filesDirectory;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * User: wangchen.gpx
 * Date: 13-1-7
 * Time: 下午3:43
 */
public class DeleteTmpFileTest {
    public static void main(String[] args) throws IOException, InterruptedException {
        //首先我们建立一个基本的临时路径
        Path basePath = FileSystems.getDefault().getPath("D:/");
        //创建临时文件
        final Path tempFile = Files.createTempFile(basePath, null, null);
        //注册hook程序，在程序推出的时候删除临时文件
        Runtime runtime = Runtime.getRuntime();
        System.out.println(runtime.availableProcessors());
        System.out.println((runtime.totalMemory()/ (1024*1024*1024)));
        System.out.println((runtime.freeMemory()/ (1024*1024*1024)));

        runtime.addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Files.delete(tempFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }));

        //暂停
        Thread.sleep(1000);

        //第二种删除文件的方法
        //首先我们创建一个文件
        String location = "D:/a/b/c.txt";
        Path path = FileSystems.getDefault().getPath(location);
        //获取他的父路径来创建不存在的父文件
        Path parent = path.getParent();
        if (!Files.exists(parent)) {
            Files.createDirectories(parent);
        }
        //创建该文件
        if (!Files.exists(path)) {
            Files.createFile(path);
        }
        //进行写文件并且文件关闭的时候删除
        try (OutputStream outputStream = Files.newOutputStream(path, StandardOpenOption.DELETE_ON_CLOSE)) {
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
