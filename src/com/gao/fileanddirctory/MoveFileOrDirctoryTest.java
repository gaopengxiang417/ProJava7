package com.gao.fileanddirctory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

/**
 * User: gaopengxiang
 * Date: 12-4-17
 * Time: 上午9:16
 */
public class MoveFileOrDirctoryTest {
    public static void main(String[] args) {
        Path sourcePath = FileSystems.getDefault().getPath("D:\\daily\\BR_BRAND_CRM_zfb_20111215", "gaopengxiang.txt");

        Path targetPath = FileSystems.getDefault().getPath("D:\\daily\\BR_BRAND_CRM_zfb_20111215");

        try {
            Path huxiaojuan = Files.move(sourcePath, sourcePath.resolveSibling("huxiaojuan.txt"), StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);

            List<String> stringList = Files.readAllLines(huxiaojuan, Charset.defaultCharset());

            for (String s : stringList) {
                System.out.println(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
