package com.gao.walkTree;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * User: gaopengxiang
 * Date: 12-4-17
 * Time: 下午2:12
 */
public class DeleteDirctoryOrFileRecusiveTest {
    public static void main(String[] args) {

        Path path = Paths.get("D:\\daily\\git");

        try {
            DeleteVisitor visitor = new DeleteVisitor();
            Files.walkFileTree(path, visitor);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
class DeleteVisitor implements FileVisitor<Path>{

    private boolean deleteFileOrDirectory(Path file){
        boolean flag = false;

        try {
            flag = Files.deleteIfExists(file);
        } catch (IOException e) {
            System.out.println("delete error:"+e.getMessage());
        }
        return flag;
    }
    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        boolean isSuccess = deleteFileOrDirectory(file);
        if(isSuccess){
            System.out.println("success delete file:"+file.getFileName().toString());
        }else{
            System.out.println("not delete file:"+file.getFileName().toString());
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        if(exc != null){
            System.out.println("visit file error:"+exc.getMessage());
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        if(exc != null){
            System.out.println("error in directory:"+exc.getMessage());
        }
        boolean isSuccess = deleteFileOrDirectory(dir);
        if(isSuccess){
            System.out.println("delete directory success:"+dir);
        }else{
            System.out.println("delete directory fail:"+dir);
        }
        return FileVisitResult.CONTINUE;
    }
}
