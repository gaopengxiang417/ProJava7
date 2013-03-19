package com.filesDirectory;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * User: wangchen.gpx
 * Date: 13-1-7
 * Time: 下午3:37
 */
public class CreateTempFileTest {
    public static void main(String[] args) throws IOException {
        //首先可以定义生成的文件的前缀和后缀
        String prefix  = "wangchen";
        String suffix = "suffix";
        //获取临时文件的基本路径
        Path baseDir = FileSystems.getDefault().getPath("D:/");

        //首先我们通过创建临时文件，使用默认的前后缀
        Path tempFile = Files.createTempFile(baseDir,null, null);
        System.out.println(tempFile);
        //使用带有前后最的形式
        Path tempFile1 = Files.createTempFile(baseDir, prefix, suffix);
        System.out.println(tempFile1);


    }
}
