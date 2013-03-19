package com.gao.seekablebytechannel;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.List;

/**
 * User: gaopengxiang
 * Date: 12-4-19
 * Time: 上午10:28
 */
public class WriteBySeekableByteChannel {
    public static void main(String[] args) {
        Path path = Paths.get("D:\\", "ss.txt");

        String str = "gao pengxiang is a post-grudate student,thank you very much";
        ByteBuffer byteBuffer = ByteBuffer.wrap(str.getBytes());

        try {
            SeekableByteChannel seekableByteChannel = Files.newByteChannel(path, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);

            int size = seekableByteChannel.write(byteBuffer);
            if(size <= 0){
                System.out.println("NULL IS write into channel");
            }else{
                List<String> stringList = Files.readAllLines(path, Charset.defaultCharset());
                System.out.println(stringList);
                stringList = null;
            }
            seekableByteChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
