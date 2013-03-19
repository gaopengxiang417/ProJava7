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
 * Time: 下午1:23
 */
public class MoveFileVisitor implements FileVisitor<Path> {
    private Path from;
    private Path to;

    public MoveFileVisitor(Path from, Path to) {
        this.from = from;
        this.to = to;
    }

    private void moreFile(Path path) {
        try {
            Files.move(path,to.resolve(from.relativize(path)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        Files.copy(dir,to.resolve(from.relativize(dir)));
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        moreFile(file);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        Files.deleteIfExists(dir);
        return FileVisitResult.CONTINUE;
    }
}
