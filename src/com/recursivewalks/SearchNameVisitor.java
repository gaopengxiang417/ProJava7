package com.recursivewalks;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * User: wangchen.gpx
 * Date: 13-1-8
 * Time: 上午9:56
 */
public class SearchNameVisitor implements FileVisitor<Path> {
    private Path resultPath;
    private String searchFileName;
    private boolean founded;

    public SearchNameVisitor(String searchFileName) {
        this.searchFileName = searchFileName;
        this.founded = false;
    }

    private void search(Path path) throws IOException {
        if (!searchFileName.isEmpty()) {
            if (path.getFileName().toString().equals(searchFileName)) {
                resultPath = path.toRealPath(LinkOption.NOFOLLOW_LINKS);
                founded = true;
            }
        }
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        System.out.println("******start visit directory..."+dir.getFileName());
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        search(file);
        if (founded == true) {
            System.out.println("founded the file......."+file.toRealPath(LinkOption.NOFOLLOW_LINKS));
            return FileVisitResult.TERMINATE;
       }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        System.out.println(file +" can not be visit");
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
    }
}
