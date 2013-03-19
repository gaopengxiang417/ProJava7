package com.recursivewalks;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.util.ArrayList;
import java.util.List;

/**
 * User: wangchen.gpx
 * Date: 13-1-8
 * Time: 上午9:35
 */
public class MySimpleFileVisitor extends SimpleFileVisitor<Path> {
    List<Path> result = new ArrayList<Path>();
    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        if (exc != null) {
            throw exc;
        }
        System.out.println("visit file failed:"+file);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        if (exc != null) {
            throw exc;
        }
        System.out.println(dir);
        result.add(dir);
        return FileVisitResult.CONTINUE;
    }

    public List<Path> getResult() {
        return result;
    }
}
