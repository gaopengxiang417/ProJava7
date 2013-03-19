package com.gao.path;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.Properties;

/**
 * User: gaopengxiang
 * Date: 12-4-10
 * Time: 下午4:01
 */
public class PathFirstTest {
    public static void main(String[] args) {

        Path path = Paths.get("D:\\daily\\BR_BRAND_IDO_gaopengxiang_20111020\\ido.properties");

        System.out.println(path.getFileName());

        Path path1 = Paths.get("/ProJava7.iml");
        System.out.println(path1);

        Path path2 = Paths.get("ProJava7.iml");
        System.out.println(path2);


        Path path3 = Paths.get("D:\\daily\\..\\BR_BRAND_IDO_gaopengxiang_20111020\\.\\ido.properties");
        System.out.println(path3);
        System.out.println(path3.normalize());

        Path path4 = Paths.get("D:\\daily", "BR_BRAND_IDO_gaopengxiang_20111020", "ido.properties");
        System.out.println(path4);


//        Path path5 = Paths.get(URI.create("D:\\daily\\BR_BRAND_IDO_gaopengxiang_20111020\\ido.properties"));
//        System.out.println(path5);

        Path path6 = FileSystems.getDefault().getPath("D:\\daily\\BR_BRAND_IDO_gaopengxiang_20111020\\ido.properties");
        System.out.println(path6);

        Path path5 = FileSystems.getDefault().getPath(System.getProperty("user.home"));
        System.out.println(path5);

        Properties properties = System.getProperties();
        Enumeration<Object> elements = properties.elements();
        while (elements.hasMoreElements()) {
            Object o = elements.nextElement();
            System.out.println(o);
        }
    }
}
