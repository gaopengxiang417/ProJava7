package com.gao.path;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.List;

/**
 * User: gaopengxiang
 * Date: 12-4-15
 * Time: 上午9:45
 */
public class UserDefiniedViewTeset {
    public static void main(String[] args) throws IOException {

        Path path = FileSystems.getDefault().getPath("D:\\daily\\BR_BRAND_CRM_gpx_20120214", "brandcrm.properties");


        FileStore fileStore = Files.getFileStore(path);

        boolean isSupported = fileStore.supportsFileAttributeView(UserDefinedFileAttributeView.class);

        System.out.println(isSupported);

        UserDefinedFileAttributeView fileAttributeView = Files.getFileAttributeView(path, UserDefinedFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);

        int locationSize = fileAttributeView.write("location", Charset.defaultCharset().encode("I live in china,but I hate is"));

        int gaoSize = fileAttributeView.write("gao", Charset.defaultCharset().encode("my name is gaopengxiang"));

        System.out.println("locationSize:"+locationSize+" gaoSize:"+gaoSize);
//        Files.setAttribute(path,"")

        List<String> list = fileAttributeView.list();
        ByteBuffer byteBuffer = null;
        for (String str : list) {
            int size = fileAttributeView.size(str);
            System.out.println(str+":size="+ size);
            byteBuffer = ByteBuffer.allocate(size);
            fileAttributeView.read(str,byteBuffer);
            byteBuffer.flip();
            System.out.println(Charset.defaultCharset().decode(byteBuffer));
        }


        fileAttributeView.delete("gao");
    }
}
