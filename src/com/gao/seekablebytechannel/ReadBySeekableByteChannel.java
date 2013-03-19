package com.gao.seekablebytechannel;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * User: gaopengxiang
 * Date: 12-4-19
 * Time: 上午10:17
 */
public class ReadBySeekableByteChannel {
    public static void main(String[] args) {
        Path path = FileSystems.getDefault().getPath("D:\\daily\\BR_BRAND_CRM_gpx_20120214", "brandcrm.properties");

        try {
            SeekableByteChannel seekableByteChannel = Files.newByteChannel(path, StandardOpenOption.READ);
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) Files.size(path));
            int read = seekableByteChannel.read(byteBuffer);
            if(read <= 0){
                System.out.println("read null...");
            }else{
                byteBuffer.flip();
                CharBuffer charBuffer = Charset.defaultCharset().decode(byteBuffer);
                System.out.println(charBuffer.toString());
            }
            seekableByteChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
