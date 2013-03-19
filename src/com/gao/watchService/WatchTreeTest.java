package com.gao.watchService;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static java.nio.file.StandardWatchEventKinds.*;

/**
 * User: gaopengxiang
 * Date: 12-4-18
 * Time: 上午10:01
 */
public class WatchTreeTest {
    public static void main(String[] args) {

        Path path = Paths.get("D:\\project");
        WatchFactory watchFactory = new WatchFactory();
        watchFactory.startMethod(path);
    }
}
class WatchFactory{
    private WatchService watchService;
    private Map<WatchKey,Path> directories = new HashMap();


    public void startMethod(Path path){
        try {
            watchService = FileSystems.getDefault().newWatchService();
            walkTree(path);

            while(true){
                WatchKey watchKey = watchService.take();
                List<WatchEvent<?>> watchEvents = watchKey.pollEvents();
                for (WatchEvent<?> watchEvent : watchEvents) {
                    Path fileName = (Path) watchEvent.context();
                    WatchEvent.Kind<?> kind = watchEvent.kind();

                    if(kind == StandardWatchEventKinds.OVERFLOW)
                        continue;

                    if(kind == ENTRY_CREATE){
                        Path subPath = directories.get(watchKey);
                        Path childDiectory = subPath.resolve(fileName);
                        if(Files.isDirectory(childDiectory,LinkOption.NOFOLLOW_LINKS)){
                            walkTree(childDiectory);
                        }
                    }
                    System.out.println(kind+";"+fileName);
                }
                boolean isValid = watchKey.reset();
                if(!isValid) {
                    directories.remove(watchKey);
                    if (directories.isEmpty()) {
                        break;
                    }
                }
            }
            watchService.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void walkTree(Path path){
        try {
            Files.walkFileTree(path,new SimpleFileVisitor<Path>(){

                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    System.out.println("register:"+dir);
                    registerPath(dir);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void registerPath(Path path){
        try {
            WatchKey watchKey = path.register(watchService, ENTRY_CREATE, ENTRY_MODIFY, ENTRY_DELETE);
            directories.put(watchKey, path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
