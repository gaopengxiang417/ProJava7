package com.gao.walkTree;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;

/**
 * User: gaopengxiang
 * Date: 12-4-17
 * Time: 上午10:04
 */
public class SearchFileByNameTest {
    public static void main(String[] args) throws IOException {

        Path path = FileSystems.getDefault().getPath("D:\\daily\\BR_BRAND_CRM_zfb_20111215");
        Search search = new Search("pbs.css");

        Files.walkFileTree(path, EnumSet.noneOf(FileVisitOption.class),Integer.MAX_VALUE,search);


    }
}
class Search implements FileVisitor<Path>{
    public String searchedFileName;
    public boolean isFound;

    Search(String searchedFileName) {
        this.searchedFileName = searchedFileName;
        isFound = false;
    }

    public void search(Path path){
        String pathName = path.getFileName().toString();

        if(!pathName.isEmpty() && pathName.equals(searchedFileName)){
            System.out.println("the file has founded,it it in location:"+path.toString());
            this.isFound = true;
        }
    }
    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        System.out.println("start visit dirctory:--------"+dir.getFileName()+"--------------");
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

        search(file);
        if(!isFound){
            return FileVisitResult.CONTINUE;
        }
        return FileVisitResult.TERMINATE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        if(exc != null){
            System.out.println("the exception is:"+exc.getMessage());
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        if (exc != null) {
            System.out.println("the exception is:"+exc.getMessage());
        }
        return FileVisitResult.CONTINUE;
    }
}
