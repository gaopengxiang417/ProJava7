package com.second.pathclass;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * User: wangchen.gpx
 * Date: 13-1-5
 * Time: 上午9:38
 */
public class DefineClassTest {
    public static void main(String[] args) {
        //通过Paths工具类来创建Path对象
        Path pathOne = Paths.get("E:\\Program Files (x86)\\EditPlus 3\\editplus.exe");

        Path pathTwo = Paths.get("E:\\Program Files (x86)\\EditPlus 3", "editplus.exe");

        Path pathThree = Paths.get("E:", "Program Files (x86)", "EditPlus 3", "editplus.exe");

        Path relativePath = Paths.get(".");
        System.out.println(relativePath.toAbsolutePath());

        Path pathRelative = Paths.get("E:\\Program Files (x86)\\EditPlus 3\\.\\editplus.exe\\..");
        System.out.println(pathRelative.toAbsolutePath());

        System.out.println(pathRelative.normalize().toAbsolutePath());


        Path fileSystemPathOne = FileSystems.getDefault().getPath("E:\\Program Files (x86)\\EditPlus 3", "editplus.exe");

        Path fileSystemPathTwo = FileSystems.getDefault().getPath("E:", "Program Files (x86)", "EditPlus 3", "editplus.exe");

        Path systemPath = FileSystems.getDefault().getPath(System.getProperty("user.home"));
        System.out.println(systemPath.toAbsolutePath());
    }
}
