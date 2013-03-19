package com.recursivewalks;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * User: wangchen.gpx
 * Date: 13-1-8
 * Time: 上午11:45
 */
public class CopyFileVisitor implements FileVisitor<Path> {
    private Path source;
    private Path target;

    public CopyFileVisitor(Path source, Path target) {
        this.source = source;
        this.target = target;
    }

    private void copyFile(Path file) {
        try {
            Files.copy(file,target.resolve(source.relativize(file)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        System.out.println("start copy directory:"+dir);
        copyFile(dir);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        System.out.println("start copy file:"+file);
        copyFile(file);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        System.out.println("fail to copy file"+file);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        System.out.println("complete to copy directory:"+dir);
        return FileVisitResult.CONTINUE;
    }
}
