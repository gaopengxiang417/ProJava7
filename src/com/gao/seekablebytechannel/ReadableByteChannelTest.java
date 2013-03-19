package com.gao.seekablebytechannel;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * User: gaopengxiang
 * Date: 12-4-19
 * Time: 上午10:37
 */
public class ReadableByteChannelTest {
    public static void main(String[] args) {
        Path path = Paths.get("D:\\", "ss.txt");
        try(ReadableByteChannel readableByteChannel = Files.newByteChannel(path)) {

            ByteBuffer byteBuffer = ByteBuffer.allocate(10);
            while(readableByteChannel.read(byteBuffer) > 0){
                byteBuffer.flip();
                System.out.print(Charset.defaultCharset().decode(byteBuffer));
                byteBuffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
