package com.gao.watchService;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

/**
 * User: gaopengxiang
 * Date: 12-4-18
 * Time: 上午9:44
 */
public class WatchServiceTest {
    public static void main(String[] args) {
        Path path = FileSystems.getDefault().getPath("D:\\daily\\BR_BRAND_CRM_zfb_20111215");

        try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
            WatchKey watchKey = path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE);

            while (true) {
                List<WatchEvent<?>> watchEvents = watchKey.pollEvents();
                for (WatchEvent<?> watchEvent : watchEvents) {
                    Path context = (Path) watchEvent.context();
                    int count = watchEvent.count();
                    WatchEvent.Kind<?> kind = watchEvent.kind();
                    if(kind == StandardWatchEventKinds.OVERFLOW){
                        continue;
                    }
                    System.out.println("file is :"+context+",count="+count+",kind="+kind);
                }

                boolean isValid = watchKey.reset();
                if(!isValid){
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
