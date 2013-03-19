package com.recursivewalks;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

/**
 * User: wangchen.gpx
 * Date: 13-1-8
 * Time: 上午10:24
 */
public class SearchGlobVisitor implements FileVisitor<Path>{

    private PathMatcher pathMatcher;
    private List<Path> resultPathList;

    public SearchGlobVisitor(String globPattern) {
        this.pathMatcher = FileSystems.getDefault().getPathMatcher(globPattern);
        resultPathList = new ArrayList();
    }

    private void search(Path path) {
        if (pathMatcher != null) {
            if (pathMatcher.matches(path.getFileName())) {
                resultPathList.add(path);
            }
        }
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        System.out.println("start visit directory:"+dir);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        search(file);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        System.out.println("end visit directory:"+dir);
        return FileVisitResult.CONTINUE;
    }

    public List<Path> getResultPathList() {
        return resultPathList;
    }
}
