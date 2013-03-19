package com.asynchronous;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * User: wangchen.gpx
 * Date: 13-1-19
 * Time: 上午10:32
 */
public class AsynchronousFileChannelBookTest {
    public static void main(String[] args) {
        //首先构建要读取或者修改的文件的path
        Path path = FileSystems.getDefault().getPath("D:/a.txt");
        //构建要存放的bytebuffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        //打开一个通道
        try(AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.READ,
                StandardOpenOption.WRITE,StandardOpenOption.CREATE)) {
            //读取字符
            Future<Integer> future = fileChannel.read(byteBuffer, 0);
            //等待读取字符完成
            if (!future.isDone()) {
                System.out.println("it is waiting for the complete ....");
            }
            System.out.println("is done :" + future.isDone());
            System.out.println("bytes readed :" + future.get());

            Future<Integer> future1 = fileChannel.write(ByteBuffer.wrap("i hope this is the last line...".getBytes()), fileChannel.size());
            while (!future1.isDone()) {
                System.out.println("second waiting for complete.......");
            }
            System.out.println("bytes writes:"+future1.get());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
