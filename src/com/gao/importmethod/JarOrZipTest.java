package com.gao.importmethod;

import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * User: gaopengxiang
 * Date: 12-4-21
 * Time: 下午7:09
 */
public class JarOrZipTest {
    public static void main(String[] args) {
        Map<String,String> env = new HashMap<>();

        env.put("create", "false");
        env.put("encoding", "ISO-8859-1");

        URI uri = URI.create("jar:file:/D:/ss.zip");

        try(FileSystem fileSystem = FileSystems.newFileSystem(uri,env)) {
            Path in_path = fileSystem.getPath("/ss.txt");
            Path out_path = fileSystem.getPath("D:/");

            Files.copy(in_path, out_path.resolve(in_path.getFileName()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
