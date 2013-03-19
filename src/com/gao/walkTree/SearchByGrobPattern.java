package com.gao.walkTree;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;

/**
 * User: gaopengxiang
 * Date: 12-4-17
 * Time: 下午1:12
 */
public class SearchByGrobPattern {
    public static void main(String[] args) {

        Path path = FileSystems.getDefault().getPath("D:\\daily\\BR_BRAND_CRM_zfb_20111215");

        try {
            Files.walkFileTree(path, EnumSet.noneOf(FileVisitOption.class), Integer.MAX_VALUE, new SearchVisitor("*.properties",100L));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
class SearchVisitor implements FileVisitor<Path>{
    private PathMatcher pathMatcher;
    private long size;
    public SearchVisitor(String grob,long size) {
        pathMatcher = FileSystems.getDefault().getPathMatcher("glob:" + grob);
        this.size = size;
    }

    public void search(Path file) throws IOException {
        String fileName = file.getFileName().toString();
        long tempSize = Files.size(file);
        if(pathMatcher.matches(file) && tempSize <= this.size){
            System.out.println("find matched file:"+fileName);
        }
    }
    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        System.out.println("----------start visit dirctory:---"+dir.getFileName().toString());
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

        search(file);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        if(exc != null){
            System.out.println(exc.getMessage());
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        System.out.println("---------------end dirctory:"+dir.getFileName());
        return FileVisitResult.CONTINUE;
    }
}
