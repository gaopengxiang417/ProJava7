package com.gao.seekablebytechannel;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * User: gaopengxiang
 * Date: 12-4-19
 * Time: 上午10:42
 */
public class WriteableByteBufferTest {
    public static void main(String[] args) {
        Path path = FileSystems.getDefault().getPath("D:/", "ss.txt");

        try(WritableByteChannel writableByteChannel = Files.newByteChannel(path, StandardOpenOption.WRITE,StandardOpenOption.APPEND)) {

            String str = "second line huxiaojuan";
            ByteBuffer byteBuffer = ByteBuffer.wrap(str.getBytes());

            int write = writableByteChannel.write(byteBuffer);
            if(write <= 0){
                System.out.println("null is written!");
            }else{
                System.out.println(Files.readAllLines(path, Charset.defaultCharset()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
