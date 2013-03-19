package com.gao.fileanddirctory;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.concurrent.TimeUnit;

/**
 * User: gaopengxiang
 * Date: 12-4-16
 * Time: 上午8:33
 */
public class DirctoryTest {
    public static void main(String[] args) throws IOException {

        /*Iterable<Path> rootDirectories = FileSystems.getDefault().getRootDirectories();

        ArrayList<Path> paths = new ArrayList<Path>();
        Path[] arrayList = new Path[paths.size()];
        for (Path rootDirectory : rootDirectories) {
            System.out.println(rootDirectory);
            paths.add(rootDirectory);
        }

        paths.toArray(arrayList);*/


        /*Path path = FileSystems.getDefault().getPath("D:\\daily\\BR_BRAND_CRM_zfb_20111215\\gaopengxiang.txt");

        Path directory = Files.createDirectory(path);

        Set<PosixFilePermission> posixFilePermissions = PosixFilePermissions.fromString("rwxr-x---");
        FileAttribute<Set<PosixFilePermission>> setFileAttribute = PosixFilePermissions.asFileAttribute(posixFilePermissions);

        System.out.println(directory);*/

        /*Path path = Paths.get("D:\\daily\\BR_BRAND_CRM_zfb_20111215\\gaopengxiang\\first.txt");
        Path directories = Files.createDirectories(path);
        System.out.println(directories);*/


        Path path = FileSystems.getDefault().getPath("D:\\daily\\BR_BRAND_CRM_zfb_20111215");

        /*try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(path)) {
            for (Path paths : directoryStream) {
                System.out.println(path.resolve(paths));
            }
        } catch (IOException e) {
            System.err.println(e);
        }*/


        /*try(DirectoryStream<Path> directoryStream = Files.newDirectoryStream(path, "*.properties")){
            for (Path paths : directoryStream) {
                System.out.println(path.relativize(paths));
            }
        }catch(IOException e){
            System.err.println(e.getMessage());
        }*/
        try(DirectoryStream<Path> directoryStream = Files.newDirectoryStream(path,new DirectoryStream.Filter<java.nio.file.Path>() {
            @Override
            public boolean accept(Path entry) throws IOException {
//                boolean directory = Files.isDirectory(entry, LinkOption.NOFOLLOW_LINKS);
//                return directory;
                /*if(Files.size(entry) > 2312L)
                    return false;
                return true;*/

                long from = FileTime.from(System.currentTimeMillis(), TimeUnit.MILLISECONDS).to(TimeUnit.DAYS);

                BasicFileAttributeView fileAttributeView = Files.getFileAttributeView(entry, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
                BasicFileAttributes basicFileAttributes = fileAttributeView.readAttributes();
                long to = basicFileAttributes.creationTime().to(TimeUnit.DAYS);
                if(from == to)
                    return true;
                return false;
            }
        })){

            for (Path paths : directoryStream) {
                System.out.println(path.relativize(paths));
            }
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }
}
