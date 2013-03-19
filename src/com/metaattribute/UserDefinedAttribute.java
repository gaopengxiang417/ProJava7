package com.metaattribute;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.List;
import java.util.Set;

/**
 * User: wangchen.gpx
 * Date: 13-1-6
 * Time: 下午3:17
 */
public class UserDefinedAttribute {
    public static void main(String[] args) throws IOException {
        Path path = Paths.get("D:/");
        FileStore fileStore = Files.getFileStore(path);

        //检查该分区是否支持自定义的这个属性
        boolean b = fileStore.supportsFileAttributeView(UserDefinedFileAttributeView.class);
        System.out.println(b);

        //列出系统所支持的所有的view的类型
        Set<String> strings = FileSystems.getDefault().supportedFileAttributeViews();
        System.out.println(strings);

        //添加一个自定义的view
        Path path1 = Paths.get("D:/a.txt");
        UserDefinedFileAttributeView fileAttributeView = Files.getFileAttributeView(path, UserDefinedFileAttributeView.class);
        int gaotest = fileAttributeView.write("gaotest", Charset.defaultCharset().encode("user defined value"));
        System.out.println("written : "+gaotest);

        //列出自定义的属性的名称
        List<String> list = fileAttributeView.list();
        System.out.println(list);

        //获取的属性的值
        int size = fileAttributeView.size("gaotest");
        ByteBuffer allocate = ByteBuffer.allocate(size);
        int gaotest1 = fileAttributeView.read("gaotest", allocate);
        allocate.flip();
        System.out.println(Charset.defaultCharset().decode(allocate));

        //delete
        try {
            fileAttributeView.delete("gao");
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
