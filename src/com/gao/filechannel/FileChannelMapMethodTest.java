package com.gao.filechannel;

import java.io.IOException;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * User: gaopengxiang
 * Date: 12-4-20
 * Time: 上午9:09
 */
public class FileChannelMapMethodTest {
    public static void main(String[] args) {
        Path path = FileSystems.getDefault().getPath("D:/", "ss.txt");

        MappedByteBuffer mappedByteBuffer = null;
        try(FileChannel fileChannel = FileChannel.open(path, StandardOpenOption.READ,StandardOpenOption.WRITE)) {
            mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE,0,fileChannel.size());

            Charset charset = Charset.forName("utf-8");
            CharsetDecoder charsetDecoder = charset.newDecoder();
            CharBuffer charBuffer = charsetDecoder.decode(mappedByteBuffer);
            System.out.println(charBuffer.toString());

            mappedByteBuffer.clear();

            mappedByteBuffer.put("gaopengxiang".getBytes());
            mappedByteBuffer.force();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
