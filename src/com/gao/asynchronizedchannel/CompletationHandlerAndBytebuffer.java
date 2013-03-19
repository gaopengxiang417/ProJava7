package com.gao.asynchronizedchannel;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * User: gaopengxiang
 * Date: 12-4-21
 * Time: 上午10:21
 */
public class CompletationHandlerAndBytebuffer {
    public static void main(String[] args) {
        CompletionHandler<Integer,ByteBuffer> completionHandler = new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                System.out.println("read size:"+result);
                attachment.flip();
                System.out.println(Charset.defaultCharset().decode(attachment));
                attachment.clear();
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                System.out.println(attachment);
                System.out.println(exc.getMessage());
            }
        };

        Path path = Paths.get("D:/", "ss.txt");

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        try(AsynchronousFileChannel asynchronousFileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.READ)) {
            asynchronousFileChannel.read(byteBuffer,0,byteBuffer,completionHandler);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
