package com.watchservice;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

/**
 * User: wangchen.gpx
 * Date: 13-1-8
 * Time: 下午3:14
 */
public class WatchServicePath {
    public static void main(String[] args) {
        Path path = FileSystems.getDefault().getPath("D:/");
        try {
            watchPath(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void watchPath(Path path) throws IOException {
        //首先检查路径的有效性
        if (!Files.exists(path)) {
            System.out.println("the directory is not exist!");
            return;
        }
        //创建一个service
        try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
            //对path注册service
            path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
            //进行监控
            while (true) {
                WatchKey watchKey = watchService.take();
                //弹出事件
                List<WatchEvent<?>> watchEvents = watchKey.pollEvents();
                //对事件进行循环
                for (WatchEvent<?> watchEvent : watchEvents) {
                    WatchEvent<Path> event = (WatchEvent<Path>) watchEvent;
                    Path context = event.context();//发生变化的path
                    WatchEvent.Kind<Path> kind = event.kind();
                    int count = event.count();
                    System.out.println(context + ":" + kind + ":" + count);
                }

                //reset
                boolean isReset = watchKey.reset();
                if (!isReset) {
                    break;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
