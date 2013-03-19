package com.asynchronous;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.*;

/**
 * User: wangchen.gpx
 * Date: 13-1-19
 * Time: 下午1:48
 */
public class FileChannelAndExcutorServiceTest {
    static int SIZE = 5;
    public static void main(String[] args) {
        //首先创建excutors
        ExecutorService executorService = Executors.newFixedThreadPool(SIZE);
        //构建paht
        Path path = FileSystems.getDefault().getPath("D:/a.txt");
        //获取次数
        int sleep = 0;
        //获取编码类型
        String encode = System.getProperty("file.encoding");
        List<Future<ByteBuffer>> list = new ArrayList();

        //打开通道
        try(AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, getOptions(),executorService)) {
            //构建线程
            for (int i = 0; i < 50; i++) {
                Callable<ByteBuffer> callable = new Callable<ByteBuffer>() {
                    @Override
                    public ByteBuffer call() throws Exception {
                        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(ThreadLocalRandom.current().nextInt(100, 200));
                        fileChannel.read(byteBuffer, ThreadLocalRandom.current().nextInt(0, 2000));
                        return byteBuffer;
                    }
                };
                Future<ByteBuffer> future = executorService.submit(callable);
                list.add(future);
            }

            //关闭
            executorService.shutdown();
            //判断是否执行完成
            while (!executorService.isTerminated()) {
                System.out.println("waiting to shutdown........."+(sleep++));
            }

            //已经完成
            for (Future<ByteBuffer> byteBufferFuture : list) {
                if (byteBufferFuture.isDone()) {
                    ByteBuffer byteBuffer = byteBufferFuture.get();
                    byteBuffer.flip();
                    System.out.println(Charset.forName(encode).decode(byteBuffer).toString());
                    byteBuffer.clear();
                    byteBuffer = null;
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
    public static Set<OpenOption> getOptions() {
        Set<OpenOption> result = new TreeSet();
        result.add(StandardOpenOption.READ);
        result.add(StandardOpenOption.WRITE);
        return result;
    }
}
