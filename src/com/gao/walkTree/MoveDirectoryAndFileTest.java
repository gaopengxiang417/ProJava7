package com.gao.walkTree;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;

/**
 * User: gaopengxiang
 * Date: 12-4-17
 * Time: 下午4:16
 */
public class MoveDirectoryAndFileTest {
    public static void main(String[] args) {

        Path fromPath = FileSystems.getDefault().getPath("D:\\project");
        Path toPath = FileSystems.getDefault().getPath("D:\\project_move");

        MoveVisitor moveVisitor = new MoveVisitor(fromPath, toPath);

        try {
            Files.walkFileTree(fromPath, moveVisitor);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
class MoveVisitor implements FileVisitor<Path>{
    private Path fromPath;
    private Path toPath;
    private FileTime modifiedTime = null;

    MoveVisitor(Path fromPath, Path toPath) {
        this.fromPath = fromPath;
        this.toPath = toPath;
    }

    public void moveSubTree(Path from,Path to){
        try {
            Files.move(from,to, StandardCopyOption.ATOMIC_MOVE,StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        Path resolve = toPath.resolve(fromPath.relativize(dir));
        Files.copy(dir, resolve,StandardCopyOption.ATOMIC_MOVE,StandardCopyOption.REPLACE_EXISTING);
        modifiedTime = Files.getLastModifiedTime(dir, LinkOption.NOFOLLOW_LINKS);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        Path newPath = toPath.resolve(fromPath.relativize(file));
        moveSubTree(file,newPath);
        newPath = null;
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        if(exc != null){
            System.out.println("error:"+exc.getMessage());
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        Path newPath = toPath.resolve(fromPath.relativize(dir));
        Files.setLastModifiedTime(newPath, modifiedTime);

        Files.delete(dir);
        return FileVisitResult.CONTINUE;
    }
}
