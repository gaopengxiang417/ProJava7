package com.gao.walkTree;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;

/**
 * User: gaopengxiang
 * Date: 12-4-17
 * Time: 下午3:55
 */
public class CopyDirectoryAndFileTest {
    public static void main(String[] args) {

        Path fromPath = FileSystems.getDefault().getPath("D:\\project");
        Path toPath = FileSystems.getDefault().getPath("D:\\project_copy");

        CopyVisitor copyVisitor = new CopyVisitor(fromPath, toPath);

        try {
            Files.walkFileTree(fromPath,copyVisitor);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
class CopyVisitor implements FileVisitor<Path>{
    private Path fromPath;
    private Path toPath;

    CopyVisitor(Path fromPath, Path toPath) {
        this.fromPath = fromPath;
        this.toPath = toPath;
    }

    public void copyFile(Path fromFile,Path toFile) {
        try {
            Files.copy(fromFile, toFile, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES);
            System.out.println("copy file success:"+toFile.getFileName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        System.out.println("start copy directory:"+dir.getFileName());
        Path resolve = toPath.resolve(fromPath.relativize(dir));
        try {
            Files.copy(fromPath, resolve, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES);
        } catch (IOException e) {
            System.out.println("exception is occur:"+e.getMessage());
            return FileVisitResult.SKIP_SUBTREE;
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        System.out.println("copy file:"+file.getFileName().toString());
        copyFile(file,toPath.resolve(fromPath.relativize(file)));
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        if(exc != null){
            System.out.println("exception is:"+exc.getMessage());
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        System.out.println("completed copy directory:"+dir);

        FileTime lastModifiedTime = Files.getLastModifiedTime(dir, LinkOption.NOFOLLOW_LINKS);

        Path realPath = toPath.resolve(fromPath.relativize(dir));
        Files.setLastModifiedTime(realPath, lastModifiedTime);
        return FileVisitResult.CONTINUE;
    }
}
