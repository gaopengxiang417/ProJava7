package com.randomaccess.copyfast;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.*;

/**
 * User: wangchen.gpx
 * Date: 13-1-10
 * Time: 上午10:03
 */
public class DifferentCopyTime {
    public static void deletePath(Path path) {
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Path from = FileSystems.getDefault().getPath("D:/u.txt");
        Path to = FileSystems.getDefault().getPath("D:/target.txt");

        //first
        //filechannel and no-directbuffer
        int bufferSize = 1024;
        int capcity = 1024 * 4;

        long start, end;

        try (FileChannel fromChannel = FileChannel.open(from, StandardOpenOption.READ);
             FileChannel toChannel = FileChannel.open(to, StandardOpenOption.WRITE,
                     StandardOpenOption.CREATE)) {
            //构建non_buffer
            start = System.nanoTime();
            ByteBuffer buffer = ByteBuffer.allocate(capcity);
            int count = 0;
            while ((count = fromChannel.read(buffer)) != -1) {
                buffer.flip();
                toChannel.write(buffer);
                buffer.clear();
            }
            end = System.nanoTime();
            System.out.println("file channel and non-direct buffer time : " + ((end -start) / 1000000000.0));
        } catch (IOException e) {
            e.printStackTrace();
        }

        deletePath(to);

        //第二种Filechannel   和 directrbuffer
        try (FileChannel fromChannel = FileChannel.open(from, StandardOpenOption.READ);
             FileChannel toChannel = FileChannel.open(to, StandardOpenOption.WRITE,
                     StandardOpenOption.CREATE)) {
            //kaishi
            start = System.nanoTime();
            //direct buffer
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(capcity);
            int count = 0;
            while ((count = fromChannel.read(byteBuffer)) != -1) {
                byteBuffer.flip();
                toChannel.write(byteBuffer);
                byteBuffer.clear();
            }
            end = System.nanoTime();
            System.out.println("file channel and direct buffer time :" + ((end -start) / 1000000000.0));
        } catch (IOException e) {
            e.printStackTrace();
        }

        deletePath(to);

        //FIlechannel transferto
        try (FileChannel fromChannel = FileChannel.open(from, StandardOpenOption.READ);
             FileChannel toChannel = FileChannel.open(to, StandardOpenOption.WRITE,
                     StandardOpenOption.CREATE)) {
            //start
            start = System.nanoTime();
            long l = fromChannel.transferTo(0, fromChannel.size(), toChannel);
            end = System.nanoTime();
            System.out.println("file channel transferto time : " + ((end -start) / 1000000000.0));
            System.out.println("transfer count :" + l);
        } catch (IOException e) {
            e.printStackTrace();
        }

        deletePath(to);

        //Filechannel transferfrom
        try (FileChannel fromChannel = FileChannel.open(from, StandardOpenOption.READ);
             FileChannel toChannel = FileChannel.open(to, StandardOpenOption.WRITE,
                     StandardOpenOption.CREATE)) {
            start = System.nanoTime();
            long l = toChannel.transferFrom(fromChannel, 0, fromChannel.size());
            end = System.nanoTime();
            System.out.println("file channel transferfrom time : " + ((end -start) / 1000000000.0));
            System.out.println("transfer count :" + l);
        } catch (IOException e) {
            e.printStackTrace();
        }

        deletePath(to);

        //map
        try (FileChannel fromChannel = FileChannel.open(from, StandardOpenOption.READ);
             FileChannel toChannel = FileChannel.open(to, StandardOpenOption.WRITE,
                     StandardOpenOption.CREATE)) {
            start = System.nanoTime();
            MappedByteBuffer map = fromChannel.map(FileChannel.MapMode.READ_ONLY, 0, fromChannel.size());
            int write = toChannel.write(map);
            end = System.nanoTime();
            System.out.println("map medhot time : " + ((end -start) / 1000000000.0));
            System.out.println("transfer count : " + write);
        } catch (IOException e) {
            e.printStackTrace();
        }

        deletePath(to);

        //buffered io
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(from.toFile()));
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(to.toFile()))) {
            start = System.nanoTime();
            byte[] bytes = new byte[bufferSize];
            int count = 0;
            while ((count = bufferedInputStream.read(bytes)) != -1) {
                bufferedOutputStream.write(bytes);
            }
            end = System.nanoTime();
            System.out.println("buffered stream time :" + ((end -start) / 1000000000.0));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        deletePath(to);

        //non buffer
        try(FileInputStream fileInputStream = new FileInputStream(from.toFile());
        FileOutputStream fileOutputStream = new FileOutputStream(to.toFile())) {
            start = System.nanoTime();
            byte[] bytes = new byte[bufferSize];
            int count = 0;
            while ((count = fileInputStream.read(bytes)) != -1) {
                fileOutputStream.write(bytes);
            }
            end = System.nanoTime();
            System.out.println("non buffered stream time : "+((end -start ) / 1000000000.0));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        deletePath(to);
        //copy path to path
        try {
            start = System.nanoTime();

            Files.copy(from,to);

            end  = System.nanoTime();
            System.out.println("copy path to path tine :"+((end -start) / 1000000000.0));
        } catch (IOException e) {
            e.printStackTrace();
        }

        deletePath(to);

        try(InputStream inputStream = Files.newInputStream(from,StandardOpenOption.READ)) {
            start = System.nanoTime();
            Files.copy(inputStream, to);
            end = System.nanoTime();
            System.out.println("copy inputstream to path :"+((end -start) / 1000000000.0));
        } catch (IOException e) {
            e.printStackTrace();
        }

        deletePath(to);

        try(OutputStream outputStream = Files.newOutputStream(to,StandardOpenOption.WRITE,StandardOpenOption.CREATE)) {
            start = System.nanoTime();
            Files.copy(from, outputStream);
            end = System.nanoTime();
            System.out.println("copy from path to outputstream time :"+((end -start ) / 1000000000.0));
        } catch (IOException e) {
            e.printStackTrace();
        }

        deletePath(to);



    }
}
