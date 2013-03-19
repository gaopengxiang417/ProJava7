package com.asynchronous;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * User: wangchen.gpx
 * Date: 13-1-18
 * Time: 下午3:41
 */
public class AsynchronousFileChannelTest {
    public static void main(String[] args) {
        //首先获取一个path
        Path path = FileSystems.getDefault().getPath("D:/a.txt");
        final ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        //打开一个通道
        try(AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.READ,
                StandardOpenOption.WRITE,StandardOpenOption.SYNC)) {
            //进行调用它的功能函数
            System.out.println(fileChannel.size());

            if (fileChannel.isOpen()) {
                Future<Integer> future = fileChannel.read(byteBuffer, 0);
                //等待接受消息
                while (!future.isDone()) {
                    System.out.println("echo waiting............");
                }
                Thread.sleep(1000);
                //接受到消息以后
                Integer count = future.get();
                if (count != -1) {
                    //说明已经读取到了数据
                    byteBuffer.flip();
                    System.out.println(Charset.forName("utf-8").decode(byteBuffer).toString());
                    byteBuffer.clear();
                }


                //second
                fileChannel.read(byteBuffer,0,null,new CompletionHandler<Integer, Object>() {
                    @Override
                    public void completed(Integer result, Object attachment) {
                        if (result != null && result != -1) {
                            byteBuffer.flip();
                            System.out.println(Charset.defaultCharset().decode(byteBuffer).toString());
                            byteBuffer.clear();
                        }else{
                            System.out.println("end of file ");
                        }
                    }

                    @Override
                    public void failed(Throwable exc, Object attachment) {
                        System.out.println(exc.getStackTrace());
                    }
                });

                Future<Integer> future1 = fileChannel.write(ByteBuffer.wrap("last line this is future".getBytes()), fileChannel.size());
                if (future1.isDone()) {
                    Integer integer = future1.get();
                    System.out.println(integer);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
