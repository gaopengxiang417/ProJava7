package com.gao.fileanddirctory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * User: gaopengxiang
 * Date: 12-4-17
 * Time: 上午8:51
 */
public class CopyPathToPathTest {
    public static void main(String[] args) {

        Path sourcePath = FileSystems.getDefault().getPath("D:\\daily\\BR_BRAND_CRM_zfb_20111215", "brandcrm.properties");
        Path targetPath = FileSystems.getDefault().getPath("D:\\daily\\BR_BRAND_CRM_zfb_20111215", "gaopengxiang.txt");

        try(InputStream inputStream = Files.newInputStream(sourcePath)){

            long copy = Files.copy(inputStream, targetPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("copy success");

            System.out.println("total bytes is "+copy);

            BufferedReader bufferedReader = Files.newBufferedReader(targetPath, Charset.defaultCharset());
            String str = null;
            while((str = bufferedReader.readLine()) != null){
                System.out.println(str);
            }

            BasicFileAttributeView fileAttributeView = Files.getFileAttributeView(targetPath, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
            BasicFileAttributes basicFileAttributes = fileAttributeView.readAttributes();
            System.out.println("lastModifiedTime:"+basicFileAttributes.lastModifiedTime());
            System.out.println("createTime:"+basicFileAttributes.creationTime());
        }catch(IOException e){
            e.printStackTrace();
        }

        /*try {
            Path result = Files.copy(sourcePath, targetPath, StandardCopyOption.COPY_ATTRIBUTES);

            System.out.println("copy is success ,let's see it content");

            BufferedReader bufferedReader = Files.newBufferedReader(result, Charset.defaultCharset());
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                System.out.println(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }
}
