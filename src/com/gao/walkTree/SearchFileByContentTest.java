package com.gao.walkTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * User: gaopengxiang
 * Date: 12-4-17
 * Time: 下午1:39
 */
public class SearchFileByContentTest {

    public static void main(String[] args) {
        Path path = FileSystems.getDefault().getPath("D:\\daily\\BR_BRAND_CRM_zfb_20111215");

        FileVisitor<Path> fileVisitor = new ContentSearch("card.service.version=1.0.0,ar.role.id=1098",",");

        try {
            Files.walkFileTree(path,fileVisitor);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
class ContentSearch implements FileVisitor<Path>{
    private String searchString;
    private StringTokenizer stringTokenizer;
    private String delimite;

    ContentSearch(String searchString,String delimite) {
        this.searchString = searchString;
        this.delimite = delimite;
        this.stringTokenizer = new StringTokenizer(searchString,delimite);
    }

    public void readText(Path file){
        try(BufferedReader bufferedReader = Files.newBufferedReader(file, Charset.defaultCharset())) {
            String str = null;
            boolean isFount = false;
            outsize:
            while((str = bufferedReader.readLine()) != null){
                isFount = search(str);
                if(isFount){
                    System.out.println("-----------in file :"+file.getFileName().toString()+" fount the string-------------");
                    break outsize;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public boolean search(String content) {
        boolean flag = false;
        List<String> list = new ArrayList<>();
        while(stringTokenizer.hasMoreTokens()){
            list.add(stringTokenizer.nextToken());
        }
        if(content == null)
            return flag;
        for (String s : list) {
            if(content.toLowerCase().contains(s.toLowerCase())){
                flag = true;
                break;
            }
        }
        return flag;
    }
    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        if(file.getFileName().toString().lastIndexOf(".properties") != -1){
            return FileVisitResult.CONTINUE;
        }
        readText(file);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        if(exc != null){
            System.out.println("exception is "+exc.getMessage());
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
    }
}
