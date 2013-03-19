package com.gao.asynchronizedchannel;

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
 * User: gaopengxiang
 * Date: 12-4-21
 * Time: 下午1:39
 */
public class ExcutorServiceTest {
    public static Set<OpenOption> getOpenSet() {
        final Set<OpenOption> set = new TreeSet<>();
        set.add(StandardOpenOption.READ);
        return set;
    }

    public static void main(String[] args) {
        Path path = FileSystems.getDefault().getPath("D:/ss.txt");

        //new a fixed executorservice for new filechannel
        final int size = 5;
        ExecutorService executorService = Executors.newFixedThreadPool(size);

        List<Future<ByteBuffer>> list = new ArrayList<>();

        try(AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path,getOpenSet(),executorService)) {

            for (int i = 0; i < 50; i++) {
                    Callable<ByteBuffer> task = new Callable<ByteBuffer>() {
                        @Override
                        public ByteBuffer call() throws Exception {
                            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(ThreadLocalRandom.current().nextInt(20, 50));
                            fileChannel.read(byteBuffer, ThreadLocalRandom.current().nextInt(2, 70));
                            return byteBuffer;
                        }
                    };

                Future<ByteBuffer> bufferFuture = executorService.submit(task);
                list.add(bufferFuture);
            }

            executorService.shutdown();

            while(!executorService.isTerminated()){
                System.out.println("please wait executor service shutdown.....");
            }

            System.out.println("\n done,here is the buffers, \n");
            for (Future<ByteBuffer> byteBufferFuture : list) {
                ByteBuffer byteBuffer = byteBufferFuture.get();
                System.out.println("\n\n"+byteBuffer);
                System.out.println("-----------------------");
                byteBuffer.flip();
                System.out.println(Charset.defaultCharset().decode(byteBuffer));
                byteBuffer.clear();
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
