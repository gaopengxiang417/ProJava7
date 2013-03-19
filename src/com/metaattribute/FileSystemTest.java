package com.metaattribute;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.spi.FileSystemProvider;
import java.util.Set;

/**
 * User: wangchen.gpx
 * Date: 13-1-6
 * Time: 下午3:36
 */
public class FileSystemTest {
    public static void main(String[] args) throws IOException {
        FileSystem fileSystem = FileSystems.getDefault();
        //获取provider
        FileSystemProvider provider = fileSystem.provider();
        System.out.println(provider);

        //close方法,因为这里是default的system，所以他是无法关闭的，这里会抛出异常
        try {
            fileSystem.close();
        } catch (UnsupportedOperationException e) {
            System.out.println(e);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //isopen，默认的system永远是开的，所以应该返回true
        boolean open = fileSystem.isOpen();
        System.out.println(open);

        //isreadonly      测试是否是只读的
        System.out.println(fileSystem.isReadOnly());

        //获取路径的分割福
        System.out.println(fileSystem.getSeparator());

        //获取root的路囧
        Iterable<Path> rootDirectories = fileSystem.getRootDirectories();
        for (Path rootDirectory : rootDirectories) {
            System.out.println(rootDirectory);
        }

        //支持的views
        Set<String> strings = fileSystem.supportedFileAttributeViews();
        System.out.println(strings);

        //pathmatcher
        PathMatcher pathMatcher = fileSystem.getPathMatcher("regex:\\*\\.java");
        Path path = Paths.get("/a/v/c/d.java");
        boolean matches = pathMatcher.matches(path);
        System.out.println(matches);

        //watchservice
        WatchService watchService = fileSystem.newWatchService();
        System.out.println(watchService);

    }
}
