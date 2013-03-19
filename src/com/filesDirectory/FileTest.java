package com.filesDirectory;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

/**
 * User: wangchen.gpx
 * Date: 13-1-7
 * Time: 下午1:40
 */
public class FileTest {
    public static void main(String[] args) throws IOException {
        //创建一个文件
        //我们首先要构造一个path
        String first = "D:/a/b/c.txt";
        Path path = FileSystems.getDefault().getPath(first);
        //创建文件,这里的父路径不存在,所以他会抛出错误
        try {
            Path file = Files.createFile(path);
        } catch (IOException e) {
            System.out.println(e);
        }

       //所以下面的做法是正常的
        //首先获取他的父路径
        Path parent = path.getParent();
        //如果父路径不存在，那么就创建
        Path directories = Files.createDirectories(parent);

        //判断该路径下的文件是否存在
        boolean exists = Files.exists(path);
        if (!exists) {
            Files.createFile(path);
        }
        System.out.println("------------华丽的分割线------------");

        //向文件中写入内容
        Path path1 = Paths.get("D:/ss.txt");
        String str = " this is a test string ,         高鹏翔,china";
        //开始向文件写入
        Files.write(path1, str.getBytes(Charset.defaultCharset()), StandardOpenOption.CREATE,
                StandardOpenOption.WRITE,StandardOpenOption.TRUNCATE_EXISTING);
        byte[] bytes = Files.readAllBytes(path1);
        System.out.println(new String(bytes,Charset.defaultCharset()));

        //向文件中写入多行数据，相当于自动的填充了行分隔符
        //首先构造要填充的数据列表
        List<String> list = new ArrayList();
        list.add("first line");
        list.add("second line");
        list.add("third line");
        //开始写入数据,我们这里是已附加的形式写入导之前的文件中
        Files.write(path1, list, Charset.forName("utf-8"), StandardOpenOption.APPEND);

        List<String> strings = Files.readAllLines(path1, Charset.forName("utf-8"));
        System.out.println(strings);

        System.out.println("-------------华丽的分割线------------");
        //读取一个小文件内容的方法，这里要注意是小文件，
        //首先第一种方法是通过读取所有的内容到一个字节数组中
        //首先我们应该判断指定额文件是否存在
        if (Files.exists(path1)) {
            byte[] bytes1 = Files.readAllBytes(path1);
            System.out.println(new String(bytes1,Charset.forName("utf-8")));
        }
        System.out.println();

        //第二种是读取所有的内容到一个字符串的list中，方便进行循环
        if (Files.exists(path1)) {
            List<String> strings1 = Files.readAllLines(path1,Charset.defaultCharset());
            System.out.println(strings1);
        }

        //注意上面提到的所有的读取和写入到文件的方法都是针对向文件的，如果是大文件不应该采用这样的方法

        //采用buffer的方法来进行数据的写入
        //我们这里采用的是append的模式进行写入
        try(BufferedWriter bufferedWriter = Files.newBufferedWriter(path1, Charset.defaultCharset(),StandardOpenOption.APPEND)){
            bufferedWriter.write("computer is a very wonderful machine");
        }

        System.out.println("--------------华丽的分割线-------------");
        //采用buffer的方式来读取数据
        try(BufferedReader bufferedReader = Files.newBufferedReader(path1, Charset.defaultCharset())){
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
        }

        //采用没有缓冲的输出流进行数据的写入，可以用于处理大文件
        //首先要构造path对象,注意这个文件是不存在的
        Path path2 = FileSystems.getDefault().getPath("D:/tart.txt");
        //要写入的String
        String str1 = "华丽的分割线，非常感谢";
        try (OutputStream outputStream = Files.newOutputStream(path2, StandardOpenOption.CREATE
                , StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
             BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream))) {
                bufferedWriter.write(str1);
        } catch (Exception e) {
            System.out.println(e);
        }

        //读取刚才写入的数据，这里有三种方法
        //第一种，直接一个字节一个字节的读取
        try (InputStream inputStream = Files.newInputStream(path2, StandardOpenOption.READ)) {
            int value = -1;
            while ((value = inputStream.read()) != -1) {
                System.out.print((char) value);
            }
            System.out.println();
            System.out.println("-------------华丽的分割线-------------");
        } catch (Exception e) {
            System.out.println(e);
        }
        //第二种，将读取的数据写入到一个字节数组中
        try(InputStream inputStream = Files.newInputStream(path2, StandardOpenOption.READ)) {
            byte[] buff = new byte[1024];
            int count = 0;
            while ((count = inputStream.read(buff)) != -1) {
                System.out.print(new String(buff, Charset.defaultCharset()));
            }
            System.out.println("------------华丽的分割线----------");
        }

        //创建临时目录的方法，第一种没有指定存放的目录，也就是存放到系统的临时目录里面
        //首先我们可以输出系统的临时目录
        System.out.println(System.getProperty("java.io.tmpdir"));
        //创建创建临时目录
        Path tempDirectory = Files.createTempDirectory("wangchen-tmp");
        System.out.println(tempDirectory);
        //在指定的目录创建临时目录
        Path tempDirectory1 = Files.createTempDirectory(Paths.get("D:/"), "wangchen-");
        System.out.println(tempDirectory1);


    }
}
