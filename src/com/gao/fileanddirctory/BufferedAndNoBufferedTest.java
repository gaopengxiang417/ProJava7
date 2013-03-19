package com.gao.fileanddirctory;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * User: gaopengxiang
 * Date: 12-4-16
 * Time: 上午10:16
 */
public class BufferedAndNoBufferedTest {
    public static void main(String[] args) {
        Path path = FileSystems.getDefault().getPath("D:\\daily\\BR_BRAND_CRM_zfb_20111215\\gaopengxiang\\first.txt");

        /*String str = "gaopengxiang";
        try(BufferedWriter bufferedWriter = Files.newBufferedWriter(path, Charset.defaultCharset());
            BufferedReader bufferedReader = Files.newBufferedReader(path,Charset.defaultCharset())){

            bufferedWriter.write(str);
            bufferedWriter.flush();

            String s = null;
            while((s = bufferedReader.readLine()) != null){
                System.out.println(s);
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }*/

        String strs = "gaopengxiang,huxiaojuan";
        try(OutputStream outputStream = Files.newOutputStream(path);
            InputStream inputStream = Files.newInputStream(path);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader)
            ) {

            outputStream.write(strs.getBytes());
            outputStreamWriter.write("second line gaopengxiang");
            bufferedWriter.write("thied thank you very mech");

            byte[] bytes = new byte[1024];
            inputStream.read(bytes);
            System.out.println(new String(bytes));

            char[] chars = new char[1024];
            inputStreamReader.read(chars);
            System.out.println(String.valueOf(chars));

            String s = null;
            while ((s = bufferedReader.readLine()) != null) {
                System.out.println(s);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
