package com.gao.walkTree;

import java.io.IOException;
import java.nio.file.*;
import java.util.EnumSet;

/**
 * User: gaopengxiang
 * Date: 12-4-17
 * Time: 上午9:42
 */
public class FileVisitorTest {
    public static void main(String[] args) {
        for(FileVisitResult fileVisitResult : FileVisitResult.values()){
            System.out.println(fileVisitResult);
        }


        Path path = FileSystems.getDefault().getPath("D:\\daily\\BR_BRAND_CRM_zfb_20111215");

        try {
            listDirctory visitor = new listDirctory();
            Path path1 = Files.walkFileTree(path, EnumSet.noneOf(FileVisitOption.class), Integer.MAX_VALUE, visitor);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
class listDirctory extends SimpleFileVisitor<Path>{
    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        System.out.println(exc.getMessage());

        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        System.out.println("the dirctory is:"+dir.getFileName());

        return FileVisitResult.CONTINUE;
    }
}
