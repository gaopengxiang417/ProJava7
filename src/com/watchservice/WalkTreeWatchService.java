package com.watchservice;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;

/**
 * User: wangchen.gpx
 * Date: 13-1-8
 * Time: 下午3:38
 */
public class WalkTreeWatchService {
    public static void main(String[] args) {
        Path path = FileSystems.getDefault().getPath("D:\\project");
        try {
            watchPath(path);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private static WatchService watchService;
    private static Map<WatchKey, Path> map = new HashMap<>();
    //对目录注册事件
    public static void registerPath(Path path) throws IOException {
        WatchKey watchKey = path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_MODIFY,
                ENTRY_DELETE);
        map.put(watchKey, path);
    }

    public static void registerTree(Path path) throws IOException {
        Files.walkFileTree(path,new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                registerPath(dir);
                System.out.println("register watch:"+dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    public static void watchPath(Path path) throws IOException, InterruptedException {
        watchService = FileSystems.getDefault().newWatchService();
        registerTree(path);

        while (true) {
            WatchKey watchKey = watchService.take();
            List<WatchEvent<?>> watchEvents = watchKey.pollEvents();
            for (WatchEvent<?> watchEvent : watchEvents) {
                WatchEvent<Path> event = (WatchEvent<Path>) watchEvent;

                Path context = event.context();
                int count = event.count();
                WatchEvent.Kind<Path> kind = event.kind();

                if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
                    Path path1 = map.get(watchKey);
                    Path child = path1.resolve(context);
                    if (Files.isDirectory(child)) {
                        registerTree(child);
                    }
                }
                System.out.println(count +":"+kind +":"+context);
            }
            boolean reset = watchKey.reset();
            if (!reset) {
                map.remove(watchKey);
            }
        }
    }
}
