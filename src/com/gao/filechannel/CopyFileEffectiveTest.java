package com.gao.filechannel;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.*;

import static java.nio.file.StandardOpenOption.*;
/**
 * User: gaopengxiang
 * Date: 12-4-20
 * Time: 上午10:06
 */
public class CopyFileEffectiveTest {
    public static void deleteFile(Path file){
        try {
            Files.deleteIfExists(file);
        } catch (IOException e) {
            System.out.println("delete file error...");
        }
    }
    public static void main(String[] args) {
        Path from_path = FileSystems.getDefault().getPath("D:\\music\\english", "Need You Now.mp3");
        Path to_path = FileSystems.getDefault().getPath("D:/", "Need You Now.mp3");

        long startTime,eclipseTime;

        deleteFile(to_path);

        //first fileChannel with non-direct buffer
        try(FileChannel from_fileChannel = FileChannel.open(from_path,READ);
            FileChannel to_fileChannel = FileChannel.open(to_path,WRITE,CREATE_NEW)) {

            startTime = System.nanoTime();
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) from_fileChannel.size());
            from_fileChannel.read(byteBuffer);
            byteBuffer.flip();
            to_fileChannel.write(byteBuffer);

            byteBuffer.clear();

            eclipseTime = System.nanoTime() - startTime;
            System.out.println("eclipse time is:"+eclipseTime/1000000000.0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //second fileChannel with direct buffer
        deleteFile(to_path);

        try(FileChannel from_fileChannel = FileChannel.open(from_path,READ);
            FileChannel to_fileChannel = FileChannel.open(to_path,WRITE,CREATE_NEW)) {

            startTime = System.nanoTime();

            ByteBuffer byteBuffer = ByteBuffer.allocateDirect((int) from_fileChannel.size());

            from_fileChannel.read(byteBuffer);
            byteBuffer.flip();
            to_fileChannel.write(byteBuffer);
            byteBuffer.clear();

            eclipseTime = System.nanoTime() - startTime;
            System.out.println("eclipse time is:"+eclipseTime/1000000000.0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //third fileChannel.transferTo
        deleteFile(to_path);

        try(FileChannel from_fileChannel = FileChannel.open(from_path,READ);
            FileChannel to_fileChannel = FileChannel.open(to_path,CREATE_NEW,WRITE)) {

            startTime = System.nanoTime();

            from_fileChannel.transferTo(0, from_fileChannel.size(), to_fileChannel);

            eclipseTime = System.nanoTime() - startTime;
            System.out.println("eclipse time is:"+eclipseTime / 1000000000.0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //fourth fileChannel.transferFrom
        deleteFile(to_path);
        try(FileChannel from_fileChannel = FileChannel.open(from_path,READ);
            FileChannel to_fileChannel = FileChannel.open(to_path,CREATE_NEW,WRITE)) {

            startTime = System.nanoTime();

            to_fileChannel.transferFrom(from_fileChannel, 0, from_fileChannel.size());

            eclipseTime = System.nanoTime() - startTime;
            System.out.println("eclipse time is:"+eclipseTime / 1000000000.0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //fifth fileChannel.map
        deleteFile(to_path);

        try(FileChannel from_fileChannel = FileChannel.open(from_path,READ);
            FileChannel to_fileChannel = FileChannel.open(to_path,WRITE,CREATE_NEW)) {

            startTime = System.nanoTime();

            MappedByteBuffer byteBuffer = from_fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, from_fileChannel.size());

            to_fileChannel.write(byteBuffer);
            byteBuffer.clear();

            eclipseTime = System.nanoTime() - startTime;
            System.out.println("eclipse time is:"+eclipseTime / 1000000000.0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //sixth bufferedStream
        deleteFile(to_path);

        try(BufferedInputStream bufferedInputStream = new BufferedInputStream(Files.newInputStream(from_path,READ));
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(Files.newOutputStream(to_path,WRITE,CREATE_NEW))) {

            startTime = System.nanoTime();

            byte[] bytes = new byte[(int) Files.size(from_path)];
            bufferedInputStream.read(bytes);

            bufferedOutputStream.write(bytes);

            eclipseTime = System.nanoTime() - startTime;
            System.out.println("eclipse time is:"+eclipseTime/1000000000.0);

            bytes = null;
        } catch (IOException e) {
            e.printStackTrace();
        }

        //seventh non-buffered stream
        deleteFile(to_path);

        try(FileInputStream fileInputStream = new FileInputStream(from_path.toFile());
            FileOutputStream fileOutputStream = new FileOutputStream(to_path.toFile())) {

            startTime = System.nanoTime();

            byte[] bytes = new byte[(int) Files.size(from_path)];
            fileInputStream.read(bytes);

            fileOutputStream.write(bytes);

            eclipseTime = System.nanoTime() - startTime;
            System.out.println("eclipse time is:"+eclipseTime/1000000000.0);

            bytes = null;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //eighth Files.copy(Path,path)
        deleteFile(to_path);

        try {
            startTime  = System.nanoTime();
            Files.copy(from_path, to_path);

            eclipseTime = System.nanoTime() - startTime;
            System.out.println("eclipse time is:"+eclipseTime / 1000000000.0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //ninth Files.copy(path,outputStream)
        deleteFile(to_path);

        try(OutputStream outputStream = Files.newOutputStream(to_path)) {
            startTime = System.nanoTime();

            Files.copy(from_path, outputStream);

            eclipseTime = System.nanoTime() - startTime;
            System.out.println("eclispe time is:"+eclipseTime / 1000000000.0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //tenth Files.copy(inputstream,path)

        deleteFile(to_path);
        try(InputStream inputStream = Files.newInputStream(from_path, StandardOpenOption.READ)) {
            startTime = System.nanoTime();

            Files.copy(inputStream, to_path);
            eclipseTime = System.nanoTime() - startTime;
            System.out.println("eclipse time is:"+eclipseTime / 1000000000.0);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
