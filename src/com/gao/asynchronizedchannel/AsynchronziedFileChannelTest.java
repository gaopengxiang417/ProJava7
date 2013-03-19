package com.gao.asynchronizedchannel;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * User: gaopengxiang
 * Date: 12-4-21
 * Time: 上午9:51
 */
public class AsynchronziedFileChannelTest {
    public static void main(String[] args) {
        Path path = FileSystems.getDefault().getPath("D:/", "ss.txt");

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        try(AsynchronousFileChannel asynchronousFileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.READ)) {
            Future<Integer> future = asynchronousFileChannel.read(byteBuffer, 0);
            for(;;){
                if(future.isDone())
                    break;
                System.out.println("reading is running,please wait.......");
            }

            Integer bytes = future.get();
            System.out.println(bytes);

            byteBuffer.flip();
            System.out.println(Charset.defaultCharset().decode(byteBuffer));
            byteBuffer.clear();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
