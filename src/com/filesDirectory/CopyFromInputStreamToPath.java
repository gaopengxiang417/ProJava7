package com.filesDirectory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.*;

/**
 * User: wangchen.gpx
 * Date: 13-1-7
 * Time: 下午4:27
 */
public class CopyFromInputStreamToPath {
    public static void main(String[] args) {
        Path fromPath = FileSystems.getDefault().getPath("D:/a.txt");
        Path targetPath = FileSystems.getDefault().getPath("D:/tar.txt");

        try(InputStream inputStream = Files.newInputStream(fromPath, StandardOpenOption.READ)) {
            Files.copy(inputStream, targetPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //copy from path to outputstream
        Path path = FileSystems.getDefault().getPath("D:/sss.txt");
        try(OutputStream  outputStream = Files.newOutputStream(path)) {
            Files.copy(fromPath, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
