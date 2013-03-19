package com.second.pathclass;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystems;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * User: wangchen.gpx
 * Date: 13-1-5
 * Time: 上午10:23
 */
public class ConvertPath {
    public static void main(String[] args) {
        //首先来获取路径
        String pathString = "G:\\idea_workspace\\gaotest1\\src\\com\\gao\\FirstTest.java";
        Path path = FileSystems.getDefault().getPath(pathString);

        //将路径转化为字符串
        String str = path.toString();
        System.out.println(str);

        //转化为URI
        URI uri = path.toUri();
        System.out.println(uri);

        //这里是首先构建一个相对路径的Path，然后根据该相对路径来获取绝对路径，这里我们相对的时我们的workspace
        String relativePathString = "src\\com\\second\\pathclass";
        Path relaticePath = Paths.get(relativePathString);
        Path absoultePath = relaticePath.toAbsolutePath();
        System.out.println(absoultePath);

        //.这里如果是本来就是绝对的路径，那么就会直接返回，不需要构建新的绝对路径
        System.out.println(path.toAbsolutePath());

        //对于toAbsolutePath他不会关心具体的路径是否存在，因此他操作的不是具体的，而是抽象层次的
        String notExistPathString = "G:\\idea_workspace\\gaotest1\\src\\com\\gao\\FirstTest2.java";
        Path notExistPath = FileSystems.getDefault().getPath(notExistPathString);
        System.out.println(notExistPath);
        System.out.println(notExistPath.toAbsolutePath());

        //但是toRealPath他会对文件或者目录进行实际的检查，如果不存在就会抛出异常
        try {
            Path realPath = notExistPath.toRealPath(LinkOption.NOFOLLOW_LINKS);
        } catch (IOException e) {
            System.out.println("some wrong happens");
            System.out.println(e);
        }

        //如果是相对路径，那么他会返回绝对的路径
        String existRelativePathString = "src\\com\\second\\pathclass";
        Path existRelativePath = FileSystems.getDefault().getPath(existRelativePathString);
        try {
            Path realPathRelatice = existRelativePath.toRealPath(LinkOption.NOFOLLOW_LINKS);
            System.out.println(realPathRelatice);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //转化为File对象，
        File file = path.toFile();
        System.out.println("path.tostring :" + path.toString());
        System.out.println("file.tostring"+file.toString());

    }
}
