package com.watchservice;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * User: wangchen.gpx
 * Date: 13-1-8
 * Time: 下午2:39
 */
public class WatchServiceStepTest {
    public static void main(String[] args) throws IOException, InterruptedException {
        //首先new一个service
        WatchService watchService = FileSystems.getDefault().newWatchService();
        //然后构造一个路径
        Path path = FileSystems.getDefault().getPath("D:/");
        //注册监听事件
        path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE,
                StandardWatchEventKinds.ENTRY_MODIFY);
        while (true) {
            WatchKey watchKey = watchService.poll(1000, TimeUnit.SECONDS);
            if (watchKey.isValid()) {
                Watchable watchable = watchKey.watchable();
                System.out.println("target :"+watchable);
                List<WatchEvent<?>> watchEvents = watchKey.pollEvents();
                for (WatchEvent<?> watchEvent : watchEvents) {
                    System.out.println(watchEvent.kind()+":"+watchEvent.context()+":"+watchEvent.count());
                }

                boolean isReset = watchKey.reset();
                if (!isReset) {
                    break;
                }
            }
            watchService.close();
        }
    }
}
