package com.gao.fileanddirctory;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * User: gaopengxiang
 * Date: 12-4-16
 * Time: 下午7:10
 */
public class DeleteOnCloseTest {
    public static void main(String[] args) {
        Path path = FileSystems.getDefault().getPath("D:\\daily\\BR_BRAND_CRM_zfb_20111215");

        try {
            Path tempFile = Files.createTempFile(path, "gao", ".doc");

            try(OutputStream outputStream = Files.newOutputStream(tempFile, StandardOpenOption.DELETE_ON_CLOSE);
                InputStream inputStream = Files.newInputStream(tempFile)) {
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                bufferedWriter.write("gaopengxiang");

                bufferedWriter.flush();
                String str = null;
                while((str = bufferedReader.readLine()) != null ){
                    System.out.println(str);
                }
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
