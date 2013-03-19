package com.gao.fileanddirctory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * User: gaopengxiang
 * Date: 12-4-16
 * Time: 上午9:45
 */
public class FileTest {
    public static void main(String[] args) {
        Path path = FileSystems.getDefault().getPath("D:\\daily\\BR_BRAND_CRM_zfb_20111215\\gaopengxiang\\first.txt");

        /*try {
            boolean directory = Files.isDirectory(path);
            if(directory){
                System.out.println("this is a directory ,can not be created by createFile");
                return;
            }
            Path file = Files.createFile(path);
            System.out.println(path);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        String str = "this book is very good,intellij is such a perfect IDE that I almost" +
                "dot use other IDE,such as eclipse and netBean .";
        byte[] bytes = str.getBytes();
        try {
            Path write = Files.write(path, bytes);
            byte[] bytes1 = Files.readAllBytes(path);
            String s = new String(bytes1);
            System.out.println(s);

            List<String> strings = Files.readAllLines(path, Charset.defaultCharset());
            System.out.println(strings);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String> lines = new ArrayList<>();
        lines.add("first line is the difference between intellij and eclipse");
        lines.add("\\u000A");
        lines.add("second thrid line is why we should use intellij ,not eclipse");
        lines.add("how about netbeans?");

        try {
            Files.write(path, lines, Charset.defaultCharset(), StandardOpenOption.APPEND,StandardOpenOption.CREATE);
            List<String> strs = Files.readAllLines(path, Charset.defaultCharset());
            System.out.println(strs);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
