package com.second.pathclass;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * User: wangchen.gpx
 * Date: 13-1-5
 * Time: 下午2:10
 */
public class RelativeTest {
    public static void main(String[] args) {
        //首先构建要比较的两个路径，这里我们的路径是都是绝对路径
        String pathStringOne = "G:\\idea_workspace\\ProJava7\\src\\com\\second\\pathclass\\ConvertPath.java";
        String pathStringTwo = "G:\\idea_workspace\\ProJava7\\src\\com\\second\\pathclass\\DefineClassTest.java";

        Path pathOne = FileSystems.getDefault().getPath(pathStringOne);
        Path pathTwo = FileSystems.getDefault().getPath(pathStringTwo);

        Path relativize = pathOne.relativize(pathTwo);
        System.out.println(relativize);
        System.out.println(pathTwo.relativize(pathOne));

        //这里的两个路径是都是绝对路径并且他们的深度不一样
        String pathStringThree = "G:\\idea_workspace\\ProJava7\\src\\com\\";
        String pathStringFour = "G:\\idea_workspace\\ProJava7\\src\\com\\second\\pathclass\\ConvertPath.java";
        Path pathThree = Paths.get(pathStringThree);
        Path pathFour = Paths.get(pathStringFour);
        System.out.println(pathThree.relativize(pathFour));
        System.out.println(pathFour.relativize(pathThree));

        //都含有绝对路径，但是在不同的根路径下
        Path pathFive = FileSystems.getDefault().getPath("/a/b/c");
        Path pathSix = FileSystems.getDefault().getPath("/f/g/c");
        System.out.println(pathFive.relativize(pathSix));
        System.out.println(pathSix.relativize(pathFive));

        //如果其中有一个不是绝对路径，那么会抛出异常，因为这样没法进行路径的计算
        Path pathSeven = FileSystems.getDefault().getPath("a/b/c");
        Path pathEight = FileSystems.getDefault().getPath("/d/f");
        System.out.println(pathSeven.relativize(pathEight));
        System.out.println(pathEight.relativize(pathSeven));

        //因此，这里如下：
        //1 。 两个路径必须全部都是绝对路径或者都是相对路径
        //2. 这里的含义是第一个路径如何进入第二个路径，因此就是第一个相对于第二个的路径
    }
}
