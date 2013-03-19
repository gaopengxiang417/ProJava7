package com.gao.fileanddirctory;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * User: gaopengxiang
 * Date: 12-4-15
 * Time: 下午3:01
 */
public class FilesAndDirctoryTest {
    public static final String WRONG_MESSAGE = "there is something wrong with this file,please check it<br>";

    public static void main(String[] args) throws IOException {
        Path path = FileSystems.getDefault().getPath("D:\\daily\\BR_BRAND_CRM_zfb_20111215", "brandcrm.properties");
        Path dirctory = FileSystems.getDefault().getPath("D:\\daily\\BR_BRAND_CRM_zfb_20111215");

        /*boolean readable = Files.isReadable(path);
        boolean writable = Files.isWritable(path);
        boolean executable = Files.isExecutable(path);
        boolean regularFile = Files.isRegularFile(path, LinkOption.NOFOLLOW_LINKS);

        boolean isAccessiable = readable && writable && executable && regularFile;
        String resultMessage = isAccessiable ? "this file can exist and accessiable and writable" : "this file is not accessiable";

        System.out.println(resultMessage);*/


        /*boolean existsFile = Files.exists(path);
        boolean notExistFile = Files.notExists(path);

        boolean existdirctory = Files.exists(dirctory);
        boolean notExistDirctory = Files.notExists(dirctory);

        if(!existsFile && !notExistFile){
            System.out.println(WRONG_MESSAGE);
        }else{
            System.out.println(existsFile);
            System.out.println(notExistFile);
        }

        if(!existdirctory && ! notExistDirctory){
            System.out.println(WRONG_MESSAGE);
        }else{
            System.out.println(existdirctory);
            System.out.println(notExistDirctory);
        }*/

       /* Path path1 = FileSystems.getDefault().getPath("E:\\idea_workspace\\first\\src\\com\\gao\\test", "first.java");
        Path path2 = FileSystems.getDefault().getPath("\\idea_workspace\\first\\src\\com\\gao\\test\\.\\first.java");
        Path path3 = FileSystems.getDefault().getPath("E:\\idea_workspace\\first\\test\\..\\src\\com\\gao\\test", "first.java");
        Path path4 = FileSystems.getDefault().getPath("E:\\idea_workspace\\first\\src\\com\\gao\\test");


        System.out.println(Files.isSameFile(path1,path2));
        System.out.println(Files.isSameFile(path2,path3));
        System.out.println(Files.isSameFile(path3,path4));*/

        Path path1 = FileSystems.getDefault().getPath("C:\\Users\\gaopengxiang\\AppData\\Local\\Google\\Chrome\\Application\\chrome.exe");
        System.out.println(Files.isHidden(path1));
    }
}
