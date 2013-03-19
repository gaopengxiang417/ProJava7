package com.randomaccess;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;

/**
 * User: wangchen.gpx
 * Date: 13-1-9
 * Time: 下午3:04
 */
public class SeekableByteChannelAttributeTest {
    public static void main(String[] args) {
        //构造路径
        Path path = FileSystems.getDefault().getPath("/home/a/project/a.txt");
        //构造channel
        try(SeekableByteChannel channel = Files.newByteChannel(path, StandardOpenOption.CREATE,
                StandardOpenOption.WRITE,StandardOpenOption.APPEND)) {
            //构造要写入的对象
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            //传入参数
            byteBuffer.put("cina".getBytes());
            //构建权限
            Set<PosixFilePermission> posixFilePermissions = PosixFilePermissions.fromString("rwx------");
            FileAttribute<Set<PosixFilePermission>> setFileAttribute = PosixFilePermissions.asFileAttribute(posixFilePermissions);
            //写入文件
            channel.write(byteBuffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
