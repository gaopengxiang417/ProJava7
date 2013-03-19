package com.gao.asynchronizedchannel;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * User: gaopengxiang
 * Date: 12-4-21
 * Time: 上午10:15
 */
public class CompetitionHandler {
    public static void main(String[] args) {
        Path path = FileSystems.getDefault().getPath("D:/", "ss.txt");

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        try(AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.READ)) {

            fileChannel.read(byteBuffer,0,path.getFileName().toString(), new CompletionHandler<Integer, Object>() {
                @Override
                public void completed(Integer result, Object attachment) {
                    System.out.println("this read is completed right...");
                    System.out.println("read bytes size is:"+result);
                    System.out.println("file name is:"+attachment);
                }

                @Override
                public void failed(Throwable exc, Object attachment) {
                    System.out.println("read is in error:"+exc.getMessage());
                }
            });

            System.out.println("read end.........");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
